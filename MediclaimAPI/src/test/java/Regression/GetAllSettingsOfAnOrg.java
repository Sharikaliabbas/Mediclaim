package Regression;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Resources;

public class GetAllSettingsOfAnOrg extends Base{

	@Test
	public void GetAllSettingsOrgs()
	{
		given()
	
		.param("idp", "test")
		
		.when().get(Resources.getAllSetting())
		
		.then().assertThat().statusCode(200).and().body("CategorizationConfig",equalTo("{\"orgToCategoryMapping\":{\"file\":\"/perfios/conf/mediclaim-config/acme-settings.txt\",\"isOrgCategorizationEnabled\":\"true\"}}")).and().body("ReportingType",equalTo("{\"type\":[\"json\"]}")).and().body("ReportingConfig",equalTo("{\"xls\":{\"xlsTemplate\":\"ExcelTemplate.xls\"},\"xml\":{\"xsltFile\":\"mediassist.xsl\"},\"json\":{\"xsltFile\":\"mediassist.xsl\",\"xmlToJson\":true},\"defaultConfig\":{\"reportType\":\"XML\"}}")).and().body("RetentionPeriod", equalTo("{\"noOfDays\":1,\"savePointDate\":\"May 27, 2020 9:42:43 AM\",\"isDataRetentionEnabled\":true}"))
		
		.extract().response();
		
		
		
	}
	
}



