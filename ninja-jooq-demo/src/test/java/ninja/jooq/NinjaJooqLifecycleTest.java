/**
 * Copyright (C) 2012 the original author or authors.
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

package ninja.jooq;

import ninja.utils.NinjaMode;
import ninja.utils.NinjaProperties;
import ninja.utils.NinjaPropertiesImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class NinjaJooqLifecycleTest {

    @Mock
    private Logger logger;

    /**
     * This tests does three things 
     * 1) Test that stuff in {@link ninja.jooq.NinjaJooqProperties} is correct and without typos
     * 2) Test that default properties are not altered accidentally. 
     * 3) All properties get called assuming they are ready to use inside the lifecycle.
     */
    @Test
    public void makeSureCorrectPropertiesGetParsed() {

        // Setup and spy on the properties
        NinjaProperties ninjaProperties = spy(new NinjaPropertiesImpl(NinjaMode.test));

        NinjaJooqLifecycle ninjaJooqLifecycle = new NinjaJooqLifecycle(logger, ninjaProperties);
//
        // Execute the server startup
        ninjaJooqLifecycle.startServer();

        // InOrder because I couldn't get the tests to pass with just verify.  Mockito would confuse multiple calls
        // to each function with different arguments incorrectly.
        InOrder inOrder = inOrder(ninjaProperties);
        inOrder.verify(ninjaProperties).getBooleanWithDefault("jooq.renderSchema", true);
        inOrder.verify(ninjaProperties).getWithDefault("jooq.renderNameStyle", "QUOTED");
        inOrder.verify(ninjaProperties).getWithDefault("jooq.renderNameStyle", "QUOTED");
        inOrder.verify(ninjaProperties).getWithDefault("jooq.renderKeywordStyle", "LOWER");
        inOrder.verify(ninjaProperties).getBooleanWithDefault("jooq.renderFormatted", false);
        inOrder.verify(ninjaProperties).getWithDefault("jooq.statementType", "PREPARED_STATEMENT");
        inOrder.verify(ninjaProperties).getBooleanWithDefault("jooq.executeLogging", true);
        inOrder.verify(ninjaProperties).getBooleanWithDefault("jooq.executeWithOptimisticLocking", true);
        inOrder.verify(ninjaProperties).getBooleanWithDefault("jooq.attachRecords", true);
        inOrder.verify(ninjaProperties).getWithDefault("jooq.sqlDialect", "DEFAULT");
    }

}
