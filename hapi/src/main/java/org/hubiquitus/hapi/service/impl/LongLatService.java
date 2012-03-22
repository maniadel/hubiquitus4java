package org.hubiquitus.hapi.service.impl;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLEncoder;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.hubiquitus.hapi.model.impl.LocationPublishEntry;

/**
 * Longitude Latitude service
 * @author o.chauvie
 *
 */
public class LongLatService {
	
	private Logger logger = LoggerFactory.getLogger(LongLatService.class);
	
	private int proxyPort;
	private String proxyHost;
	private boolean useProxy = false;
	
	/*
	 * Rayon de la terre
	 */
	private int R = 6366;
    
	private static final String GEOCODE_REQUEST_URL = "http://maps.googleapis.com/maps/api/geocode/xml?sensor=false&";
    private static HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());

    /**
     * Set the Longitude and Latitude for {@link LocationPublishEntry}
     * @param localisation the {@link LocationPublishEntry} to locate
     * @return the {@link LocationPublishEntry} with longitude and latitude
     */
    public LocationPublishEntry setLongitudeLatitude(LocationPublishEntry localisation) {
    	LocationPublishEntry locationResult = localisation;
        try {
        	
            StringBuilder urlBuilder = new StringBuilder(GEOCODE_REQUEST_URL);
            
            String address = localisation.getStreetNumber() + ", " + localisation.getStreet() + " " + localisation.getCity();
            
            if (StringUtils.isNotBlank(address)) {
                urlBuilder.append("&address=").append(URLEncoder.encode(address, "UTF-8"));
            }
 
            final GetMethod getMethod = new GetMethod(urlBuilder.toString());
            try {
            	if (useProxy) {
            		httpClient.getHostConfiguration().setProxy(proxyHost, proxyPort);
            	}
                httpClient.executeMethod(getMethod);
                Reader reader = new InputStreamReader(getMethod.getResponseBodyAsStream(), getMethod.getResponseCharSet());
                 
                int data = reader.read();
                char[] buffer = new char[1024];
                Writer writer = new StringWriter();
                while ((data = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, data);
                }
 
                String result = writer.toString();
                logger.debug(result.toString());
                
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader("<"+writer.toString().trim()));
                Document doc = db.parse(is);

                String strLatitude = getXpathValue(doc, "//GeocodeResponse/result/geometry/location/lat/text()");
                logger.debug("Latitude:" + strLatitude);
                locationResult.setLat(strLatitude);
                 
                String strLongtitude = getXpathValue(doc,"//GeocodeResponse/result/geometry/location/lng/text()");
                logger.debug("Longitude:" + strLongtitude);
                locationResult.setIng(strLongtitude);
                
            } finally {
                getMethod.releaseConnection();
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }
        return locationResult;
    }

    private String getXpathValue(Document doc, String strXpath) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xPath.compile(strXpath);
        String resultData = null;
        Object result4 = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result4;
        for (int i = 0; i < nodes.getLength(); i++) {
            resultData = nodes.item(i).getNodeValue();
        }
        return resultData;
    }
    
    /**
     * Return fly distance between two localizations (km)
     * @param localisation1 
     * @param localisation2
     * @return the distance in km
     */
    public double getBirdFlyDistance(LocationPublishEntry localisation1, LocationPublishEntry localisation2) {
    	double lat1 = Double.parseDouble(localisation1.getLat());
    	double lon1 = Double.parseDouble(localisation1.getIng());
    	double lat2 = Double.parseDouble(localisation2.getLat());
    	double lon2 = Double.parseDouble(localisation2.getIng());
    	return getBirdFlyDistance(lat1, lon1, lat2, lon2);
    }
    
    /**
     * Return fly distance between localization and point (km)
     * @param localisation
     * @param lat2
     * @param lon2
     * @return the distance in km
     */
    public double getBirdFlyDistance(LocationPublishEntry localisation, double lat2, double lon2) {
    	double lat1 = Double.parseDouble(localisation.getLat());
    	double lon1 = Double.parseDouble(localisation.getIng());
    	return getBirdFlyDistance(lat1, lon1, lat2, lon2);
    }
    
    
    /**
     * Return fly distance between two points (km)
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return the distance in km
     */
    public double getBirdFlyDistance(double lat1, double lon1, double lat2, double lon2) {
    	double distance = R * Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1-lon2));
		return distance;
    }
    
    /**
	 * Getter proxyPort
	 * @return the proxyPort
	 */
	public int getProxyPort() {
		return proxyPort;
	}

	/**
	 * Setter proxyPort
	 * @param proxyPort the proxyPort to set
	 */
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	/**
	 * Getter proxyHost
	 * @return the proxyHost
	 */
	public String getProxyHost() {
		return proxyHost;
	}

	/**
	 * Setter proxyHost
	 * @param proxyHost the proxyHost to set
	 */
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	/**
	 * Getter useProxy
	 * @return the useProxy
	 */
	public boolean isUseProxy() {
		return useProxy;
	}

	/**
	 * Setter isProxy
	 * @param isProxy the isProxy to set
	 */
	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}

}
