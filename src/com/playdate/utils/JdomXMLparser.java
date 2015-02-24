package com.playdate.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.playdate.R;

import android.content.Context;
import android.util.Log;

public class JdomXMLparser {

	String currentCity;
	private static final String LOGTAG = "playdate";

	public List<String> parseXML(Context context) {

		InputStream stream = context.getResources()
				.openRawResource(R.raw.citys);
		SAXBuilder builder = new SAXBuilder();
		List<String> cities = new ArrayList<String>();

		try {

			Document document = (Document) builder.build(stream);
			org.jdom2.Element rootNode = document.getRootElement();
			List<org.jdom2.Element> list = rootNode.getChildren("city");

			for (Element node : list) {
				currentCity = node.getChildText("Name");
				cities.add(currentCity);
			}

		} catch (IOException e) {
			Log.i(LOGTAG, e.getMessage());
		} catch (JDOMException e) {
			Log.i(LOGTAG, e.getMessage());
		}
		return cities;
	}

}
