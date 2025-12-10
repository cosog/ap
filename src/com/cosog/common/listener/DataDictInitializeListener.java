package com.cosog.common.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cosog.controller.base.BaseController;
import com.cosog.task.CalculateDataManagerTask;
import com.cosog.task.DatabaseMaintenanceTask;
import com.cosog.utils.Config;
import com.cosog.utils.StringManagerUtils;

/**
 * <p>描述：程序一开始启动，就会启动该监听，执行数据字典缓存操作</p>
 * 
 * @author gao 2014-05-09
 *@version 1.0
 */
public class DataDictInitializeListener implements ServletContextListener {
	 private boolean cache=true;
	public void contextDestroyed(ServletContextEvent event) { 
		ServletContext context = event.getServletContext();
		CalculateDataManagerTask.scheduledDestory();
		DatabaseMaintenanceTask.scheduledDestory();
        
		StringManagerUtils.printLog("context game over ",0);
		context.log("context game over ", new Throwable());
	}

	public void contextInitialized(ServletContextEvent event) {
		cache=Config.getInstance().configFile.getAp().getOthers().getCache();
		if(cache){
			StringManagerUtils.printLog("数据字典缓存启动中...",0);
			ServletContext context = event.getServletContext();
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
			BaseController base = (BaseController) ctx.getBean("baseAction");
//			base.initDataDictionaryPutInCache();
		}else{
			StringManagerUtils.printLog("数据字典缓存已经被禁用！",0);
		}
	}
}