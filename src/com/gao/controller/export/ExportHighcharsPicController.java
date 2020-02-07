package com.gao.controller.export;


import java.io.StringReader;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fop.svg.PDFTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.Module;
import com.gao.service.base.CommonDataService;
import com.gao.utils.ParamUtils;


/**
 * <p>
 * 描述：华北油田通信公司油井实时数据监测
 * </p>
 * 
 * @author zhao 2017-08-27
 * @version 1.0
 */
@Controller
@RequestMapping("/exportHighcharsPicController")
@Scope("prototype")
public class ExportHighcharsPicController extends BaseController {

	private static Log log = LogFactory.getLog(ExportHighcharsPicController.class);
	private static final long serialVersionUID = 1L;
	private List<Module> list = null;
	@Autowired
	private CommonDataService commonDataService;

	public List<Module> getList() {
		return list;
	}

	public void setList(List<Module> list) {
		this.list = list;
	}
	
	@RequestMapping("/export")
	public void export() {  
		String type = ParamUtils.getParameter(request, "type");
		String svg = ParamUtils.getParameter(request, "svg");
		
        try {
            request.setCharacterEncoding("utf-8");// 注意编码  
            ServletOutputStream out = response.getOutputStream();  
            if (null != type && null != svg) {  
                svg = svg.replaceAll(":rect", "rect").replaceAll("''", "'").replaceAll("<br>", "<br/>");
                String ext = "";  
                SVGAbstractTranscoder t = null;  
                if (type.equals("image/png")) {  
                    ext = "png";  
                    t = new PNGTranscoder();
                } else if (type.equals("image/jpeg")) {  
                    ext = "jpg";  
                    t = new JPEGTranscoder();
                } else if (type.equals("application/pdf")) {  
                    ext = "pdf";  
                    t = new PDFTranscoder();
                } else if (type.equals("image/svg+xml")) {  
                    ext = "svg";  
                }  
  
                response.addHeader("Content-Disposition","attachment; filename=chart." + ext);  
                response.addHeader("Content-Type", type);  
  
                if (null != t) {  
                    TranscoderInput input = new TranscoderInput(new StringReader(svg));  
                    TranscoderOutput output = new TranscoderOutput(out);  
                    t.transcode(input, output);  
                }  
            }  
            out.flush();  
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
}
