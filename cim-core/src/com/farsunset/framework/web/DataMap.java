package com.farsunset.framework.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;


public class DataMap {

	final static LinkedHashMap<String,String> powerMap = new LinkedHashMap<String,String>();
	final static List<HashMap<String,String>> powerList =new ArrayList<HashMap<String,String>>() ;
	public static void loadPowerMap()
	{
		SAXReader sax = new SAXReader();
		Document root ;
		try {
			root = sax.read(DataMap.class.getResourceAsStream("/power.xml"));
			List<Node>  nodeList = root.selectNodes("/powers/power");
			for(Node node : nodeList)
			{
				DefaultElement element = (DefaultElement) node;
				powerMap.put(element.attributeValue("index"),element.attributeValue("name"));
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("index", element.attributeValue("index"));
				map.put("name", element.attributeValue("name"));
				powerList.add(map);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static LinkedHashMap<String, String> getPowerMap() {
		return powerMap;
	}

	public static List<HashMap<String, String>> getPowerList() {
		return powerList;
	}
	
	
	
	

}
