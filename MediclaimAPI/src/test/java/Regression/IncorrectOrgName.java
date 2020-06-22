package Regression;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Payloads;
import MediclaimAutomation.MediclaimAPI.Resources;

public class IncorrectOrgName extends Base{
	
   @Test
   public void Incorrect_OrgName()
   {
	   given()
	   
	   .header("signature","test").header("Content-Type","application/json")
	   
	   .body(Payloads.StartTransactionPayload)
	   
	   .when().post(Resources.IncorrectOrgName())
	   
	   .then().assertThat().statusCode(404).and().body("errorCode", equalTo("404")).and().body("errorMessage", equalTo("Organisation acme1 not present"))
	   
	   .extract().response();
   }
}



