/*
 * Copyright 2013-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.docs;

import static java.net.InetAddress.getLoopbackAddress;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.LauncherSessionListener;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalSetupTeardownListener implements LauncherSessionListener {

    private Logger logger = LoggerFactory.getLogger(GlobalSetupTeardownListener.class);
    private Fixture fixture;

    @Override
    public void launcherSessionOpened(LauncherSession session) {
        // Avoid setup for test discovery by delaying it until tests are about to be executed
        session.getLauncher().registerTestExecutionListeners(new TestExecutionListener() {
            @Override
            public void testPlanExecutionStarted(TestPlan testPlan) {
                if (fixture == null) {
                    fixture = new Fixture();
                    fixture.setUp();
                }
                logger.info("TestPlan started.");
            }
        });
    }

    @Override
    public void launcherSessionClosed(LauncherSession session) {
        if (fixture != null) {
            fixture.tearDown();
            fixture = null;
        }
        logger.info("LauncherSession closed.");
    }

    static class Fixture {

        private HttpServer server;
        private ExecutorService executorService;

        void setUp() {
            try {
                server = HttpServer.create(new InetSocketAddress(getLoopbackAddress(), 0), 0);
            }
            catch (IOException e) {
                throw new UncheckedIOException("Failed to start HTTP server", e);
            }
            server.createContext("/test", exchange -> {
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
            });
            executorService = Executors.newCachedThreadPool();
            server.setExecutor(executorService);
            server.start();
            int port = server.getAddress().getPort();
            System.setProperty("http.server.host", getLoopbackAddress().getHostAddress());
            System.setProperty("http.server.port", String.valueOf(port));
        }

        void tearDown() {
            server.stop(0);
            executorService.shutdownNow();
        }
    }

}