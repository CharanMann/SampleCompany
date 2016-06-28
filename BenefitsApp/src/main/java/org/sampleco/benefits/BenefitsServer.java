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

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;

import java.util.logging.Logger;


/**
 * BenefitsServer implementation based on grizzly server
 */
public final class BenefitsServer {

    private static final Logger LOGGER = Logger.getLogger(BenefitsServer.class.getName());

    /**
     * Not used.
     */
    private BenefitsServer() {
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

        httpServer.getServerConfiguration().addHttpHandler(new BenefitsHandler());


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

}
