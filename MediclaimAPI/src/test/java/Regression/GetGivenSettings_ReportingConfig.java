package Regression;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Resources;
import io.restassured.response.Response;

public class GetGivenSettings_ReportingConfig extends Base{

	@Test
	public void GetGivenSettingsReportingConfig()
	{
		Response response = given()
	
		.param("idp", "test")
		
		.when().get(Resources.getGivenSettingReportingConfig())
		
		.then().assertThat().statusCode(200)		
		
		.extract().response();		
		
		String responseString = response.asString();
		
		Assert.assertEquals(responseString, "{\"xls\":{\"xlsTemplate\":\"ExcelTemplate.xls\"},\"xml\":{\"xsltFile\":\"mediassist.xsl\"},\"json\":{\"xsltFile\":\"mediassist.xsl\",\"xmlToJson\":true},\"defaultConfig\":{\"reportType\":\"XML\"}}");
		
		
		
	}
	
}



