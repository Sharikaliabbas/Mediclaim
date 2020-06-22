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
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ABMErrorCode extends Base{
	
  	@Test
  	public void ABM_ErrorCode() throws Exception{
  		
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
  		   	
  		  	multiPart("bill", new File(System.getProperty("user.dir")+"/Samples/image.jpg")).
  		   	
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
  		   
  		  // Minio.writeJsonFile(transaction_id);
  		   
  		   
  		   	
  		   	PublishingResponseToRedis.connectToJedis(Payloads.AmountBalanceMisMatchErrorCode(organisation,transaction_id));
  		   	
  		   	TimeUnit.SECONDS.sleep(5);
  		   	
  		   	given()
  			  
  			  .header("signature", "test")
  			  
  			  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
  			  
  			  .then().assertThat().statusCode(200).body("status",equalTo("REJECTED")).and().body("errorCode",equalTo("REPORT_ERROR")).and().body("reportStatus", equalTo("E_AMOUNT_BALANCE_MISMATCH"))
  			  
  			  .extract().response();
  	}
}