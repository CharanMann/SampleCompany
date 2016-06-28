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
 * SampleCompany: Created by Charan Mann on 6/27/16 , 7:48 PM.
 */

package org.sampleco.benefits;

import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;


/**
 * Simple servlet allowing user-agents to get a home page,
 * and to post form-based login to access a protected profile page.
 */
public final class SampleServer {

    private static final Logger LOGGER = Logger.getLogger(SampleServer.class.getName());

    /**
     * Not used.
     */
    private SampleServer() {
    }

    /**
     * Start an HTTP server.
     */
    public static void main(String[] args) {

        runServer(Constants.DEFAULT_PORT, true);
    }

    /**
     * Run the HTTP server, listening on the chosen port.
     *
     * @param port         Port on which the server listens for HTTP
     * @param waitForCtrlC If true, only stop the server when the user enters Ctrl+C
     * @return The HttpServer that is running if letRun is true
     */
    static HttpServer runServer(final int port, final boolean waitForCtrlC) {

        final HttpServer httpServer = new HttpServer();
        httpServer.addListener(new NetworkListener("HTTP", "benefits.sc.com", port));

        httpServer.getServerConfiguration().addHttpHandler(new SampleHandler());


        if (waitForCtrlC) {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

                public void run() {
                    httpServer.shutdownNow();
                }
            }, "shutDownHook"));
        }

        try {
            System.out.println("Starting server...");
            httpServer.start();
            if (waitForCtrlC) {
                System.out.println("Press Ctrl+C to stop the server.");
                Thread.currentThread().join();
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

        return httpServer;
    }

    /**
     * Check whether username and password credentials are valid.
     *
     * @param username A username such as {@code demo}
     * @param password A password such as {@code changeit}
     * @return True if the username matches the password in credentials.properties
     * @throws IOException Could not read credentials.properties
     */
    static synchronized boolean credentialsAreValid(
            final String username, final String password)
            throws IOException {

        if (isNullOrEmpty(username) || isNullOrEmpty(password)) {
            return false;
        }

        boolean result = false;

        Properties credentials = new Properties();
        InputStream in = SampleHandler.class.getResourceAsStream("/credentials.properties");
        credentials.load(in);

        final String pwd = credentials.getProperty(username);
        if (pwd != null) {
            result = pwd.equals(password);
        }

        in.close();

        return result;
    }

    /**
     * Read the contents of a resource file into a string.
     *
     * @param resource Path to resource file
     * @return String holding the content of the resource file
     */
    static synchronized String getResourceAsString(final String resource) {

        StringBuilder content = new StringBuilder();
        InputStream inputStream = SampleHandler.class.getResourceAsStream(resource);

        Scanner scanner = null;
        try {
            scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append(Constants.EOL);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return content.toString();
    }

    private static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }


    /**
     * Handler for HTTP GET and HTTP PUT requests.
     */
    static class SampleHandler extends HttpHandler {

        /**
         * Returns true if the String to test is neither null nor empty.
         *
         * @param s The String to test.
         * @return true if the String to test is neither null nor empty.
         */
        private static boolean notNullOrEmpty(final String s) {
            return s != null && !s.isEmpty();
        }

        /**
         * Sets SSO Cookie.
         *
         * @param username A username such as {@code demo}
         * @param response The response to the request
         * @throws IOException Failed when checking credentials
         */
        private static void setSSOCookie(String username,
                                         Request request, Response response) throws IOException {
            Cookie cookie = new Cookie(Constants.SSO_COOKIE, Constants.SSO_COOKIE_USERTOKEN + username);
            cookie.setDomain(request.getServerName());
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        private static String isUserAuthenticated(Request request) {
            Cookie[] cookies = request.getCookies();

            for (Cookie cookie : cookies) {
                if (Constants.SSO_COOKIE.equals(cookie.getName())) {
                    return cookie.getValue().substring(Constants.SSO_COOKIE_USERTOKEN.length());
                }
            }
            return null;
        }

        @Override
        public void service(Request request, Response response) throws Exception {

            String requestURI = request.getRequestURI();
            if (requestURI.length() >= Constants.APP_CONTEXT.length())
            {
                requestURI = requestURI.substring(Constants.APP_CONTEXT.length());
            }

            String userID = isUserAuthenticated(request);

            if (notNullOrEmpty(userID)) {
                redirectToHome(request, response, userID);
            }

            switch (requestURI) {

                case "/":
                case "":
                    returnError(403, "Forbidden", response);
                    break;

                case Constants.LOGIN_URI:
                    if (Method.GET == request.getMethod()) {
                        String loginPage = getResourceAsString(Constants.LOGIN_URI);

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
                        if (notNullOrEmpty(request.getParameter("username"))) {
                            username = request.getParameter("username");
                        }
                        if (notNullOrEmpty(request.getParameter("password"))) {
                            password = request.getParameter("password");
                        }

                        if (username == null || password == null) {
                            returnError(401, "Authentication Failed", response);
                            return;
                        }

                        if (credentialsAreValid(username, password)) {

                            // Sets the SSO cookie
                            setSSOCookie(username, request, response);

                            // Redirect to Home page
                            //redirectToHome(request, response, username);

                            response.setStatus(302);
                            response.setHeader("Location",Constants.APP_CONTEXT + Constants.HOME_URI);
                        } else {
                            returnError(401, "Authentication Failed", response);
                        }
                    }
                    break;

                case Constants.HOME_URI:
                    response.setStatus(302);
                    response.setHeader("Location",Constants.APP_CONTEXT + Constants.LOGIN_URI);
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

            String homePage = getResourceAsString("/home.html")
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
}
