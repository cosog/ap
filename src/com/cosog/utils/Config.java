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
				
				Yaml oemYaml = new Yaml(new Constructor(OEMConfigFile.class));
				oemInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/OEMConfig.yml");
				OEMConfigFile oemConfigFile = oemYaml.load(oemInputStream);
				
				if(configFile!=null && configFile.getAp()!=null && oemConfigFile!=null && oemConfigFile.getList()!=null
						&& configFile.getAp().getOemIndex()>=oemConfigFile.getList().size()){
					configFile.getAp().setOemIndex(0);
				}
				
				if(configFile!=null && configFile.getAp()!=null && oemConfigFile!=null && oemConfigFile.getList()!=null
						&& configFile.getAp().getOemIndex()<oemConfigFile.getList().size()){
					configFile.getAp().setOem(oemConfigFile.getList().get(configFile.getAp().getOemIndex()).getOem());
					configFile.getAp().setReport(oemConfigFile.getList().get(configFile.getAp().getOemIndex()).getReport());
					configFile.getAp().setEmail(oemConfigFile.getList().get(configFile.getAp().getOemIndex()).getEmail());
					configFile.getAp().setOthers(oemConfigFile.getList().get(configFile.getAp().getOemIndex()).getOthers());
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
