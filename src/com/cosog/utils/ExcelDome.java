package com.cosog.utils;

import java.io.File;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelDome {

	private static WritableCellFormat wcf_value;// 表格数据样式

	private static WritableCellFormat wcf_value_left;

	private static WritableCellFormat wcf_key;// 表头样式

	private static WritableCellFormat wcf_name_left;// 表名样式

	private static WritableCellFormat wcf_name_right;// 表名样式

	private static WritableCellFormat wcf_name_center;// 表名样式

	private static WritableCellFormat wcf_title;// 页名称样式

	private static WritableCellFormat wcf_percent_float;

	private static final int MAXCOLS = 7;// 表名称样式

	// 生成Excel文件

	public void genarateExcel(File file) throws Exception {

		/****** 定义表格格式start *****/

		WritableFont wf_key = new jxl.write.WritableFont(
				WritableFont.createFont("微软雅黑"), 10, WritableFont.BOLD);

		WritableFont wf_value = new jxl.write.WritableFont(
				WritableFont.createFont("微软雅黑"), 10, WritableFont.NO_BOLD);

		wcf_value = new WritableCellFormat(wf_value);

		wcf_value.setAlignment(jxl.format.Alignment.CENTRE);

		wcf_value.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

		wcf_value.setBorder(jxl.format.Border.ALL,
				jxl.format.BorderLineStyle.THIN);

		wcf_value_left = new WritableCellFormat(wf_value);

		wcf_value_left.setAlignment(jxl.format.Alignment.LEFT);

		wcf_value_left
				.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

		wcf_value_left.setBorder(jxl.format.Border.ALL,
				jxl.format.BorderLineStyle.THIN);

		wcf_value_left.setWrap(true);

		wcf_key = new WritableCellFormat(wf_key);

		wcf_key.setAlignment(jxl.format.Alignment.CENTRE);

		wcf_key.setBorder(jxl.format.Border.ALL,
				jxl.format.BorderLineStyle.THIN);

		wcf_name_left = new WritableCellFormat(wf_key);

		wcf_name_left.setAlignment(Alignment.LEFT);

		wcf_name_left.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

		wcf_name_right = new WritableCellFormat(wf_key);

		wcf_name_right.setAlignment(Alignment.LEFT);

		wcf_name_center = new WritableCellFormat(wf_key);

		wcf_name_center.setAlignment(Alignment.CENTRE);

		jxl.write.NumberFormat wf_percent_float = new jxl.write.NumberFormat(
				"0.00");

		wcf_percent_float = new jxl.write.WritableCellFormat(wf_value,
				wf_percent_float);

		wcf_percent_float.setAlignment(jxl.format.Alignment.CENTRE);

		wcf_percent_float
				.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

		wcf_percent_float.setBorder(jxl.format.Border.ALL,
				jxl.format.BorderLineStyle.THIN);

		WritableFont wf_title = new jxl.write.WritableFont(
				WritableFont.createFont("微软雅黑"), 12, WritableFont.BOLD);

		wcf_title = new WritableCellFormat(wf_title);

		wcf_title.setAlignment(Alignment.LEFT);

		/****** 定义表格格式end *****/

		try {

			WritableWorkbook wb = Workbook.createWorkbook(file);

			WritableSheet ws = wb.createSheet("数据报表", 0);

			int startRowNum = 0;// 起始行

			int startColNum = 0;// 起始列

			int maxColSize = 4;// 最大列数

			// 设置列宽

			ws.setColumnView(0, 20);

			ws.setColumnView(1, 18);

			ws.setColumnView(2, 20);

			ws.setColumnView(3, 18);

			generateCells(ws, startRowNum++, startColNum, 1, MAXCOLS);

			ws.addCell(new Label(startColNum, startRowNum, "薪资计算周期：", wcf_title));

			ws.mergeCells(startColNum, startRowNum, startColNum + maxColSize
					- 1, startRowNum);

			startColNum = 0;

			startRowNum++;

			generateCells(ws, startRowNum++, startColNum, 1, MAXCOLS);

			// 第1行

			ws.addCell(new Label(startColNum, startRowNum, "姓       名：",
					wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "SSS", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "所 在 部 门：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "SSS", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			startRowNum++;

			startColNum = 0;

			// 第1行

			ws.addCell(new Label(startColNum, startRowNum, "出 勤 天 数：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "SSS", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "在 职 状 态：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "XXXXXX", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			startRowNum++;

			startColNum = 0;

			ws.addCell(new Label(startColNum, startRowNum, "应发项目",
					wcf_name_right));

			ws.mergeCells(startColNum, startRowNum, startColNum + maxColSize
					- 1, startRowNum);

			startColNum = 0;

			startRowNum++;

			// 第3行

			ws.addCell(new Label(startColNum, startRowNum, "基 本 工 资：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "SSS", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "保 密 工 资：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "XXXXXX", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			startRowNum++;

			startColNum = 0;

			// 第3行

			ws.addCell(new Label(startColNum, startRowNum, "佣 金 收 入：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "SSS", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "提 成 点 数：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "SSS", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			startRowNum++;

			startColNum = 0;

			// 第3行

			ws.addCell(new Label(startColNum, startRowNum, "发 放 比 例：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "XXXXXX", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "本月提成金额：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "XXXXXX", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			startRowNum++;

			startColNum = 0;

			// 第3行

			ws.addCell(new Label(startColNum, startRowNum, "转入佣金收入：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "SSS", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "转入提成金额：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "XXXXXX", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			startRowNum++;

			startColNum = 0;

			// 第3行

			ws.addCell(new Label(startColNum, startRowNum, "餐       补：",
					wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "SSS", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "应 发 小 计：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "XXXXXX", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			startRowNum++;

			startColNum = 0;

			ws.addCell(new Label(startColNum, startRowNum, "扣除项目",
					wcf_name_right));

			ws.mergeCells(startColNum, startRowNum, startColNum + maxColSize
					- 1, startRowNum);

			startColNum = 0;

			startRowNum++;

			// 第5行

			ws.addCell(new Label(startColNum, startRowNum, "社 会 保 险：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "XXXXXX", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "考 勤 扣 款：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "SSS", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			startRowNum++;

			startColNum = 0;

			// 第5行

			ws.addCell(new Label(startColNum, startRowNum, "工 作 扣 罚：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "XXXXXX", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "个人所得税：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "SSS", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			startRowNum++;

			startColNum = 0;

			// 第5行

			ws.addCell(new Label(startColNum, startRowNum, "其       他：",
					wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "XXXXXX", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "扣 除 小 计：", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			ws.addCell(new Label(startColNum, startRowNum, "XXXXXX", wcf_key));

			ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);

			startColNum = startColNum + 1;

			startRowNum++;

			startColNum = 0;

			generateCells(ws, startRowNum++, startColNum, 1, MAXCOLS);

			generateCells(ws, startRowNum++, startColNum, 1, MAXCOLS);

			ws.addCell(new Label(startColNum, startRowNum, "本月实际到账金额：",
					wcf_name_right));

			ws.mergeCells(startColNum, startRowNum, startColNum + maxColSize
					- 1, startRowNum);

			startColNum = 0;

			startRowNum++;

			wb.write();

			wb.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	// 生成空单元格

	public void generateCells(WritableSheet ws, int startRows,

	int startColNums, int rows, int cols) {

		for (int r = 0; r < rows; r++) {

			for (int c = 0; c < cols; c++) {

				try {

					ws.addCell(new Label(startColNums + c, startRows + r, ""));

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

		}

	}

	public static void main(String[] args) throws Exception {

		for (int c = 0; c < 3; c++) {

			String name = "D:\\xls\\2012-01(我我我)+" + c + ".xls";

			File file = new File(name);

			file.createNewFile();

			new ExcelDome().genarateExcel(file);

			System.out.println(file);

		}
	}
}