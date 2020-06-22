package Regression;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Resources;
import io.restassured.response.Response;

public class GetGivenSettings_RetentionPeriod extends Base{

	@Test
	public void GetGivenSettingsRetentionPeriod()
	{
		Response response = given()
	
		.param("idp", "test")
		
		.when().get(Resources.getGivenSettingRetentionPeriod())
		
		.then().assertThat().statusCode(200)		
		
		.extract().response();		
		
		String responseString = response.asString();
		
		Assert.assertEquals(responseString, "{\"noOfDays\":1,\"savePointDate\":\"May 27, 2020 9:42:43 AM\",\"isDataRetentionEnabled\":true}");	
	}
	
}



