package com.qa.SpotifyAPI;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SpotifyApi {
	
	String token="";
    String userId;
    String playlistId;
    
	@BeforeTest
	public void setup() {
		token="Bearer BQAJSUXB7bjaITfwfivUWDdwDiSeKAYw0p-TbNnZ1ZFbvCO6KBMOGC7LnlnWQJQoZi85f1J3a7WnX8n0eSA0owpnsYHg46fgWk5nlcjGUq7ZHdV_gkvog4j2VGP2KN8zbALOXroEgqLHdtf8fNKtGLYJaOD_rT_r5gyB8fkhYDOyWmwULiw44PknrC-KF7HExowWk5sW7Plp2iVkaeo5WCwz7m0XnTLIezF3lsGTCEPHahEn67XCZm6aDNSjT7MFwpyOxmB9CdDe";
	}
	
	//User
	@Test(priority=1)
	public void get_Current_UserProfile() {
		Response response =RestAssured.given().contentType(ContentType.JSON)
	               .accept(ContentType.JSON)
	               .header("Authorization", token)
	               .get("https://api.spotify.com/v1/me");
		response.prettyPrint();
		userId = response.path("id");
		System.out.println("UserId :" + userId);
	    int statusCode = response.getStatusCode();
		System.out.println("status code is:" + statusCode);
	    Assert.assertEquals(statusCode, 200);
	    System.out.println("--------------------------------------1");
	}
	
	@Test(priority = 2)
	public void get_Users_Profile() {
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.accept(ContentType.JSON);
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", token);
		Response response = httpRequest.get("https://api.spotify.com/v1/users/" + userId + "/");
		System.out.println("Response code: " + response.getStatusCode());
		String responseBody = response.getBody().asString();
    	System.out.println("response body is:" + responseBody);
		int statusCode = response.statusCode();
		Assert.assertEquals(statusCode, 200);
		System.out.println("--------------------------------------2");
	}
	
	//playlist
	
	@Test(priority=3)
	public void playlist_Creation() {
		RequestSpecification httpRequest = RestAssured.given();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "Ajay playlist");
		jsonObject.put("description", "New Song");
		jsonObject.put("public", "false");
		httpRequest.header("Authorization", token);
		httpRequest.body(jsonObject.toString());
		Response response = httpRequest.request(Method.POST, "https://api.spotify.com/v1/users/"+userId+"/playlists");
		String responseBody = response.getBody().asString();
		System.out.println("response body is:" + responseBody);
		int statusCode = response.getStatusCode();
		playlistId = response.path("id");
        System.out.println("Playlist Id :" + playlistId);
	    System.out.println("status code is:" + statusCode);
	    Assert.assertEquals(statusCode, 201);
	    System.out.println("--------------------------------------3");
	    
	}
	
	@Test(priority=4)
	
	public void changePlaylistDetails() {
		RequestSpecification httpRequest = RestAssured.given();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "Ajay playlist");
		jsonObject.put("description", "New Song B Praak");
		httpRequest.header("Authorization", token);
		httpRequest.body(jsonObject.toString());
		Response response = httpRequest.request(Method.PUT, "https://api.spotify.com/v1/playlists/"+playlistId);
		String responseBody = response.getBody().asString();
		System.out.println("response body is:" + responseBody);
		int statusCode = response.getStatusCode();
		System.out.println(response.asString());
	    Assert.assertEquals(statusCode, 200);
	    System.out.println("status code is:" + statusCode);
	    System.out.println("--------------------------------------4");
	}
	    
	    
	    @Test(priority=5)
	    public void addItemstoPlaylist() {
	    	RequestSpecification httpRequest = RestAssured.given();
	    	httpRequest.queryParam("uris","spotify:track:72zHuDxFQTjbL51qJQSA7j");
	    	httpRequest.header("Authorization", token);
	    	Response response = httpRequest.request(Method.POST, "https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
	    	String responseBody = response.getBody().asString();
	    	System.out.println("response body is:" + responseBody);
	    	int statusCode = response.getStatusCode();
	    	Assert.assertEquals(statusCode, 201);
		    System.out.println("status code is:" + statusCode);
		    System.out.println("--------------------------------------5");
		    
	    	
	    }
	    
	    
	    
	    @Test(priority=6)
	    public void playlist_Get_CurrentUser_Playlist(){
	    	RequestSpecification httpRequest = RestAssured.given();
			httpRequest.accept(ContentType.JSON);
			httpRequest.contentType("application/json");
			httpRequest.header("Authorization", token);
			Response response = httpRequest.get("https://api.spotify.com/v1/me/playlists");
			System.out.println("Response code: " + response.getStatusCode());
			String responseBody = response.getBody().asString();
	    	System.out.println("response body is:" + responseBody);
			int statusCode = response.statusCode();
			Assert.assertEquals(statusCode, 200);
			System.out.println("--------------------------------------6");
	    	
	    }
	    
	    @Test(priority = 7)
	    public void playlist_Get_Playlist_Item(){
	    	RequestSpecification httpRequest = RestAssured.given();
	    	httpRequest.accept(ContentType.JSON);
	    	httpRequest.contentType("application/json");
	    	httpRequest.header("Authorization", token);
	    	Response response = httpRequest.get("https://api.spotify.com/v1/playlists/" + playlistId + "/tracks");
	    	String responseBody = response.getBody().asString();
	    	System.out.println("response body is:" + responseBody);
	    	int statusCode = response.statusCode();
			Assert.assertEquals(statusCode, 200);
			System.out.println("--------------------------------------7");
	    }
	    
	    @Test(priority=8)
	    public void Remove_Playlist_Items() {
	    	Response response =RestAssured.given().contentType(ContentType.JSON)
	        .accept(ContentType.JSON)
	        .header("Authorization", token)
	        .body("{\r\n"
            		+ "\"tracks\": [{\"uri\":\"spotify:track:72zHuDxFQTjbL51qJQSA7j\"}]\r\n"
            		+ "}")
	        .delete("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
	    	response.prettyPrint();
	    	int statusCode = response.getStatusCode();
	    	System.out.println("Status code is :=" + statusCode);
	    	Assert.assertEquals(statusCode, 200);
	    	System.out.println("--------------------------------------8");
	    }


}