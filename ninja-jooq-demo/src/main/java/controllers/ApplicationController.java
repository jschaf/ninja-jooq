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
import ninja.params.Param;
import repositories.Repository;

import java.util.List;
import java.util.Map;

@Singleton
public class ApplicationController {

    @Inject
    private Repository repository;

    public ApplicationController() {
    }

    public Result index() {

        List<String> titles = repository.trendingTitles();

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
