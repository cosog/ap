package com.cosog.utils;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class Config {
	public static ConfigFile configFile=null;
	private static Config instance=new Config();
	
	public static Config getInstance(){
		if(configFile==null){
			Yaml yaml = new Yaml(new Constructor(ConfigFile.class));
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/config.yml");
			configFile = yaml.load(inputStream);
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
		}
		return instance;
	}
}
