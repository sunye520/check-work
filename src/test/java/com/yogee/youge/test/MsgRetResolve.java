package com.yogee.youge.test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public class MsgRetResolve {

    public static void main(String[] args) throws DocumentException{
        String send = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
                "<returnsms>\n" +
                " <returnstatus>Success</returnstatus>\n" +
                " <message>ok</message>\n" +
                " <remainpoint>12949</remainpoint>\n" +
                " <taskID>1637086</taskID>\n" +
                " <successCounts>1</successCounts>" +
                "</returnsms>";


        //解析返回的xml，判断是否成功
        Document doc = DocumentHelper.parseText(send);
        Element root = doc.getRootElement();
        String returnstatus = root.elementText("returnstatus");

    }
}
