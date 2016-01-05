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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.utils.NinjaConstant;
import ninja.utils.NinjaProperties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderKeywordStyle;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.slf4j.Logger;

import static ninja.jooq.NinjaJooqProperties.*;

/**
 * This is an internal class of Ninja Ebeans support. It is responsible for
 * creating db connections and shutting them down upon server start / stop.
 * <p>
 * As end-user you should NOT use this method directly. Instead simply inject
 * EbeanServer into the class you want to use. The interface EbeanServer is
 * configured by this class and provided by a provider.
 * <p>
 */
@Singleton
public class NinjaJooqLifecycle {


    private final NinjaProperties ninjaProperties;

    private final Logger logger;

    private DSLContext dslContext;


    @Inject
    public NinjaJooqLifecycle(Logger logger,
                              NinjaProperties ninjaProperties) {

        this.logger = logger;
        this.ninjaProperties = ninjaProperties;
        
        startServer();
    }

    /**
     * This method reads the configuration properties from
     * your application.conf file and configures jOOQ accordingly.
     * 
     */
    public final void startServer(){
        logger.info("Starting jOOQ Module.");

        // Setup basic parameters
        boolean jooqRenderSchema = ninjaProperties.getBooleanWithDefault(renderSchema, true);

        //renderMapping

        String jooqRenderNameStyleString = ninjaProperties.getWithDefault(renderNameStyle, "QUOTED");
        RenderNameStyle jooqRenderNameStyle = RenderNameStyle.fromValue(jooqRenderNameStyleString);

        String jooqRenderKeywordStyleString = ninjaProperties.getWithDefault(renderKeywordStyle, "LOWER");
        RenderKeywordStyle jooqRenderKeywordStyle = RenderKeywordStyle.valueOf(jooqRenderKeywordStyleString);

        boolean jooqRenderFormatted = ninjaProperties.getBooleanWithDefault(renderFormatted, false);

        String jooqStatementTypeString = ninjaProperties.getWithDefault(statementType, "PREPARED_STATEMENT");
        StatementType jooqStatmentType = StatementType.valueOf(jooqStatementTypeString);

        boolean jooqExecuteLogging = ninjaProperties.getBooleanWithDefault(executeLogging, true);

        // Execute listeners

        boolean jooqExecuteWithOptimisticLocking = ninjaProperties.getBooleanWithDefault(
                executeWithOptimisticLocking, true);

        boolean jooqAttachRecords = ninjaProperties.getBooleanWithDefault(attachRecords, true);

        String jooqSqlDialect = ninjaProperties.getWithDefault(sqlDialect, "DEFAULT");
        SQLDialect sqlDialect = SQLDialect.valueOf(jooqSqlDialect);


        Settings settings = new Settings();

        settings.setRenderSchema(jooqRenderSchema);
        settings.setRenderNameStyle(jooqRenderNameStyle);
        settings.setRenderKeywordStyle(jooqRenderKeywordStyle);
        settings.setRenderFormatted(jooqRenderFormatted);
        settings.setStatementType(jooqStatmentType);
        settings.setExecuteLogging(jooqExecuteLogging);
        settings.setExecuteWithOptimisticLocking(jooqExecuteWithOptimisticLocking);
        settings.setAttachRecords(jooqAttachRecords);

        String connectionUrl = ninjaProperties.getOrDie(NinjaConstant.DB_CONNECTION_URL);
        String connectionUsername = ninjaProperties.getOrDie(NinjaConstant.DB_CONNECTION_USERNAME);
        String connectionPassword = ninjaProperties.getWithDefault(NinjaConstant.DB_CONNECTION_PASSWORD, "");

        BasicDataSource connectionPool = new BasicDataSource();

        connectionPool.setUrl(connectionUrl);
        connectionPool.setUsername(connectionUsername);
        connectionPool.setPassword(connectionPassword);

        Configuration configuration = new DefaultConfiguration();
        configuration.set(sqlDialect);
        configuration.set(settings);
        configuration.set(connectionPool);

        dslContext = DSL.using(configuration);
    }
    
    public DSLContext getDslContext() {
        return dslContext;
    }

}
