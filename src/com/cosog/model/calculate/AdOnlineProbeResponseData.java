package com.cosog.model.calculate;

import java.util.List;

public class AdOnlineProbeResponseData {

    private List<String> OnlineID;
    
    private List<String> OnlineIPPort;

	public List<String> getOnlineID() {
		return OnlineID;
	}

	public void setOnlineID(List<String> onlineID) {
		OnlineID = onlineID;
	}

	public List<String> getOnlineIPPort() {
		return OnlineIPPort;
	}

	public void setOnlineIPPort(List<String> onlineIPPort) {
		OnlineIPPort = onlineIPPort;
	}
}
