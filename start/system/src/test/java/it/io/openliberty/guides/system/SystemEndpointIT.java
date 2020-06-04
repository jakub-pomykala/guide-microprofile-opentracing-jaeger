// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2020 IBM Corporation and others.
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

import javax.json.JsonObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SystemEndpointIT {

    @Test
    public void testGetProperties() {
        String port = System.getProperty("sys.http.port");
        String url = "http://localhost:" + port + "/";

        Client client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);

        WebTarget target = client.target(url + "system/properties");
        Response response = target.request().get();

        assertEquals(200, response.getStatus(),
                "Incorrect response code from " + url);

        JsonObject obj = response.readEntity(JsonObject.class);

        assertEquals(System.getProperty("os.name"), obj.getString("os.name"),
                "The system property for the local and remote JVM should match");

        response.close();
    }
}