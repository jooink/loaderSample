package com.jooink.loader.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Loader implements EntryPoint {

	
	private static final GreetingServiceAsync gs = GWT.create(GreetingService.class);
	
	public static final void hideLoader() {
		
		Element e = Document.get().getElementById("loader-wrapper");
				
		if(e != null) {
			e.getStyle().setDisplay(Display.NONE);
		}
	}

	public static final void setLoaderMessage(String msg) {
		Element e = Document.get().getElementById("loader-text");
		if(e != null) {
			e.setInnerHTML(msg);
		} 		
	}
	
	
	public static final void showLoader() {
		Element e = Document.get().getElementById("loader-wrapper");
		if(e != null) {
			e.getStyle().setDisplay(Display.BLOCK);
		} 
	}
	
	@Override
	public void onModuleLoad() {

		

		RootPanel.get().add( new HTML("Inserito prima di aver tolto il loader"));
		hideLoader();
		RootPanel.get().add( new HTML("Inserito dopo aver tolto il loader")); 
		
		Anchor a = new Anchor("I work just whan the loader is not active");
		a.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
					Window.alert("clicked");
			}
		});
		
		
		Button b5s = new Button(" Say \"Hello\" for 5 seconds");
		b5s.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				setLoaderMessage( "Hello ;)" );
				showLoader();
				Scheduler.get().scheduleFixedDelay( new RepeatingCommand() {
					private int n = 5;
					@Override
					public boolean execute() {
						n--;
						if(n>0) {
							setLoaderMessage( "Hello :| ... still " + n + " seconds" );
							return true;
						} 
						hideLoader();
						return false;
					}
				}, 1000 );
				
			}
		});
		

		Button brc = new Button("Long Remote Call");
		brc.addClickHandler(new ClickHandler() {
			

			@Override
			public void onClick(ClickEvent event) {
				setLoaderMessage( "RPC Call" );
				showLoader();
				gs.greetServer("Someone", callback);
			}
		});

		
		RootPanel.get().add(a);
		RootPanel.get().add(new HTML());
		
		RootPanel.get().add(b5s);
		RootPanel.get().add(brc);
				
	}
	
	
	private AsyncCallback<String> callback = new AsyncCallback<String>() {
		
		@Override
		public void onSuccess(String result) {
			RootPanel.get().add(new HTML(result));
			hideLoader();
		}
		
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("ERROR" + caught.getMessage());
			hideLoader();
		}
	};

	
}