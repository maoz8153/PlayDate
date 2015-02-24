package com.playdate.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.playdate.R;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

public class XmlPullParserHelper {

	private static final String LOGTAG = "playdate";

	List<String> cities = new ArrayList<String>();

	public List<String> parseXML(Context context) {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();

			InputStream stream = context.getResources().openRawResource(
					R.raw.citys);
			xpp.setInput(stream, null);

			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.TEXT) {
					handleText(xpp.getText());
				}
				eventType = xpp.nextToken();
			}

		} catch (NotFoundException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (XmlPullParserException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (IOException e) {
			Log.d(LOGTAG, e.getMessage());
		}

		return cities;
	}

	private void handleText(String text) {
		String xmlText = text;
		if (xmlText != "Name" && xmlText != "city") {
			cities.add(xmlText);
		}

	}
}
