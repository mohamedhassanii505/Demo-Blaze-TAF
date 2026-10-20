package com.automationexercise.apis;

import com.automationexercise.utils.dataReader.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Builder {
    private static String baseURI = PropertyReader.getProperty("baseUrlApi");

    private Builder() {
        // private constructor to prevent instantiation
    }

// build request specification

    public static RequestSpecification getUserManagementRequestSpecification(Map<String, ?> formParams) {
         return new RequestSpecBuilder().setBaseUri(baseURI)
                 .setContentType(ContentType.URLENC)
                 .addFormParams(formParams).build();

    }


}