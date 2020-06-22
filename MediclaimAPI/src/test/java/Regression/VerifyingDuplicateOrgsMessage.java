package Regression;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Payloads;

public class VerifyingDuplicateOrgsMessage extends Base{

	@Test
	public void VerifyingDuplicateOrgs_Message()
	{
		given()
		
		.header("Content-Type","application/json")
	
		.queryParam("idp", "test")
		
		.body(Payloads.AddingOrgsPayload)
		
		.when().post("/api/v1/admin/orgs/org")
		
		.then().assertThat().statusCode(422).and().body("errorMessage",equalTo("createOrganisation.input: Duplicate Organisation Name"))
		
		.extract().response();
		
}
}



