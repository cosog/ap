package com.gao.utils;

/**
 * 　　* 重写org.logicalcobwebs.proxool.ProxoolDataSource 　　* 重置数据库密码为明文 　　* @author
 * Administrator 　　* 　　
 */
public class DataSource extends org.logicalcobwebs.proxool.ProxoolDataSource {

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return super.getPassword();
	}

	@Override
	public String getUser() {
		// TODO Auto-generated method stub
		return super.getUser();
	}

	@SuppressWarnings("unused")
	@Override
	public void setPassword(String password)  {
		// TODO Auto-generated method stub

		EncryptionDecryption des;
		try {
			des = new EncryptionDecryption("Oh my God!");
			//password=des.decrypt(password);
//			System.out.println("解密后的字符：************************************" + password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 自定义密钥

		super.setPassword(password);
	}

	@Override
	public void setUser(String user) {
		// TODO Auto-generated method stub
		super.setUser(user);
	}

}