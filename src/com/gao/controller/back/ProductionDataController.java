package com.gao.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.ProductionOutWellInfo;
import com.gao.model.User;
import com.gao.model.WellInformation;
import com.gao.model.drive.KafkaConfig;
import com.gao.model.drive.RTUDriveConfig;
import com.gao.model.gridmodel.ProductionOutGridPanelData;
import com.gao.model.gridmodel.WellHandsontableChangedData;
import com.gao.model.gridmodel.WellProHandsontableChangedData;
import com.gao.service.back.ProductionDataManagerService;
import com.gao.service.base.CommonDataService;
import com.gao.task.EquipmentDriverServerTask;
import com.gao.task.KafkaServerTask;
import com.gao.utils.Constants;
import com.gao.utils.EquipmentDriveMap;
import com.gao.utils.Page;
import com.gao.utils.PagingConstants;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * <p>描述：采出井生产数据</p>
 * 
 * @author gao 2014-05-09
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/productionDataController")
@Scope("prototype")
public class ProductionDataController extends BaseController {
	private static Log log = LogFactory.getLog(ProductionDataController.class);
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	private String jbh;
	private String wellName;
	private String limit;
	private List<ProductionOutWellInfo> list;
	private String msg = "";
	private String orgCode;
	private String orgId;
	private ProductionOutWellInfo out;
	private String page;
	private String resCode;
	private String wellType;
	public String getWellType() {
		return wellType;
	}



	public void setWellType(String wellType) {
		this.wellType = wellType;
	}


	@Autowired
	private ProductionDataManagerService<ProductionOutWellInfo> services;
	@Autowired
	private CommonDataService commonDataService;
	

	/**
	 * @return null
	 * @throws IOException
	 * @argument object "no" ,"add"
	 */
	@RequestMapping("/doBzgtBhEdit")
	public String doBzgtBhEdit() throws IOException {
		String result = "";
		PrintWriter out1 = response.getWriter();
		ProductionOutWellInfo pro = new ProductionOutWellInfo();
		try {
			int bzgtbh = ParamUtils.getIntegerParameter(request, "bzgtbh", 1);
			int jbh = ParamUtils.getIntegerParameter(request, "jbh", 1);
			pro.setBzgtbh(bzgtbh);
			pro.setJbh(jbh);
			pro.setRcql(0.0);
			this.services.doBzgtBhEdit(pro);
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			out1.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
			out1.print(result);
		}
		return null;
	}

	
	
