package com.cosog.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;

public class FileReadUtil {
	public static String readAll(String fileName) throws IOException {
        BufferedReader reader = createLineRead(fileName);
        List<String> lines = reader.lines().collect(Collectors.toList());
        return Joiner.on("\n").join(lines);
    }


    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     *
     * @param fileName 文件的名
     */
    public static InputStream createByteRead(String fileName) throws IOException {
        return getStreamByFileName(fileName);
    }


    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     *
     * @param fileName 文件名
     */
    public static Reader createCharRead(String fileName) throws IOException {
        return new InputStreamReader(getStreamByFileName(fileName), Charset.forName("UTF-8"));
    }


    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @param fileName 文件名
     */
    public static BufferedReader createLineRead(String fileName) throws IOException {
        return new BufferedReader(new InputStreamReader(getStreamByFileName(fileName), Charset.forName("UTF-8")));
    }


    public static InputStream getStreamByFileName(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName should not be null!");
        }
        InputStream is=null;
        Path path=null;
        try {
        	try {
        		path = Paths.get(fileName);
        	} catch (Exception e) {
    			if(fileName.startsWith("/")){
    				fileName=fileName.substring(1);
    			}else{
    				fileName="/"+fileName;
    			}
    			try {
    				path = Paths.get(fileName);
            	} catch (Exception e2) {
            		throw new IllegalArgumentException("file not be find!");
            	}
    		}
        	is= Files.newInputStream(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return is;
    }

    /**
     * 将字节数组转换成16进制字符串
     */
    private static String bytesToHex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }


        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 获取文件对应的魔数
     *
     * @param file
     * @return
     */
    public static String getMagicNum(String file) {
        try (InputStream stream = FileReadUtil.getStreamByFileName(file)) {

            byte[] b = new byte[28];
            stream.read(b, 0, 28);

            return bytesToHex(b);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取流文件对应的魔数
     * @param inputStream
     * @return
     */
    public static String getMagicNum(ByteArrayInputStream inputStream) {
        byte[] bytes = new byte[28];
        inputStream.read(bytes, 0, 28);
        inputStream.reset();
        return bytesToHex(bytes);
    }
}
