package com.cosog.utils;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class AdInitThreadPoolConfig {
	public static AdInitThreadPoolConfigFile adInitThreadPoolConfigFile=null;
	private static AdInitThreadPoolConfig instance=new AdInitThreadPoolConfig();
	
	public static AdInitThreadPoolConfig getInstance(){
		if(adInitThreadPoolConfigFile==null){
			Yaml yaml = new Yaml(new Constructor(AdInitThreadPoolConfigFile.class));
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/adInitThreadPoolConfig.yml");
			adInitThreadPoolConfigFile = yaml.load(inputStream);
		}
		return instance;
	}
}
