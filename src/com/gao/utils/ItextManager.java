package com.gao.utils;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;

public class ItextManager {

    private Font font;
    private Font titlefont;
    private Font subheadfont;
    private BaseFont bfChinese;

    public ItextManager() throws Exception {
        // 设置中文字体  
        bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        font = new Font(bfChinese);
        font.setSize(12);
        font.setStyle(FontFactory.HELVETICA);
        font.setColor(new Color(0, 0, 0));
        
        subheadfont = new Font(bfChinese);
        subheadfont.setSize(12);
        subheadfont.setStyle(FontFactory.HELVETICA);
        subheadfont.setStyle(Font.BOLD);//加粗  
        subheadfont.setColor(new Color(0, 0, 0));
        
        titlefont = new Font(bfChinese);
        titlefont.setSize(22);
        titlefont.setStyle(FontFactory.HELVETICA);
        titlefont.setStyle(Font.BOLD);//加粗  
        titlefont.setColor(new Color(0, 0, 0));
    }
    public static ItextManager getInstance() throws Exception {
        return new ItextManager();
    }

    public void createRtfContext(List < String > imgList, OutputStream out, String type,List<?> list,String title) {
        Document doc = new Document(PageSize.A4, 40, 40, 40, 40); //创建文档对象 纸张大小为A4
        float lineHeight1 = (float)25.0;
        PdfPTable headerTable = null;
        PdfPTable leftTable = null;//创建左边表格
        PdfPCell leftCell = null;
        PdfPCell cell=null;
        String line="";
        Image img = null;
        float heigth =0;
        float width =0;
        int percent =0;
        float[] wid1 ={0.6f,0.4f}; //两列宽度的比例
        try {
            if ("word".equals(type)) {
                RtfWriter2.getInstance(doc, out);
                line="--------------------------------------------------------------------------------------------------------------------------------";
            } else if ("pdf".equals(type)) {
                PdfWriter.getInstance(doc, out);
                line="------------------------------------------------------------------------------------------------------------------";
            }
            //设置文档创建日期
            doc.addCreationDate();
            //设置标题
            doc.addTitle(getChinese(title));
            //构建页脚
            HeaderFooter footer=new HeaderFooter(new Phrase(), true);
            //设置页脚是否有边框  0表示无   1上边框   2下边框  3上下边框都有 默认都有
            footer.setBorder(0);
            //设置页脚的对齐方式
            footer.setAlignment(Element.ALIGN_CENTER);
            //将页脚添加到文档中
            doc.setFooter(footer);
            //打开文档开始写内容
            doc.open();
            if(list.size()>0){
            	Object[] obj=(Object[]) list.get(0);
            	
            	//添加标题部分
                Paragraph par1=null;
                par1=new Paragraph(title+"-"+obj[0],titlefont);
                par1.setAlignment(Element.ALIGN_CENTER);
                doc.add(par1);
                par1=new Paragraph("日期："+ new SimpleDateFormat("yyyy.MM.dd").format(new Date())+"",font);
                par1.setAlignment(Element.ALIGN_RIGHT);
                doc.add(par1);
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
            	
                //添加基本信息部分
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                leftTable.setWidthPercentage(100);//表格的宽度为100%
                
                leftCell = new PdfPCell(new Paragraph("井名："+obj[0]+"",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("单位："+obj[1]+"",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
//                leftCell = new PdfPCell(new Paragraph("目前平衡度："+obj[4],font));
//                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                leftCell.setBorder(0);
//                leftCell.setFixedHeight(lineHeight1);
//                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("平衡状态："+obj[5],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("整体移动距离："+obj[6]+" cm",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("+代表外移   -代表内移",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                doc.add(leftTable);
                
                //添加抽油机和功图部分
                headerTable = new PdfPTable(2);
                headerTable.setHorizontalAlignment(Element.ALIGN_CENTER); //垂直居中
                headerTable.setWidthPercentage(100);//表格的宽度为100%
                headerTable.setWidths(wid1); 
                headerTable.getDefaultCell().setBorderWidth(0); //不显示边框
                
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                
                leftCell = new PdfPCell(new Paragraph("1、抽油机&功图",subheadfont));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("抽油机厂家："+obj[2],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("抽油机型号："+obj[3],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("功图采集时间："+obj[14],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("冲程："+obj[12]+" m",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("冲次："+obj[13]+" 1/min",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
                
                if(imgList.size()>0){
                	//将表格加入到第一列中
                    cell = new PdfPCell(leftTable);
                    cell.setPadding(0);
                    cell.setBorder(0);
                    headerTable.addCell(cell);
                    
                    img = null;
                    img = Image.getInstance(imgList.get(0));
                    heigth = headerTable.getTotalHeight();
                    width = img.getWidth();
                    percent = getPercent(heigth, width);
                    img.setAlignment(Image.MIDDLE);
                    img.scalePercent(percent + 3); // 表示是原来图像的比例;  
                    headerTable.addCell(img);
                    doc.add(headerTable);
                }else{
                	leftTable.setWidthPercentage(100);//表格的宽度为100%
                	doc.add(leftTable);
                }
                
                //添加调前状态部分
                headerTable = new PdfPTable(2);
                headerTable.setHorizontalAlignment(Element.ALIGN_CENTER); //垂直居中
                headerTable.setWidthPercentage(100);//表格的宽度为100%
                headerTable.setWidths(wid1); 
                headerTable.getDefaultCell().setBorderWidth(0); //不显示边框
                
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                
                leftCell = new PdfPCell(new Paragraph("2、目前状态",subheadfont));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("平衡度："+obj[4],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                String currBalanceArr[]=obj[10].toString().split(";");
                for(int i=0;i<currBalanceArr.length;i++){
                	String everyBalance[]=currBalanceArr[i].split(",");
                	float position=Float.parseFloat(everyBalance[0])*100;
                	leftCell = new PdfPCell(new Paragraph("位置"+(i+1)+"："+StringManagerUtils.floatToString(position, 2)+"cm    块重："+everyBalance[1]+" kN",font));
                    leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    leftCell.setBorder(0);
                    leftCell.setFixedHeight(lineHeight1);
                    leftTable.addCell(leftCell);
                }
                
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
                
                if(imgList.size()>1){
                	//将表格加入到第一列中
                    cell = new PdfPCell(leftTable);
                    cell.setPadding(0);
                    cell.setBorder(0);
                    headerTable.addCell(cell);
                    img = null;
                    img = Image.getInstance(imgList.get(1));
                    heigth = headerTable.getTotalHeight();
                    width = img.getWidth();
                    percent = getPercent(heigth, width);
                    img.setAlignment(Image.MIDDLE);
                    img.scalePercent(percent + 3); // 表示是原来图像的比例;  
                    headerTable.addCell(img);
                    doc.add(headerTable);
                }else{
                	leftTable.setWidthPercentage(100);//表格的宽度为100%
                	doc.add(leftTable);
                }
                
                
                
               //添加预期状态部分
                headerTable = new PdfPTable(2);
                headerTable.setHorizontalAlignment(Element.ALIGN_CENTER); //垂直居中
                headerTable.setWidthPercentage(100);//表格的宽度为100%
                headerTable.setWidths(wid1); 
                headerTable.getDefaultCell().setBorderWidth(0); //不显示边框
                
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                
                leftCell = new PdfPCell(new Paragraph("3、预期状态",subheadfont));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("平衡度："+obj[8],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                String optBalanceArr[]=obj[11].toString().split(";");
                for(int i=0;i<optBalanceArr.length;i++){
                	String everyBalance[]=optBalanceArr[i].split(",");
                	float position=Float.parseFloat(everyBalance[0])*100;
                	leftCell = new PdfPCell(new Paragraph("位置"+(i+1)+"："+StringManagerUtils.floatToString(position, 2)+"cm    块重："+everyBalance[1]+" kN",font));
                    leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    leftCell.setBorder(0);
                    leftCell.setFixedHeight(lineHeight1);
                    leftTable.addCell(leftCell);
                }
                
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
                
                if(imgList.size()>2){
                	//将表格加入到第一列中
                    cell = new PdfPCell(leftTable);
                    cell.setPadding(0);
                    cell.setBorder(0);
                    headerTable.addCell(cell);
                    img = null;
                    img = Image.getInstance(imgList.get(2));
                    heigth = headerTable.getTotalHeight();
                    width = img.getWidth();
                    percent = getPercent(heigth, width);
                    img.setAlignment(Image.MIDDLE);
                    img.scalePercent(percent + 3); // 表示是原来图像的比例;  
                    headerTable.addCell(img);
                    doc.add(headerTable);
                }else{
                	leftTable.setWidthPercentage(100);//表格的宽度为100%
                	doc.add(leftTable);
                }
                
                
            }
            doc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void exportWellTatalBalanceReport(OutputStream out, String type,List<?> list,String title) {
        Document doc = new Document(PageSize.A4, 40, 40, 40, 40); //创建文档对象 纸张大小为A4
        float lineHeight1 = (float)25.0;
        PdfPTable headerTable = null;
        PdfPTable leftTable = null;//创建左边表格
        PdfPCell leftCell = null;
        PdfPCell cell=null;
        String line="";
        float heigth =0;
        float width =0;
        int percent =0;
        float[] wid1 ={0.6f,0.4f}; //两列宽度的比例
        try {
            if ("word".equals(type)) {
                RtfWriter2.getInstance(doc, out);
                line="--------------------------------------------------------------------------------------------------------------------------------";
            } else if ("pdf".equals(type)) {
                PdfWriter.getInstance(doc, out);
                line="------------------------------------------------------------------------------------------------------------------";
            }
            //设置文档创建日期
            doc.addCreationDate();
            //设置标题
            doc.addTitle(getChinese(title));
            //构建页脚
            HeaderFooter footer=new HeaderFooter(new Phrase(), true);
            //设置页脚是否有边框  0表示无   1上边框   2下边框  3上下边框都有 默认都有
            footer.setBorder(0);
            //设置页脚的对齐方式
            footer.setAlignment(Element.ALIGN_CENTER);
            //将页脚添加到文档中
            doc.setFooter(footer);
            //打开文档开始写内容
            doc.open();
            if(list.size()>0){
            	Object[] obj=(Object[]) list.get(0);
            	
            	//添加标题部分
                Paragraph par1=null;
                par1=new Paragraph(title+"-"+obj[0],titlefont);
                par1.setAlignment(Element.ALIGN_CENTER);
                doc.add(par1);
                par1=new Paragraph("日期："+ obj[1]+"",font);
                par1.setAlignment(Element.ALIGN_RIGHT);
                doc.add(par1);
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
            	
                //添加基本信息部分
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                leftTable.setWidthPercentage(100);//表格的宽度为100%
                
//                leftCell = new PdfPCell(new Paragraph("1、基本信息",subheadfont));
//                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                leftCell.setBorder(0);
//                leftCell.setFixedHeight(lineHeight1);
//                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("井名："+obj[0]+"",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("单位："+obj[2]+"",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("平衡状态："+obj[3],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("整体移动距离："+obj[6]+" cm",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("+代表外移   -代表内移",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                doc.add(leftTable);
                
                
              //添加抽油机
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                leftTable.setWidthPercentage(100);//表格的宽度为100%
                
                leftCell = new PdfPCell(new Paragraph("1、抽油机",subheadfont));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("抽油机厂家："+obj[8]+"",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("抽油机型号："+obj[9]+"",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("汇总日期："+obj[1],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
                
                doc.add(leftTable);
                
                
                //添加调前状态部分
                
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                leftTable.setWidthPercentage(100);//表格的宽度为100%
                
                leftCell = new PdfPCell(new Paragraph("2、目前状态",subheadfont));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("平衡度："+obj[5],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                String currBalanceArr[]=obj[4].toString().split(";");
                for(int i=0;i<currBalanceArr.length;i++){
                	String everyBalance[]=currBalanceArr[i].split(",");
                	float position=Float.parseFloat(everyBalance[0])*100;
                	leftCell = new PdfPCell(new Paragraph("位置"+(i+1)+"："+StringManagerUtils.floatToString(position, 2)+"cm    块重："+everyBalance[1]+" kN",font));
                    leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    leftCell.setBorder(0);
                    leftCell.setFixedHeight(lineHeight1);
                    leftTable.addCell(leftCell);
                }
                
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
                
                doc.add(leftTable);
                
                
               //添加预期状态部分
                
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                leftTable.setWidthPercentage(100);//表格的宽度为100%
                
                leftCell = new PdfPCell(new Paragraph("3、预期状态",subheadfont));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                String optBalanceArr[]=obj[7].toString().split(";");
                for(int i=0;i<optBalanceArr.length;i++){
                	String everyBalance[]=optBalanceArr[i].split(",");
                	float position=Float.parseFloat(everyBalance[0])*100;
                	leftCell = new PdfPCell(new Paragraph("位置"+(i+1)+"："+StringManagerUtils.floatToString(position, 2)+"cm    块重："+everyBalance[1]+" kN",font));
                    leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    leftCell.setBorder(0);
                    leftCell.setFixedHeight(lineHeight1);
                    leftTable.addCell(leftCell);
                }
                
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
                
                doc.add(leftTable);
            }
            doc.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    
    public void exportWellCycleBalanceReport(OutputStream out, String type,List<?> list,String title) {
        Document doc = new Document(PageSize.A4, 40, 40, 40, 40); //创建文档对象 纸张大小为A4
        float lineHeight1 = (float)25.0;
        PdfPTable headerTable = null;
        PdfPTable leftTable = null;//创建左边表格
        PdfPCell leftCell = null;
        PdfPCell cell=null;
        String line="";
        float heigth =0;
        float width =0;
        int percent =0;
        float[] wid1 ={0.6f,0.4f}; //两列宽度的比例
        try {
            if ("word".equals(type)) {
                RtfWriter2.getInstance(doc, out);
                line="--------------------------------------------------------------------------------------------------------------------------------";
            } else if ("pdf".equals(type)) {
                PdfWriter.getInstance(doc, out);
                line="------------------------------------------------------------------------------------------------------------------";
            }
            //设置文档创建日期
            doc.addCreationDate();
            //设置标题
            doc.addTitle(getChinese(title));
            //构建页脚
            HeaderFooter footer=new HeaderFooter(new Phrase(), true);
            //设置页脚是否有边框  0表示无   1上边框   2下边框  3上下边框都有 默认都有
            footer.setBorder(0);
            //设置页脚的对齐方式
            footer.setAlignment(Element.ALIGN_CENTER);
            //将页脚添加到文档中
            doc.setFooter(footer);
            //打开文档开始写内容
            doc.open();
            if(list.size()>0){
            	Object[] obj=(Object[]) list.get(0);
            	
            	//添加标题部分
                Paragraph par1=null;
                par1=new Paragraph(title+"-"+obj[0],titlefont);
                par1.setAlignment(Element.ALIGN_CENTER);
                doc.add(par1);
                par1=new Paragraph("日期："+ obj[1]+"",font);
                par1.setAlignment(Element.ALIGN_RIGHT);
                doc.add(par1);
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
            	
                //添加基本信息部分
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                leftTable.setWidthPercentage(100);//表格的宽度为100%
                
                leftCell = new PdfPCell(new Paragraph("井名："+obj[0]+"",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("单位："+obj[2]+"",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("评价周期："+obj[10]+" 天",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("平衡状态："+obj[3],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("整体移动距离："+obj[6]+" cm",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("+代表外移   -代表内移",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                doc.add(leftTable);
                
                
              //添加抽油机
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                leftTable.setWidthPercentage(100);//表格的宽度为100%
                
                leftCell = new PdfPCell(new Paragraph("1、抽油机",subheadfont));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("抽油机厂家："+obj[8]+"",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("抽油机型号："+obj[9]+"",font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("汇总日期："+obj[1],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
                
                doc.add(leftTable);
                
                
                //添加调前状态部分
                
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                leftTable.setWidthPercentage(100);//表格的宽度为100%
                
                leftCell = new PdfPCell(new Paragraph("2、目前状态",subheadfont));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                leftCell = new PdfPCell(new Paragraph("平衡度："+obj[5],font));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                String currBalanceArr[]=obj[4].toString().split(";");
                for(int i=0;i<currBalanceArr.length;i++){
                	String everyBalance[]=currBalanceArr[i].split(",");
                	float position=Float.parseFloat(everyBalance[0])*100;
                	leftCell = new PdfPCell(new Paragraph("位置"+(i+1)+"："+StringManagerUtils.floatToString(position, 2)+"cm    块重："+everyBalance[1]+" kN",font));
                    leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    leftCell.setBorder(0);
                    leftCell.setFixedHeight(lineHeight1);
                    leftTable.addCell(leftCell);
                }
                
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
                
                doc.add(leftTable);
                
                
               //添加预期状态部分
                
                leftTable = new PdfPTable(1);//创建左边表格
                leftTable.getDefaultCell().setBorderWidth(0); //不显示边框
                leftTable.setWidthPercentage(100);//表格的宽度为100%
                
                leftCell = new PdfPCell(new Paragraph("3、预期状态",subheadfont));
                leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leftCell.setBorder(0);
                leftCell.setFixedHeight(lineHeight1);
                leftTable.addCell(leftCell);
                
                String optBalanceArr[]=obj[7].toString().split(";");
                for(int i=0;i<optBalanceArr.length;i++){
                	String everyBalance[]=optBalanceArr[i].split(",");
                	float position=Float.parseFloat(everyBalance[0])*100;
                	leftCell = new PdfPCell(new Paragraph("位置"+(i+1)+"："+StringManagerUtils.floatToString(position, 2)+"cm    块重："+everyBalance[1]+" kN",font));
                    leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    leftCell.setBorder(0);
                    leftCell.setFixedHeight(lineHeight1);
                    leftTable.addCell(leftCell);
                }
                
                par1=new Paragraph(line,font);
                par1.setAlignment(Element.ALIGN_LEFT);
                doc.add(par1);
                
                doc.add(leftTable);
            }
            doc.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static String getChinese(String s) {
        try {
           return new String(s.getBytes("gb2312"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
           return s;
        }
     }

    /** 
     * 第一种解决方案 在不改变图片形状的同时，判断，如果h>w，则按h压缩，否则在w>h或w=h的情况下，按宽度压缩
     *
     * @param h
     * @param w
     * @return
     */

    public static int getPercent(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 297 / h * 100;
        } else {
            p2 = 210 / w * 100;
        }
        p = Math.round(p2);
        return p;
    }

    /** 
     * 第二种解决方案，统一按照宽度压缩 这样来的效果是，所有图片的宽度是相等的，自我认为给客户的效果是最好的
     *
     * @param args
     */
    public static int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }
}