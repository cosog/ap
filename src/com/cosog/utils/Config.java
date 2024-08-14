package com.cosog.utils;

import java.io.IOException;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class Config {
	public static ConfigFile configFile=null;
	private static Config instance=new Config();
	
	public static Config getInstance(){
		if(configFile==null){
			InputStream inputStream=null;
			InputStream oemInputStream=null;
			try{
				Yaml yaml = new Yaml(new Constructor(ConfigFile.class));
				inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/config.yml");
				configFile = yaml.load(inputStream);
				
				OEMConfigFile oemConfigFile=null;
				try{
					if(configFile!=null && configFile.getAp()!=null){
						String oemConfigFileName=configFile.getAp().getOemConfigFile();
						if(StringManagerUtils.isNotNull(oemConfigFileName)){
							if(!oemConfigFileName.endsWith(".yml")){
								oemConfigFileName+=".yml";
							}
							Yaml oemYaml = new Yaml(new Constructor(OEMConfigFile.class));
							oemInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/"+oemConfigFileName+"");
							oemConfigFile = oemYaml.load(oemInputStream);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				if(configFile!=null && configFile.getAp()!=null && oemConfigFile!=null ){
					configFile.getAp().setOem(oemConfigFile.getOem());
					configFile.getAp().setReport(oemConfigFile.getReport());
					configFile.getAp().setEmail(oemConfigFile.getEmail());
					configFile.getAp().setOthers(oemConfigFile.getOthers());
				}
				
				if(configFile.getAp()!=null && configFile.getAp().getOthers()!=null && configFile.getAp().getOthers().getExportLimit()>65534){
					configFile.getAp().getOthers().setExportLimit(65534);
				}
				if(configFile.getAp().getOthers().getVacuateThreshold()<=0){
					configFile.getAp().getOthers().setVacuateThreshold(500);
				}
				if(configFile.getAp()!=null && configFile.getAp().getReport()!=null){
					if(configFile.getAp().getReport().getInterval()<=0){
						configFile.getAp().getReport().setInterval(2);
					}
					if(configFile.getAp().getReport().getOffsetHour()<0 || configFile.getAp().getReport().getOffsetHour()>12){
						configFile.getAp().getReport().setOffsetHour(0);
					}
					if(configFile.getAp().getReport().getDelay()<0 || configFile.getAp().getReport().getDelay()>30){
						configFile.getAp().getReport().setDelay(5);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(inputStream!=null){
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if(oemInputStream!=null){
					try {
						oemInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return instance;
	}
}