	/**
	 * <p>
	 * 描述：油井基本信息Handsontable表格编辑数据保存
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveWellProHandsontableData")
	public String saveWellProHandsontableData() throws Exception {

		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String orgid=user.getUserorgids();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "");
		wellType=ParamUtils.getParameter(request, "wellType");
//		System.out.println(data);
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<WellProHandsontableChangedData>() {}.getType();
		WellProHandsontableChangedData wellProHandsontableChangedData=gson.fromJson(data, type);
		this.services.saveProductionDataEditerGridData(wellProHandsontableChangedData,wellType, orgid);
		
		if("200".equalsIgnoreCase(wellType)){
			downKafkaProductionData(wellProHandsontableChangedData);
		}
		String json ="{success:true}";
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	public String downKafkaProductionData(WellProHandsontableChangedData wellProHandsontableChangedData) throws Exception {
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.initDriverConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		
		KafkaConfig driveConfig=(KafkaConfig)equipmentDriveMap.get("KafkaDrive");
		if(driveConfig!=null){
			if(wellProHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<wellProHandsontableChangedData.getUpdatelist().size();i++){
					if(StringManagerUtils.isNotNull(wellProHandsontableChangedData.getUpdatelist().get(i).getWellName())){
						String sql="select t.drivercode,t.driveraddr from tbl_wellinformation t where t.wellname='"+wellProHandsontableChangedData.getUpdatelist().get(i).getWellName()+"'";
						List list = this.services.findCallSql(sql);
						if(list.size()>0){
							Object[] obj=(Object[]) list.get(0);
							String driverCode=obj[0]==null?"":obj[0].toString();
							String ID=obj[1]==null?"":obj[1].toString();
							if(driverCode.toUpperCase().contains("KAFKA")&&StringManagerUtils.isNotNull(ID)){
								String FluidPVTTopic=driveConfig.getTopic().getDown().getModel_FluidPVT().replace("-ID-", "-"+ID+"-");
								String ReservoirTopic=driveConfig.getTopic().getDown().getModel_Reservoir().replace("-ID-", "-"+ID+"-");
								String RodStringTopic=driveConfig.getTopic().getDown().getModel_RodString().replace("-ID-", "-"+ID+"-");
								String TubingStringTopic=driveConfig.getTopic().getDown().getModel_TubingString().replace("-ID-", "-"+ID+"-");
								String PumpTopic=driveConfig.getTopic().getDown().getModel_Pump().replace("-ID-", "-"+ID+"-");
								String TailtubingStringTopic=driveConfig.getTopic().getDown().getModel_TailtubingString().replace("-ID-", "-"+ID+"-");
								String CasingStringTopic=driveConfig.getTopic().getDown().getModel_CasingString().replace("-ID-", "-"+ID+"-");
								String ProductionTopic=driveConfig.getTopic().getDown().getModel_Production().replace("-ID-", "-"+ID+"-");
								String ManualInterventionTopic=driveConfig.getTopic().getDown().getModel_ManualIntervention().replace("-ID-", "-"+ID+"-");
								float waterCut=0;
								if(!StringManagerUtils.isNotNull(wellProHandsontableChangedData.getUpdatelist().get(i).getBarrelTypeName())){
									wellProHandsontableChangedData.getUpdatelist().get(i).setBarrelTypeName("组合泵");
								}
								if(!StringManagerUtils.isNotNull(wellProHandsontableChangedData.getUpdatelist().get(i).getPumpTypeName())){
									wellProHandsontableChangedData.getUpdatelist().get(i).setPumpTypeName("管式泵");
								}
								
								if((!StringManagerUtils.isNotNull(wellProHandsontableChangedData.getUpdatelist().get(i).getWaterCut()))&&StringManagerUtils.isNotNull(wellProHandsontableChangedData.getUpdatelist().get(i).getWaterCut_W())){
									float waterCut_W=StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getWaterCut_W());
									float waterDensity=StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getWaterDensity());
									float crudeOilDensity=StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getCrudeOilDensity());
									
									if(waterCut_W>0.0001&&crudeOilDensity!=0){
										waterCut=100/(1+waterDensity/crudeOilDensity*(100-waterCut_W)/waterCut_W);
									}
								}else if(StringManagerUtils.isNotNull(wellProHandsontableChangedData.getUpdatelist().get(i).getWaterCut())){
									waterCut=StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getWaterCut());
								}
								
								
								String FluidPVTData="{"
										+ "\"CrudeOilDensity\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getCrudeOilDensity()+","
										+ "\"WaterDensity\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getWaterDensity()+","
										+ "\"NaturalGasRelativeDensity\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getNaturalGasRelativeDensity()+","
										+ "\"SaturationPressure\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getSaturationPressure()+""
										+ "}";
								String ReservoirData="{"
										+ "\"Depth\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getReservoirDepth()+","
										+ "\"Temperature\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getReservoirTemperature()+""
										+ "}";
								String RodStringData="{\"EveryRod\":[";
								if(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength1())>0){
									RodStringData+="{"
											+ "\"Grade\":\""+wellProHandsontableChangedData.getUpdatelist().get(i).getRodGrade1()+"\","
											+ "\"Length\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength1()+","
											+ "\"OutsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter1())*0.001+","
											+ "\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter1())*0.001+""
											+ "}";
									if(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength2())>0){
										RodStringData+=",{"
												+ "\"Grade\":\""+wellProHandsontableChangedData.getUpdatelist().get(i).getRodGrade2()+"\","
												+ "\"Length\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength2()+","
												+ "\"OutsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter2())*0.001+","
												+ "\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter2())*0.001+""
												+ "}";
										if(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength3())>0){
											RodStringData+=",{"
													+ "\"Grade\":\""+wellProHandsontableChangedData.getUpdatelist().get(i).getRodGrade3()+"\","
													+ "\"Length\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength3()+","
													+ "\"OutsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter3())*0.001+","
													+ "\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter3())*0.001+""
													+ "}";
											if(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength4())>0){
												RodStringData+=",{"
														+ "\"Grade\":\""+wellProHandsontableChangedData.getUpdatelist().get(i).getRodGrade4()+"\","
														+ "\"Length\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength4()+","
														+ "\"OutsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter4())*0.001+","
														+ "\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter4())*0.001+""
														+ "}";
											}
										}
									}
								}
								RodStringData+="]}";
								String TubingStringData="{\"EveryTubing\":[{\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getTubingStringInsideDiameter())*0.001+"}]}";
								String CasingStringData="{\"EveryCasing\":[{\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getCasingStringInsideDiameter())*0.001+"}]}";
								
								String ProductionData="{"
										+ "\"WaterCut\":"+waterCut+","
										+ "\"ProductionGasOilRatio\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getProductionGasOilRatio()+","
										+ "\"TubingPressure\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getTubingPressure()+","
										+ "\"CasingPressure\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getCasingPressure()+","
										+ "\"WellHeadFluidTemperature\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getWellHeadFluidTemperature()+","
										+ "\"ProducingfluidLevel\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getProducingfluidLevel()+","
										+ "\"PumpSettingDepth\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getPumpSettingDepth()+","
										+ "\"Submergence\":"+(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getPumpSettingDepth())-StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getProducingfluidLevel()))+""
										+ "}";
								
								String PumpData="{"
										+ "\"PumpType\":\""+("管式泵".equals(wellProHandsontableChangedData.getUpdatelist().get(i).getBarrelTypeName())?"T":"R")  +"\","
										+ "\"BarrelType\":\""+("组合泵".equals(wellProHandsontableChangedData.getUpdatelist().get(i).getBarrelTypeName())?"L":"H")  +"\","
										+ "\"PumpGrade\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getPumpGrade()+","
										+ "\"BarrelLength\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getBarrelLength()+","
										+ "\"PlungerLength\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getPlungerLength()+","
										+ "\"PumpBoreDiameter\":"+(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getUpdatelist().get(i).getPumpBoreDiameter())*0.001)+""
										+ "}";
								
								String ManualInterventionData="{\"NetGrossRatio\":"+wellProHandsontableChangedData.getUpdatelist().get(i).getNetGrossRatio()+"}";
								
								KafkaServerTask.producerMsg(FluidPVTTopic, "下行PVT物性结数据", FluidPVTData);
								KafkaServerTask.producerMsg(ReservoirTopic, "下行油藏数据", ReservoirData);
								KafkaServerTask.producerMsg(RodStringTopic, "下行抽油杆数据", RodStringData);
								KafkaServerTask.producerMsg(TubingStringTopic, "下行油管数据", TubingStringData);
								KafkaServerTask.producerMsg(PumpTopic, "下行泵数据", PumpData);
								KafkaServerTask.producerMsg(CasingStringTopic, "下行套管数据", CasingStringData);
								KafkaServerTask.producerMsg(ProductionTopic, "下行生产参数", ProductionData);
								KafkaServerTask.producerMsg(ManualInterventionTopic, "下行人工干预", ManualInterventionData);
							}
						}
					}
				}
			}
			
			if(wellProHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<wellProHandsontableChangedData.getInsertlist().size();i++){
					if(StringManagerUtils.isNotNull(wellProHandsontableChangedData.getInsertlist().get(i).getWellName())){
						String sql="select t.drivercode,t.driveraddr from tbl_wellinformation t where t.wellname='"+wellProHandsontableChangedData.getInsertlist().get(i).getWellName()+"'";
						List list = this.services.findCallSql(sql);
						if(list.size()>0){
							Object[] obj=(Object[]) list.get(0);
							String driverCode=obj[0].toString();
							String ID=obj[1].toString();
							if(driverCode.toUpperCase().contains("KAFKA")&&StringManagerUtils.isNotNull(ID)){
								String FluidPVTTopic=driveConfig.getTopic().getDown().getModel_FluidPVT().replace("-ID-", "-"+ID+"-");
								String ReservoirTopic=driveConfig.getTopic().getDown().getModel_Reservoir().replace("-ID-", "-"+ID+"-");
								String RodStringTopic=driveConfig.getTopic().getDown().getModel_RodString().replace("-ID-", "-"+ID+"-");
								String TubingStringTopic=driveConfig.getTopic().getDown().getModel_TubingString().replace("-ID-", "-"+ID+"-");
								String PumpTopic=driveConfig.getTopic().getDown().getModel_Pump().replace("-ID-", "-"+ID+"-");
								String TailtubingStringTopic=driveConfig.getTopic().getDown().getModel_TailtubingString().replace("-ID-", "-"+ID+"-");
								String CasingStringTopic=driveConfig.getTopic().getDown().getModel_CasingString().replace("-ID-", "-"+ID+"-");
								String ProductionTopic=driveConfig.getTopic().getDown().getModel_Production().replace("-ID-", "-"+ID+"-");
								String ManualInterventionTopic=driveConfig.getTopic().getDown().getModel_ManualIntervention().replace("-ID-", "-"+ID+"-");
								float waterCut=0;
								if(!StringManagerUtils.isNotNull(wellProHandsontableChangedData.getInsertlist().get(i).getBarrelTypeName())){
									wellProHandsontableChangedData.getInsertlist().get(i).setBarrelTypeName("组合泵");
								}
								if(!StringManagerUtils.isNotNull(wellProHandsontableChangedData.getInsertlist().get(i).getPumpTypeName())){
									wellProHandsontableChangedData.getInsertlist().get(i).setPumpTypeName("管式泵");
								}
								
								if((!StringManagerUtils.isNotNull(wellProHandsontableChangedData.getInsertlist().get(i).getWaterCut()))&&StringManagerUtils.isNotNull(wellProHandsontableChangedData.getInsertlist().get(i).getWaterCut_W())){
									float waterCut_W=StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getWaterCut_W());
									float waterDensity=StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getWaterDensity());
									float crudeOilDensity=StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getCrudeOilDensity());
									
									if(waterCut_W>0.0001&&crudeOilDensity!=0){
										waterCut=100/(1+waterDensity/crudeOilDensity*(100-waterCut_W)/waterCut_W);
									}
								}else if(StringManagerUtils.isNotNull(wellProHandsontableChangedData.getInsertlist().get(i).getWaterCut())){
									waterCut=StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getWaterCut());
								}
								
								String FluidPVTData="{"
										+ "\"CrudeOilDensity\":"+wellProHandsontableChangedData.getInsertlist().get(i).getCrudeOilDensity()+","
										+ "\"WaterDensity\":"+wellProHandsontableChangedData.getInsertlist().get(i).getWaterDensity()+","
										+ "\"NaturalGasRelativeDensity\":"+wellProHandsontableChangedData.getInsertlist().get(i).getNaturalGasRelativeDensity()+","
										+ "\"SaturationPressure\":"+wellProHandsontableChangedData.getInsertlist().get(i).getSaturationPressure()+""
										+ "}";
								String ReservoirData="{"
										+ "\"Depth\":"+wellProHandsontableChangedData.getInsertlist().get(i).getReservoirDepth()+","
										+ "\"Temperature\":"+wellProHandsontableChangedData.getInsertlist().get(i).getReservoirTemperature()+""
										+ "}";
								String RodStringData="{\"EveryRod\":[";
								if(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodLength1())>0){
									RodStringData+="{"
											+ "\"Grade\":\""+wellProHandsontableChangedData.getInsertlist().get(i).getRodGrade1()+"\","
											+ "\"Length\":"+wellProHandsontableChangedData.getInsertlist().get(i).getRodLength1()+","
											+ "\"OutsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodOutsideDiameter1())*0.001+","
											+ "\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodInsideDiameter1())*0.001+""
											+ "}";
									if(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodLength2())>0){
										RodStringData+=",{"
												+ "\"Grade\":\""+wellProHandsontableChangedData.getInsertlist().get(i).getRodGrade2()+"\","
												+ "\"Length\":"+wellProHandsontableChangedData.getInsertlist().get(i).getRodLength2()+","
												+ "\"OutsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodOutsideDiameter2())*0.001+","
												+ "\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodInsideDiameter2())*0.001+""
												+ "}";
										if(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodLength3())>0){
											RodStringData+=",{"
													+ "\"Grade\":\""+wellProHandsontableChangedData.getInsertlist().get(i).getRodGrade3()+"\","
													+ "\"Length\":"+wellProHandsontableChangedData.getInsertlist().get(i).getRodLength3()+","
													+ "\"OutsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodOutsideDiameter3())*0.001+","
													+ "\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodInsideDiameter3())*0.001+""
													+ "}";
											if(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodLength4())>0){
												RodStringData+=",{"
														+ "\"Grade\":\""+wellProHandsontableChangedData.getInsertlist().get(i).getRodGrade4()+"\","
														+ "\"Length\":"+wellProHandsontableChangedData.getInsertlist().get(i).getRodLength4()+","
														+ "\"OutsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodOutsideDiameter4())*0.001+","
														+ "\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getRodInsideDiameter4())*0.001+""
														+ "}";
											}
										}
									}
								}
								RodStringData+="]}";
								String TubingStringData="{\"EveryTubing\":[{\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getTubingStringInsideDiameter())*0.001+"}]}";
								String CasingStringData="{\"EveryCasing\":[{\"InsideDiameter\":"+StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getCasingStringInsideDiameter())*0.001+"}]}";
								
								String ProductionData="{"
										+ "\"WaterCut\":"+waterCut+","
										+ "\"ProductionGasOilRatio\":"+wellProHandsontableChangedData.getInsertlist().get(i).getProductionGasOilRatio()+","
										+ "\"TubingPressure\":"+wellProHandsontableChangedData.getInsertlist().get(i).getTubingPressure()+","
										+ "\"CasingPressure\":"+wellProHandsontableChangedData.getInsertlist().get(i).getCasingPressure()+","
										+ "\"WellHeadFluidTemperature\":"+wellProHandsontableChangedData.getInsertlist().get(i).getWellHeadFluidTemperature()+","
										+ "\"ProducingfluidLevel\":"+wellProHandsontableChangedData.getInsertlist().get(i).getProducingfluidLevel()+","
										+ "\"PumpSettingDepth\":"+wellProHandsontableChangedData.getInsertlist().get(i).getPumpSettingDepth()+","
										+ "\"Submergence\":"+(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getPumpSettingDepth())-StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getProducingfluidLevel()))+""
										+ "}";
								
								String PumpData="{"
										+ "\"PumpType\":\""+("管式泵".equals(wellProHandsontableChangedData.getInsertlist().get(i).getBarrelTypeName())?"T":"R")  +"\","
										+ "\"BarrelType\":\""+("组合泵".equals(wellProHandsontableChangedData.getInsertlist().get(i).getBarrelTypeName())?"L":"H")  +"\","
										+ "\"PumpGrade\":"+wellProHandsontableChangedData.getInsertlist().get(i).getPumpGrade()+","
										+ "\"BarrelLength\":"+wellProHandsontableChangedData.getInsertlist().get(i).getBarrelLength()+","
										+ "\"PlungerLength\":"+wellProHandsontableChangedData.getInsertlist().get(i).getPlungerLength()+","
										+ "\"PumpBoreDiameter\":"+(StringManagerUtils.stringToFloat(wellProHandsontableChangedData.getInsertlist().get(i).getPumpBoreDiameter())*0.001)+""
										+ "}";
								
								String ManualInterventionData="{\"NetGrossRatio\":"+wellProHandsontableChangedData.getInsertlist().get(i).getNetGrossRatio()+"}";
								
								KafkaServerTask.producerMsg(FluidPVTTopic, "下行PVT物性结数据", FluidPVTData);
								KafkaServerTask.producerMsg(ReservoirTopic, "下行油藏数据", ReservoirData);
								KafkaServerTask.producerMsg(RodStringTopic, "下行抽油杆数据", RodStringData);
								KafkaServerTask.producerMsg(TubingStringTopic, "下行油管数据", TubingStringData);
								KafkaServerTask.producerMsg(PumpTopic, "下行泵数据", PumpData);
								KafkaServerTask.producerMsg(CasingStringTopic, "下行套管数据", CasingStringData);
								KafkaServerTask.producerMsg(ProductionTopic, "下行生产参数", ProductionData);
								KafkaServerTask.producerMsg(ManualInterventionTopic, "下行人工干预", ManualInterventionData);
							}
						}
					}
				}
			}
		}
		return null;
	}

	@RequestMapping("/findProductionDataByJhList")
	public String findProductionDataByJhList() throws Exception {
		String orgCode = ParamUtils.getParameter(request, "orgCode");
		list = this.services.fingProductionDataByJhList(orgCode);
		String json = "";
		StringBuffer sbf = new StringBuffer();
		sbf.append("{");
		sbf.append("\"totals\":" + 10000).append(" ,\"list\":[");
		for (int i = 0; i < list.size(); i++) {
			sbf.append(" {\"jh\":\"" + list.get(i)).append("\"},");
			log.debug(" Jh list = " + list.get(i) + " , ");
		}
		sbf.append("]}");
		json = sbf.toString();
		log.debug(" json = " + json);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	/**
	 * <p>描述：判断当前井名是否已经已经存在了</p>
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/judgeWellExistOrNot")
	public String judgeWellExistOrNot() throws IOException {
		String jbh = ParamUtils.getParameter(request, "jbh");
		boolean flag = this.services.judgeWellExistsOrNot(jbh);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		StringBuffer dynSbf = new StringBuffer();
		try {
			if (flag) {
				dynSbf.append("{success:true,msg:'1'}");
			} else {
				dynSbf.append("{success:true,msg:'0'}");
			}

			String json = "";
			json = dynSbf.toString();
			pw.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/loadProductionOutWellID")
	public String loadProductionOutWellID() throws Exception {
		// TODO Auto-generated method stub
		List<?> list = this.services.loadproductionOutWellID(ProductionOutWellInfo.class);
		log.debug("loadProductionInWellID list==" + list.size());
		WellInformation op = null;
		List<WellInformation> olist = new ArrayList<WellInformation>();
		for (int i = 0; i < list.size(); i++) {
			// 使用对象数组
			Object[] objArray = (Object[]) list.get(i);
			// 最后使用forEach迭代obj对象
			op = new WellInformation();
			op.setJlbh((Integer) objArray[0]);
			op.setJh(objArray[1].toString());
			olist.add(op);
		}
		Gson g = new Gson();
		String json = g.toJson(olist);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	/**
	 * <p>描述：下拉菜单三级联动共用Action数据方法</p>
	 * 
	 * @author gao 2014-05-09
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryProduceOutDataParams")
	public String queryProduceOutDataParams() throws Exception {
		String orgCode = ParamUtils.getParameter(request, "orgCode");
		String resCode = ParamUtils.getParameter(request, "resCode");
		String type = ParamUtils.getParameter(request, "type");
		String json = this.services.queryProduceOutDataInfoParams(orgCode, resCode, type);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	/**<p>描述：获取举升类型的下拉菜单树形数据方法</p>
	 * 
	 * @return null
	 * @throws Exception
	 */
	@RequestMapping("/showLiftTypeTree")
	public String showLiftTypeTree() throws Exception {
		String json = this.services.showLiftTypeTree();
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}

