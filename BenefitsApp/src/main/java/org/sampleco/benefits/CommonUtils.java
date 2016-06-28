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
 * SampleCompany: Created by Charan Mann on 6/28/16 , 9:20 AM.
 */

package org.sampleco.benefits;

import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 * Common utility class
 */
public class CommonUtils {

    /**
     * Suppress constructor
     */
    private CommonUtils() {
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }

    /**
     * Returns true if the String to test is neither null nor empty.
     *
     * @param s The String to test.
     * @return true if the String to test is neither null nor empty.
     */
    public static boolean notNullOrEmpty(final String s) {
        return s != null && !s.isEmpty();
    }

    /**
     * Sets SSO Cookie.
     *
     * @param username A username such as {@code demo}
     * @param response The response to the request
     * @throws IOException Failed when checking credentials
     */
    public static void setSSOCookie(String username,
                                    Request request, Response response) throws IOException {
        Cookie cookie = new Cookie(Constants.SSO_COOKIE, Constants.SSO_COOKIE_USERTOKEN + username);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * Checks if user has already been authenticated.
     *
     * @param request
     * @return UserID if user has already been authenticated, null otherwise
     */
    public static String isUserAuthenticated(Request request) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (Constants.SSO_COOKIE.equals(cookie.getName())) {
                return cookie.getValue().substring(Constants.SSO_COOKIE_USERTOKEN.length());
            }
        }
        return null;
    }

    /**
     * Check whether username and password credentials are valid.
     *
     * @param username A username such as {@code demo}
     * @param password A password such as {@code changeit}
     * @return True if the username matches the password in credentials.properties
     * @throws IOException Could not read credentials.properties
     */
    public static boolean credentialsAreValid(
            final String username, final String password)
            throws IOException {

        if (isNullOrEmpty(username) || isNullOrEmpty(password)) {
            return false;
        }

        boolean result = false;

        Properties credentials = new Properties();
        InputStream in = BenefitsHandler.class.getResourceAsStream("/credentials.properties");
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
    public static String getResourceAsString(final String resource) {

        StringBuilder content = new StringBuilder();
        InputStream inputStream = BenefitsHandler.class.getResourceAsStream(resource);

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
}
