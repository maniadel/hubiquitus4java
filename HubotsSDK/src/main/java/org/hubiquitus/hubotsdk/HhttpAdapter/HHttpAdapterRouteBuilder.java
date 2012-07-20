package org.hubiquitus.hubotsdk.HhttpAdapter;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class HHttpAdapterRouteBuilder extends RouteBuilder {

	private String jettyCamelUri = "";
	private Processor processor = null;
	
	public HHttpAdapterRouteBuilder(String jettyCamelUri, Processor processor) {
		this.jettyCamelUri = jettyCamelUri;
	}
	
	@Override
	public void configure() throws Exception {
		from(this.jettyCamelUri).process(processor);
	}

}
