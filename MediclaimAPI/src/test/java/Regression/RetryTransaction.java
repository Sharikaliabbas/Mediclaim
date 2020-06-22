package Regression;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Payloads;
import MediclaimAutomation.MediclaimAPI.PublishingResponseToRedis;
import MediclaimAutomation.MediclaimAPI.Resources;
import MediclaimAutomation.MediclaimAPI.UploadingJSONResponseToMinio;
import MediclaimAutomation.MediclaimAPI.UploadingJSONResponseToMinio_MA;
import MediclaimAutomation.MediclaimAPI.minioReportChecker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RetryTransaction extends Base{

   @Test
   	public void Retry_Transaction() throws Exception
   	{
	  
	 String organisation = properties.getProperty("ORG");
   	
     Response responseINIT=given()
   	
   	.body(Payloads.StartTransactionPayload)
   	
   	.header("signature","test").header("Content-Type","application/json")
   	
   	.when().post(Resources.StartMediclaimJobAPI(organisation))
   	
   	.then().assertThat().statusCode(200).contentType(ContentType.JSON).and().body("success",equalTo(true)).and().body("status",equalTo("INIT")).and().body("statusMessage", equalTo("Job created.")).and().body("errorCode", equalTo("E_NO_ERROR"))
   
   	.extract().response();
   	
   	
   	String responseStringINIT = responseINIT.asString();
   	
   	System.out.println("responseStringINIT "+ responseStringINIT);
   	
   	JsonPath jp = new JsonPath(responseStringINIT);
   	
   	String transaction_id = jp.getString("perfiosTransactionId");
   	
   	//-----------------------------------------------//
  	Response responseDocUpload = given().header("signature","test").
   	
 	multiPart("bill", new File((System.getProperty("user.dir")+"/Samples/image.jpg"))).
   	
 	when().put(Resources.DocUploadAPI(organisation, transaction_id)).
   	
 	then().assertThat().statusCode(200)
   	
 	.extract().response();
   	
  	
  	String responseStringDocUpload = responseDocUpload.asString();
   	
  	System.out.println("responseStringDocUpload "+ responseStringDocUpload);
   	
   	//--------------------------------------------------//
  	
   Response responseProcess = given().header("signature","test").
   	
   when().put(Resources.ProcessAPI(organisation, transaction_id)).
   
   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
   
   extract().response();
   
   
   String responseStringProcess = responseProcess.asString();
   
   System.out.println("responseStringProcess "+ responseStringProcess);
   
  
   if(properties.getProperty("ORG").equals("acme")) {
   
   UploadingJSONResponseToMinio.writeJsonFileForRetry(transaction_id);
   	
   	PublishingResponseToRedis.connectToJedis(Payloads.SuccessfulReportGeneration(organisation,transaction_id));
   	
   	TimeUnit.SECONDS.sleep(5);
   	
   	given()
	  
	  .header("signature", "test")
	  
	  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
	  
	  .then().assertThat().statusCode(200).body("status",equalTo("ERROR_GENERATING_REPORT"))
	  
	  .extract().response();
   	
   	TimeUnit.SECONDS.sleep(2);
   	
   	UploadingJSONResponseToMinio.writeJsonFile(transaction_id);
   	
   	TimeUnit.SECONDS.sleep(2);
   	
given()
   	
   	.queryParam("idp", "test")
   	
   	.when().post(Resources.retry(transaction_id))
   	
   	.then().assertThat().statusCode(200)
   	
   	.extract().response();
   	
   	given()
	  
	  .header("signature", "test")
	  
	  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
	  
	  .then().assertThat().statusCode(200).body("status",equalTo("COMPLETED"))
	  
	  .extract().response();
   	
   	
   	
   	
   	given()
   	  .header("signature","test")
   	  
   	  .when().get(Resources.DownloadAPI(transaction_id))
   	  
   	  .then().assertThat().statusCode(200)
   	  
   	  .extract().response();
   	
   	TimeUnit.SECONDS.sleep(15);
   	
   	minioReportChecker.reportCheckerAcme(transaction_id);
   	
   }
    else {
    	UploadingJSONResponseToMinio_MA.writeJsonFileForRetry(transaction_id);
       	
       	PublishingResponseToRedis.connectToJedis(Payloads.SuccessfulReportGeneration(organisation,transaction_id));
       	
       	TimeUnit.SECONDS.sleep(5);
       	
       	given()
    	  
    	  .header("signature", "test")
    	  
    	  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
    	  
    	  .then().assertThat().statusCode(200).body("status",equalTo("ERROR_GENERATING_REPORT"))
    	  
    	  .extract().response();
       	
       	TimeUnit.SECONDS.sleep(2);
       	
       	UploadingJSONResponseToMinio_MA.writeJsonFile(transaction_id);
	   
   
   	
   	/*Response responseGetJobs = given()
   	
   	.queryParam("idp", "test")
   	
   	.body(Payloads.getJobsPayload)
   	
   	.when().post(Resources.getJobs())
   	
   	.then().assertThat().statusCode(200)
   	
   	.extract().response();
   	
   	String getJobsResponse = responseGetJobs.asString();
   	
   	JsonPath jp1 = new JsonPath(getJobsResponse);
   	
   	String retryTransactionId = jp1.getString("data[0].jobId");
   	*/
   	given()
   	
   	.queryParam("idp", "test")
   	
   	.when().post(Resources.retry(transaction_id))
   	
   	.then().assertThat().statusCode(200)
   	
   	.extract().response();
   	
   	given()
	  
	  .header("signature", "test")
	  
	  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
	  
	  .then().assertThat().statusCode(200).body("status",equalTo("COMPLETED"))
	  
	  .extract().response();
   	
   	
   	
   	
   	given()
   	  .header("signature","test")
   	  
   	  .when().get(Resources.DownloadAPI_MA(transaction_id))
   	  
   	  .then().assertThat().statusCode(200)
   	  
   	  .extract().response();
   	
   	TimeUnit.SECONDS.sleep(15);
   	
   	minioReportChecker.reportCheckerMediAssist(transaction_id);
   }
}
}


