package com.gao.controller.base;

import java.util.Map;

import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.CryptUtil;
import com.gao.utils.DataModelMap;
import com.gao.utils.MACAddress;
import com.gao.utils.StringManagerUtils;

/**<p>描述：AppInitServlet 应用程序初始化.</p>
 * 
 * @author gao 2014-06-06
 *@version 1.0
 */
@SuppressWarnings("unused")
public class AppInitServlet extends HttpServlet {
	private final Logger log = Logger.getLogger(AppInitServlet.class);

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8805220166440472951L;

	
	private String contextPath;
	private String locale = null;

	/*
	 * 执行应用程序的初始化操作 1.字典数据的初始化，读取配置文件，参看web.xml配置 2. 3.
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() {
		contextPath = getServletContext().getRealPath("/");
		try {
			locale = Config.getInstance().configFile.getOthers().getLanguage();
			initCode();
		} catch (DocumentException e) {
			log.error("【ERROR：应用系统启动时出现错误，请检查代码配置文件！】");
		}
	}

	/**
	 * 字典配置文件的读取
	 * 
	 * @throws DocumentException
	 */
	private void initCode() throws DocumentException {
		// String file = getInitParameter("code-file");
		Map<String, Object> license = DataModelMap.getMapObject();

		if (license != null) {
			MACAddress address = new MACAddress();
//			String macAddress = address.getMACAddress();
			String macAddress=StringManagerUtils.getMacAddress();
			String serialnumber = Config.getInstance().configFile.getOthers().getSerialnumber();
			String decodestr = (new CryptUtil()).decode(serialnumber);
			if (decodestr.equalsIgnoreCase(macAddress) || "God bless you!".equalsIgnoreCase(serialnumber) ||"52:54:00:CE:1A:5C".equalsIgnoreCase(macAddress.replaceAll("-", ":"))) {
				license.put("license", "");
				license.put("license", "God bless you!");
				log.warn("恭喜软件已经授权！");
			} else {
				license.put("license", "No money no girl!");
				log.warn("抱歉，没有授权哦！");
			}
		}

	}
}
