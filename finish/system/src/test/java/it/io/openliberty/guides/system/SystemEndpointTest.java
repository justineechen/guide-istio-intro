// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
 // end::copyright[]
package it.io.openliberty.guides.system;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javax.json.JsonObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;

public class SystemEndpointTest {

    @Test
    public void testGetProperties() {
        // Allows the "Host" header to be set
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        String port = System.getProperty("test.port");
        String ip = System.getProperty("test.ip");
        String url = "http://" + ip + ":" + port + "/system/properties";

        Client client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);

        WebTarget target = client.target(url);
        Response response = target.request().header("Host", "my-inventory.com").get();

        assertEquals("Incorrect response code from " + url, 200, response.getStatus());

        JsonObject obj = response.readEntity(JsonObject.class);

        assertEquals("The OS name should be \"Linux\"",
                     "Linux",
                     obj.getString("os.name"));
        
        response.close();
    }
}
