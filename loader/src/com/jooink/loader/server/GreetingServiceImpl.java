package com.jooink.loader.server;

import java.util.Random;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jooink.loader.client.GreetingService;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
    GreetingService {

  public String greetServer(String input) throws IllegalArgumentException {
	  try {
		  
		  int l = (new Random()).nextInt(3000);

		  Thread.sleep(l);
		return "<hr/>"+l+"ms<hr/>";
	} catch (InterruptedException e) {
		e.printStackTrace();
		throw new IllegalArgumentException(e.getMessage());
	}
	  
  }
}