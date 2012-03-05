package org.hubiquitus.hserver.jaxrs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hubiquitus.hapi.model.ObjectMapperInstanceDomainKey;
import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.hubiquitus.hapi.model.impl.PayloadResultEntry;
import org.hubiquitus.hapi.persistence.DataBase;
import org.hubiquitus.hapi.persistence.exeption.DBException;
import org.hubiquitus.hapi.utils.JsonObjectMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

	
@Path("/requestservice/")
public class RequestService {
    long currentId = 123;
    Map<Long, Customer> customers = new HashMap<Long, Customer>();
    
    Logger logger = LoggerFactory.getLogger(RequestService.class);

    
    /**
	 * hServer data base configuration
	 */
	private DataBase hServerDataBase;
	
    
    public RequestService() {
    }

    
    @POST
    @Path("/hrequest/")
    public Response hRequest(String hRequest) {
        
    	logger.debug("Invoking hRequest: " + hRequest);
    	PayloadResultEntry results = null;
   		
    	DataRequestEntry dataRequestEntry = null;
		try {
			dataRequestEntry = JsonObjectMapperFactory.getInstance(ObjectMapperInstanceDomainKey.PUBLISH_ENTRY).readValue(hRequest, DataRequestEntry.class);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		

        if (dataRequestEntry!= null) {
        	
			
        	try {
				// Requete de type FIND()
				if (DataRequestEntry.TYPE_FIND.equals(dataRequestEntry.getRequestType())) { 
					results = hServerDataBase.findDocumentsByKeys(dataRequestEntry);
			        
			    // Requete de type GROUP()   
				} else if (DataRequestEntry.TYPE_GROUP.equals(dataRequestEntry.getRequestType())) {
					results = hServerDataBase.groupDocumentsByKeys(dataRequestEntry);
				
				// Requete de type cmd
				} else if (DataRequestEntry.TYPE_CMD.equals(dataRequestEntry.getRequestType())) {
					// TODO
				
				// Requete de type ditsinct
				} else if (DataRequestEntry.TYPE_DISTINCT.equals(dataRequestEntry.getRequestType())) {
					results = hServerDataBase.distinctDocumentsByKeys(dataRequestEntry);
	 			}
			
			} catch (DBException e) {
				logger.error(e.getMessage(), e);
			}
        	
        }
        
        return Response.ok(results).build();
    }
    
    @Required
    public void setHServerDataBase(DataBase hServerDataBase) {
		this.hServerDataBase = hServerDataBase;
	}
    

}
