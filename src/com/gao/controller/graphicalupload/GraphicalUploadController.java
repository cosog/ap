package com.gao.controller.graphicalupload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.gao.controller.base.BaseController;
import com.gao.model.calculate.PCPCalculateRequestData;
import com.gao.model.calculate.PCPCalculateResponseData;
import com.gao.model.calculate.RPCCalculateResponseData;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.model.User;
import com.gao.model.calculate.EnergyCalculateResponseData;
import com.gao.model.calculate.FSDiagramModel;
import com.gao.model.calculate.WellAcquisitionData;
import com.gao.service.base.CommonDataService;
import com.gao.service.graphicalupload.GraphicalUploadService;
import com.gao.tast.KafkaServerTast;
import com.gao.tast.KafkaServerTast.KafkaUpData;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.Constants;
import com.gao.utils.DataModelMap;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import oracle.sql.CLOB;

/**<p>描述：后台管理-报警设置Action</p>
 * 
 * @author gao 2014-06-06
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/graphicalUploadController")
@Scope("prototype")
public class GraphicalUploadController extends BaseController {
	private static Log log = LogFactory.getLog(GraphicalUploadController.class);
	private static final long serialVersionUID = -281275682819237996L;
	private List<File> SurfaceCardFile;
	private List<String> SurfaceCardFileFileName;// 上传文件的名字 ,FileName 固定的写法  
	private List<String> SurfaceCardFileContentType ; //上传文件的类型， ContentType 固定的写法
	
	
	
//	private DistreteAlarmLimit limit;

	@Autowired
	private GraphicalUploadService<?> raphicalUploadService;
	@Autowired
	private CommonDataService commonDataService;
	
	/**<p>描述：显示功图类型列表</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSurfaceCardTypeList")
	public String getSurfaceCardTypeList() throws Exception {
		String json = "";
		this.pager = new Page("pagerForm", request);
		json = raphicalUploadService.getSurfaceCardTrpeList();
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getKafkaConfigWellList")
	public String getKafkaConfigWellList() throws Exception {
		String json = "";
		String orgId = ParamUtils.getParameter(request, "orgId");
		String wellName = ParamUtils.getParameter(request, "wellName");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = raphicalUploadService.getKafkaConfigWellList(orgId,wellName);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**<p>描述：保存上传功图文件</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
	@RequestMapping("/saveUploadSurfaceCardFile")
	public String saveUploadSurfaceCardFile() throws Exception {
		Map<String, Object> map = DataModelMap.getMapObject();
		Map<String,String> surfaceCardFileMap=(Map<String, String>) map.get("surfaceCardFileMap");//从内存中将上传的功图取出来
		String json = "{success:true,flag:true}";
		String uploadSurfaceCardListStr = ParamUtils.getParameter(request, "uploadSurfaceCardListStr");
		String uploadAll = ParamUtils.getParameter(request, "uploadAll");
		Gson gson=new Gson();
		java.lang.reflect.Type type = null;
		if(surfaceCardFileMap!=null){
			if("1".equals(uploadAll)){
				for(String key : surfaceCardFileMap.keySet()){
					try{
						String diagramData =surfaceCardFileMap.get(key);
						type = new TypeToken<FSDiagramModel>() {}.getType();
						FSDiagramModel FSDiagramModel = gson.fromJson(diagramData, type);
						raphicalUploadService.saveSurfaceCard(FSDiagramModel);
					}catch(Exception e){
						continue;
					}	
				}
				
			}else{
				String uploadSurfaceCardListStrArr[]=uploadSurfaceCardListStr.split(",");
				for(int i=0;i<uploadSurfaceCardListStrArr.length;i++){
					try{
						for(String key : surfaceCardFileMap.keySet()){
							if(key.equals(uploadSurfaceCardListStrArr[i])){
								String diagramData =surfaceCardFileMap.get(key);
								type = new TypeToken<FSDiagramModel>() {}.getType();
								FSDiagramModel FSDiagramModel = gson.fromJson(diagramData, type);
								raphicalUploadService.saveSurfaceCard(FSDiagramModel);
							}
						}
					}catch(Exception e){
						continue;
					}
				}
			}
		}
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSurfaceCardGraphicalData")
	public String getSurfaceCardGraphicalData() throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		Map<String,String> surfaceCardFileMap=(Map<String, String>) map.get("surfaceCardFileMap");//从内存中将上传的功图取出来
		String json = "{}";
		String param = ParamUtils.getParameter(request, "param");
		if(surfaceCardFileMap!=null){
			for(String key : surfaceCardFileMap.keySet()){
				if(key.equals(param)){
					json =surfaceCardFileMap.get(key);
					break;
				}
			}
		}
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**<p>描述：解析上传功图文件</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUploadSurfaceCardFile")
	public String getUploadSurfaceCardFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String surfaceCardType = ParamUtils.getParameter(request, "surfaceCardType");
		String json="";
		if("101".equals(surfaceCardType)){
			getUploadSurfaceCardFile101(files);
		}else if("121".equals(surfaceCardType)){
			getUploadSurfaceCardFile121(files);
		}else if("122".equals(surfaceCardType)||"100".equals(surfaceCardType)){
			getUploadSurfaceCardFile122(files);
		}else{
			String columns = "[{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50},{ \"header\":\"井名\",\"dataIndex\":\"wellname\"},{ \"header\":\"功图采集时间\",\"dataIndex\":\"cjsj\"}]";
			result_json.append("{ \"success\":true,\"flag\":true,\"columns\":"+columns+",");
			result_json.append("\"totalCount\":"+SurfaceCardFileFileName.size()+",");
			result_json.append("\"totalRoot\":[]}");
			json=result_json.toString();
			//HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		}
		return null;
	}
	
	
	
	@RequestMapping("/upload2"  )  
    public String upload2(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {
        //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
//                        System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        String fileName = "demoUpload" + file.getOriginalFilename();  
                        //定义上传路径  
                        String path = "H:/" + fileName;  
                        File localFile = new File(path);  
                        file.transferTo(localFile);  
                    }  
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
//                System.out.println(finaltime - pre);  
            }  
              
        }  
        return "/success";  
    }
	
	/**<p>蚌埠</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked"})
	@RequestMapping("/getUploadSurfaceCardFile101")
	public String getUploadSurfaceCardFile101(CommonsMultipartFile[] files) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		Map<String,String> surfaceCardFileMap=(Map<String, String>) map.get("surfaceCardFileMap");
		if(surfaceCardFileMap!=null){
			map.remove("surfaceCardFileMap",surfaceCardFileMap);
		}
		surfaceCardFileMap=new HashMap<String,String>();
		String json = "";
		String columns = "[{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50},{ \"header\":\"井名\",\"dataIndex\":\"wellName\"},{ \"header\":\"功图采集时间\",\"dataIndex\":\"acquisitionTime\"},{ \"header\":\"冲程\",\"dataIndex\":\"cch\"},{ \"header\":\"冲次\",\"dataIndex\":\"cci\"}]";
		result_json.append("{ \"success\":true,\"flag\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+files.length+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<files.length;i++){
			if(!files[i].isEmpty()){
				try{
					byte[] buffer = files[i].getBytes();
					String diagramData = new String(buffer);
					diagramData=diagramData.replaceAll("\r", "").replaceAll("\n", "\r\n");
			        String diagramDataStrArr[]=diagramData.split("\r\n");
			        String fileName=files[i].getOriginalFilename();
			        String fileNameArr[]=fileName.split("\\.")[0].split("%");
			        String wellName=fileNameArr[0];
//			        String acquisitionTimeStr=fileNameArr[1].replaceAll("-", "").replaceAll("/", "").replaceAll(":", "").replaceAll(" ", "");
			        String acquisitionTimeStr=diagramDataStrArr[0]+diagramDataStrArr[1];
			        if(acquisitionTimeStr.length()==6){
			        	acquisitionTimeStr="00000000"+acquisitionTimeStr;
			        }else if(acquisitionTimeStr.length()==8){
			        	acquisitionTimeStr+="000000";
			        }
			        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
					Date date = df2.parse(acquisitionTimeStr);
					String acquisitionTime=df.format(date);
					
					
					StringBuffer diagramDataBuff = new StringBuffer();
					String spm=diagramDataStrArr[3];
					String stroke=diagramDataStrArr[4];
					int pointCount=StringManagerUtils.stringToInteger(diagramDataStrArr[2]);
					String sData="",fData="",wattData="",iData="";
					for(int j=0;j<pointCount;j++){
						sData+=diagramDataStrArr[j*2+5];
						fData+=diagramDataStrArr[j*2+6];
						if(j<pointCount-1){
							sData+=",";
							fData+=",";
						}
			        }
		        	diagramDataBuff.append("{\"wellName\":\""+wellName+"\",\"acquisitionTime\":\""+acquisitionTime+"\",\"stroke\":"+stroke+","+"\"spm\":"+spm+",");
		        	diagramDataBuff.append("\"S\":["+sData+"],");
		        	diagramDataBuff.append("\"F\":["+fData+"],");
		        	diagramDataBuff.append("\"Watt\":["+wattData+"],");
		        	diagramDataBuff.append("\"I\":["+iData+"]");
		        	diagramDataBuff.append("}");
		        	
			        result_json.append("{\"id\":"+(i+1)+",");
					result_json.append("\"wellName\":\""+wellName+"\",");
					result_json.append("\"acquisitionTime\":\""+acquisitionTime+"\",");
					result_json.append("\"stroke\":\""+stroke+"\",");
					result_json.append("\"spm\":\""+spm+"\"},");
					surfaceCardFileMap.put(wellName+"@"+acquisitionTime,diagramDataBuff.toString());
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString();
		
		map.put("surfaceCardFileMap", surfaceCardFileMap);//将上传的功图文件放到内存中
		
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**<p>上传的功图Excel文件</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked"})
	@RequestMapping("/getUploadSurfaceCardFile121")
	public String getUploadSurfaceCardFile121(CommonsMultipartFile[] files) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		Map<String,String> surfaceCardFileMap=(Map<String, String>) map.get("surfaceCardFileMap");
		if(surfaceCardFileMap!=null){
			map.remove("surfaceCardFileMap",surfaceCardFileMap);
		}
		surfaceCardFileMap=new HashMap<String,String>();
		String json = "";
		String tablecolumns = "[{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50},{ \"header\":\"井名\",\"dataIndex\":\"wellName\"},{ \"header\":\"功图采集时间\",\"dataIndex\":\"acquisitionTime\"},{ \"header\":\"冲程\",\"dataIndex\":\"stroke\"},{ \"header\":\"冲次\",\"dataIndex\":\"spm\"}]";
		result_json.append("{ \"success\":true,\"flag\":true,\"columns\":"+tablecolumns+",");
		result_json.append("\"totalCount\":"+files.length+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<files.length;i++){
			if(!files[i].isEmpty()){
				try{
					Workbook rwb=Workbook.getWorkbook(files[i].getInputStream());
					rwb.getNumberOfSheets();
					Sheet oFirstSheet = rwb.getSheet(0);// 使用索引形式获取第一个工作表，也可以使用rwb.getSheet(sheetName);其中sheetName表示的是工作表的名称  
			        int rows = oFirstSheet.getRows();//获取工作表中的总行数  
			        int columns = oFirstSheet.getColumns();//获取工作表中的总列数  
			        for (int j = 1; j < rows; j++) {
			        	try{
			        		StringBuffer diagramDataBuff = new StringBuffer();
				        	String wellName= oFirstSheet.getCell(1,j).getContents().replaceAll(" ", "");
				        	String acquisitionTimeStr="";
				        	Cell cell = oFirstSheet.getCell(2,j);  
				        	if(cell.getType() == CellType.DATE){//如果是日期类型
				        		DateCell dc = (DateCell) cell;   
	                            Date date = dc.getDate();   
	                            TimeZone zone = TimeZone.getTimeZone("GMT");  
	                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                            sdf.setTimeZone(zone);  
	                            acquisitionTimeStr = sdf.format(date);  
				        	}else{
				        		acquisitionTimeStr=cell.getContents();
				        	}
				        	acquisitionTimeStr=acquisitionTimeStr.replaceAll("/", "-");
				        	String sDataString=oFirstSheet.getCell(3,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
				        	String fDataString=oFirstSheet.getCell(4,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",").replaceAll("00\\.", "0\\.").replaceAll("01\\.", "1\\.");
				        	String wattDataString=oFirstSheet.getCell(5,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",").replaceAll("00\\.", "0\\.").replaceAll("01\\.", "1\\.");
				        	String iDataString=oFirstSheet.getCell(6,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",").replaceAll("00\\.", "0\\.").replaceAll("01\\.", "1\\.");
				        	String stroke=oFirstSheet.getCell(7,j).getContents().replaceAll(" ", "");
				        	String spm=oFirstSheet.getCell(8,j).getContents().replaceAll(" ", "");
				        	
				        	String[] sArr=sDataString.split(",");
				        	String[] fArr=fDataString.split(",");
				        	String[] wattArr=wattDataString.split(",");
				        	String[] iArr=iDataString.split(",");
				        	String sData="";
				        	String fData="";
				        	String wattData="";
				        	String iData="";
				        	for(int m=0;m<sArr.length&&StringManagerUtils.isNotNull(sDataString);m++){
				        		sData+=StringManagerUtils.stringToFloat(sArr[m]);
				        		if(m<sArr.length-1){
				        			sData+=",";
				        		}
				        	}
				        	for(int m=0;m<fArr.length&&StringManagerUtils.isNotNull(fDataString);m++){
				        		fData+=StringManagerUtils.stringToFloat(fArr[m]);
				        		if(m<sArr.length-1){
				        			fData+=",";
				        		}
				        	}
				        	for(int m=0;m<wattArr.length&&StringManagerUtils.isNotNull(wattDataString);m++){
				        		wattData+=StringManagerUtils.stringToFloat(wattArr[m]);
				        		if(m<sArr.length-1){
				        			wattData+=",";
				        		}
				        	}
				        	for(int m=0;m<iArr.length&&StringManagerUtils.isNotNull(iDataString);m++){
				        		iData+=StringManagerUtils.stringToFloat(iArr[m]);
				        		if(m<sArr.length-1){
				        			iData+=",";
				        		}
				        	}
				        	
				        	diagramDataBuff.append("{\"wellName\":\""+wellName+"\",\"acquisitionTime\":\""+acquisitionTimeStr+"\",\"stroke\":"+StringManagerUtils.stringToFloat(stroke)+","+"\"spm\":"+StringManagerUtils.stringToFloat(spm)+",");
				        	diagramDataBuff.append("\"S\":["+sData+"],");
				        	diagramDataBuff.append("\"F\":["+fData+"],");
				        	diagramDataBuff.append("\"Watt\":["+wattData+"],");
				        	diagramDataBuff.append("\"I\":["+iData+"]");
				        	diagramDataBuff.append("}");
							
							result_json.append("{\"id\":"+j+",");
							result_json.append("\"wellName\":\""+wellName+"\",");
							result_json.append("\"acquisitionTime\":\""+acquisitionTimeStr+"\",");
							result_json.append("\"stroke\":\""+stroke+"\",");
							result_json.append("\"spm\":\""+spm+"\"},");
							surfaceCardFileMap.put(wellName+"@"+acquisitionTimeStr,diagramDataBuff.toString());
			        	}catch(Exception e){
							continue;
						}
			        }
				}catch(Exception e){
					continue;
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString();
		
		map.put("surfaceCardFileMap", surfaceCardFileMap);//将上传的功图文件放到内存中
		
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	
	/**<p>上传的长庆功图文件</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked"})
	@RequestMapping("/getUploadSurfaceCardFile122")
	public String getUploadSurfaceCardFile122(CommonsMultipartFile[] files) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		Map<String,String> surfaceCardFileMap=(Map<String, String>) map.get("surfaceCardFileMap");
		if(surfaceCardFileMap!=null){
			map.remove("surfaceCardFileMap",surfaceCardFileMap);
		}
		surfaceCardFileMap=new HashMap<String,String>();
		String json = "";
		String tablecolumns = "[{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50},{ \"header\":\"井名\",\"dataIndex\":\"wellName\"},{ \"header\":\"功图采集时间\",\"dataIndex\":\"acquisitionTime\"},{ \"header\":\"冲程\",\"dataIndex\":\"stroke\"},{ \"header\":\"冲次\",\"dataIndex\":\"spm\"}]";
		result_json.append("{ \"success\":true,\"flag\":true,\"columns\":"+tablecolumns+",");
		result_json.append("\"totalCount\":"+files.length+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<files.length;i++){
			if(!files[i].isEmpty()){
				try{
					Workbook rwb=Workbook.getWorkbook(files[i].getInputStream());
					rwb.getNumberOfSheets();
					Sheet oFirstSheet = rwb.getSheet(0);// 使用索引形式获取第一个工作表，也可以使用rwb.getSheet(sheetName);其中sheetName表示的是工作表的名称  
			        int rows = oFirstSheet.getRows();//获取工作表中的总行数  
			        int columns = oFirstSheet.getColumns();//获取工作表中的总列数  
			        for (int j = 1; j < rows; j++) {
			        	try{
			        		StringBuffer diagramDataBuff = new StringBuffer();
				        	String wellName= oFirstSheet.getCell(0,j).getContents().replaceAll(" ", "");
				        	String acquisitionTimeStr="";
				        	Cell cell = oFirstSheet.getCell(1,j);  
				        	if(cell.getType() == CellType.DATE){//如果是日期类型
				        		DateCell dc = (DateCell) cell;   
	                            Date date = dc.getDate();   
	                            TimeZone zone = TimeZone.getTimeZone("GMT");  
	                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                            sdf.setTimeZone(zone);  
	                            acquisitionTimeStr = sdf.format(date);  
				        	}else{
				        		acquisitionTimeStr=cell.getContents();
				        	}
				        	acquisitionTimeStr=acquisitionTimeStr.replaceAll("/", "-");
				        	
				        	String spm=oFirstSheet.getCell(2,j).getContents().replaceAll(" ", "");
				        	
				        	
				        	String em_ele_param=oFirstSheet.getCell(3,j).getContents().replaceAll(" ", "");
				        	String pr_dynm_card=oFirstSheet.getCell(4,j).getContents().replaceAll(" ", "");
				        	if(em_ele_param.indexOf("x")>=0){
				        		em_ele_param=em_ele_param.substring(em_ele_param.indexOf("x")+1);
				        	}
				        	if(pr_dynm_card.indexOf("x")>=0){
				        		pr_dynm_card=pr_dynm_card.substring(pr_dynm_card.indexOf("x")+1);
				        	}
				        	
				        	byte[] em_ele_paramByteArr=StringManagerUtils.hexStringToBytes(em_ele_param);
				        	byte[] pr_dynm_cardByteArr=StringManagerUtils.hexStringToBytes(pr_dynm_card);
//				        	System.out.println(StringManagerUtils.bytesToHexString(em_ele_paramByteArr, em_ele_paramByteArr.length));
//				        	System.out.println(StringManagerUtils.bytesToHexString(pr_dynm_cardByteArr, pr_dynm_cardByteArr.length));
				        	
				        	//无功功率正负标志
				        	int qMaxSign=(short) (0x0000 | (0x80 & em_ele_paramByteArr[0])>>7)==0?1:-1; 
				        	int qMinSign=(short) (0x0000 | (0x40 & em_ele_paramByteArr[0])>>6)==0?1:-1;  
				        	
				        	//有功功率正负标志
				        	int pMaxSign=(short) (0x0000 | (0x20 & em_ele_paramByteArr[0])>>5)==0?1:-1; 
				        	int pMinSign=(short) (0x0000 | (0x10 & em_ele_paramByteArr[0])>>4)==0?1:-1; 
				        	
				        	//电流正负标志
				        	int iMaxSign=(short) (0x0000 | (0x08 & em_ele_paramByteArr[0])>>3)==0?1:-1; 
				        	int iMinSign=(short) (0x0000 | (0x04 & em_ele_paramByteArr[0])>>2)==0?1:-1; 
				        	
				        	//电压正负标志
				        	int uMaxSign=(short) (0x0000 | (0x02 & em_ele_paramByteArr[0])>>1)==0?1:-1; 
				        	int uMinSign=(short) (0x0000 | (0x01 & em_ele_paramByteArr[0])>>0)==0?1:-1; 
				        	
				        	//载荷正负标志
				        	int fMaxSign=(short) (0x0000 | (0x08 & pr_dynm_cardByteArr[0])>>3)==0?1:-1; 
				        	int fMinSign=(short) (0x0000 | (0x04 & pr_dynm_cardByteArr[0])>>2)==0?1:-1;  
				        	
				        	//位移正负标志
				        	int sMaxSign=(short) (0x0000 | (0x02 & pr_dynm_cardByteArr[0])>>1)==0?1:-1; 
				        	int sMinSign=(short) (0x0000 | (0x01 & pr_dynm_cardByteArr[0])>>0)==0?1:-1; 
				        	
				        	//计算各物理量最大最小值
				        	float qMax= (StringManagerUtils.getShort(em_ele_paramByteArr[29])+(float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, 30)/65535)*qMaxSign;
				        	float qMin= (StringManagerUtils.getShort(em_ele_paramByteArr[26])+(float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, 27)/65535)*qMinSign;
				        	
				        	float pMax= (StringManagerUtils.getShort(em_ele_paramByteArr[23])+(float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, 24)/65535)*pMaxSign;
				        	float pMin= (StringManagerUtils.getShort(em_ele_paramByteArr[20])+(float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, 31)/65535)*pMinSign;
				        	
				        	float iMax= (StringManagerUtils.getShort(em_ele_paramByteArr[17])+(float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, 18)/65535)*iMaxSign;
				        	float iMin= (StringManagerUtils.getShort(em_ele_paramByteArr[14])+(float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, 15)/65535)*iMinSign;
				        	
				        	float uMax= (StringManagerUtils.getShort(em_ele_paramByteArr[11])+(float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, 12)/65535)*uMaxSign;
				        	float uMin= (StringManagerUtils.getShort(em_ele_paramByteArr[8])+(float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, 9)/65535)*uMinSign;
				        	
				        	float sMax_of_emep= (StringManagerUtils.getShort(em_ele_paramByteArr[5])+(float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, 6)/65535)*sMaxSign;
				        	float sMin_of_emep= (StringManagerUtils.getShort(em_ele_paramByteArr[2])+(float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, 3)/65535)*sMinSign;
				        	
				        	float fMax= (StringManagerUtils.getShort(pr_dynm_cardByteArr[10])+(float)StringManagerUtils.getUnsignedShort(pr_dynm_cardByteArr, 11)/65535)*fMaxSign;
				        	float fMin= (StringManagerUtils.getShort(pr_dynm_cardByteArr[7])+(float)StringManagerUtils.getUnsignedShort(pr_dynm_cardByteArr, 8)/65535)*fMinSign;
				        	
				        	float sMax_of_prdc= (StringManagerUtils.getShort(pr_dynm_cardByteArr[4])+(float)StringManagerUtils.getUnsignedShort(pr_dynm_cardByteArr, 5)/65535)*sMaxSign;
				        	float sMin_of_prdc= (StringManagerUtils.getShort(pr_dynm_cardByteArr[1])+(float)StringManagerUtils.getUnsignedShort(pr_dynm_cardByteArr, 2)/65535)*sMinSign;
				        	
				        	float stroke=sMax_of_prdc-sMin_of_prdc;
				        	
				        	String qData="";
				        	String pData="";
				        	String iData="";
				        	String uData="";
				        	String sData_of_emep="";
				        	
				        	String fData="";
				        	String sData_of_prdc="";
				        	
				        	for(int m=38;m<em_ele_paramByteArr.length;m+=10){
				        		sData_of_emep += ((float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, m)/65535)*(stroke);
				        		uData += ((float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, m+2)/65535)*(uMax-uMin)+uMin;
				        		iData += ((float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, m+4)/65535)*(iMax-iMin)+iMin;
				        		pData += ((float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, m+6)/65535)*(pMax-pMin)+pMin;
				        		qData += ((float)StringManagerUtils.getUnsignedShort(em_ele_paramByteArr, m+8)/65535)*(qMax-qMin)+qMin;
				        		if(m+10<em_ele_paramByteArr.length){
				        			sData_of_emep+=",";
				        			uData+=",";
				        			iData+=",";
				        			pData+=",";
				        			qData+=",";
				        		}
				        	}
				        	
				        	for(int m=13;m<pr_dynm_cardByteArr.length;m+=4){
				        		sData_of_prdc += ((float)StringManagerUtils.getUnsignedShort(pr_dynm_cardByteArr, m)/65535)*(stroke);
				        		fData += ((float)StringManagerUtils.getUnsignedShort(pr_dynm_cardByteArr, m+2)/65535)*(fMax-fMin)+fMin;
				        		if(m+4<pr_dynm_cardByteArr.length){
				        			sData_of_prdc+=",";
				        			fData+=",";
				        		}
				        	}
				        	
				        	diagramDataBuff.append("{\"wellName\":\""+wellName+"\",\"acquisitionTime\":\""+acquisitionTimeStr+"\",\"stroke\":"+stroke+","+"\"spm\":"+StringManagerUtils.stringToFloat(spm)+",");
				        	diagramDataBuff.append("\"S\":["+sData_of_prdc+"],");
				        	diagramDataBuff.append("\"F\":["+fData+"],");
				        	diagramDataBuff.append("\"Watt\":["+pData+"],");
				        	diagramDataBuff.append("\"I\":["+iData+"]");
				        	diagramDataBuff.append("}");
							
							result_json.append("{\"id\":"+j+",");
							result_json.append("\"wellName\":\""+wellName+"\",");
							result_json.append("\"acquisitionTime\":\""+acquisitionTimeStr+"\",");
							result_json.append("\"stroke\":\""+sMax_of_prdc+"\",");
							result_json.append("\"spm\":\""+spm+"\"},");
							surfaceCardFileMap.put(wellName+"@"+acquisitionTimeStr,diagramDataBuff.toString());
			        	}catch(Exception e){
							continue;
						}
			        }
				}catch(Exception e){
					continue;
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString();
		
		map.put("surfaceCardFileMap", surfaceCardFileMap);//将上传的功图文件放到内存中
		
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getPSToFSElectricReaultData")
	public String getPSToFSElectricReaultData(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String json="";
		String cjsj="";
		String wellName="";
		int pointCount=0;
		List<Float> currentAList=new ArrayList<Float>();
		List<Float> currentBList=new ArrayList<Float>();
		List<Float> currentCList=new ArrayList<Float>();
		List<Float> voltageAList=new ArrayList<Float>();
		List<Float> voltageBList=new ArrayList<Float>();
		List<Float> voltageCList=new ArrayList<Float>();
		List<Float> activePowerAList=new ArrayList<Float>();
		List<Float> activePowerBList=new ArrayList<Float>();
		List<Float> activePowerCList=new ArrayList<Float>();
		for(int i=0;i<files.length;i++){
			if(!files[i].isEmpty()){
				try{
					if(files[i].getFileItem().getName().lastIndexOf("CSV")>0||files[i].getFileItem().getName().lastIndexOf("csv")>0){
						System.out.println("CSV文件:"+files[i].getFileItem().getName());
					}else if(files[i].getFileItem().getName().lastIndexOf("XLSX")>0||files[i].getFileItem().getName().lastIndexOf("xlsx")>0){
						System.out.println("xlsx文件:"+files[i].getFileItem().getName());
					}else if(files[i].getFileItem().getName().lastIndexOf("XLS")>0||files[i].getFileItem().getName().lastIndexOf("xls")>0){
						System.out.println("xls文件:"+files[i].getFileItem().getName());
						Workbook rwb=Workbook.getWorkbook(files[i].getInputStream());
						rwb.getNumberOfSheets();
						Sheet oFirstSheet = rwb.getSheet(0);// 使用索引形式获取第一个工作表，也可以使用rwb.getSheet(sheetName);其中sheetName表示的是工作表的名称  
				        int rows = oFirstSheet.getRows();//获取工作表中的总行数  
				        int columns = oFirstSheet.getColumns();//获取工作表中的总列数  
				        
				        for (int j = 1; j < 10; j++) {
				        	if(j==1){
				        		cjsj = oFirstSheet.getCell(1,j).getContents();
				        		wellName= oFirstSheet.getCell(0,j).getContents();
				        	}
				        	String cjsjtemp=oFirstSheet.getCell(1,j).getContents();
				        	if(cjsjtemp.equals(cjsj)){//只解析第一组数据
				        		String bsid=oFirstSheet.getCell(2,j).getContents();
					        	if("1".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.02);
						        		currentAList.add(dataReault);
					        		}
					        	}else if("2".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.1);
						        		voltageAList.add(dataReault);
					        		}
					        	}else if("3".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*5*0.001);
						        		activePowerAList.add(dataReault);
					        		}
					        	}else if("4".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.02);
						        		currentBList.add(dataReault);
					        		}
					        	}else if("5".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.1);
						        		voltageBList.add(dataReault);
					        		}
					        	}else if("6".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*5*0.001);
						        		activePowerBList.add(dataReault);
					        		}
					        	}else if("7".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.02);
						        		currentCList.add(dataReault);
					        		}
					        	}else if("8".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.1);
						        		voltageCList.add(dataReault);
					        		}
					        	}else if("9".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*5*0.001);
						        		activePowerCList.add(dataReault);
					        		}
					        	}
				        	}
				        }
				        rwb.close();
					}
					
					
				}catch(Exception e){
					e.printStackTrace();
					result_json = new StringBuffer();
					result_json.append("{\"success\":false}");
					continue;
				}
			}
		}
		System.out.println(result_json.length());
		if(result_json.length()==0){
			result_json.append("{\"success\":true,\"cjsj\":\""+cjsj+"\",\"wellName\":\""+wellName+"\",");
			result_json.append("\"totalRoot\":[");
			for(int i=0;i<pointCount;i++){
				float activePowerSum=0;
				String data="{";
				data+="\"id\":\""+(i+1)+"\",";
				if(currentAList.size()>i){
					data+="\"currentA\":\""+currentAList.get(i)+"\",";
				}
				if(currentBList.size()>i){
					data+="\"currentB\":\""+currentBList.get(i)+"\",";
				}
				if(currentCList.size()>i){
					data+="\"currentC\":\""+currentCList.get(i)+"\",";
				}
				if(voltageAList.size()>i){
					data+="\"voltageA\":\""+voltageAList.get(i)+"\",";
				}
				if(voltageBList.size()>i){
					data+="\"voltageB\":\""+voltageBList.get(i)+"\",";
				}
				if(voltageCList.size()>i){
					data+="\"voltageC\":\""+voltageCList.get(i)+"\",";
				}
				if(activePowerAList.size()>i){
					data+="\"activePowerA\":\""+activePowerAList.get(i)+"\",";
					activePowerSum+=activePowerAList.get(i);
				}
				if(activePowerBList.size()>i){
					data+="\"activePowerB\":\""+activePowerBList.get(i)+"\",";
					activePowerSum+=activePowerBList.get(i);
				}
				if(activePowerCList.size()>i){
					data+="\"activePowerC\":\""+activePowerCList.get(i)+"\",";
					activePowerSum+=activePowerCList.get(i);
				}
				data+="\"activePowerSum\":\""+StringManagerUtils.stringToFloat(activePowerSum+"", 3)+"\"},";
				result_json.append(data);
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]}");
		}
		json=result_json.toString();
		System.out.println(json);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**<p>描述：保存上传功图文件</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/getAndsaveTestSurfaceCardData")
	public String GetAndsaveTestSurfaceCardData() throws Exception {
		String json = "{success:true,flag:true}";
		
		// 1、构造excel文件输入流对象  
        String sFilePath = "C:/Users/ThinkPad/Desktop/冀东油田数据/2017-10-22载荷数据.xls";  
        InputStream is = new FileInputStream(sFilePath);
        // 2、声明工作簿对象  
        Workbook rwb = Workbook.getWorkbook(is);
        // 3、获得工作簿的个数,对应于一个excel中的工作表个数  
        rwb.getNumberOfSheets();
  
        Sheet oFirstSheet = rwb.getSheet(3);// 使用索引形式获取第一个工作表，也可以使用rwb.getSheet(sheetName);其中sheetName表示的是工作表的名称  
        int rows = oFirstSheet.getRows();//获取工作表中的总行数  
        int columns = oFirstSheet.getColumns();//获取工作表中的总列数  
        for (int i = 1; i < rows; i++) {
        	String wellName=oFirstSheet.getCell(0,i).getContents();
        	
        	DateCell dc = (DateCell) oFirstSheet.getCell(1,i);   
            Date date = dc.getDate();   
              
            TimeZone zone = TimeZone.getTimeZone("GMT");  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            sdf.setTimeZone(zone);  
            String acquisitionTimeStr = sdf.format(date);
        	
//        	String acquisitionTimeStr=oFirstSheet.getCell(1,i).getContents();//2017-10-22 01:00:00
        	String dateStr=acquisitionTimeStr.split(" ")[0].replaceAll("-", "");
        	String timeStr=acquisitionTimeStr.split(" ")[1].replaceAll(":", "");
        	String cch=oFirstSheet.getCell(2,i).getContents();
        	String cci=oFirstSheet.getCell(3,i).getContents();
        	String gtstr=oFirstSheet.getCell(7,i).getContents();
        	List<String> gtDataList=StringManagerUtils.SubStringToList(gtstr, 9);
        	int gtCount=gtDataList.size();
        	StringBuffer gtBuf = new StringBuffer();
        	gtBuf.append(dateStr).append("\r\n");
        	gtBuf.append(timeStr).append("\r\n");
        	gtBuf.append(gtCount).append("\r\n");
        	gtBuf.append(cci).append("\r\n");
        	gtBuf.append(cch).append("\r\n");
        	for(int j=0;j<gtCount;j++){
        		float sData=Float.parseFloat(gtDataList.get(j).substring(0, 4))/1000;
        		float fData=Float.parseFloat(gtDataList.get(j).substring(4, 9))/100;
        		gtBuf.append(sData).append("\r\n");
        		gtBuf.append(fData);
        		if(j<gtCount-1){
        			gtBuf.append("\r\n");
        		}
        	}
//        	System.out.println(gtBuf.toString());
//        	raphicalUploadService.saveSurfaceCard(wellName,acquisitionTimeStr,gtBuf.toString());
        }
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAndsaveDQSurfaceCardData")
	public String GetAndsaveDQSurfaceCardData() throws Exception {
		String json = "{success:true,flag:true}";
		
		// 1、构造excel文件输入流对象  
        String sFilePath = "C:/Users/ThinkPad/Desktop/daqingqichangshuju.xls";  
        InputStream is = new FileInputStream(sFilePath);
        // 2、声明工作簿对象  
        Workbook rwb = Workbook.getWorkbook(is);
        // 3、获得工作簿的个数,对应于一个excel中的工作表个数  
        rwb.getNumberOfSheets();
  
        Sheet oFirstSheet = rwb.getSheet(0);// 使用索引形式获取第一个工作表，也可以使用rwb.getSheet(sheetName);其中sheetName表示的是工作表的名称  
        int rows = oFirstSheet.getRows();//获取工作表中的总行数  
        int columns = oFirstSheet.getColumns();//获取工作表中的总列数  
        for (int i = 4; i < 124; i++) {
        	String wellName=oFirstSheet.getCell(1,i).getContents();
        	
        	String dateStr="20180425";
        	String timeStr="080000";
        	String[] disstr=oFirstSheet.getCell(23,i).getContents().split(";");
        	String[] loadstr=oFirstSheet.getCell(24,i).getContents().split(";");
        	String cch=oFirstSheet.getCell(25,i).getContents();
        	String cci=oFirstSheet.getCell(26,i).getContents();
        	
        	StringBuffer gtBuf = new StringBuffer();
        	gtBuf.append(dateStr).append("\r\n");
        	gtBuf.append(timeStr).append("\r\n");
        	gtBuf.append(disstr.length).append("\r\n");
        	gtBuf.append(cci).append("\r\n");
        	gtBuf.append(cch).append("\r\n");
        	
        	for(int j=0;j<disstr.length;j++){
        		gtBuf.append(disstr[j]).append("\r\n");
        		gtBuf.append(loadstr[j]);
        		if(j<disstr.length){
        			gtBuf.append("\r\n");
        		}
        	}
        	
//        	System.out.println(gtBuf.toString());
//        	raphicalUploadService.saveSurfaceCard(wellName,"2018-04-25 08:00:00",gtBuf.toString());
        }
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAndsaveTempSurfaceCardData")
	public String GetAndsaveTempSurfaceCardData() throws Exception {
		String json = "{success:true,flag:true}";
		
		// 1、构造excel文件输入流对象  
        String sFilePath = "C:/Users/ThinkPad/Desktop/采油二厂数据.xls";  
        InputStream is = new FileInputStream(sFilePath);
        // 2、声明工作簿对象  
        Workbook rwb = Workbook.getWorkbook(is);
        // 3、获得工作簿的个数,对应于一个excel中的工作表个数  
        rwb.getNumberOfSheets();
  
        Sheet oFirstSheet = rwb.getSheet(0);// 使用索引形式获取第一个工作表，也可以使用rwb.getSheet(sheetName);其中sheetName表示的是工作表的名称  
        int rows = oFirstSheet.getRows();//获取工作表中的总行数  
        int columns = oFirstSheet.getColumns();//获取工作表中的总列数  
        String wellName="J1-1";
        String dateStr="20180426";
    	String timeStr="080000";
    	
    	String cch="4.79";
    	String cci="2";
    	
    	StringBuffer gtBuf = new StringBuffer();
    	gtBuf.append(dateStr).append("\r\n");
    	gtBuf.append(timeStr).append("\r\n");
    	gtBuf.append(144).append("\r\n");
    	gtBuf.append(cci).append("\r\n");
    	gtBuf.append(cch).append("\r\n");
        
        for (int i = 1; i < 145; i++) {
        	
        	String disstr=oFirstSheet.getCell(0,i).getContents();
        	String loadstr=oFirstSheet.getCell(2,i).getContents();
        	
        	
        	gtBuf.append(disstr).append("\r\n");
    		gtBuf.append(loadstr);
    		if(i<144){
    			gtBuf.append("\r\n");
    		}
        	
//        	System.out.println(gtBuf.toString());
        	
        }
//        raphicalUploadService.saveSurfaceCard(wellName,"2018-04-26 08:00:00",gtBuf.toString());
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * 描述：导出功图数据
	 * @throws SQLException 
	 */
	@RequestMapping("/exportSurfaceCardData")
	public String exportSurfaceCardData() throws IOException, SQLException {
		String sql="select t2.jh,to_char(t.cjsj,'yyyymmdd_hh24miss'),t.gtsj "
				+ " from tbl_rpc_diagram_hist t,tbl_wellinformation t2 "
				+ " where t.jbh=t2.jlbh order by jh,t.cjsj";
		String filecontent="";
		String fileName="";
		String json = "{success:true,flag:true}";
		List<?> list=commonDataService.findCallSql(sql);
		
		for(int i=0;i<list.size();i++){
			fileName="C:\\Users\\ThinkPad\\Desktop\\export\\";
			Object[] obj = (Object[]) list.get(i);
			fileName+=obj[0]+"%"+obj[1]+".t";
			SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[2]);
			CLOB realClob = (CLOB) proxy.getWrappedClob(); 
			filecontent=StringManagerUtils.CLOBtoString(realClob);
			StringManagerUtils.createFile(fileName,filecontent);
		}
		
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		this.pager = new Page("pagerForm", request);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveRTUAcquisitionData")
	public String saveRTUAcquisitionData() throws Exception {
		String FSdiagramCalculateHttpServerURL[]=Config.getInstance().configFile.getAgileCalculate().getFESDiagram();
		String ScrewPumpCalculateHttpServerURL[]=Config.getInstance().configFile.getAgileCalculate().getPcpProduction();
		Gson gson=new Gson();
		String totalDate = StringManagerUtils.getCurrentTime();
		String totalUrl="";
		ServletInputStream ss = request.getInputStream();
		String data=convertStreamToString(ss,"utf-8");
		java.lang.reflect.Type type = new TypeToken<WellAcquisitionData>() {}.getType();
		WellAcquisitionData wellAcquisitionData = gson.fromJson(data, type);
		if(wellAcquisitionData!=null){
			String requestData="";
			String[] calculateHttpServerURL=null;
			if(wellAcquisitionData.getLiftingType()>=400 && wellAcquisitionData.getLiftingType()<500){//螺杆泵
				requestData=raphicalUploadService.getScrewPumpRPMCalculateRequestData(wellAcquisitionData);
				calculateHttpServerURL=ScrewPumpCalculateHttpServerURL;
			}else if(wellAcquisitionData.getLiftingType()>=200 && wellAcquisitionData.getLiftingType()<300){//抽油机
				requestData=raphicalUploadService.getFSdiagramCalculateRequestData(wellAcquisitionData);
				calculateHttpServerURL=FSdiagramCalculateHttpServerURL;
			}
			String responseData=StringManagerUtils.sendPostMethod(calculateHttpServerURL[0], requestData,"utf-8");
			
			PCPCalculateResponseData pcpCalculateResponseData=null;
			RPCCalculateResponseData rpcCalculateResponseData=null;
			if(wellAcquisitionData.getLiftingType()>=400 && wellAcquisitionData.getLiftingType()<500){//螺杆泵
				type = new TypeToken<PCPCalculateResponseData>() {}.getType();
				pcpCalculateResponseData=gson.fromJson(responseData, type);
				raphicalUploadService.savePCPAcquisitionAndCalculateData(wellAcquisitionData,pcpCalculateResponseData);
				totalUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/PCPRPMDailyCalculation?date="+totalDate;
			}else if(wellAcquisitionData.getLiftingType()>=200 && wellAcquisitionData.getLiftingType()<300){//抽油机
				type = new TypeToken<RPCCalculateResponseData>() {}.getType();
				rpcCalculateResponseData=gson.fromJson(responseData, type);
				raphicalUploadService.saveRPCAcquisitionAndCalculateData(wellAcquisitionData,rpcCalculateResponseData);
				totalUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/FSDiagramDailyCalculation?date="+totalDate;
			}
			
			if((rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1)
					||(pcpCalculateResponseData!=null&&pcpCalculateResponseData.getCalculationStatus().getResultStatus()==1&&pcpCalculateResponseData.getCalculationStatus().getResultCode()!=1232)){
				totalUrl+="&wellId="+wellAcquisitionData.getWellId();
				StringManagerUtils.sendPostMethod(totalUrl, "","utf-8");
			}
			
		}
		
		String json = "{success:true,flag:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveKafkaUpData")
	public String saveKafkaUpData() throws Exception {
		Gson gson=new Gson();
		String tiemEffUrl=Config.getInstance().configFile.getAgileCalculate().getRun()[0];
		String commUrl=Config.getInstance().configFile.getAgileCalculate().getCommunication()[0];
		String energyUrl=Config.getInstance().configFile.getAgileCalculate().getEnergy()[0];
		String FSdiagramCalculateHttpServerURL=Config.getInstance().configFile.getAgileCalculate().getFESDiagram()[0];
		ServletInputStream ss = request.getInputStream();
		String data=convertStreamToString(ss,"utf-8");
		java.lang.reflect.Type type = new TypeToken<KafkaUpData>() {}.getType();
		KafkaUpData kafkaUpData=gson.fromJson(data, type);
		if(kafkaUpData!=null){
			String sql="select t.wellName,to_char(t2.acquisitiontime,'yyyy-mm-dd hh24:mi:ss'),"
					+ " t2.runstatus,t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ " t2.totalKWattH,t2.totalPKWattH,t2.totalNKWattH,t2.totalKVarH,t2.totalpKVarH,t2.totalNKVarH,t2.totalKVAH,"
					+ " t2.todayKWattH,t2.todayPKWattH,t2.todayNKWattH,t2.todayKVarH,t2.todaypKVarH,t2.todayNKVarH,t2.todayKVAH "
					+ " from tbl_wellinformation t ,tbl_rpc_discrete_latest  t2 "
					+ " where t2.wellId=t.id and t.driverAddr='"+kafkaUpData.getKey()+"'";
			List list = this.commonDataService.findCallSql(sql);
			if(list.size()>0){
				Object[] obj=(Object[]) list.get(0);
				kafkaUpData.setWellName(obj[0]+"");
				//进行时率计算
				TimeEffResponseData timeEffResponseData=null;
				String tiemEffRequest="{"
						+ "\"AKString\":\"\","
						+ "\"WellName\":\""+kafkaUpData.getWellName()+"\",";
				if(StringManagerUtils.isNotNull(obj[1]+"")&&StringManagerUtils.isNotNull(obj[5]+"")){
					tiemEffRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+obj[1]+"\","
							+ "\"RunStatus\": "+("1".equals(obj[2]+"")?true:false)+","
							+ "\"RunEfficiency\": {"
							+ "\"Efficiency\": "+obj[3]+","
							+ "\"Time\": "+obj[2]+","
							+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(obj[5]+"")+""
							+ "}"
							+ "},";
				}	
				tiemEffRequest+= "\"Current\": {"
						+ "\"AcqTime\":\""+kafkaUpData.getAcqTime()+"\","
						+ "\"RunStatus\":"+kafkaUpData.getRunStatus()+""
						+ "}"
						+ "}";
				String timeEffResponse="";
				timeEffResponse=StringManagerUtils.sendPostMethod(tiemEffUrl, tiemEffRequest,"utf-8");
				type = new TypeToken<TimeEffResponseData>() {}.getType();
				timeEffResponseData=gson.fromJson(timeEffResponse, type);
				
				//进行电量计算
				EnergyCalculateResponseData energyCalculateResponseData=null;
				String energyRequest="{"
						+ "\"AKString\":\"\","
						+ "\"WellName\":\""+kafkaUpData.getWellName()+"\",";
				if(StringManagerUtils.isNotNull(obj[1]+"")){
					energyRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+obj[1]+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+obj[6]+","
							+ "\"PKWattH\":"+obj[7]+","
							+ "\"NKWattH\":"+obj[8]+","
							+ "\"KVarH\":"+obj[9]+","
							+ "\"PKVarH\":"+obj[10]+","
							+ "\"NKVarH\":"+obj[11]+","
							+ "\"KVAH\":"+obj[12]+""
							+ "},\"Today\":{"
							+ "\"KWattH\":"+obj[13]+","
							+ "\"PKWattH\":"+obj[14]+","
							+ "\"NKWattH\":"+obj[15]+","
							+ "\"KVarH\":"+obj[16]+","
							+ "\"PKVarH\":"+obj[17]+","
							+ "\"NKVarH\":"+obj[18]+","
							+ "\"KVAH\":"+obj[19]+""
							+ "}"
							+ "},";
				}	
				energyRequest+= "\"Current\": {"
						+ "\"AcqTime\":\""+kafkaUpData.getAcqTime()+"\","
						+ "\"Total\":{"
						+ "\"KWattH\":"+kafkaUpData.getTotalEnergy().getKWattH()+","
						+ "\"PKWattH\":"+kafkaUpData.getTotalEnergy().getPKWattH()+","
						+ "\"NKWattH\":"+kafkaUpData.getTotalEnergy().getNKWattH()+","
						+ "\"KVarH\":"+kafkaUpData.getTotalEnergy().getKVarH()+","
						+ "\"PKVarH\":"+kafkaUpData.getTotalEnergy().getPKVarH()+","
						+ "\"NKVarH\":"+kafkaUpData.getTotalEnergy().getNKVarH()+","
						+ "\"KVAH\":"+kafkaUpData.getTotalEnergy().getKVAH()+""
						+ "}"
						+ "}"
						+ "}";
				String energyResponse=StringManagerUtils.sendPostMethod(energyUrl, energyRequest,"utf-8");
				type = new TypeToken<EnergyCalculateResponseData>() {}.getType();
				energyCalculateResponseData=gson.fromJson(energyResponse, type);
				
				//更新数据
				String updateDailyData="";
				String updateProdData="update tbl_rpc_productiondata_latest t set t.acquisitionTime=to_date('"+kafkaUpData.getAcqTime()+"','yyyy-mm-dd hh24:mi:ss')";
				if(kafkaUpData.getWaterCut()>=0){
					updateProdData+=",t.WaterCut_W="+kafkaUpData.getWaterCut();
				}
				if(kafkaUpData.getTubingPressure()>=0){
					updateProdData+=",t.TubingPressure="+kafkaUpData.getTubingPressure();
				}
				if(kafkaUpData.getCasingPressure()>=0){
					updateProdData+=",t.CasingPressure="+kafkaUpData.getCasingPressure();
				}
				if(kafkaUpData.getWellHeadFluidTemperature()>=0){
					updateProdData+=",t.WellHeadFluidTemperature="+kafkaUpData.getWellHeadFluidTemperature();
				}
				if(kafkaUpData.getProducingfluidLevel()>=0){
					updateProdData+=",t.ProducingfluidLevel="+kafkaUpData.getProducingfluidLevel();
				}
				updateProdData+=" where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+kafkaUpData.getWellName()+"') ";
				
				String updateDiscreteData="update tbl_rpc_discrete_latest t set t.CommStatus=1,"
						+ "t.runStatus="+(kafkaUpData.getRunStatus()?1:0)+","
						+ "t.workingconditioncode="+kafkaUpData.getResultCode()+","
						+ "t.FrequencyRunValue="+kafkaUpData.getFreq()+","
						+ "t.AcquisitionTime=to_date('"+kafkaUpData.getAcqTime()+"','yyyy-mm-dd hh24:mi:ss')";
				if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
					updateDiscreteData+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
						+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime()
						+ " ,t.runRange= '"+timeEffResponseData.getCurrent().getRunEfficiency().getRangeString()+"'";
					if(timeEffResponseData.getDaily()!=null&&StringManagerUtils.isNotNull(timeEffResponseData.getDaily().getDate())){
						
					}
				}
				
				if(energyCalculateResponseData!=null&&energyCalculateResponseData.getResultStatus()==1){
					updateDiscreteData+=",t.TotalKWattH= "+energyCalculateResponseData.getCurrent().getTotal().getKWattH()
							+ ",t.TotalPKWattH= "+energyCalculateResponseData.getCurrent().getTotal().getPKWattH()
							+ ",t.TotalNKWattH= "+energyCalculateResponseData.getCurrent().getTotal().getNKWattH()
							+ ",t.TotalKVarH= "+energyCalculateResponseData.getCurrent().getTotal().getKVarH()
							+ ",t.TotalPKVarH= "+energyCalculateResponseData.getCurrent().getTotal().getPKVarH()
							+ ",t.TotalNKVarH= "+energyCalculateResponseData.getCurrent().getTotal().getNKVarH()
							+ ",t.TotalKVAH= "+energyCalculateResponseData.getCurrent().getTotal().getKVAH()
							
							+ ",t.TodayKWattH= "+energyCalculateResponseData.getCurrent().getToday().getKWattH()
							+ ",t.TodayPKWattH= "+energyCalculateResponseData.getCurrent().getToday().getPKWattH()
							+ ",t.TodayNKWattH= "+energyCalculateResponseData.getCurrent().getToday().getNKWattH()
							+ ",t.TodayKVarH= "+energyCalculateResponseData.getCurrent().getToday().getKVarH()
							+ ",t.TodayPKVarH= "+energyCalculateResponseData.getCurrent().getToday().getPKVarH()
							+ ",t.TodayNKVarH= "+energyCalculateResponseData.getCurrent().getToday().getNKVarH()
							+ ",t.TodayKVAH= "+energyCalculateResponseData.getCurrent().getToday().getKVAH();
					if(energyCalculateResponseData.getDaily()!=null&&StringManagerUtils.isNotNull(energyCalculateResponseData.getDaily().getDate())){
						updateDailyData="update tbl_rpc_total_day t set t.TotalKWattH= "+energyCalculateResponseData.getDaily().getKWattH()
								+ ",t.TotalPKWattH= "+energyCalculateResponseData.getDaily().getPKWattH()
								+ ",t.TotalNKWattH= "+energyCalculateResponseData.getDaily().getNKWattH()
								+ ",t.TotalKVarH= "+energyCalculateResponseData.getDaily().getKVarH()
								+ ",t.TotalPKVarH= "+energyCalculateResponseData.getDaily().getPKVarH()
								+ ",t.TotalNKVarH= "+energyCalculateResponseData.getDaily().getNKVarH()
								+ ",t.TotalKVAH= "+energyCalculateResponseData.getDaily().getKVAH()
								
								+ ",t.TodayKWattH= "+energyCalculateResponseData.getDaily().getKWattH()
								+ ",t.TodayPKWattH= "+energyCalculateResponseData.getDaily().getPKWattH()
								+ ",t.TodayNKWattH= "+energyCalculateResponseData.getDaily().getNKWattH()
								+ ",t.TodayKVarH= "+energyCalculateResponseData.getDaily().getKVarH()
								+ ",t.TodayPKVarH= "+energyCalculateResponseData.getDaily().getPKVarH()
								+ ",t.TodayNKVarH= "+energyCalculateResponseData.getDaily().getNKVarH()
								+ ",t.TodayKVAH= "+energyCalculateResponseData.getDaily().getKVAH()
								+ " where t.calculatedate=to_date('"+energyCalculateResponseData.getDaily().getDate()+"','yyyy-mm-dd') "
						         +" and t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+kafkaUpData.getWellName()+"') ";
					}
				}else{
					updateDiscreteData+= " ,t.totalKWattH= "+kafkaUpData.getTotalEnergy().getKWattH()
							+ " ,t.totalKVarH= "+kafkaUpData.getTotalEnergy().getKVarH();
				}
				updateDiscreteData+=" where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+kafkaUpData.getWellName()+"') ";
				commonDataService.getBaseDao().updateOrDeleteBySql(updateProdData);
				commonDataService.getBaseDao().updateOrDeleteBySql(updateDiscreteData);
				if(StringManagerUtils.isNotNull(updateDailyData)){
					commonDataService.getBaseDao().updateOrDeleteBySql(updateDailyData);
				}
				
				//处理曲线数据
				String requestData=raphicalUploadService.getFSdiagramCalculateRequestData(kafkaUpData);
				String responseData=StringManagerUtils.sendPostMethod(FSdiagramCalculateHttpServerURL, requestData,"utf-8");
				type = new TypeToken<RPCCalculateResponseData>() {}.getType();
				RPCCalculateResponseData rpcCalculateResponseData=gson.fromJson(responseData, type);
				raphicalUploadService.saveRPCAcquisitionAndCalculateData(kafkaUpData,rpcCalculateResponseData);
				//汇总
				String totalDate = StringManagerUtils.getCurrentTime();
				String totalUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/FSDiagramDailyCalculation?date="+totalDate;
				if((rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1)){
					totalUrl+="&wellId="+kafkaUpData.getWellId();
					StringManagerUtils.sendPostMethod(totalUrl, "","utf-8");
				}
			}
			
		}
		String json = "{success:true,flag:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/kafkaProducerMsg")
	public String kafkaProducerMsg() throws Exception {
		String type = ParamUtils.getParameter(request, "type");
		String wellName = ParamUtils.getParameter(request, "wellName");
		String data = ParamUtils.getParameter(request, "data");
		String sql="select t.drivercode, t.driveraddr from tbl_wellinformation t where t.wellname='"+wellName+"'";
		List list = this.commonDataService.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			String driverCode=obj[0]+"";
			String ID=obj[1]+"";
			if("KafkaDrive".equalsIgnoreCase(driverCode)&&StringManagerUtils.isNotNull(ID)){
				String topic="Down-"+ID+"-";
				if("1".equals(type)){
					topic+="RTC";
				}else if("2".equals(type)){
					topic+="StartRPC";
				}else if("3".equals(type)){
					topic+="StopRPC";
				}else if("4".equals(type)){
					topic+="FixPositionStopRPC";
				}else if("5".equals(type)){
					topic+="Freq";
				}else if("6".equals(type)){
					topic+="Config";
				}else if("7".equals(type)){
					topic+="Model";
				}
				KafkaServerTast.producerMsg(topic, "下行数据", data);
			}
		}
		
		
		
		String json ="{success:true}";
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	public static String convertStreamToString(InputStream is,String encoding) {      
		StringBuilder sb = new StringBuilder();
       try {
    	   BufferedReader reader = new BufferedReader(new InputStreamReader(is,encoding));
           String line = null;    
           while ((line = reader.readLine()) != null) {      
                sb.append(line + "\n");      
            }      
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
           try {      
                is.close();      
            } catch (IOException e) {      
                e.printStackTrace();      
            }      
        }      
    
       return sb.toString();      
    }

	
	@RequestMapping("/downLoadFSdiagramUploadExcelModel")
    public void downLoadFSdiagramUploadExcelModel(){
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String path=stringManagerUtils.getFilePath("功图上传Excel模板.xls","download/");
        File file = new File(path);
        try {
        	response.setContentType("application/vnd.ms-excel;charset=utf-8");
            String fileName = "功图上传Excel模板.xls";
            response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
            InputStream in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
            in.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	/**<p>获取对接数据库的功图文件文件</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOuterSurfaceCardData")
	public String getOuterSurfaceCardData(){
		String localSql="select t.wellName,t2.pumpsettingdepth,to_char(t3.acquisitionTime,'yyyy-mm-dd hh24:mi:ss') "
				+ " from tbl_wellinformation t  "
				+ " left outer join tbl_rpc_productiondata_latest t2 on t2.wellid=t.id "
				+ " left outer join tbl_rpc_diagram_latest t3 on t3.wellid=t.id "
				+ " where 1=1 "
				+ " and t.liftingtype >=200 and t.liftingtype<300 "
				+ " order by t.sortnum";
		Connection outerConn= OracleJdbcUtis.getOuterConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		if(outerConn!=null){
//			System.out.println("connection success");
			List<?> localList=commonDataService.findCallSql(localSql);
			for(int i=0;i<localList.size();i++){
				try{
					Object[] obj = (Object[]) localList.get(i);
					String wellName=obj[0]+"";
					String pumpSettingSepth=obj[1]+"";
					String acquisitionTime=obj[2]+"";
					int record=100;
//					acquisitionTime=StringManagerUtils.isNotNull(acquisitionTime)?acquisitionTime:"1970-01-01 00:00:00";
					if(StringManagerUtils.isNotNull(pumpSettingSepth)){//如果生产数据不是空
						pstmt=null;
						rs=null;
						String outerSql=""
								+ " select t.well_common_name,to_char(t.dyna_create_time,'yyyy-mm-dd hh24:mi:ss'),"
								+ " t.stroke,t.frequency,t.dyna_points,t.displacement,t.disp_load,t.disp_current,t.active_power "
								+ " from a11prod.pc_fd_pumpjack_dyna_dia_t t  "
								+ " where 1=1 ";
						
						if(!StringManagerUtils.isNotNull(acquisitionTime)){
//							acquisitionTime="1970-01-01 00:00:00";
							record=1;
							outerSql+=" and t.dyna_create_time > to_date('"+StringManagerUtils.getCurrentTime()+"','yyyy-mm-dd')-30 ";
						}else{
							outerSql+= " and t.dyna_create_time > to_date('"+acquisitionTime+"','yyyy-mm-dd hh24:mi:ss') ";
						}
						outerSql+= " and t.dyna_create_time < to_date('2020-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
						outerSql+=" and t.well_common_name='"+wellName+"' "
								+ " order by t.dyna_create_time ";
//								+ " ) v where rownum<="+record+"";
//						System.out.println("outerSql-"+wellName+":"+outerSql);
						pstmt = outerConn.prepareStatement(outerSql);
						rs=pstmt.executeQuery();
//						System.out.println("outerSql-"+wellName+"查询完成!!!");
						
						while(rs.next()){
							try{
								wellName=rs.getString(1)==null?"":rs.getString(1);
								String acquisitionTimeStr=rs.getString(2)==null?"":rs.getString(2);
								float stroke;
								float frequency;
								if(rs.getObject(3)==null){
									stroke=0;
								}else{
									stroke=rs.getFloat(3);
								}
								if(rs.getObject(4)==null){
									frequency=0;
								}else{
									frequency=rs.getFloat(4);
								}
								
								int point;
								String SStr=rs.getString(6)==null?"":rs.getString(6).replaceAll(";", ",");
								String FStr=rs.getString(7)==null?"":rs.getString(7).replaceAll(";", ",");
								String AStr=rs.getString(8)==null?"":rs.getString(8).replaceAll(";", ",");
								String PStr=rs.getString(9)==null?"":rs.getString(9).replaceAll(";", ",");
								point=SStr.split(",").length;
								System.out.println(acquisitionTimeStr);
								raphicalUploadService.saveSurfaceCard(wellName,acquisitionTimeStr,point,stroke,frequency,SStr,FStr,AStr,PStr);
							}catch(Exception ee){
								ee.printStackTrace();
								continue;
							}
							
							
						}
						if(pstmt!=null)
	                		pstmt.close();  
	                	if(rs!=null)
	                		rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		//关闭数据库连接
		OracleJdbcUtis.closeDBConnection(outerConn, pstmt, rs);
		
		try {
			String json = "{success:true,flag:true}";
			response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw;
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<File> getSurfaceCardFile() {
		return SurfaceCardFile;
	}

	public void setSurfaceCardFile(List<File> surfaceCardFile) {
		SurfaceCardFile = surfaceCardFile;
	}

	public List<String> getSurfaceCardFileFileName() {
		return SurfaceCardFileFileName;
	}

	public void setSurfaceCardFileFileName(List<String> surfaceCardFileFileName) {
		SurfaceCardFileFileName = surfaceCardFileFileName;
	}

	public List<String> getSurfaceCardFileContentType() {
		return SurfaceCardFileContentType;
	}

	public void setSurfaceCardFileContentType(List<String> surfaceCardFileContentType) {
		SurfaceCardFileContentType = surfaceCardFileContentType;
	}

	
}