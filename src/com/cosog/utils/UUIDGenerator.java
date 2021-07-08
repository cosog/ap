package com.cosog.utils;

import java.io.Serializable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
//import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
//import org.hibernate.util.PropertiesHelper;

/**
 * @title UUIDGenerator
 * @description 随机生成32位UUID，格式如：a11686c39a154cf2a5238fb14cf3d097 <br>
 * <br>
 */
public class UUIDGenerator implements IdentifierGenerator, Configurable {

	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		 UUIDImpl pkg = UUIDImpl.getInstance(); 
		 String pk =pkg.genericPK();
		 Long myPk=Long.parseLong(pk);
		return myPk;
	}

	/**
	 * 主键生成机制
	 * 
	 * @return
	 */
	public static String randomUUID() {
		String result = "";
		UUID uuid = java.util.UUID.randomUUID();
		String temp = uuid.toString();
		StringTokenizer token = new StringTokenizer(temp, "-");
		while (token.hasMoreTokens()) {
			result += token.nextToken();
		}
		return result;
	}

	public void configure(Type type, Properties params, Dialect d) {
		@SuppressWarnings("unused")
		String sep = PropertiesHelper.getString("separator", params, "");
	}

	@Override
	public void configure(Type arg0, Properties arg1, ServiceRegistry arg2) throws MappingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor arg0, Object arg1) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

}