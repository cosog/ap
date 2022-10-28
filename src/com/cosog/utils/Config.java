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
		}
		return instance;
	}
}
