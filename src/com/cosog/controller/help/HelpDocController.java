package com.cosog.controller.help;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.utils.MarkDown2HtmlWrapper;
import com.cosog.utils.MarkdownEntity;
import com.cosog.utils.StringManagerUtils;

@Controller
@RequestMapping("/helpDocController")
@Scope("prototype")
public class HelpDocController extends BaseController{
	private static final long serialVersionUID = 1L;
	@RequestMapping("/getHelpDocHtml")
	public String getHelpDocHtml() throws Exception {
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
//		String path=stringManagerUtils.getFilePath("help.md","readme/");
//		MarkdownEntity html = MarkDown2HtmlWrapper.ofFile(path);
//		String fileContent=html.toString();
		
		String path=stringManagerUtils.getFilePath("ap.html","readme/ap/");
		String fileContent=stringManagerUtils.readFile(path,"utf-8");
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(fileContent);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
