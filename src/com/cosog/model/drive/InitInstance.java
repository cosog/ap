package com.cosog.model.drive;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;


public class InitInstance  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int Id;
	
	private String Method;
	
	private String InstanceName;
	
	private String ProtocolName;
	
	private int ProtocolId;
    
    private String AcqProtocolType;
    
    private String CtrlProtocolType;
    
    private boolean SignInPrefixSuffixHex;
    
    private String SignInPrefix;
    
    private String SignInSuffix;
    
    private boolean SignInIDHex;
    
    private boolean HeartbeatPrefixSuffixHex;
    
    private String HeartbeatPrefix;
    
    private String HeartbeatSuffix;
    
    private int PacketSendInterval;
    
    private List<Group> AcqGroup;
    
    private List<Group> CtrlGroup;
	
    public InitInstance() {
		super();
	}
    
    public static class Group implements Serializable {

    	private static final long serialVersionUID = 1L;
    	
	    private Integer Id;
	    
    	private List<Integer> Addr;
    	
    	private List<String> AddrAndHighLowByte;

	    private int GroupTimingInterval;

	    public void setAddr(List<Integer> Addr){
	        this.Addr = Addr;
	    }
	    public List<Integer> getAddr(){
	        return this.Addr;
	    }
		public int getGroupTimingInterval() {
			return GroupTimingInterval;
		}
		public void setGroupTimingInterval(int groupTimingInterval) {
			GroupTimingInterval = groupTimingInterval;
		}
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public List<String> getAddrAndHighLowByte() {
			return AddrAndHighLowByte;
		}
		public void setAddrAndHighLowByte(List<String> addrAndHighLowByte) {
			AddrAndHighLowByte = addrAndHighLowByte;
		}
	}

	public String getMethod() {
		return Method;
	}

	public void setMethod(String method) {
		Method = method;
	}

	public String getInstanceName() {
		return InstanceName;
	}

	public void setInstanceName(String instanceName) {
		InstanceName = instanceName;
	}

	public String getProtocolName() {
		return ProtocolName;
	}

	public void setProtocolName(String protocolName) {
		ProtocolName = protocolName;
	}

	public String getAcqProtocolType() {
		return AcqProtocolType;
	}

	public void setAcqProtocolType(String acqProtocolType) {
		AcqProtocolType = acqProtocolType;
	}

	public String getCtrlProtocolType() {
		return CtrlProtocolType;
	}

	public void setCtrlProtocolType(String ctrlProtocolType) {
		CtrlProtocolType = ctrlProtocolType;
	}

	public String getSignInPrefix() {
		return SignInPrefix;
	}

	public void setSignInPrefix(String signInPrefix) {
		SignInPrefix = signInPrefix;
	}

	public String getSignInSuffix() {
		return SignInSuffix;
	}

	public void setSignInSuffix(String signInSuffix) {
		SignInSuffix = signInSuffix;
	}

	public String getHeartbeatPrefix() {
		return HeartbeatPrefix;
	}

	public void setHeartbeatPrefix(String heartbeatPrefix) {
		HeartbeatPrefix = heartbeatPrefix;
	}

	public String getHeartbeatSuffix() {
		return HeartbeatSuffix;
	}

	public void setHeartbeatSuffix(String heartbeatSuffix) {
		HeartbeatSuffix = heartbeatSuffix;
	}

	public List<Group> getAcqGroup() {
		return AcqGroup;
	}

	public void setAcqGroup(List<Group> acqGroup) {
		AcqGroup = acqGroup;
	}

	public List<Group> getCtrlGroup() {
		return CtrlGroup;
	}

	public void setCtrlGroup(List<Group> ctrlGroup) {
		CtrlGroup = ctrlGroup;
	}

	public int getPacketSendInterval() {
		return PacketSendInterval;
	}

	public void setPacketSendInterval(int packetSendInterval) {
		PacketSendInterval = packetSendInterval;
	}

	public boolean isSignInPrefixSuffixHex() {
		return SignInPrefixSuffixHex;
	}

	public void setSignInPrefixSuffixHex(boolean signInPrefixSuffixHex) {
		SignInPrefixSuffixHex = signInPrefixSuffixHex;
	}

	public boolean isSignInIDHex() {
		return SignInIDHex;
	}

	public void setSignInIDHex(boolean signInIDHex) {
		SignInIDHex = signInIDHex;
	}

	public boolean isHeartbeatPrefixSuffixHex() {
		return HeartbeatPrefixSuffixHex;
	}

	public void setHeartbeatPrefixSuffixHex(boolean heartbeatPrefixSuffixHex) {
		HeartbeatPrefixSuffixHex = heartbeatPrefixSuffixHex;
	}
	
	public String toString(){
		Gson gson = new Gson();
		String result="";
		try {
			result=gson.toJson(this);
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode jsonNodes;
			jsonNodes = objectMapper.readValue(result, ObjectNode.class);
			
			jsonNodes.remove("Id");
			jsonNodes.remove("ProtocolId");
			Iterator<Entry<String, JsonNode>> iterator = jsonNodes.fields();
	        while (iterator.hasNext()) {
	            Entry<String, JsonNode> entry = iterator.next();
	            if("AcqGroup".equalsIgnoreCase(entry.getKey())){
	            	if (entry.getValue().isArray()) {
	            	    for (JsonNode acqGroup : entry.getValue()) {
	            	    	((ObjectNode)acqGroup).remove("Id");
	            	    	((ObjectNode)acqGroup).remove("AddrAndHighLowByte");
	            	    }
	            	}
	            }else if("CtrlGroup".equalsIgnoreCase(entry.getKey())){
	            	if (entry.getValue().isArray()) {
	            	    for (JsonNode ctrlGroup : entry.getValue()) {
	            	    	((ObjectNode)ctrlGroup).remove("Id");
	            	    	((ObjectNode)ctrlGroup).remove("GroupTimingInterval");
	            	    	((ObjectNode)ctrlGroup).remove("AddrAndHighLowByte");
	            	    }
	            	}
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
	
	public boolean containGroup(int type,int groupId){
		boolean r=false;
		if(type==0){//采集组
			if(this.getAcqGroup()!=null && this.getAcqGroup().size()>0){
				for(Group group:this.getAcqGroup()){
					if(groupId==group.getId()){
						r=true;
						break;
					}
				}
			}
		}else if(type==1){//控制组
			if(this.getCtrlGroup()!=null && this.getCtrlGroup().size()>0){
				for(Group group:this.getCtrlGroup()){
					if(groupId==group.getId()){
						r=true;
						break;
					}
				}
			}
		}
		return r;
	}
	
	public Group getGroup(int type,int groupId){
		Group rtnGroup=null;
		if(type==0){//采集组
			if(this.getAcqGroup()!=null && this.getAcqGroup().size()>0){
				for(Group group:this.getAcqGroup()){
					if(groupId==group.getId()){
						rtnGroup=group;
						break;
					}
				}
			}
		}else if(type==1){//控制组
			if(this.getCtrlGroup()!=null && this.getCtrlGroup().size()>0){
				for(Group group:this.getCtrlGroup()){
					if(groupId==group.getId()){
						rtnGroup=group;
						break;
					}
				}
			}
		}
		return rtnGroup;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getProtocolId() {
		return ProtocolId;
	}

	public void setProtocolId(int protocolId) {
		ProtocolId = protocolId;
	}
}
