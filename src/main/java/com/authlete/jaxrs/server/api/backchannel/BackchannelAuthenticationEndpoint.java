/*
 * Copyright (C) 2019 Authlete, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */
package com.authlete.jaxrs.server.api.backchannel;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import com.authlete.common.api.AuthleteApiFactory;
import com.authlete.jaxrs.BaseBackchannelAuthenticationEndpoint;


/**
 *  An implementation of backchannel authentication endpoint of CIBA (Client Initiated
 *  Backchannel Authentication).
 *
 * @author Hideki Ikeda
 */
@Path("/api/backchannel/authentication")
public class BackchannelAuthenticationEndpoint extends BaseBackchannelAuthenticationEndpoint
{
    /**
     * The backchannel authentication endpoint.
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response post(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            MultivaluedMap<String, String> parameters,
            @Context HttpServletRequest request)
    {
        String[] clientCertificates = extractClientCertificateChain(request);

        // Handle the backchannel authentication request.
        return handle(AuthleteApiFactory.getDefaultApi(),
                new BackchannelAuthenticationRequestHandlerSpiImpl(), parameters,
                authorization, clientCertificates);
    }
}
