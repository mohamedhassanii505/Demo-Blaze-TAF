package com.automationexercise.apis;

import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class UserManagementAPI {
    RequestSpecification requestSpecification;
    Response response;

    Verification verification;

    public UserManagementAPI(){
        // constructor
        requestSpecification = RestAssured.given();
        verification = new Verification();
    }

    // endpoints
    private static final  String createAccount_endpoint = "/createAccount";
    private static final  String deleteAccount_endpoint = "/deleteAccount";

    private static final String verifyAccount_endpoint = "/verifyLogin";

    // api methods

    @Step(" Create User Account with full details")
    public UserManagementAPI createRegisterUserAccount(String name, String email, String pass, String title,
                                                       String birth_date, String birth_month, String birth_year,
                                                       String firstname, String lastname, String company,
                                                       String address1, String address2, String country, String zipcode,
                                                       String state, String city, String mobile_number)
{
    Map<String, String> formParams =  new HashMap<>();
           formParams.put("name", name);
              formParams.put("email", email);
                formParams.put("password", pass);
                formParams.put("title", title);
                formParams.put("birth_date", birth_date);
                formParams.put("birth_month", birth_month);
                formParams.put("birth_year", birth_year);
                formParams.put("firstname", firstname);
                formParams.put("lastname", lastname);
                formParams.put("company", company);
                formParams.put("address1", address1);
                formParams.put("address2", address2);
                formParams.put("country", country);
                formParams.put("zipcode", zipcode);
                formParams.put("state", state);
                formParams.put("city", city);
                formParams.put("mobile_number", mobile_number);
             response =    requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                        .post(createAccount_endpoint);
             LogsManager.info(response.asPrettyString());
                return this;


    }
@Step(" Create User Account with minimal details")
    public UserManagementAPI createRegisterUserAccount(String name,String email,String pass,String firstName,String lastName){

        Map<String,String> formParams = new HashMap<>();
    formParams.put("name", name);
    formParams.put("email", email);
    formParams.put("password", pass);
    formParams.put("title", "Mr");
    formParams.put("birth_date", "1");
    formParams.put("birth_month", "January");
    formParams.put("birth_year", "1990");
    formParams.put("firstname", firstName);
    formParams.put("lastname", lastName);
    formParams.put("company", "company");
    formParams.put("address1", "address1");
    formParams.put("address2", "address2");
    formParams.put("country", "country");
    formParams.put("zipcode", "123456");
    formParams.put("state", "state");
    formParams.put("city", "city");
    formParams.put("mobile_number", "1234567890");


        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .post(createAccount_endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }



    @Step("Delete User Account by email")
    public UserManagementAPI deleteUserAccount(String email,String password){
        Map<String,String> formParams = new HashMap<>();
        formParams.put("email",email);
        formParams.put("password",password);
        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .delete(deleteAccount_endpoint);
        LogsManager.info(response.asPrettyString());
        return this;

    }

    @Step("Verify User Account by email and password")
    public UserManagementAPI verifyUserAccountLogin(String email,String password){
        Map<String,String> formParams = new HashMap<>();
        formParams.put("email",email);
        formParams.put("password",password);
        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .post(verifyAccount_endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }


    // validations
        @Step("Verify that User Account is Created Successfully")
        public UserManagementAPI verifyUserAccountCreatedSuccessfully(){

           verification.Equals(response.jsonPath().get("message"),"User created!",
            "User account creation failed");
           return this;
        }

        @Step("Verify that User Account is Deleted Successfully")
        public UserManagementAPI verifyUserAccountDeletedSuccessfully(){
        verification.Equals(response.jsonPath().get("message"),"Account deleted!","User account deletion failed");
        return this;
        }


        @Step("Verify that User Account is Verified Successfully")
        public UserManagementAPI verifyUserAccountVerifiedLoginSuccessfully(){
            verification.Equals(response.jsonPath().get("message"),"User exists!","User account verification failed");
            return this;
        }






}
