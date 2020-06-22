package Regression;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Resources;

public class GetAllOrgs extends Base{
	
	@Test
	public void GetAll_Orgs()
	{
		given()
	
		.param("idp", "test")
		
		.when().get(Resources.getOrgs())
		
		.then().assertThat().statusCode(200).and().body("data[0].orgName",equalTo("acme")).and().body("data[1].orgName",equalTo("mediassist"))
		
		.extract().response();
		
		
		
	}
	
}



