package com.qa.restassured;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestApi {

	
	public static RequestSpecification request;
    public static Response response;

    @Test(priority = 1)
    public void getTest_Posts() {
        response = RestAssured.get( "http://localhost:3000/posts");
        response.prettyPrint();
        System.out.println("Status Code:" + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),  200);
        System.out.println("---------------------------------------");
    }
    
    @Test(priority = 2)
    public void postTest_Posts(){
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", 13);
            jsonObject.put("title", "Triple");
            jsonObject.put("author", "H");
        request.body(jsonObject.toJSONString());
        response = request.post("http://localhost:3000/posts");
            System.out.println("Status Code:" + response.getStatusCode());
        response.prettyPrint();
        System.out.println("Status Code:" + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),  201);
        System.out.println("---------------------------------------");
    }
    
    @Test(priority = 3)
    public void putTest_Posts(){
        request = RestAssured.given();
        request.header("Content-type", "application/json");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", 1);
            jsonObject.put("title", "First");
            jsonObject.put("author", "Modification");
        request.body(jsonObject.toJSONString());
        response = request.put( "http://localhost:3000/posts/13");
        response.prettyPrint();
        System.out.println("Status Code:" + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),  200);
        System.out.println("---------------------------------------");
    }
    
    @Test(priority = 4)
    public void deleteTest_Posts(){
        request = RestAssured.given();
        response = request.delete("http://localhost:3000/posts/13");
            System.out.println("Status Code:" + response.getStatusCode());
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),  200);
        System.out.println("---------------------------------------");
    }
}
