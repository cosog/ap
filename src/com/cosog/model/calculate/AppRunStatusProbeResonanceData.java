package com.cosog.model.calculate;

import java.util.List;

public class AppRunStatusProbeResonanceData {

	private String Ver;

    private List<String> URL;

    public void setVer(String Ver){
        this.Ver = Ver;
    }
    public String getVer(){
        return this.Ver;
    }
    public void setURL(List<String> URL){
        this.URL = URL;
    }
    public List<String> getURL(){
        return this.URL;
    }
}
