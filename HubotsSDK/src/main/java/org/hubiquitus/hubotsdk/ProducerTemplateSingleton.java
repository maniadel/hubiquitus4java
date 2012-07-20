package org.hubiquitus.hubotsdk;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

public class ProducerTemplateSingleton {
	private static ProducerTemplate producerTemplate;
	private static DefaultCamelContext context;
	
	public static ProducerTemplate getProducerTemplate() {
		if (producerTemplate == null) {
			producerTemplate = context.createProducerTemplate();
		}
		return producerTemplate;
	}
	
	public static void setContext(DefaultCamelContext context) {
		ProducerTemplateSingleton.context = context;
	}	
}
