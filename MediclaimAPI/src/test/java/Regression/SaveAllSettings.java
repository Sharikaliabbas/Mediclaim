package Regression;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Payloads;
import MediclaimAutomation.MediclaimAPI.Resources;
import io.restassured.response.Response;

public class SaveAllSettings extends Base{
	
   @Test
   public void SaveAll_Settings(){
	   
   Response responseProcess = given().
		   
   header("Content-Type","application/json").
		   
   queryParam("idp", "test").
   
   body(Payloads.SaveAllSettings).
   	
   when().put(Resources.saveAllSettings()).
   
   then().assertThat().statusCode(200).
   
   extract().response();
   
   
   String responseStringProcess = responseProcess.asString();
   
   System.out.println(responseStringProcess);
   
   Assert.assertEquals(responseStringProcess, "{\"CategorizationConfig\":\"{\\\"orgToCategoryMapping\\\":{\\\"file\\\":\\\"/perfios/conf/mediclaim-config/acme-settings.txt\\\",\\\"isOrgCategorizationEnabled\\\":\\\"true\\\"}}\",\"ReportingType\":\"{\\\"type\\\":[\\\"json\\\"]}\",\"ReportingConfig\":\"{\\\"xls\\\":{\\\"xlsTemplate\\\":\\\"ExcelTemplate.xls\\\"},\\\"xml\\\":{\\\"xsltFile\\\":\\\"mediassist.xsl\\\"},\\\"json\\\":{\\\"xsltFile\\\":\\\"mediassist.xsl\\\",\\\"xmlToJson\\\":true},\\\"defaultConfig\\\":{\\\"reportType\\\":\\\"XML\\\"}}\",\"RetentionPeriod\":\"{\\\"noOfDays\\\":1,\\\"savePointDate\\\":\\\"May 27, 2020 9:42:43 AM\\\",\\\"isDataRetentionEnabled\\\":true}\"}");
   	
   }
}



