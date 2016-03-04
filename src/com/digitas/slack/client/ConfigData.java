package com.digitas.slack.client;

import com.google.gwt.core.client.JavaScriptObject;

public class ConfigData extends JavaScriptObject 
{
	protected ConfigData() { }
	
	public final native String getApikey() /*-{ return apikey; }-*/;

}
