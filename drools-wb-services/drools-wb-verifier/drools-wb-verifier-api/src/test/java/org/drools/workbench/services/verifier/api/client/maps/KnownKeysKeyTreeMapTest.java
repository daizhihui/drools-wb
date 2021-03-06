/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.workbench.services.verifier.api.client.maps;

import org.drools.workbench.services.verifier.api.client.index.keys.Key;
import org.drools.workbench.services.verifier.api.client.index.keys.UUIDKey;
import org.drools.workbench.services.verifier.api.client.AnalyzerConfigurationMock;
import org.drools.workbench.services.verifier.api.client.maps.util.HasKeys;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KnownKeysKeyTreeMapTest {

    private KeyTreeMap<Person> map;

    @Before
    public void setUp() throws Exception {
        map = new KeyTreeMap<>( KeyDefinition.newKeyDefinition().withId( "age" ).build() );
    }

    @Test
    public void testExisting() throws Exception {
        assertNotNull( map.get( KeyDefinition.newKeyDefinition().withId( "age" ).build() ) );
    }

    @Test
    public void testUnknown() throws Exception {
        assertNull( map.get( KeyDefinition.newKeyDefinition().withId( "unknown" ).build() ) );
    }

    class Person
            implements HasKeys {

        private UUIDKey uuidKey = new AnalyzerConfigurationMock().getUUID( this );

        @Override
        public Key[] keys() {
            return new Key[]{
                    uuidKey,
                    new Key( KeyDefinition.newKeyDefinition().withId( "name" ).build(),
                             "hello" )
            };
        }

        @Override
        public UUIDKey getUuidKey() {
            return uuidKey;
        }
    }
}