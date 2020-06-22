package Regression;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Payloads;
import MediclaimAutomation.MediclaimAPI.Resources;
import io.restassured.http.ContentType;

public class InitiateJob extends Base{
	
	@Test
	public void InitiateMediclaimJob() throws Exception
	{
		
		String organisation = properties.getProperty("ORG");
		
		given()
		
		.body(Payloads.StartTransactionPayload)
		
		.header("signature","test").header("Content-Type","application/json")
		
		.when().post(Resources.StartMediclaimJobAPI(organisation))
		
		.then().assertThat().statusCode(200).contentType(ContentType.JSON).and().body("success",equalTo(true)).and().body("status",equalTo("INIT")).and().body("statusMessage", equalTo("Job created.")).and().body("errorCode", equalTo("E_NO_ERROR"))
		
		.extract().response();
		
	}
	
}



