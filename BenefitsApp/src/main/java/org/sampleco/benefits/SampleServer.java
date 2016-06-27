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
 * information: "Portions Copyright [year] [name of copyright owner]".
 *
 * Copyright 2014-2015 ForgeRock AS.
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

    private static final String EOL = System.getProperty("line.separator");
    private static final Logger LOGGER = Logger.getLogger(SampleServer.class.getName());
    private static final int DEFAULT_PORT = 8014;

    /**
     * Not used.
     */
    private SampleServer() {
    }

    /**
     * Start an HTTP server.
     *
     * @param args Optionally specify a free port number.
     *             Defaults: 8014 (HTTP)
     */
    public static void main(String[] args) {
        final String usage = "Optionally specify HTTP port number. "
                + "Defaults: " + DEFAULT_PORT;
        int port = DEFAULT_PORT;

        runServer(port);
    }

    /**
     * Run the HTTP server, listening on the chosen port.
     * <p>
     * On HTTP GET the server returns a home page with a login form.
     * <p>
     * On HTTP PUT with valid credentials, the server returns a profile page.
     *
     * @param port Port on which the server listens for HTTP
     */
    static void runServer(int port) {
        start(port, true);
    }

    /**
     * Run the HTTP server, listening on the chosen port.
     * <p>
     * Use stop() to shut the server down.
     *
     * @param port Port on which the server listens for HTTP
     * @return The HttpServer that is running if letRun is true
     */
    static HttpServer start(final int port) {
        return start(port, false);
    }

    /**
     * Run the HTTP server, listening on the chosen port.
     *
     * @param port         Port on which the server listens for HTTP
     * @param waitForCtrlC If true, only stop the server when the user enters Ctrl+C
     * @return The HttpServer that is running if letRun is true
     */
    static HttpServer start(final int port, final boolean waitForCtrlC) {

        final HttpServer httpServer = new HttpServer();
        System.out.println("Preparing to listen for HTTP on port " + port + ".");
        httpServer.addListener(new NetworkListener("HTTP", "0.0.0.0", port));

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
     * Stop the HTTP Server started with waitForCtrlC set to false.
     *
     * @param httpServer The server to stop
     */
    static void stop(final HttpServer httpServer) {
        httpServer.shutdownNow();
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

        if (null == username || null == password) {
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
                content.append(scanner.nextLine()).append(EOL);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return content.toString();
    }

    /**
     * Handler for HTTP GET and HTTP PUT requests.
     */
    static class SampleHandler extends HttpHandler {

        /**
         * Returns true if the String to test is null nor empty.
         *
         * @param s The String to test.
         * @return true if the String to test is null nor empty.
         */
        private static boolean isNullOrEmpty(final String s) {
            return s == null || s.isEmpty();
        }

        /**
         * Returns true if the String to test is neither null nor empty.
         *
         * @param s The String to test.
         * @return true if the String to test is neither null nor empty.
         */
        private static boolean notNullOrEmpty(final String s) {
            return s != null && !s.isEmpty();
        }

        @Override
        public void service(Request request, Response response) throws Exception {
            if (request.getHttpHandlerPath().equalsIgnoreCase("/login")) {
                response.addCookie(new Cookie("login-cookie", "chocolate-chip"));
            }

            if (Method.GET == request.getMethod()) {
                String homePage = getResourceAsString("/login.html");

                response.setContentType("text/html");
                response.setStatus(200, "OK");
                response.setContentLength(homePage.length());
                response.getWriter().write(homePage);
            }

            if (Method.POST == request.getMethod()) {
                String username;
                String password;

                // Allow use of IDToken1 (username) and IDToken2 (password)
                // to simulate the behavior of the OpenAM classic UI login page.
                username = request.getParameter("IDToken1");
                password = request.getParameter("IDToken2");
                if (username != null && password != null) {
                    //simulateOpenAMResponse(username, password, response);
                    return;
                }

                // Accept username and password as headers for testing.
                if (notNullOrEmpty(request.getHeader("username"))) {
                    username = request.getHeader("username");
                }
                if (notNullOrEmpty(request.getHeader("password"))) {
                    password = request.getHeader("password");
                }

                // Accept username and password as parameters
                // in the query string or as form-encoded data.
                if (notNullOrEmpty(request.getParameter("username"))) {
                    username = request.getParameter("username");
                }
                if (notNullOrEmpty(request.getParameter("password"))) {
                    password = request.getParameter("password");
                }

                if (username == null || password == null) {
                    final String authRequired = "Authorization Required";
                    response.setStatus(401, authRequired);
                    response.setContentLength(authRequired.length() + EOL.length());
                    response.getWriter().write(authRequired + EOL);
                    return;
                }

                if (credentialsAreValid(username, password)) {

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

                    String profilePage = getResourceAsString("/home.html")
                            .replaceAll(EOL, "####")
                            .replaceAll("USERNAME", username)
                            .replace("METHOD", request.getMethod().getMethodString())
                            .replace("REQUEST_URI", request.getDecodedRequestURI())
                            .replace("HEADERS", headers.toString())
                            .replaceAll("####", EOL);

                    response.setContentType("text/html");
                    response.setStatus(200, "OK");
                    response.setContentLength(profilePage.length());
                    response.getWriter().write(profilePage);

                } else {
                    final String forbidden = "Forbidden";
                    response.setStatus(403, forbidden);
                    response.setContentLength(forbidden.length() + EOL.length());
                    response.getWriter().write(forbidden + EOL);
                }
            }
        }
    }
}