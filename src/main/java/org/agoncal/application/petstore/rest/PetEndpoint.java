package org.agoncal.application.petstore.rest;

import com.amazonaws.apigatewaydemo.action.DemoAction;
import com.amazonaws.apigatewaydemo.logic.PetService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import java.io.IOException;
import java.io.InputStream;
import org.agoncal.application.petstore.util.Loggable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Stateless
@Path("/pets")
@Loggable
@Api("Pet")
public class PetEndpoint
{
    @Inject PetService service; 



    @POST
    @Consumes( {MediaType.APPLICATION_JSON})
    @Produces( {MediaType.APPLICATION_JSON})
    public  String lambdaHandler(InputStream request) throws Exception {
       

        JsonParser parser = new JsonParser();
        JsonObject inputObj;
        try {
            inputObj = parser.parse(IOUtils.toString(request)).getAsJsonObject();
        } catch (IOException e) {
           System.out.println("Error while reading request\n" + e.getMessage());
            throw new Exception(e.getMessage());
        }

        if (inputObj == null || inputObj.get("action") == null || inputObj.get("action").getAsString().trim().equals("")) {
           System.out.println("Invald inputObj, could not find action parameter");
            throw new BadRequestException("Could not find action value in request");
        }

        String actionClass = inputObj.get("action").getAsString();
        DemoAction action;

        try {
            action = DemoAction.class.cast(Class.forName(actionClass).newInstance());
        } catch (final InstantiationException e) {
           System.out.println("Error while instantiating action class\n" + e.getMessage());
            throw new Exception(e.getMessage());
        } catch (final IllegalAccessException e) {
           System.out.println("Illegal access while instantiating action class\n" + e.getMessage());
            throw new Exception(e.getMessage());
        } catch (final ClassNotFoundException e) {
           System.out.println("Action class could not be found\n" + e.getMessage());
            throw new Exception(e.getMessage());
        }

        if (action == null) {
           System.out.println("Action class is null");
            throw new BadRequestException("Invalid action class");
        }

        JsonObject body = null;
        if (inputObj.get("body") != null) {
            body = inputObj.get("body").getAsJsonObject();
        }

        String output = action.handle(body, service);

        return output;
    }
}
