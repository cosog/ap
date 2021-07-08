package com.cosog.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class XmlParse {

	// ����xml�ļ�

	public XmlParse() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public static void XmlParse() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		InputStream file = new FileInputStream("src/xml/po.xml");
		Document document = builder.build(file);// ����ĵ�����
		Element root = document.getRootElement();// ��ø��ڵ�
		List<Element> list = root.getChildren();
		for (Element e : list) {
			System.out.println("ID=" + e.getAttributeValue("id"));
			System.out.println("username=" + e.getChildText("username"));
			System.out.println("password=" + e.getChildText("password"));
		}
	}

	// ��
	public static void addXml() throws JDOMException, FileNotFoundException,
			IOException {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build("src/xml/po.xml");// ����ĵ�����
		Element root = doc.getRootElement();// ��ø��ڵ�

		// �����Ԫ��
		Element element = new Element("person");
		element.setAttribute("id", "3");
		Element element1 = new Element("username");
		element1.setText("zhangdaihao");
		Element element2 = new Element("password");
		element2.setText("mima");
		element.addContent(element1);
		element.addContent(element2);
		root.addContent(element);
		doc.setRootElement(root);

		// �ļ�����
		XMLOutputter out = new XMLOutputter();
		out.output(doc, new FileOutputStream("src/xml/po.xml"));
	}

	// ����IDֵɾ��һ���ڵ�
	public static void deletePerson(int id) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		InputStream file = new FileInputStream("src/xml/po.xml");
		Document doc = builder.build(file);// ����ĵ�����
		Element root = doc.getRootElement();// ��ø��ڵ�
		List<Element> list = root.getChildren();
		for (Element e : list) {
			// ��ȡIDֵ
			if (Integer.parseInt(e.getAttributeValue("id")) == id) {
				root.removeContent(e);
				break;// ??
			}
		}

		// �ļ�����
		XMLOutputter out = new XMLOutputter();
		out.output(doc, new FileOutputStream("src/xml/po.xml"));
	}

	// ����IDֵ�޸�һ���ڵ�
	@SuppressWarnings("unchecked")
	public static void updatePerson(int id) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		InputStream file = new FileInputStream("src/xml/po.xml");
		Document doc = builder.build(file);// ����ĵ�����
		Element root = doc.getRootElement();// ��ø��ڵ�
		List<Element> list = root.getChildren();
		for (Element e : list) {
			// ��ȡIDֵ
			if (Integer.parseInt(e.getAttributeValue("id")) == id) {
				System.out.println("--------------------");
				e.getChild("username").setText("111111111");
				e.getChild("password").setText("password");

			}
		}

		// �ļ�����
		XMLOutputter out = new XMLOutputter();
		out.output(doc, new FileOutputStream("src/xml/po.xml"));
	}

	public static Vector<String> XmlParseTools(String xmlfile)
			throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Vector<String> tables = new Vector<String>();
		StringBuilder strThBuilder = new StringBuilder();
		StringBuilder strSqlBuilder = new StringBuilder();
		StringBuilder strConBuilder = new StringBuilder();
		InputStream file = XmlParse.class.getResourceAsStream(xmlfile);
		Document document = builder.build(file);// ����ĵ�����
		Element root = document.getRootElement();// ��ø��ڵ�
		List<Element> list = root.getChildren();
		// Element child = root.getChild("column"); // ��ȡ�ӽڵ�
		System.out.println(" root.getChildren()  ===" + list.size());
		for (int i = 0; i < list.size(); i++) {
			Element e = list.get(i);
			if (i == 0) {
				System.out.println("ID=" + e.getAttributeValue("id"));
				List<Element> theads = e.getChildren();
				for (int m = 0; m < theads.size(); m++) {
					Element th = theads.get(m);
					if (th.getAttributeValue("show", "false").equals("true")) {
						if (m == theads.size() - 1) {
							strThBuilder.append(th.getTextTrim());
						} else {
							strThBuilder.append(th.getTextTrim()).append(",");
						}
					}
				}
				tables.add(strThBuilder.toString());
				System.out
						.println("strthBuilder***=" + strThBuilder.toString());
			} else if (i == 1) {
				System.out.println("ID=" + e.getAttributeValue("id"));
				List<Element> sqls = e.getChildren();
				for (int n = 0; n < sqls.size(); n++) {
					Element sql = sqls.get(n);
					if (sql.getAttributeValue("show", "true").equals("true")) {
						if (n == sqls.size() - 1) {
							strSqlBuilder.append(sql.getTextTrim());
						} else {
							strSqlBuilder.append(sql.getTextTrim()).append(",");
						}
					}
				}
				tables.add(strSqlBuilder.toString());
				System.out.println("strsqlBuilder***="
						+ strSqlBuilder.toString());
			} else if (i == 2) {
				System.out.println("ID=" + e.getAttributeValue("id"));
				List<Element> conditions = e.getChildren();
				for (int p = 0; p < conditions.size(); p++) {
					Element condition = conditions.get(p);
					if (condition.getAttributeValue("show", "true").equals(
							"true")) {
						if (p == conditions.size() - 1) {
							strConBuilder.append(condition.getTextTrim());
						} else {
							strConBuilder.append(condition.getTextTrim())
									.append(",");
						}
					}
				}
				System.out.println("strConBuilder***="
						+ strConBuilder.toString());
				tables.add(strConBuilder.toString());
			}

		}
		return tables;
	}

	static public void main(String ars[]) throws JDOMException, IOException {

		// addXml();//����XML
		// deletePerson(3);//ɾ��XML
		// updatePerson(2);//�޸�XML
		// XmlParse();//����XML
		String filePath = "/xml/wells.xml";
		Vector<String> tables = XmlParseTools(filePath);
		for (String s : tables) {
			System.out.println("s==" + s);
		}

	}
}