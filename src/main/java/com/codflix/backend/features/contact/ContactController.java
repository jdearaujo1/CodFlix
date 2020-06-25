package com.codflix.backend.features.contact;

import com.codflix.backend.core.Template;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ContactController {

    public String home(Request request, Response response) {

        Map<String, Object> model = new HashMap<>();
        return Template.render("contact.html", model);
    }

}
