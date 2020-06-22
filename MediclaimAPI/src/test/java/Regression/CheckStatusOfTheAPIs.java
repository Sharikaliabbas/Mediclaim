package Regression;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Payloads;
import MediclaimAutomation.MediclaimAPI.Resources;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CheckStatusOfTheAPIs extends Base{
	
  @Test
  public void CheckAllStatus() throws Exception
  {
	  
	  	String organisation = properties.getProperty("ORG");
	  
	  	Response response = given()
		
		.body(Payloads.StartTransactionPayload)
		
		.header("signature","test").header("Content-Type","application/json")
		
		.when().post(Resources.StartMediclaimJobAPI(organisation))
		
		.then().assertThat().statusCode(200).contentType(ContentType.JSON).and().body("success",equalTo(true)).and().body("status",equalTo("INIT")).and().body("statusMessage", equalTo("Job created.")).and().body("errorCode", equalTo("E_NO_ERROR"))
		
		.extract().response();
	  
	 String responseString = response.asString();
	 
	 JsonPath jp = new JsonPath(responseString);
	 
	 String transaction_id = jp.getString("perfiosTransactionId");
	 
	 TimeUnit.SECONDS.sleep(2);
	 
	  given()
	  
	  .header("signature", "test")
	  
	  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
	  
	  .then().assertThat().statusCode(200).and().body("status",equalTo("INIT"))
	  
	  .extract().response();	
	  
	  
	  given()
		
	  .header("signature","test").multiPart("bill", new File(System.getProperty("user.dir")+"/Samples/image.jpg")).
		
		when().put(Resources.DocUploadAPI(organisation, transaction_id)).
		
		then().assertThat().statusCode(200)
		
		.extract().response();
	  
	  
	  given()
	  
	  .header("signature", "test")
	  
	  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
	  
	  .then().assertThat().statusCode(200).and().body("status",equalTo("DOCUMENT_UPLOAD_IN_PROGRESS"))
	  
	  .extract().response();
	  
	  
	  given()
	  
	  .header("signature","test").
	   	
	   when().put(Resources.ProcessAPI(organisation, transaction_id)).
	   
	   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
	   
	   extract().response();
	  
	  
	  given()
	  
	  .header("signature", "test")
	  
	  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
	  
	  .then().assertThat().statusCode(200).and().body("status",equalTo("PROCESSING"))
	  
	  .extract().response();  
	  
  }
  }



