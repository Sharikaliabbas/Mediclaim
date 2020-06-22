package Regression;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Payloads;
import MediclaimAutomation.MediclaimAPI.Resources;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CancelJobAfterInit extends Base{
	
	@Test(enabled = false)
	public void CancelJobAfterInitiation() throws Exception
	{
		
		String organisation = properties.getProperty("ORG");
		
		Response responseINIT = given()
		
		.body(Payloads.StartTransactionPayload)
		
		.header("signature","test").header("Content-Type","application/json")
		
		.when().post(Resources.StartMediclaimJobAPI(organisation))
		
		.then().assertThat().statusCode(200).contentType(ContentType.JSON).and().body("success",equalTo(true)).and().body("status",equalTo("INIT")).and().body("statusMessage", equalTo("Job created.")).and().body("errorCode", equalTo("E_NO_ERROR"))
		
		.extract().response();
		
		String responseStringINIT = responseINIT.asString();
	   	
	   	System.out.println("responseStringINIT "+ responseStringINIT);
	   	
	   	JsonPath jp = new JsonPath(responseStringINIT);
	   	
	   	String transaction_id = jp.getString("perfiosTransactionId");
		
		
		given()
		
		.queryParam("idp","test")
		
		.when().post(Resources.cancel(transaction_id))
		
		.then().assertThat().statusCode(200)
		
		.extract().response();
		
		given()
		  
		  .header("signature", "test")
		  
		  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
		  
		  .then().assertThat().statusCode(200).and().body("status",equalTo("CANCELLED"))
		  
		  .extract().response();
		
	}
	
}



