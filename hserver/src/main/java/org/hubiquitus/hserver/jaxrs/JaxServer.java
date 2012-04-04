package org.hubiquitus.hserver.jaxrs;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

public class JaxServer implements Runnable {

	Logger logger = LoggerFactory.getLogger(JaxServer.class);

	/**
	 * Jax server configuration
	 */
	protected JaxServerConfiguration jaxServerConfiguration;

	@Override
	public void run() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
	    sf.setResourceClasses(RequestService.class);
	    sf.setResourceProvider(RequestService.class, new SingletonResourceProvider(new RequestService()));
	        
	    String address = "http://" + jaxServerConfiguration.getJaxServerHost() + ":" + jaxServerConfiguration.getJaxServerPort() + "/";;
	    logger.info("JaxServer: " + address);
	        
	    sf.setAddress(address);
	    sf.create();
	}

	
	// Spring injections ------------------------------------
    @Required
    public void setJaxServerConfiguration(JaxServerConfiguration jaxServerConfiguration) {
		this.jaxServerConfiguration = jaxServerConfiguration;
	}

}
