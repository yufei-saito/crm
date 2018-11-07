package yufei.store.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class BeanFactory {
	public static Object getBean(String name)  {
		try {
			SAXReader sax = new SAXReader();
			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("Dao.xml");
			Document doc = sax.read(is);
			Element root =doc.getRootElement();
			Element e = (Element)root.selectSingleNode("//Bean[@id='"+name+"']");
			String classname =e.attributeValue("class");
			Class clazz = Class.forName(classname);
			return clazz.newInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
