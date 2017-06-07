package com.sample.itheima.heima.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;
import android.util.Xml;

/**
 * Test002 XML.
 * 
 * @author Aaric
 *
 */
public class Test002_XML extends AndroidTestCase {
	
	/**
	 * TAG
	 */
	public static final String TAG = Test002_XML.class.getSimpleName(); 
	
	/**
	 * Test XmlPullParser.
	 * 
	 * @throws Exception
	 */
	public void testXmlPullParser() throws Exception {
		FileInputStream fis = this.getContext().openFileInput("dome.xml");
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(fis, HTTP.UTF_8);
		
		StringBuffer sb = null;
		int type = parser.getEventType();
		while(XmlPullParser.END_DOCUMENT != type) {
			// Parse.
			switch (type) {
				case XmlPullParser.START_TAG:
					if ("objects".equals(parser.getName())) {
						sb = new StringBuffer();
					} else if ("object".equals(parser.getName())) {
						sb.append("ID: " + parser.getAttributeValue(null, "id") + " | ");
					} else if ("title".equals(parser.getName())) {
						sb.append("Title: " + parser.nextText() + " | ");
					} else if ("content".equals(parser.getName())) {
						sb.append("Content: " + parser.nextText() + " | ");
					}
					break;
					
				case XmlPullParser.END_TAG:
					if ("objects".equals(parser.getName())) {
						Log.v(TAG, sb.toString());
					} else if ("object".equals(parser.getName())) {
						sb.append("\n");
					}
					break;
			}
			
			// Next.
			type = parser.next();
			
		}
		
	}
	
	/**
	 * Test XmlSerializer.
	 */
	public void testXmlSerializer() throws Exception {
		FileOutputStream fos = this.getContext().openFileOutput("dome.xml", Context.MODE_PRIVATE);
		XmlSerializer serializer =  Xml.newSerializer();
		serializer.setOutput(fos, HTTP.UTF_8);
		serializer.startDocument(HTTP.UTF_8, true);
		serializer.startTag(null, "objects");
		
		// Content.
		for (int i = 0; i < 10; i++) {
			serializer.startTag(null, "object");
			serializer.attribute(null, "id", Integer.toString(i + 1));
			
			serializer.startTag(null, "title");
			serializer.text("title" + i);
			serializer.endTag(null, "title");
			
			serializer.startTag(null, "content");
			serializer.text("content" + i);
			serializer.endTag(null, "content");
			
			serializer.endTag(null, "object");
			
		}
		
		serializer.endTag(null, "objects");
		serializer.endDocument();
		
		fos.close();
		
	}

}
