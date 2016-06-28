/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2016 Charan Mann
 * Portions Copyrighted 2016 ForgeRock AS
 *
 * SampleCompany: Created by Charan Mann on 6/28/16 , 9:11 AM.
 */

package org.sampleco.benefits;

import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.IOException;

/**
 * Handler for HTTP requests.
 */
public class BenefitsHandler extends HttpHandler {

    @Override
    public void service(Request request, Response response) throws Exception {

        String requestURI = request.getRequestURI();
        if (requestURI.length() >= Constants.APP_CONTEXT.length()) {
            requestURI = requestURI.substring(Constants.APP_CONTEXT.length());
        }

        String userID = CommonUtils.isUserAuthenticated(request);

        if (CommonUtils.notNullOrEmpty(userID)) {
            redirectToHome(request, response, userID);
        }

        switch (requestURI) {

            case "/":
            case "":
                returnError(403, "Forbidden", response);
                break;

            case Constants.LOGIN_URI:
                if (Method.GET == request.getMethod()) {
                    String loginPage = CommonUtils.getResourceAsString(Constants.LOGIN_URI);

                    response.setContentType("text/html");
                    response.setStatus(200, "OK");
                    response.setContentLength(loginPage.length());
                    response.getWriter().write(loginPage);
                }

                if (Method.POST == request.getMethod()) {
                    String username = null;
                    String password = null;

                    // Accept username and password as parameters
                    // in the query string or as form-encoded data.
                    if (CommonUtils.notNullOrEmpty(request.getParameter("username"))) {
                        username = request.getParameter("username");
                    }
                    if (CommonUtils.notNullOrEmpty(request.getParameter("password"))) {
                        password = request.getParameter("password");
                    }

                    if (username == null || password == null) {
                        returnError(401, "Authentication Failed", response);
                        return;
                    }

                    if (CommonUtils.credentialsAreValid(username, password)) {

                        // Sets the SSO cookie
                        CommonUtils.setSSOCookie(username, request, response);

                        // Redirect to Home page
                        //redirectToHome(request, response, username);

                        response.setStatus(302);
                        response.setHeader("Location", Constants.APP_CONTEXT + Constants.HOME_URI);
                    } else {
                        returnError(401, "Authentication Failed", response);
                    }
                }
                break;

            case Constants.HOME_URI:
                response.setStatus(302);
                response.setHeader("Location", Constants.APP_CONTEXT + Constants.LOGIN_URI);
                break;

            default:
                returnError(403, "Forbidden", response);
        }
    }

    private void returnError(int errorCode, String errorMessage, Response response) throws IOException {
        response.setStatus(errorCode, errorMessage);
        response.setContentLength(errorMessage.length() + Constants.EOL.length());
        response.getWriter().write(errorMessage + Constants.EOL);
    }

    private void redirectToHome(Request request, Response response, String username) throws IOException {
        // Replace profile page placeholders and respond.
        final StringBuilder headers = new StringBuilder();

        String homePage = CommonUtils.getResourceAsString("/home.html")
                .replaceAll(Constants.EOL, "####")
                .replaceAll("USERNAME", username)
                .replace("METHOD", request.getMethod().getMethodString())
                .replace("REQUEST_URI", request.getDecodedRequestURI())
                .replace("HEADERS", headers.toString())
                .replaceAll("####", Constants.EOL);

        response.setContentType("text/html");
        response.setStatus(200, "OK");
        response.setContentLength(homePage.length());
        response.getWriter().write(homePage);
    }
}

