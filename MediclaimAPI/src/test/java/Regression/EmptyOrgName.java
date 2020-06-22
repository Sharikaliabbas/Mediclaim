package Regression;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Payloads;
import MediclaimAutomation.MediclaimAPI.Resources;

public class EmptyOrgName extends Base{
	
	@Test
	public void NoOrgName()
  {	
	  given()
	   
	   .header("signature","test").header("Content-Type","application/json")
	   
	   .body(Payloads.StartTransactionPayload)
	   
	   .when().post(Resources.NoOrgName())
	   
	   .then().assertThat().statusCode(404).and().body("errorCode", equalTo("404")).and().body("errorMessage", equalTo("Organisation null not present"))
	   
	   .extract().response();
  }
  }



