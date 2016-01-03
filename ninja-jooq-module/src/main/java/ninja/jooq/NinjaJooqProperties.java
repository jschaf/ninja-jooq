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

/**
 * All currently supported jOOQ properties in file
 * application.conf.
 * 
 * Add them to your application.conf file to alter them.
 * 
 */
public interface NinjaJooqProperties {
    
    /** 
     * Comma separated list of Ebean models which should be registered
     * at the EbeanServer.
     * 
     * Usually The NinjaCasino plugin registers all models it
     * can get from sub directory. But sometimes that is not enough
     * (especially when using external jars.). To
     * make that reliable you can use that property.
     * 
     * ebean.models=model.myModel1, model.myModel2
     * 
     * To register all classes in a package, simply append a ".*" at the end
     * of your model name.
     * 
     * ebean.models=model.MyModel1,com.company.models.*
     * 
     */


    // http://www.jooq.org/xsd/jooq-runtime-2.5.0.xsd for default values

    // boolean
    public final String renderSchema = "true";

    // RenderMapping
    // public final String renderMapping = "";

    // RenderNameStyle
    public final String renderNameStyle = "QUOTED";

    // RenderKeywordStyle
    public final String renderKeywordStyle = "LOWER";

    // boolean
    public final String renderFormatted = "false";

    // StatementType
    public final String statementType = "PREPARED_STATEMENT";

    // boolean
    public final String executeLogging = "true";

    // ExecuteListeners
    // public final String executeListeners = "";

    // boolean
    public final String executeWithOptimisticLocking = "false";

    // boolean
    public final String attachRecords = "true";

    public final String sqlDialect = "DEFAULT";
}
