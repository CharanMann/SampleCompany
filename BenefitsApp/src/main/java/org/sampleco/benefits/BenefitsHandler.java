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
import java.io.InputStream;
import java.io.OutputStream;

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

        switch (requestURI) {

            case Constants.LOGIN_URI:
                if (CommonUtils.notNullOrEmpty(userID)) {
                    // Redirect to Home page
                    redirectResponse(response, Constants.APP_CONTEXT + Constants.HOME_URI);
                    break;
                }

                if (Method.GET == request.getMethod()) {
                    buildPage(response, Constants.LOGIN_URI);
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
                        buildError(401, "Authentication Failed", response);
                        return;
                    }

                    if (CommonUtils.credentialsAreValid(username, password)) {

                        // Sets the SSO cookie
                        CommonUtils.setSSOCookie(username, request, response);

                        // Redirect to Home page
                        redirectResponse(response, Constants.APP_CONTEXT + Constants.HOME_URI);
                    } else {
                        buildError(401, "Authentication Failed", response);
                    }
                }
                break;

            case Constants.HOME_URI:
                if (CommonUtils.notNullOrEmpty(userID)) {
                    buildHomePage(request, response, userID);
                } else {
                    // Redirect to Login page
                    redirectResponse(response, Constants.APP_CONTEXT + Constants.LOGIN_URI);
                }
                break;

            case Constants.LOGOUT_URI:
                buildPage(response, Constants.LOGOUT_URI);
                break;

            default:
                // Redirect to Login page
                redirectResponse(response, Constants.APP_CONTEXT + Constants.LOGIN_URI);
        }
    }

    /**
     * Redirect to specified page
     *
     * @param response
     * @param redirectURL
     */
    private void redirectResponse(Response response, String redirectURL) {
        response.setStatus(302);
        response.setHeader("Location", redirectURL);
    }

    /**
     * Builds Requested page
     *
     * @param page
     * @param response
     * @throws IOException
     */
    private void buildPage(Response response, String page) throws IOException {
        String resource = CommonUtils.getResourceAsString(page);

        response.setContentType("text/html");
        response.setStatus(200, "OK");
        response.setContentLength(resource.length());
        response.getWriter().write(resource);
    }

    /**
     * Builds error message
     *
     * @param errorCode
     * @param errorMessage
     * @param response
     * @throws IOException
     */
    private void buildError(int errorCode, String errorMessage, Response response) throws IOException {
        response.setStatus(errorCode, errorMessage);
        response.setContentLength(errorMessage.length() + Constants.EOL.length());
        response.getWriter().write(errorMessage + Constants.EOL);
    }

    /**
     * Builds Home Page
     *
     * @param request
     * @param response
     * @param username
     * @throws IOException
     */
    private void buildHomePage(Request request, Response response, String username) throws IOException {
        // Replace profile page placeholders and respond.
        final StringBuilder headers = new StringBuilder();

        for (String name : request.getHeaderNames()) {
            for (String header : request.getHeaders(name)) {
                headers.append(name)
                        .append(": ")
                        .append(header)
                        .append("<br>");
            }
        }

        String homePage = CommonUtils.getResourceAsString(Constants.HOME_URI)
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

