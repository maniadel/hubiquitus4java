package org.hubiquitus.hubotsdk.HhttpAdapter;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.hubiquitus.hubotsdk.AdapterInbox;

public class HHttpAdapterInbox extends AdapterInbox implements Processor{

	public String host = "0.0.0.0";
	public int port = 80;
	public String path = "";
	public String jettyCamelUri = "";
	
	@Override
	public void setProperties(Map<String, String> params) {
		if(params != null && params.containsKey("host")) {
			this.host = params.get("host");
		}
		
		if(params != null && params.containsKey("port")) {
			this.port = Integer.parseInt(params.get("port"));
		}
		
		if(params != null && params.containsKey("path")) {
			this.path = params.get("path");
			if (this.path.contains("?")) {
				int interrogationIndex = this.path.indexOf("?");
				this.path = this.path.substring(interrogationIndex, this.path.length());
			}
		}
		
	}

	@Override
	public void start() {
		String jettyOptions = "matchOnUriPrefix=true";
		jettyCamelUri = "jetty:http://" + this.host + ":" + this.port + this.path;
		jettyCamelUri += "?" + jettyOptions;
		
		//add route 
		HHttpAdapterRouteBuilder routes = new HHttpAdapterRouteBuilder(jettyCamelUri, this);
		try {
			camelContext.addRoutes(routes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
				
	}

	public void process(Exchange arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		
		//get body as bytes
		byte[] rawBody = (byte[]) in.getBody(byte[].class);
		Map<String, Object> headers = in.getHeaders();
		Map<String, DataHandler> attachments = in.getAttachments();
		
		
		
		//HttpServletRequest req = exchange.getIn().getBody(HttpServletRequest.class);
		
		
		
	}*/


}
