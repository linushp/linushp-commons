package com.github.linushp.commons.xmlstring;

import com.github.linushp.commons.BeanUtils;
import com.github.linushp.commons.StringUtils;
import com.github.linushp.commons.xmlstring.xmlelement.RootElement;
import com.github.linushp.commons.xmlstring.xmlelement.StringElement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class XmlString {

    private RootElement rootElement;
    private Map<String, String> idStringMap = new ConcurrentHashMap<>();

    public XmlString(String path) throws Exception {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        this.loadXml(inputStream);
    }

    public XmlString(URL url) throws Exception {
        InputStream inputStream = url.openStream();
        this.loadXml(inputStream);
    }

    public XmlString(Class clazz) throws Exception {
        String name = clazz.getSimpleName() + ".xml";
        InputStream inputStream = clazz.getResourceAsStream(name);
        this.loadXml(inputStream);
    }


    public XmlString(Object object) throws Exception {
        Class clazz = object.getClass();
        String name = clazz.getSimpleName() + ".xml";
        InputStream inputStream = clazz.getResourceAsStream(name);
        this.loadXml(inputStream);
    }


    private void loadXml(InputStream in) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(RootElement.class);
        Unmarshaller u = jc.createUnmarshaller();
        this.rootElement = (RootElement) u.unmarshal(in);


        List<StringElement> stringElements = rootElement.getString();
        for (StringElement stringElement : stringElements) {
            String id = stringElement.getId();
            if (!StringUtils.isEmpty(id)) {
                String content = stringElement.getContent();
                this.idStringMap.put(id, content);
            }
        }
    }


    //name可以重复，可以返回多个
    public List<String> getStringByName(String name) {
        List<StringElement> stringElements = rootElement.getString();
        List<String> result = new ArrayList<>();
        for (StringElement stringElement : stringElements) {
            String elementName = stringElement.getName();
            if (name.equals(elementName)) {
                result.add(stringElement.getContent());
            }
        }
        return result;
    }


    //ID不能重复，只能返回第一个
    public String getStringById(String id) {
        return idStringMap.get(id);
    }

    public <T> T toBean(Class<? extends T> tClass) throws Exception {
        return BeanUtils.mapToBean(tClass, idStringMap);
    }

    public Map<String, String> getIdStringMap() {
        return this.idStringMap;
    }

}
