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

package controllers;


import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.Result;
import ninja.Results;
import ninja.i18n.Lang;
import ninja.params.Param;
import org.jooq.DSLContext;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

import static models.Tables.AUTHOR;
import static models.Tables.BOOK;

@Singleton
public class ApplicationController {

    private Logger logger;

    private Lang lang;

    private DSLContext database;

    @Inject
    public ApplicationController(Lang lang,
                                 Logger logger,
                                 DSLContext database) {
        this.lang = lang;
        this.logger = logger;
        this.database = database;

    }

    public Result index() {

        org.jooq.Result<?> result = database.select(BOOK.TITLE)
                .from(AUTHOR)
                .join(BOOK)
                .on(BOOK.AUTHOR_ID.eq(AUTHOR.ID))
                .orderBy(BOOK.ID.asc())
                .fetch();

        List<String> titles = result.getValues(BOOK.TITLE);

        Map<String, Object> toRender = Maps.newHashMap();
        toRender.put("titles", titles);

        // Default rendering is simple by convention
        // This renders the page in views/ApplicationController/index.ftl.html
        return Results.html().render(toRender);

    }

    public Result post(@Param("email") String email,
                       @Param("content") String content) {


        // ... and redirect to main page
        return Results.redirect("/");

    }
}
