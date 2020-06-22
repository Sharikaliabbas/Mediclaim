package Regression;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Resources;
import io.restassured.response.Response;

public class GetGivenSettings_ReportingType extends Base{
	
	@Test
	public void GetGivenSettingsReportingType()
	{
		Response response = given()
	
		.param("idp", "test")
		
		.when().get(Resources.getGivenSettingReportingType())
		
		.then().assertThat().statusCode(200)		
		
		.extract().response();		
		
		String responseString = response.asString();
		
		Assert.assertEquals(responseString, "{\"type\":[\"json\"]}");
		
		
		
	}
	
}



