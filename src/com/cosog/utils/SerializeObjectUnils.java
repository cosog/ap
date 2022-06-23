package com.cosog.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化工具类
 * */
public class SerializeObjectUnils {
	//序列化
    public static byte[] serialize(Object obj) {
        ObjectOutputStream obi = null;
        ByteArrayOutputStream bai = null;
        try {
            bai = new ByteArrayOutputStream();
            obi = new ObjectOutputStream(bai);
            obi.writeObject(obj);
            obi.flush();
            byte[] byt = bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
        	try {
        		bai.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
            try {
            	obi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return null;
    }

    // 反序列化
    public static Object unserizlize(byte[] byt) {
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        bis = new ByteArrayInputStream(byt);
        try {
            oii = new ObjectInputStream(bis);
            Object obj = oii.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	try {
        		bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
            try {
            	oii.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }

        return null;
    }
}
