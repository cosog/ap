package com.cosog.utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cosog.model.DataModels;
import com.cosog.model.TabModel;
import com.cosog.model.TabPojo;

/**
 * DOM4J解析XML数据工具类
 * 
 * @param args
 * @throws Exception
 */
/**
 * @author Administrator
 *
 */
@SuppressWarnings({ "unused",  "rawtypes" })
public class XmlParseToolsHandler {
	private static Log log = LogFactory.getLog(XmlParseToolsHandler.class);
	private static Map<String, Object> gridMap;
	static {
		gridMap = new HashMap<String, Object>();
	}

	public static Map<String, Object> getGridMapObject() {
		return gridMap;
	}

	public XmlParseToolsHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
	}

	public String constructFields(DataModels data) {
		List<String> fields = data.getFields();
		List<String> dataTypes = data.getDataType();
		StringBuffer sb = new StringBuffer(" [");
		for (int i = 0; i < fields.size(); i++) {
			sb.append("{ name:'" + fields.get(i) + "',type:'"
					+ dataTypes.get(i) + "'},");
		}

		String fieldStr = sb.toString();
		fieldStr = fieldStr.substring(0, fieldStr.length() - 1);
		fieldStr = fieldStr + "]";
		log.debug("fieldStr==" + fieldStr);
		return fieldStr;

	}

	public static String montageColumns(DataModels data) {
		String columns = "";
		List<String> headers = data.getHeaders();
		StringBuffer strBuf = new StringBuffer(" ");
		List<String> fields = data.getFields();
		List<String> dataTypes = data.getDataType();
		List<String> hiddens = data.getHidden();
		List<String> widths = data.getWidth();
		strBuf.append(" [ ");
		for (int i = 0; i < headers.size(); i++) {
			strBuf.append("{ ");
			strBuf.append("header:'" + headers.get(i) + "',");
			strBuf.append("dataIndex:'" + fields.get(i) + "',");
			strBuf.append("dataType:'" + dataTypes.get(i) + "',");
			strBuf.append("width:'" + widths.get(i) + "',");
			strBuf.append("hidden:'" + hiddens.get(i) + "'");
			strBuf.append(" }");
			if (i != headers.size() - 1) {
				strBuf.append(",");
			}
		}
		strBuf.append(" ]");
		return columns = strBuf.toString();

	}

	public String constructColumns(DataModels data) {
		List<String> headers = data.getHeaders();
		List<String> fields = data.getFields();
		List<String> dataTypes = data.getDataType();
		List<String> widths = data.getWidth();
		StringBuffer sb = new StringBuffer(" [");
		for (int i = 0; i < headers.size(); i++) {
			if (i == 0) {
				sb.append("new Ext.grid.RowNumberer({text : '"
						+ headers.get(i)
						+ "',width : 40,align : 'left',locked : false,renderer : function(value, metadata, record,rowIndex) {return (store.currentPage - 1)* (store.pageSize) + rowIndex + 1;}}),");
			} else {
				if (dataTypes.get(i).equalsIgnoreCase("timestamp")) {
					sb.append("{text : '<div style=\"text-align:center\">"
							+ headers.get(i)
							+ "</div>',menuDisabled : false,locked : false,dataIndex : '"
							+ fields.get(i)
							+ "',width : "
							+ widths.get(i)
							+ ",sortable : true,align:'center',renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')},");
				} else if (dataTypes.get(i).equalsIgnoreCase("date")) {
					sb.append("{text : '<div style=\"text-align:center\">"
							+ headers.get(i)
							+ "</div>',menuDisabled : false,locked : false,dataIndex : '"
							+ fields.get(i)
							+ "',width : "
							+ widths.get(i)
							+ ",sortable : true,align:'center',renderer : Ext.util.Format.dateRenderer('Y-m-d')},");
				} else {
					if (dataTypes.get(i).equalsIgnoreCase("gtsj")) {
						sb.append("{text : '<div style=\"text-align:center\">"
								+ headers.get(i)
								+ "</div>',menuDisabled : false,locked : false,dataIndex : '"
								+ fields.get(i)
								+ "',width : "
								+ widths.get(i)
								+ ",sortable : true,align:'center',renderer : showGtsj},");
					} else if (dataTypes.get(i).equalsIgnoreCase("bgt")) {
						sb.append("{text : '<div style=\"text-align:center\">"
								+ headers.get(i)
								+ "</div>',menuDisabled : false,locked : false,dataIndex : '"
								+ fields.get(i)
								+ "',width : "
								+ widths.get(i)
								+ ",sortable : true,align:'center',renderer : showBgt},");
					} else if (dataTypes.get(i).equalsIgnoreCase("dlqx")) {
						sb.append("{text : '<div style=\"text-align:center\">"
								+ headers.get(i)
								+ "</div>',menuDisabled : false,locked : false,dataIndex : '"
								+ fields.get(i)
								+ "',width : "
								+ widths.get(i)
								+ ",sortable : true,align:'center',renderer : showDlqx},");
					} else {
						sb.append("{text : '<div style=\"text-align:center\">"
								+ headers.get(i)
								+ "</div>',menuDisabled : false,locked : false,dataIndex : '"
								+ fields.get(i) + "',width : " + widths.get(i)
								+ ",sortable : true,align:'center'},");

					}

				}
			}
		}
		String columnStr = sb.toString();
		columnStr = columnStr.substring(0, columnStr.length() - 1);
		columnStr = columnStr + "]";
		log.debug("columnStr==" + columnStr);
		return columnStr;

	}

	public static DataModels loadXMLData(String filePath) {
		SAXReader saxReader = new SAXReader();
		DataModels dataModel = new DataModels();
		List<String> myheaders = new ArrayList<String>();
		List<String> myfields = new ArrayList<String>();
		String mycolumns = "";
		String mytables = "";
		String mycondSqls = "";
		String myorderBys = "";
		String mydirction = "";
		Integer mypageSize = 20;
		List<String> mydynamics = new ArrayList<String>();
		List<String> mywidth = new ArrayList<String>();
		List<String> mydataTypes = new ArrayList<String>();
		List<String> myparaTypes = new ArrayList<String>();
		List<String> myhiddens = new ArrayList<String>();
		InputStream is = XmlParseToolsHandler.class.getResourceAsStream(filePath);
		Document document;
		try {
			document = saxReader.read(is);
			log.debug("now date==" + new Date());
			Element root = document.getRootElement();
			Element headers = root.element("headers");
			List headList = headers.elements("header");
			Attribute attr = root.attribute("loading");
			dataModel.setLoading(Boolean.valueOf(attr.getText()));
			for (int i = 0; i < headList.size(); i++) {
				Element header = (Element) headList.get(i);
				myheaders.add(header.getTextTrim());
				mywidth.add(header.attribute("width").getText());
			}
			Element fields = root.element("fields");
			List fieldList = fields.elements("field");
			for (int i = 0; i < fieldList.size(); i++) {
				Element field = (Element) fieldList.get(i);
				myfields.add(field.getTextTrim());
				mydataTypes.add(field.attribute("dataType").getText());
				myhiddens.add(field.attribute("hidden").getText());
			}
			Element columns = root.element("columns");
			mycolumns = columns.getTextTrim();
			Element tables = root.element("tables");
			mytables = tables.getTextTrim();
			Element condSqls = root.element("condSqls");
			mycondSqls = condSqls.getTextTrim();
			Element dynamics = root.element("dynamics");
			List dynamicList = dynamics.elements("dynamic");
			for (int i = 0; i < dynamicList.size(); i++) {
				Element dynamic = (Element) dynamicList.get(i);
				mydynamics.add(dynamic.getTextTrim());
				myparaTypes.add(dynamic.attribute("paramType").getText());
			}
			Element orderBys = root.element("orderBys");
			myorderBys = orderBys.getTextTrim();
			mydirction = orderBys.attribute("direct").getText();
			Element pageSize = root.element("pageSize");
			mypageSize = Integer.parseInt(pageSize.getTextTrim());
			dataModel.setHeaders(myheaders);
			dataModel.setWidth(mywidth);
			dataModel.setFields(myfields);
			dataModel.setDataType(mydataTypes);
			dataModel.setHidden(myhiddens);
			dataModel.setColumns(mycolumns);
			dataModel.setTables(mytables);
			dataModel.setCondSqls(mycondSqls);
			dataModel.setParamType(myparaTypes);
			dataModel.setDynamics(mydynamics);
			dataModel.setOrderBys(myorderBys);
			dataModel.setDirection(mydirction);
			dataModel.setPageSize(mypageSize);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataModel;
	}
	
	//解析分组函数的xml
		public static DataModels loadGroupXMLData(String filePath) {
			// "/code/user.xml"
			SAXReader saxReader = new SAXReader();
			DataModels dataModel = new DataModels();
			List<String> myheaders = new ArrayList<String>();
			List<String> myfields = new ArrayList<String>();
			String mycolumns = "";
			String mytables = "";
			String mycondSqls = "";
			//String mygroupBys = "";
			String myorderBys = "";
			String mydirction = "";
			Integer mypageSize = 20;
			List<String> mydynamics = new ArrayList<String>();
			List<String> mywidth = new ArrayList<String>();
			List<String> mydataTypes = new ArrayList<String>();
			List<String> myparaTypes = new ArrayList<String>();
			List<String> myhiddens = new ArrayList<String>();
			InputStream is = XmlParseToolsHandler.class.getResourceAsStream(filePath);
			Document document;
			// StringManagerUtils.printLog("hello");
			try {
				document = saxReader.read(is);
				// StringManagerUtils.printLog("now date==" + new Date());
				log.debug("now date==" + new Date());
				// Document document = saxReader.read(new File("./code/user.xml"));
				Element root = document.getRootElement();
				Element headers = root.element("headers");
				List headList = headers.elements("header");
				Attribute attr = root.attribute("loading");
				dataModel.setLoading(Boolean.valueOf(attr.getText()));
				// StringManagerUtils.printLog(attr.getTextTrim());

				for (int i = 0; i < headList.size(); i++) {
					Element header = (Element) headList.get(i);
					myheaders.add(header.getTextTrim());
					mywidth.add(header.attribute("width").getText());
				}
				Element fields = root.element("fields");
				List fieldList = fields.elements("field");
				for (int i = 0; i < fieldList.size(); i++) {
					Element field = (Element) fieldList.get(i);
					myfields.add(field.getTextTrim());
					mydataTypes.add(field.attribute("dataType").getText());
					myhiddens.add(field.attribute("hidden").getText());
				}

				Element columns = root.element("columns");
				mycolumns = columns.getTextTrim();
				Element tables = root.element("tables");
				mytables = tables.getTextTrim();
				Element condSqls = root.element("condSqls");
				mycondSqls = condSqls.getTextTrim();

				Element dynamics = root.element("dynamics");
				List dynamicList = dynamics.elements("dynamic");
				for (int i = 0; i < dynamicList.size(); i++) {
					Element dynamic = (Element) dynamicList.get(i);
					mydynamics.add(dynamic.getTextTrim());
					myparaTypes.add(dynamic.attribute("paramType").getText());
				}
				
//				Element groupBys = root.element("groupBys");
//				mygroupBys = groupBys.getTextTrim();
				Element orderBys = root.element("orderBys");
				myorderBys = orderBys.getTextTrim();
				mydirction = orderBys.attribute("direct").getText();
				Element pageSize = root.element("pageSize");
				mypageSize = Integer.parseInt(pageSize.getTextTrim());
				dataModel.setHeaders(myheaders);
				dataModel.setWidth(mywidth);
				dataModel.setFields(myfields);
				dataModel.setDataType(mydataTypes);
				dataModel.setHidden(myhiddens);
				dataModel.setColumns(mycolumns);
				dataModel.setTables(mytables);
				dataModel.setCondSqls(mycondSqls);
				dataModel.setParamType(myparaTypes);
				dataModel.setDynamics(mydynamics);
				//dataModel.setGroupBys(mygroupBys);
				dataModel.setOrderBys(myorderBys);
				dataModel.setDirection(mydirction);
				dataModel.setPageSize(mypageSize);

			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return dataModel;
		}

	// 根据指定的xml文件返回document对象
	public static Document getDocFromFile(String filename)
			throws DocumentException {
		if (filename == null) {
			return null;
		}
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(filename));

		return document;
	}

	// 根据指定的xml文件返回document对象
	public static Document getDocmentFromFile(String filename)
			throws DocumentException {
		if (filename == null) {
			return null;
		}
		InputStream is = XmlParseToolsHandler.class.getResourceAsStream(filename);
		Document document;
		SAXReader saxReader = new SAXReader();

		document = saxReader.read(is);

		return document;
	}

	/*
	 * 解析autoLoad.xml
	 */
	public static Map<String, Object> initGridData(String fileName,
			String locale) {
		try {
			log.debug(" initGridData start parse...");
			Element rootElmt = getDocFromFile(fileName).getRootElement();// 获取到根节点root
			List pages = rootElmt.elements("page");// 获取到所有的page节点集合
			for (Iterator iter = pages.iterator(); iter.hasNext();) {
				Element curPage = (Element) iter.next();// 循环取出page节点
				String loading = curPage.attribute("loading").getValue();
				log.debug("loading==" + loading);
				if (loading.equalsIgnoreCase("true")) {

					String pageId = curPage.attribute("pageId").getValue();
					String liftTypeUrl = curPage.attribute("liftTypeUrl")
							.getValue();
					TabModel tab = initTabData(liftTypeUrl, locale);
					gridMap.put(pageId, tab);// 将tab对象放入map中
					/* abtain all report nodes */
					List reports = curPage.elements("report");// 获取到当前page的所有report节点集合
					for (Iterator iterr = reports.iterator(); iterr.hasNext();) {
						Element curReport = (Element) iterr.next();// 循环遍历出当前report元素
						/* abtain all files nodes */
						List files = curReport.elements("files"); // 获取到当前report对应的files元素集合
						for (Iterator iterf = files.iterator(); iterf.hasNext();) {
							Element curFile = (Element) iterf.next();
							String language = curFile.attribute("language")
									.getValue();
							log.debug("language==" + language);
							// 循环遍历出当前files,判断是否为locale所对应的国家语言
							if (language.equalsIgnoreCase(locale)) {
								/* abtain locale file nodes */
								List file = curFile.elements("file");
								for (Iterator iterff = file.iterator(); iterff
										.hasNext();) {
									Element data = (Element) iterff.next();
									String key = data.attribute("key")
											.getValue();
									// put data in my map object!
									// 解析出当前文件名对应xml数据
									String fileXMl = data.getTextTrim();
									log.debug("filename ==" + fileXMl);
									DataModels dataModel = loadXMLData(fileXMl);
									log.debug("dataModel ==" + dataModel);
									gridMap.put(key, dataModel);
								}
							}
						}

					}
				}

			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gridMap;
	}

	/*
	 * 解析liftType.xml
	 */
	public static TabModel initTabData(String fileName, String locale) {
		TabModel tab = new TabModel();
		try {
			log.debug(" initTabData start parse...");
			String language = "zh_CN";
			String display = "true";
			String hidden = "false";
			String tabUrl = "";
			String tabValue = "";
			String tabCode = "";
			TabPojo tabPojo = null;
			String tabWellType = "outWell";
			Map<String, String> wellTypeMap = new LinkedHashMap<String, String>();
			Map<String, TabPojo> liftTypeMap = new LinkedHashMap<String, TabPojo>();
			Map<String, TabPojo> injectionTypeMap = new LinkedHashMap<String, TabPojo>();
			Element rootElmt = getDocmentFromFile(fileName).getRootElement();// 获取到根节点root
			List wellTypes = rootElmt.elements("wellTypes");// 获取到所有的wellTypes节点集合
			log.debug("wellTypes==" + wellTypes.size());
			for (Iterator itwps = wellTypes.iterator(); itwps.hasNext();) {
				Element curWellTypes = (Element) itwps.next();// 循环取出Welltypes节点
				language = curWellTypes.attribute("language").getValue();
				log.debug("language==" + language);
				if (language.equalsIgnoreCase(locale)) {
					List wellType = curWellTypes.elements("wellType");// 获取到所有的wellTypes节点集合
					for (Iterator itwp = wellType.iterator(); itwp.hasNext();) {
						Element curWellType = (Element) itwp.next();// 循环取出Welltypes节点
						display = curWellType.attribute("display").getValue();
						log.debug("display==" + display);
						if (display.equalsIgnoreCase("true")) {
							String key = curWellType.attribute("jlx")
									.getValue();
							String value = curWellType.getTextTrim();
							// log.debug("key==" + key + "value==" + value);
							wellTypeMap.put(key, value);// 将jlx与对应的值放到map
						}
					}
				}
			}
			List i18ns = rootElmt.elements("i18n");// 获取到所有的i18n节点集合
			for (Iterator iti18ns = i18ns.iterator(); iti18ns.hasNext();) {
				Element curI18n = (Element) iti18ns.next();// 循环取出i18n节点
				language = curI18n.attribute("language").getValue();
				log.debug("language==" + language);
				if (language.equalsIgnoreCase(locale)) {

					List tabWellTypes = curI18n.elements("tabWellTypes");// 获取到所有的tabWellTypes节点集合
					for (Iterator ittabwp = tabWellTypes.iterator(); ittabwp
							.hasNext();) {
						Element curTabWellType = (Element) ittabwp.next();// 循环取出TabWellType节点\
						tabWellType = curTabWellType.attribute("wellType")
								.getValue();
						if (tabWellType.equalsIgnoreCase("outWell")) {

							List liftwellTypes = curTabWellType
									.elements("liftwellType");// 获取到所有的liftwellType节点集合
							for (Iterator itlwt = liftwellTypes.iterator(); itlwt
									.hasNext();) {
								Element curListType = (Element) itlwt.next();// 循环取出ListType节点

								hidden = curListType.attribute("hidden")
										.getValue();
								boolean hide = Boolean.parseBoolean(hidden);
								log.debug("display==" + hidden);

								tabCode = curListType.attribute("jslx")
										.getValue();
								tabValue = curListType.getTextTrim();
								log.debug("tabCode==" + tabCode+ "  tabValue ==  " + tabValue);
								tabPojo = new TabPojo(tabCode, tabValue, hide);
								liftTypeMap.put(tabCode, tabPojo);

							}
						} else {
							List injectionwellTypes = curTabWellType
									.elements("injectionwellType");// 获取到所有的injectionwellType节点集合

							for (Iterator itliwt = injectionwellTypes
									.iterator(); itliwt.hasNext();) {
								Element curLinjectionwellType = (Element) itliwt
										.next();// 循环取出injectionwellType节点\
								hidden = curLinjectionwellType.attribute(
										"hidden").getValue();
								boolean hide = Boolean.parseBoolean(hidden);
								log.debug("display==" + hidden);

								tabCode = curLinjectionwellType
										.attribute("jlx").getValue();
								tabValue = curLinjectionwellType.getTextTrim();
								log.debug("key==" + tabCode + "value=="
										+ tabValue);
								tabPojo = new TabPojo(tabCode, tabValue, hide);
								log.debug("tabCode==" + tabCode
										+ "  tabValue ==  " + tabValue);
								injectionTypeMap.put(tabCode, tabPojo);// 将tabPojo对象放入值injectionTypeMap

							}
						}
					}

				}

			}
			tab.setWellType(wellTypeMap);
			tab.setLiftType(liftTypeMap);
			tab.setInjectionType(injectionTypeMap);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tab;
	}
}