	/** <p>描述：显示采出井生产数据grid 信息</p>
	 * 
	 * @author gao 2014-05-09
	 * @return
	 */
	@RequestMapping("/showProductionOutData")
	public String showProductionOutData() {
		Map<String, Object> map = new HashMap<String, Object>();
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		wellName = ParamUtils.getParameter(request, "wellName");
		orgId=ParamUtils.getParameter(request, "orgId");
		wellType=ParamUtils.getParameter(request, "wellType");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount")) ;
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("wellName", wellName);
		map.put("orgCode", orgCode);
		map.put("resCode", resCode);
		map.put("orgId", orgId);
		this.pager = new Page("pagerForm", request);// 分页Page 工具类
		String json = this.services.getProductionWellProductionData(wellName,orgId,pager,recordCount,wellType);
//		log.warn("showProductionOutData=-= " + json);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/exportWellProdInformationData")
	public String exportWellProdInformationData() throws Exception {
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		//wellInformationName = new String(wellInformationName.getBytes("iso-8859-1"), "utf-8");
//		String orgId=this.findCurrentUserOrgIdInfo("");
		wellName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "wellName"),"utf-8");
		orgId=ParamUtils.getParameter(request, "orgId");
		String wellType=ParamUtils.getParameter(request, "wellType");
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);// 新疆分页Page 工具类
		String json = this.services.exportWellProdInformationData(wellName,orgId,pager,wellType);
		
		
		this.commonDataService.exportGridPanelData(response,fileName,title, heads, fields,json);
		return null;
	}

	/**
	 * <p>
	 * 描述：加载采出井生产数据的下拉菜单数据信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadMenuTypeData")
	public String loadMenuTypeData() throws Exception {
		String type = ParamUtils.getParameter(request, "type");
		String json = this.services.loadMenuTypeData(type);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * <p>
	 * 描述：加载采出井生产数据的躯替类型下拉菜单数据信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadQtlxTypeData")
	public String loadQtlxTypeData() throws Exception {
		String type = ParamUtils.getParameter(request, "type");
		String json = this.services.loadQtlxTypeData(type);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	public String getEndDate() {
		return endDate;
	}

	public String getJbh() {
		return jbh;
	}

	public String getWellName() {
		return wellName;
	}

	public String getLimit() {
		return limit;
	}

	public List<ProductionOutWellInfo> getList() {
		return list;
	}

	public String getMsg() {
		return msg;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public ProductionOutWellInfo getOut() {
		return out;
	}

	public String getPage() {
		return page;
	}

	public String getResCode() {
		return resCode;
	}

	public String getStartDate() {
		return startDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setJbh(String jbh) {
		this.jbh = jbh;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void setList(List<ProductionOutWellInfo> list) {
		this.list = list;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOut(ProductionOutWellInfo out) {
		this.out = out;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}



}
