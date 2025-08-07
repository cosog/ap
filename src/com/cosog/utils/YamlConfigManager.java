package com.cosog.utils;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class YamlConfigManager {

    private static final ReentrantLock GLOBAL_LOCK = new ReentrantLock(true);
    private static String CONFIG_PATH = ""; // 默认相对路径
    private static String BACKUP_DIR = ""; // 备份目录相对路径
    private static YamlConfigManager instance=new YamlConfigManager();
    
    public static YamlConfigManager getInstance(){
    	if(!StringManagerUtils.isNotNull(CONFIG_PATH)){
    		String filePath=Config.getInstance().configFile.getAp().getOemConfigFile();
        	String backupsPath=filePath.substring(0,filePath.lastIndexOf("/"))+"/backups";
        	
            // 初始化配置路径
            init(filePath, backupsPath);
    	}
    	return instance;
    }
    
    // 初始化配置路径
    public static void init(String configPath, String backupDir) {
        if (configPath != null && !configPath.isEmpty()) {
            CONFIG_PATH = configPath;
        }
        if (backupDir != null && !backupDir.isEmpty()) {
            BACKUP_DIR = backupDir;
        }
    }

    // 读取整个配置文件
    public static Map<String, Object> loadConfig() throws IOException {
        Path configPath = getAbsolutePath(CONFIG_PATH);
        return loadYaml(configPath);
    }

    // 更新单个配置项
    public static void updateConfig(String keyPath, Object newValue) throws Exception {
        GLOBAL_LOCK.lock();
        Path configPath = null;
        Path tempFile = null;
        Path backupPath = null;
        
        try {
            configPath = getAbsolutePath(CONFIG_PATH);
            
            // 确保配置文件存在
            if (!Files.exists(configPath)) {
                createDefaultConfig(configPath);
            }
            
            // 1. 创建备份目录
            Path backupDirPath = getAbsolutePath(BACKUP_DIR);
            createDirectoryIfNotExists(backupDirPath);
            
            // 2. 创建备份文件
            backupPath = createTimestampedBackup(configPath, backupDirPath);
            
            // 3. 安全读取配置（在锁定前）
            Map<String, Object> configMap = loadYaml(configPath);
            
            // 4. 记录旧值用于审计
            Object oldValue = getNestedProperty(configMap, keyPath);
            
            // 5. 更新配置
            updateNestedProperty(configMap, keyPath, newValue);
            
            // 6. 创建临时文件
            tempFile = Files.createTempFile("oemConfig", ".tmp");
            
            // 7. 将更新后的配置写入临时文件
            writeYaml(tempFile, configMap);
            
            // 8. 获取文件锁并替换文件
//            try (RandomAccessFile raf = new RandomAccessFile(configPath.toFile(), "rw");
//                 FileLock fileLock = raf.getChannel().tryLock()) {
//                
//                if (fileLock == null) {
//                    throw new IOException("配置文件被其他进程锁定");
//                }
//                
//                // 9. 原子替换文件
//                Files.copy(tempFile, configPath, StandardCopyOption.REPLACE_EXISTING);
//            }
            
         // 使用 NIO 原子操作替换文件
//            Files.move(tempFile, configPath, 
//                       StandardCopyOption.REPLACE_EXISTING,
//                       StandardCopyOption.ATOMIC_MOVE);
            
            try (FileChannel channel = FileChannel.open(configPath, 
                    StandardOpenOption.READ, 
                    StandardOpenOption.WRITE);
            		FileLock fileLock = channel.tryLock()) {

            	if (fileLock == null) {
            		throw new IOException("配置文件被其他进程锁定");
            	}

            	// 将临时文件内容直接写入锁定通道（避免文件占用）
            	try (FileInputStream tempStream = new FileInputStream(tempFile.toFile())) {
            		channel.truncate(0); // 清空原文件内容
            		tempStream.getChannel().transferTo(0, tempFile.toFile().length(), channel);
            	}

            }
            
            
            // 10. 审计日志
//            auditLog(keyPath, oldValue, newValue, backupDirPath);
            
            // 11. 通知配置刷新
            reloadConfiguration(keyPath);
            
        } catch (Exception ex) {
            // 12. 失败时恢复备份
            if (backupPath != null && Files.exists(backupPath)) {
                restoreBackup(backupPath, configPath);
            }
            throw ex;
        } finally {
            // 13. 清理临时文件
            if (tempFile != null && Files.exists(tempFile)) {
                Files.deleteIfExists(tempFile);
            }
            // 14. 清理旧备份
            Path backupDirPath = getAbsolutePath(BACKUP_DIR);
            cleanupOldBackups(backupDirPath);
            GLOBAL_LOCK.unlock();
        }
    }
    
    //更新整个配置文件
    public static void updateConfig(OEMConfigFile oemConfigFile) throws Exception {
        GLOBAL_LOCK.lock();
        Path configPath = null;
        Path tempFile = null;
        Path backupPath = null;
        
        try {
            configPath = getAbsolutePath(CONFIG_PATH);
            
            // 确保配置文件存在
            if (!Files.exists(configPath)) {
                createDefaultConfig(configPath);
            }
            
            //创建备份目录
            Path backupDirPath = getAbsolutePath(BACKUP_DIR);
            createDirectoryIfNotExists(backupDirPath);
            
            //创建备份文件
            backupPath = createTimestampedBackup(configPath, backupDirPath);
            
            //创建临时文件
            tempFile = Files.createTempFile("oemConfig", ".tmp");
            
            //将配置写入临时文件
            writeYaml(tempFile, oemConfigFile);
            
            try (FileChannel channel = FileChannel.open(configPath, 
                    StandardOpenOption.READ, 
                    StandardOpenOption.WRITE);
            		FileLock fileLock = channel.tryLock()) {

            	if (fileLock == null) {
            		throw new IOException("配置文件被其他进程锁定");
            	}

            	// 将临时文件内容直接写入锁定通道（避免文件占用）
            	try (FileInputStream tempStream = new FileInputStream(tempFile.toFile())) {
            		channel.truncate(0); // 清空原文件内容
            		tempStream.getChannel().transferTo(0, tempFile.toFile().length(), channel);
            	}
            }
            
        } catch (Exception ex) {
            //失败时恢复备份
            if (backupPath != null && Files.exists(backupPath)) {
                restoreBackup(backupPath, configPath);
            }
            throw ex;
        } finally {
            //清理临时文件
            if (tempFile != null && Files.exists(tempFile)) {
                Files.deleteIfExists(tempFile);
            }
            //清理旧备份
            Path backupDirPath = getAbsolutePath(BACKUP_DIR);
            cleanupOldBackups(backupDirPath);
            GLOBAL_LOCK.unlock();
        }
    }

    // 获取绝对路径（基于工作目录）
    private static Path getAbsolutePath(String relativePath) {
    	StringManagerUtils stringManagerUtils=new StringManagerUtils();
    	String path=stringManagerUtils.getFilePath2(relativePath);
    	Path configPath = Paths.get(path);
    	return configPath;
    	
//        return Paths.get(System.getProperty("user.dir"), relativePath);
    }

    // 创建默认配置文件
    private static void createDefaultConfig(Path configPath) throws IOException {
        // 创建父目录
        Path parentDir = configPath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
        
        // 创建默认配置
        Map<String, Object> defaultConfig = new LinkedHashMap<>();
        defaultConfig.put("server", Collections.singletonMap("port", 8080));
        defaultConfig.put("database", new LinkedHashMap<String, Object>() {{
            put("url", "jdbc:mysql://localhost:3306/mydb");
            put("username", "admin");
            put("password", "secret");
        }});
        defaultConfig.put("logging", Arrays.asList("INFO", "DEBUG", "ERROR"));
        
        // 使用SnakeYAML写入默认配置
        writeYaml(configPath, defaultConfig);
    }

    // 创建目录（如果不存在）
    private static void createDirectoryIfNotExists(Path dirPath) throws IOException {
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
    }

    // 创建带时间戳的备份
    private static Path createTimestampedBackup(Path configPath, Path backupDir) throws IOException {
        String timestamp = Instant.now().toString().replace(":", "-");
        String backupFileName = "oemConfig_" + timestamp + ".bak";
        Path backupPath = backupDir.resolve(backupFileName);
        
        Files.copy(configPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
        return backupPath;
    }

    // 安全读取YAML
    private static Map<String, Object> loadYaml(Path path) throws IOException {
    	InputStream in=null;
    	Map<String, Object> map=null;
    	try{
    		in = Files.newInputStream(path);
            Yaml yaml = new Yaml();
            map=yaml.load(in);
        }catch(Exception e){
			e.printStackTrace();
		}finally{
        	if(in!=null){
        		in.close();
        	}
        	
        }
    	return map;
    }

    // 获取嵌套属性值
    private static Object getNestedProperty(Map<String, Object> configMap, String keyPath) {
        String[] keys = keyPath.split("\\.");
        Map<String, Object> current = configMap;
        
        for (int i = 0; i < keys.length - 1; i++) {
            Object next = current.get(keys[i]);
            if (next instanceof Map) {
                current = (Map<String, Object>) next;
            } else {
                return null; // 路径不存在
            }
        }
        return current.get(keys[keys.length - 1]);
    }

    // 更新嵌套属性
    private static void updateNestedProperty(
        Map<String, Object> configMap, 
        String keyPath, 
        Object newValue
    ) {
        String[] keys = keyPath.split("\\.");
        Map<String, Object> current = configMap;
        
        for (int i = 0; i < keys.length - 1; i++) {
            Object next = current.get(keys[i]);
            if (!(next instanceof Map)) {
                // 路径不存在则创建
                Map<String, Object> newMap = new LinkedHashMap<>();
                current.put(keys[i], newMap);
                current = newMap;
            } else {
                current = (Map<String, Object>) next;
            }
        }
        current.put(keys[keys.length - 1], newValue);
    }

    // 使用SnakeYAML回写配置
    private static void writeYaml(Path path, Map<String, Object> data) throws IOException {
        // 配置YAML输出格式
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); // 块状格式
        options.setIndent(2); // 缩进2空格
        options.setPrettyFlow(true); // 美化输出
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN); // 纯文本标量
        
        // 创建临时文件
        Path tempFile = Files.createTempFile(path.getParent(), "oemConfig", ".tmp");
        
        BufferedWriter writer=null;
        try {
        	writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
            // 使用SnakeYAML写入数据
            Yaml yaml = new Yaml(options);
            yaml.dump(data, writer);
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	if(writer!=null){
        		writer.close();
        	}
        }
        
        // 原子替换原文件
        Files.move(
            tempFile, 
            path, 
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE
        );
    }
    
    private static void writeYaml(Path path, OEMConfigFile oemConfigFile) throws IOException {
        // 配置YAML输出格式
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); // 块状格式
        options.setIndent(2); // 缩进2空格
        options.setPrettyFlow(true); // 美化输出
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN); // 纯文本标量
        
        // 创建临时文件
        Path tempFile = Files.createTempFile(path.getParent(), "oemConfig", ".tmp");
        
        BufferedWriter writer=null;
        try {
        	writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);
            // 使用SnakeYAML写入数据
            Yaml yaml = new Yaml(options);
            yaml.dump(oemConfigFile, writer);
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	if(writer!=null){
        		writer.close();
        	}
        }
        
        // 原子替换原文件
        Files.move(
            tempFile, 
            path, 
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE
        );
    }

    // 恢复备份
    private static void restoreBackup(Path backupPath, Path configPath) throws IOException {
        if (Files.exists(backupPath)) {
            Files.copy(backupPath, configPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    // 清理旧备份
    private static void cleanupOldBackups(Path backupDir) throws IOException {
        if (!Files.exists(backupDir) || !Files.isDirectory(backupDir)) {
            return;
        }
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(backupDir, "*.bak")) {
            List<Path> backups = new ArrayList<>();
            stream.forEach(backups::add);
            
            if (backups.size() <= 3) {
                return;
            }
            
            // 按修改时间排序
            backups.sort(Comparator.comparingLong(p -> p.toFile().lastModified()));
            
            // 保留最近3个备份
            for (int i = 0; i < backups.size() - 3; i++) {
                Files.delete(backups.get(i));
            }
        }
    }

    // 审计日志
    private static void auditLog(String keyPath, Object oldValue, Object newValue, Path backupDir) {
        String logEntry = String.format("[%s] %s changed: %s -> %s%n", 
            Instant.now(), keyPath, 
            (oldValue != null) ? oldValue.toString() : "null", 
            (newValue != null) ? newValue.toString() : "null");
        
        try {
            Path logPath = backupDir.resolve("config_audit.log");
            Files.write(logPath, logEntry.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("审计日志写入失败: " + e.getMessage());
        }
    }

    // 配置重载
    private static void reloadConfiguration(String updatedKey) {
        // 实际实现应使用框架特定机制
        System.out.println("配置已更新: " + updatedKey + "，请重载配置");
    }

    // 使用示例
    public static void test() {
        try {
        	String filePath=Config.getInstance().configFile.getAp().getOemConfigFile();
        	String backupsPath=filePath.substring(0,filePath.lastIndexOf("/"))+"/backups";
        	
        	
            // 初始化配置路径
            init(filePath, backupsPath);
            
            // 示例1: 读取整个配置
            Map<String, Object> config = loadConfig();
            
            System.out.println("是否输出日志: " + ((Map<?, ?>) config.get("others")).get("printLog"));
            
            // 示例2: 更新配置项
            updateConfig("others.printLog", false);
            
//            // 示例3: 添加新配置项
//            updateConfig("features.newFeature.enabled", true);
            
            System.out.println("配置更新成功!");
        } catch (Exception e) {
            System.err.println("配置操作失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}