package com.digitas.slack.client;

import com.google.gwt.core.client.JavaScriptObject;

public class PhaseData extends JavaScriptObject 
{
	protected PhaseData() { }

	public final native String getName() /*-{ return name; }-*/;
	public final native String getStart() /*-{ return start; }-*/;
	public final native String getEnd() /*-{ return end; }-*/;

}
