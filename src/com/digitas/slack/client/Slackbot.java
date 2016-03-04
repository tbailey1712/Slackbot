package com.digitas.slack.client;


import java.util.ArrayList;

import com.digitas.slack.shared.FieldVerifier;
import com.digitas.slack.shared.Preferences;
import com.google.apphosting.api.ApiProxy;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Slackbot implements EntryPoint {

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	private static final String JSON_URL_APIKEY = "/bot/apikey";	

	private VerticalPanel mainPanel = new VerticalPanel();
	  
	  private HorizontalPanel propsPanel = new HorizontalPanel();
	  
	  private Label apiKeyLabel = new Label("API Key");
	  private TextBox apiKeyTextBox = new TextBox();
	  private Button propsUpdateButton = new Button("Update"); 
	  
	  private FlexTable datesTable = new FlexTable();
	  
	  private HorizontalPanel addPanel = new HorizontalPanel();
	  
	  private Label phaseNameLabel = new Label("Phase Name:");
	  private TextBox newPhaseTextBox = new TextBox();
	  private Label phaseStartLabel = new Label("Phase Start Date:");
	  private TextBox phaseStartTextBox = new TextBox();
	  private Label phaseEndLabel = new Label("Phase End Date:");
	  private TextBox phaseEndTextBox = new TextBox();
	  private Button addPhaseButton = new Button("Add");
	  private Label lastUpdatedLabel = new Label();
	  private ArrayList<String> phases = new ArrayList<String>();
	  
	  private Label errorMsgLabel = new Label();
	  
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{
		
		
		propsPanel.add(apiKeyLabel);
		propsPanel.add(apiKeyTextBox);
		propsPanel.add(propsUpdateButton);
		
		datesTable.setText(0, 0, "Phase Name");
		datesTable.setText(0, 1, "Start Date");
		datesTable.setText(0, 2, "End Date");
		
		addPanel.add(phaseNameLabel);
		addPanel.add(newPhaseTextBox);
		addPanel.add(phaseStartLabel);
		addPanel.add(phaseStartTextBox);
		addPanel.add(phaseEndLabel);
		addPanel.add(phaseEndTextBox);
		addPanel.add(addPhaseButton);
		
		mainPanel.add(propsPanel);
		mainPanel.add(datesTable);
		mainPanel.add(addPanel);
		
		errorMsgLabel.setStyleName("errorMessage");
		errorMsgLabel.setVisible(false);
		mainPanel.add(errorMsgLabel);		
		
		RootPanel.get("rootpanel").add(mainPanel);
		
		newPhaseTextBox.setFocus(true);
		
		propsUpdateButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//updateApiKey();
			}
		});
		
		addPhaseButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addPhase();
			}
		});
		updateData();
	}
	
	private void updateApiKey(ConfigData data)
	{
		//apiKeyTextBox.setValue( data.getApiKey() );		
		errorMsgLabel.setVisible(false);
	}
	
	private void addPhase()
	{
		final String phaseName = newPhaseTextBox.getText().trim();
		final String phaseStart = phaseStartTextBox.getText().trim();
		final String phaseEnd = phaseEndTextBox.getText().trim();
		
		if (phases.contains(phaseName)) {
			return;
		}
		
		int row = datesTable.getRowCount();
		phases.add(phaseName);
		datesTable.setText(row, 0, phaseName);
		datesTable.setText(row, 1, phaseStart);
		datesTable.setText(row, 2, phaseEnd);
		
		Button removePhaseButton = new Button("x");
		removePhaseButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        int removedIndex = phases.indexOf(phaseName);
		        phases.remove(removedIndex);
		        datesTable.removeRow(removedIndex + 1);
		      }
		    });
		datesTable.setWidget(row, 3, removePhaseButton);
	}

	private void updateData()
	{
		String url = URL.encode(JSON_URL_APIKEY);
	    try 
	    {
	    	RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
	    	Request request = builder.sendRequest(null, new RequestCallback() 
	    	{
	    	    public void onError(Request request, Throwable exception) 
	    	    {
	    	      displayError("Couldn't retrieve JSON");
	    	    }

	    	    public void onResponseReceived(Request request, Response response) 
	    	    {
	    	    	if (200 == response.getStatusCode()) 
	    	    	{
	    	    		//updateTable(JsonUtils.<JsArray<PhaseData>>safeEval(response.getText()));
	    	    		updateApiKey(JsonUtils.<ConfigData>safeEval(response.getText()));
	    	    	} else {
	    	    		displayError("Couldn't retrieve JSON (" + response.getStatusText() + ")");
	    	    	}
	    	    }
	    	  });
	    	} 
	    catch (RequestException e) {
	    	displayError("Couldn't retrieve JSON");
	    }		
	}
	
	private void displayError(String error)
	{
		errorMsgLabel.setText("Error: " + error);
	    errorMsgLabel.setVisible(true);		
		System.out.println(error);
	}
}
