package com.cosog.model.calculate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

public class PCPProductionData {
	
	private String AKString;                                              //秘钥
	private String WellName;                                              //井名
	private PCPCalculateRequestData.FluidPVT FluidPVT;                                            //流体PVT物性
	private PCPCalculateRequestData.Reservoir Reservoir;                                          //油藏物性
	private PCPCalculateRequestData.RodString RodString;                                          //抽油杆参数
	private PCPCalculateRequestData.TubingString TubingString;                                    //油管参数
	private PCPCalculateRequestData.Pump Pump;                                                    //抽油泵参数
	private PCPCalculateRequestData.TailTubingString TailTubingString;		                      //尾管参数
	private PCPCalculateRequestData.CasingString CasingString;                                    //套管参数
	private PCPCalculateRequestData.Production Production;                      //生产数据
	private PCPCalculateRequestData.ManualIntervention ManualIntervention;                        //人工干预
	public PCPProductionData(String aKString, String wellName,
			com.cosog.model.calculate.PCPCalculateRequestData.FluidPVT fluidPVT,
			com.cosog.model.calculate.PCPCalculateRequestData.Reservoir reservoir,
			com.cosog.model.calculate.PCPCalculateRequestData.RodString rodString,
			com.cosog.model.calculate.PCPCalculateRequestData.TubingString tubingString,
			com.cosog.model.calculate.PCPCalculateRequestData.Pump pump,
			com.cosog.model.calculate.PCPCalculateRequestData.TailTubingString tailTubingString,
			com.cosog.model.calculate.PCPCalculateRequestData.CasingString casingString,
			com.cosog.model.calculate.PCPCalculateRequestData.Production production,
			com.cosog.model.calculate.PCPCalculateRequestData.ManualIntervention manualIntervention) {
		super();
		AKString = aKString;
		WellName = wellName;
		FluidPVT = fluidPVT;
		Reservoir = reservoir;
		RodString = rodString;
		TubingString = tubingString;
		Pump = pump;
		TailTubingString = tailTubingString;
		CasingString = casingString;
		Production = production;
		ManualIntervention = manualIntervention;
	}
	public String getAKString() {
		return AKString;
	}
	public void setAKString(String aKString) {
		AKString = aKString;
	}
	public String getWellName() {
		return WellName;
	}
	public void setWellName(String wellName) {
		WellName = wellName;
	}
	public PCPCalculateRequestData.FluidPVT getFluidPVT() {
		return FluidPVT;
	}
	public void setFluidPVT(PCPCalculateRequestData.FluidPVT fluidPVT) {
		FluidPVT = fluidPVT;
	}
	public PCPCalculateRequestData.Reservoir getReservoir() {
		return Reservoir;
	}
	public void setReservoir(PCPCalculateRequestData.Reservoir reservoir) {
		Reservoir = reservoir;
	}
	public PCPCalculateRequestData.RodString getRodString() {
		return RodString;
	}
	public void setRodString(PCPCalculateRequestData.RodString rodString) {
		RodString = rodString;
	}
	public PCPCalculateRequestData.TubingString getTubingString() {
		return TubingString;
	}
	public void setTubingString(PCPCalculateRequestData.TubingString tubingString) {
		TubingString = tubingString;
	}
	public PCPCalculateRequestData.Pump getPump() {
		return Pump;
	}
	public void setPump(PCPCalculateRequestData.Pump pump) {
		Pump = pump;
	}
	public PCPCalculateRequestData.TailTubingString getTailTubingString() {
		return TailTubingString;
	}
	public void setTailTubingString(PCPCalculateRequestData.TailTubingString tailTubingString) {
		TailTubingString = tailTubingString;
	}
	public PCPCalculateRequestData.CasingString getCasingString() {
		return CasingString;
	}
	public void setCasingString(PCPCalculateRequestData.CasingString casingString) {
		CasingString = casingString;
	}
	public PCPCalculateRequestData.Production getProduction() {
		return Production;
	}
	public void setProduction(PCPCalculateRequestData.Production production) {
		Production = production;
	}
	public PCPCalculateRequestData.ManualIntervention getManualIntervention() {
		return ManualIntervention;
	}
	public void setManualIntervention(PCPCalculateRequestData.ManualIntervention manualIntervention) {
		ManualIntervention = manualIntervention;
	}

	public String toString(){
		Gson gson = new Gson();
		String result="";
		try {
			result=gson.toJson(this);
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode jsonNodes;
			jsonNodes = objectMapper.readValue(result, ObjectNode.class);
			Iterator<Entry<String, JsonNode>> iterator = jsonNodes.fields();
	        while (iterator.hasNext()) {
	            Entry<String, JsonNode> entry = iterator.next();
	            if("Production".equalsIgnoreCase(entry.getKey())){
	            	((ObjectNode)entry.getValue()).remove("WeightWaterCut");
	            	break;
	            }
	        }
	        result = objectMapper.writeValueAsString(jsonNodes);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
	}
}
