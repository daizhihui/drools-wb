/*
 * Copyright 2005 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.workbench.screens.dtablexls.backend.server;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.guvnor.common.services.backend.file.upload.AbstractFileServlet;
import org.jboss.errai.bus.client.api.QueueSession;
import org.jboss.errai.bus.server.api.SessionProvider;
import org.jboss.errai.bus.server.servlet.ServletBootstrapUtil;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.backend.vfs.Path;
import org.uberfire.io.IOService;

/**
 * This is for dealing with XLS Decision Tables
 */
public class DecisionTableXLSFileServlet extends AbstractFileServlet {

    private static final long serialVersionUID = 510l;

    @Inject
    @Named("ioStrategy")
    private IOService ioService;

    @Inject
    private ExtendedDecisionTableXLSService decisionTableXLSService;

    @Override
    protected InputStream doLoad( final Path path,
                                  final HttpServletRequest request ) {
        return decisionTableXLSService.load( path,
                                             getSessionId( request, sessionProvider ) );
    }

    @Override
    protected void doCreate( final Path path,
                             final InputStream data,
                             final HttpServletRequest request,
                             final String comment ) {
        decisionTableXLSService.create( path,
                                        data,
                                        getSessionId( request, sessionProvider ),
                                        comment );
    }

    @Override
    protected void doUpdate( final Path path,
                             final InputStream data,
                             final HttpServletRequest request,
                             final String comment ) {
        decisionTableXLSService.save( path,
                                      data,
                                      getSessionId( request, sessionProvider ),
                                      comment );
    }

    @Override
    protected Path convertPath( final String fileName,
                                final String contextPath ) throws URISyntaxException {
        final org.uberfire.java.nio.file.Path path = ioService.get( new URI( contextPath ) );
        return Paths.convert( path.resolve( fileName ) );
    }

    @Override
    protected Path convertPath( final String fullPath ) throws URISyntaxException {
        final org.uberfire.java.nio.file.Path path = ioService.get( new URI( fullPath ) );
        return Paths.convert( path );
    }
}
