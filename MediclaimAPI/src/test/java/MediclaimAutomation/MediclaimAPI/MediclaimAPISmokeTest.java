package MediclaimAutomation.MediclaimAPI;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MediclaimAPISmokeTest{
	
	private static final String SIGNATURE = "signature";
	
	FileInputStream fis = null;
	
	Properties properties= new Properties();
	
	String organisation = "";
		
	@BeforeTest
	public void PropertyFileAndBaseURL() throws Exception
	{
		fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\MediclaimAutomation\\MediclaimAPI\\properties.file");
        
		properties.load(fis);
        
		RestAssured.baseURI=properties.getProperty("HOST");
	}

	@Test(enabled = false)

	   public void SuccessfulReportGeneration() throws Exception
	   {
	   	
	   	Response responseINIT=given()
	   	
	   	.body(Payloads.StartTransactionPayload)
	   	
	   	.header(SIGNATURE,"test").header("Content-Type","application/json")
	   	
	   	.when().post(Resources.StartMediclaimJobAPI(organisation))
	   	
	   	.then().assertThat().statusCode(200).contentType(ContentType.JSON).and().body("success",equalTo(true)).and().body("status",equalTo("INIT")).and().body("statusMessage", equalTo("Job created.")).and().body("errorCode", equalTo("E_NO_ERROR"))
	   
	   	.extract().response();
	   	
	   	
	   	String responseStringINIT = responseINIT.asString();
	   	
	   	System.out.println("responseStringINIT "+ responseStringINIT);
	   	
	   	JsonPath jp = new JsonPath(responseStringINIT);
	   	
	   	String organisation = properties.getProperty("ORG");
	   	
	   	String transaction_id = jp.getString("perfiosTransactionId");
	   	
	   	//-----------------------------------------------//
	  	Response responseDocUpload = given().header(SIGNATURE,"test").
	   	
	 	multiPart("bill", new File((System.getProperty("user.dir")+"/Samples/image.jpg"))).
	   	
	 	when().put(Resources.DocUploadAPI(organisation, transaction_id)).
	   	
	 	then().assertThat().statusCode(200)
	   	
	 	.extract().response();
	   	
	  	
	  	String responseStringDocUpload = responseDocUpload.asString();
	   	
	  	System.out.println("responseStringDocUpload "+ responseStringDocUpload);
	   	
	   	//--------------------------------------------------//
	  	
	   Response responseProcess = given().header(SIGNATURE,"test").
	   	
	   when().put(Resources.ProcessAPI(organisation, transaction_id)).
	   
	   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
	   
	   extract().response();
	   
	   
	   String responseStringProcess = responseProcess.asString();
	   
	   System.out.println("responseStringProcess "+ responseStringProcess);
	   
	   UploadingJSONResponseToMinio.writeJsonFile(transaction_id);
	   
	   
	   	
	   	PublishingResponseToRedis.connectToJedis(Payloads.SuccessfulReportGeneration(organisation,transaction_id));
	   	
	   	TimeUnit.SECONDS.sleep(5);
	   	
	   	given()
		  
		  .header(SIGNATURE, "test")
		  
		  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
		  
		  .then().assertThat().statusCode(200).body("status",equalTo("COMPLETED"))
		  
		  .extract().response();
	   	
	   	given()
	   	  .header("signature","test")
	   	  
	   	  .when().get(Resources.DownloadAPI(transaction_id))
	   	  
	   	  .then().assertThat().statusCode(200)
	   	  
	   	  .extract().response();
	   	
	   	TimeUnit.SECONDS.sleep(5);
	   	
	   	minioReportChecker.reportCheckerAcme(transaction_id);
	   }
	
	@Test

	   public void SuccessfulReportGenerationMA() throws Exception
	   {
	   	
	   	Response responseINIT=given()
	   	
	   	.body(Payloads.StartTransactionPayload)
	   	
	   	.header(SIGNATURE,"test").header("Content-Type","application/json")
	   	
	   	.when().post(Resources.StartMediclaimJobAPI(organisation))
	   	
	   	.then().assertThat().statusCode(200).contentType(ContentType.JSON).and().body("success",equalTo(true)).and().body("status",equalTo("INIT")).and().body("statusMessage", equalTo("Job created.")).and().body("errorCode", equalTo("E_NO_ERROR"))
	   
	   	.extract().response();
	   	
	   	
	   	String responseStringINIT = responseINIT.asString();
	   	
	   	System.out.println("responseStringINIT "+ responseStringINIT);
	   	
	   	JsonPath jp = new JsonPath(responseStringINIT);
	   	
	   	String organisation = "mediassist";
	   	
	   	String transaction_id = jp.getString("perfiosTransactionId");
	   	
	   	//-----------------------------------------------//
	  	Response responseDocUpload = given().header(SIGNATURE,"test").
	   	
	 	multiPart("bill", new File((System.getProperty("user.dir")+"/Samples/image.jpg"))).
	   	
	 	when().put(Resources.DocUploadAPI(organisation, transaction_id)).
	   	
	 	then().assertThat().statusCode(200)
	   	
	 	.extract().response();
	   	
	  	
	  	String responseStringDocUpload = responseDocUpload.asString();
	   	
	  	System.out.println("responseStringDocUpload "+ responseStringDocUpload);
	   	
	   	//--------------------------------------------------//
	  	
	   Response responseProcess = given().header(SIGNATURE,"test").
	   	
	   when().put(Resources.ProcessAPI(organisation, transaction_id)).
	   
	   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
	   
	   extract().response();
	   
	   
	   String responseStringProcess = responseProcess.asString();
	   
	   System.out.println("responseStringProcess "+ responseStringProcess);
	   
	   UploadingJSONResponseToMinio_MA.writeJsonFile(transaction_id);
	   
	   
	   	
	   	PublishingResponseToRedis.connectToJedis(Payloads.SuccessfulReportGeneration(organisation,transaction_id));
	   	
	   	TimeUnit.SECONDS.sleep(5);
	   	
	   	given()
		  
		  .header(SIGNATURE, "test")
		  
		  .when().get(Resources.CheckStatusAPI(organisation, transaction_id))
		  
		  .then().assertThat().statusCode(200).body("status",equalTo("COMPLETED"))
		  
		  .extract().response();
	   	
	   	given()
	   	  .header("signature","test")
	   	  
	   	  .when().get(Resources.DownloadAPI_MA(transaction_id))
	   	  
	   	  .then().assertThat().statusCode(200)
	   	  
	   	  .extract().response();
	   	
	   	TimeUnit.SECONDS.sleep(5);
	   	
	   	minioReportChecker.reportCheckerMediAssist(transaction_id);
	   }
	
	
	
	
	
	
	
	@Test(enabled = false)
  	public void OtherErrorCode() throws Exception {
  		   	
  		   	Response responseINIT=given()
  		   	
  		   	.body(Payloads.StartTransactionPayload)
  		   	
  		   	.header(SIGNATURE,"test").header("Content-Type","application/json")
  		   	
  		   	.when().post(Resources.StartMediclaimJobAPI(organisation))
  		   	
  		   	.then().assertThat().statusCode(200).contentType(ContentType.JSON).and().body("success",equalTo(true)).and().body("status",equalTo("INIT")).and().body("statusMessage", equalTo("Job created.")).and().body("errorCode", equalTo("E_NO_ERROR"))
  		   
  		   	.extract().response();
  		   	
  		   	
  		   	String responseStringINIT = responseINIT.asString();
  		   	
  		   	System.out.println("responseStringINIT "+ responseStringINIT);
  		   	
  		   	JsonPath jp = new JsonPath(responseStringINIT);
  		   	
  		   	String organisation = properties.getProperty("ORG");
  		   	
  		   	String transaction_id = jp.getString("perfiosTransactionId");
  		   	
  		   	
  		   	//-----------------------------------------------//
  		  	Response responseDocUpload = given().header(SIGNATURE,"test").
  		   	
  		  	multiPart("bill", new File(System.getProperty("user.dir")+"/Samples/image.jpg")).
  		   	
  		 	when().put(Resources.DocUploadAPI(organisation, transaction_id)).
  		   	
  		 	then().assertThat().statusCode(200)
  		   	
  		 	.extract().response();
  		   	
  		  	
  		  	String responseStringDocUpload = responseDocUpload.asString();
  		   	
  		  	System.out.println("responseStringDocUpload "+ responseStringDocUpload);
  		   	
  		   	//--------------------------------------------------//
  		  	
  		   Response responseProcess = given().header(SIGNATURE,"test").
  		   	
  		   when().put(Resources.ProcessAPI(organisation, transaction_id)).
  		   
  		   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR"))
  		   
  		   .extract().response();
  		   
  		   
  		   String responseStringProcess = responseProcess.asString();
  		   
  		   System.out.println("responseStringProcess "+ responseStringProcess);
  		   
  		  // Minio.writeJsonFile(transaction_id);
  		   
  		   
  		   	
  		   	PublishingResponseToRedis.connectToJedis(Payloads.OtherErrorCode(organisation,transaction_id));
  		   	
  		   	TimeUnit.SECONDS.sleep(5);
  		   	
  		   	given()
  			  
  			  .header(SIGNATURE, "test")
  			  
  			  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
  			  
  			  .then().assertThat().statusCode(200).body("status",equalTo("REJECTED")).and().body("errorCode",equalTo("REPORT_ERROR")).and().body("reportStatus", equalTo("E_OTHER"))
  			  
  			  .extract().response();
  	}
	
	@Test(enabled = false)
	public void getOrgsAPI() {
		
		Response response = given().
		
		param("idp", "test").
		
		when().get("/api/v1/admin/orgs").
		
		then().assertThat().statusCode(200).
		
		extract().response();
		
		String ResponseString = response.asString();
		
		System.out.println("List of Orgs : "+ ResponseString);
	
	}
	
	
	
	
	
	
}
