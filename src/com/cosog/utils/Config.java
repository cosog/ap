package com.cosog.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Config {
	private static final ReentrantLock GLOBAL_LOCK = new ReentrantLock(true);
	public static ConfigFile configFile=null;
	public static OEMConfigFile oemConfigFile=null;
	private static Config instance=new Config();
	private static final String BACKUP_DIR = "config/backups/";
	
	public static Config getInstance(){
		if(configFile==null){
			InputStream inputStream=null;
			InputStream oemInputStream=null;
			try{
				Yaml yaml = new Yaml(new Constructor(ConfigFile.class));
				inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/config.yml");
				configFile = yaml.load(inputStream);
				
				if(oemConfigFile==null){
					try{
						if(configFile!=null && configFile.getAp()!=null){
							String oemConfig=configFile.getAp().getOemConfigFile();
							
							StringManagerUtils stringManagerUtils=new StringManagerUtils();
					    	String path=stringManagerUtils.getFilePath2(oemConfig);
					    	Path configPath = Paths.get(path);
					    	
					    	
					    	InputStream in=null;
					    	try{
					    		in = Files.newInputStream(configPath);
					    		Yaml oemYaml = new Yaml(new Constructor(OEMConfigFile.class));
					            oemConfigFile=oemYaml.load(in);
					        }catch(Exception e){
								e.printStackTrace();
							}finally{
					        	if(in!=null){
					        		in.close();
					        	}
					        	
					        }
					    	
							
//							String oemConfigFileName=configFile.getAp().getOemConfigFile();
//							if(StringManagerUtils.isNotNull(oemConfigFileName)){
//								if(!oemConfigFileName.endsWith(".yml")){
//									oemConfigFileName+=".yml";
//								}
//								Yaml oemYaml = new Yaml(new Constructor(OEMConfigFile.class));
//								oemInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/"+oemConfigFileName+"");
//								oemConfigFile = oemYaml.load(oemInputStream);
//							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
				
				if(configFile!=null && configFile.getAp()!=null && oemConfigFile!=null ){
					configFile.getAp().setOem(oemConfigFile.getOem());
					configFile.getAp().setReport(oemConfigFile.getReport());
					configFile.getAp().setEmail(oemConfigFile.getEmail());
					configFile.getAp().setModuleContent(oemConfigFile.getModuleContent());
					configFile.getAp().setDataVacuate(oemConfigFile.getDataVacuate());
					configFile.getAp().setOthers(oemConfigFile.getOthers());
					
					String loginLanguage=configFile.getAp().getOthers().getLoginLanguage().toLowerCase().replace("zh_cn", "zh_CN");
					configFile.getAp().getOthers().setLoginLanguage(loginLanguage);
					
					configFile.getAp().setDatabaseMaintenance(oemConfigFile.getDatabaseMaintenance());
				}
				
				if(configFile.getAp()!=null && configFile.getAp().getOthers()!=null && configFile.getAp().getOthers().getExportLimit()>65534){
					configFile.getAp().getOthers().setExportLimit(65534);
				}
				if(configFile.getAp().getDataVacuate().getVacuateRecord()<=0){
					configFile.getAp().getDataVacuate().setVacuateRecord(500);
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
	
	public static boolean updateConfig(){
		boolean r=true;
		try{
			if(configFile!=null && configFile.getAp()!=null && oemConfigFile!=null ){
				configFile.getAp().setOem(oemConfigFile.getOem());
				configFile.getAp().setReport(oemConfigFile.getReport());
				configFile.getAp().setEmail(oemConfigFile.getEmail());
				configFile.getAp().setModuleContent(oemConfigFile.getModuleContent());
				configFile.getAp().setDataVacuate(oemConfigFile.getDataVacuate());
				configFile.getAp().setOthers(oemConfigFile.getOthers());
				
				String loginLanguage=configFile.getAp().getOthers().getLoginLanguage().toLowerCase().replace("zh_cn", "zh_CN");
				configFile.getAp().getOthers().setLoginLanguage(loginLanguage);
				
				configFile.getAp().setDatabaseMaintenance(oemConfigFile.getDatabaseMaintenance());
			}
			
			if(configFile.getAp()!=null && configFile.getAp().getOthers()!=null && configFile.getAp().getOthers().getExportLimit()>65534){
				configFile.getAp().getOthers().setExportLimit(65534);
			}
			if(configFile.getAp().getDataVacuate().getVacuateRecord()<=0){
				configFile.getAp().getDataVacuate().setVacuateRecord(500);
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
			r=false;
			e.printStackTrace();
		}
		return r;
	}
	
	@SuppressWarnings("static-access")
	public static boolean updateOemConfigFile(String keyPath, Object newValue){
		boolean r=true;
		try {
			YamlConfigManager.getInstance().updateConfig(keyPath,newValue);
		} catch (Exception e) {
			r=false;
			e.printStackTrace();
		}
		return r;
	}
	
	public static boolean updateOemConfigFile(OEMConfigFile oemConfigFile){
		boolean r=true;
		try {
			YamlConfigManager.getInstance().updateConfig(oemConfigFile);
		} catch (Exception e) {
			r=false;
			e.printStackTrace();
		}
		return r;
	}
	
	private static Path createTimestampedBackup(Path configPath) throws IOException {
        Files.createDirectories(Paths.get(BACKUP_DIR));
        String timestamp = Instant.now().toString().replace(":", "-");
        Path backupPath = Paths.get(BACKUP_DIR, "config_" + timestamp + ".bak");
        Files.copy(configPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
        return backupPath;
    }
	
	// 原子写入YAML
    private static void writeYamlAtomically(Path path) throws IOException {
        Path tempFile = Files.createTempFile(path.getParent(), "config", ".tmp");
        
        // 保留原始格式
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(2);
        options.setPrettyFlow(true);
        
        // 写入临时文件
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8)) {
            new Yaml(options).dump(oemConfigFile, writer);
        }
        
        // 设置文件权限 (rw-r-----)
        try {
            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.GROUP_READ);
            Files.setPosixFilePermissions(tempFile, perms);
        } catch (UnsupportedOperationException e) {
            // 非POSIX系统（如Windows），忽略权限设置
        }
        
        // 原子替换
        Files.move(tempFile, path, 
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE);
    }
    
 // 恢复备份
    private static void restoreBackup(Path backupPath, Path configPath) throws IOException {
        if (Files.exists(backupPath)) {
            Files.move(backupPath, configPath, 
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE);
        }
    }
    
 // 清理旧备份
    private static void cleanupOldBackups() throws IOException {
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists() || !backupDir.isDirectory()) {
            return;
        }
        
        File[] backupFiles = backupDir.listFiles((dir, name) -> name.endsWith(".bak"));
        if (backupFiles == null || backupFiles.length <= 3) {
            return;
        }
        
        // 按修改时间排序
        Arrays.sort(backupFiles, Comparator.comparingLong(File::lastModified));
        
        // 保留最近3个备份
        for (int i = 0; i < backupFiles.length - 3; i++) {
            Files.delete(backupFiles[i].toPath());
        }
    }
}
