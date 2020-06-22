package MediclaimAutomation.MediclaimAPI;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.ExtentReports;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MediclaimAPIRegressionTest{

	private static final String SIGNATURE = "signature";

	FileInputStream fis = null;
	
	Properties properties= new Properties();
	
	public ExtentReports extent;
	
	String organisation ="";
		
		
	@BeforeTest
	public void PropertyFileAndBaseURL() throws Exception
	{
		fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\MediclaimAutomation\\MediclaimAPI\\properties.file");
        
		properties.load(fis);
        
		RestAssured.baseURI=properties.getProperty("HOST");

	}
	
	@Test
	public void InitiateMediclaimJob() throws Exception
	{
		
		String organisation = properties.getProperty("ORG");
		
		given()
		
		.body(Payloads.StartTransactionPayload)
		
		.header(SIGNATURE,"test").header("Content-Type","application/json")
		
		.when().post(Resources.StartMediclaimJobAPI(organisation))
		
		.then().assertThat().statusCode(200).contentType(ContentType.JSON).and().body("success",equalTo(true)).and().body("status",equalTo("INIT")).and().body("statusMessage", equalTo("Job created.")).and().body("errorCode", equalTo("E_NO_ERROR"))
		
		.extract().response();
		
		ExtentTest logger = extent.startTest("InitiateMediclaimJob");
		logger.assignCategory("Functional");
		logger.assignAuthor("PerfiosTesters");
		
		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
		
		extent.endTest(logger);
		extent.flush();
		
	}
	
	
	
   @Test

   public void UploadMediclaimFile() throws Exception
   {
	
	Response response=given()
	
	.body(Payloads.StartTransactionPayload)
	
	.header(SIGNATURE,"test").header("Content-Type","application/json")
	
	.when().post(Resources.StartMediclaimJobAPI(organisation))
	
	.then().assertThat().statusCode(200).contentType(ContentType.JSON).and().body("success",equalTo(true)).and().body("status",equalTo("INIT")).and().body("statusMessage", equalTo("Job created.")).and().body("errorCode", equalTo("E_NO_ERROR"))
	
	.extract().response();
	
	String responseString = response.asString();
	
	JsonPath jp = new JsonPath(responseString);
	
	String organisation = properties.getProperty("ORG") ;
	
	String transaction_id = jp.getString("perfiosTransactionId");
	
	given()
	
	.header(SIGNATURE,"test").multiPart("bill", new File(System.getProperty("user.dir")+"/Samples/image.jpg")).
	
	when().put(Resources.DocUploadAPI(organisation, transaction_id)).
	
	then().assertThat().statusCode(200)
	
	.extract().response();
	
	ExtentTest logger = extent.startTest("UploadMediclaimFile");
	logger.assignCategory("Functional");
	logger.assignAuthor("PerfiosTesters");
	
	logger.log(LogStatus.PASS, "TestCase executed Succesfully");
	extent.endTest(logger);
	extent.flush();
	
	
	}
   
   
   @Test

   public void ProcessMediclaimFile() throws Exception
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
   	
  	multiPart("bill", new File(System.getProperty("user.dir")+"/Samples/image.jpg")).
   	
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
   	
   
   ExtentTest logger = extent.startTest("ProcessMediclaimFile");
	logger.assignCategory("Functional");
	logger.assignAuthor("PerfiosTesters");
	
	logger.log(LogStatus.PASS, "TestCase executed Succesfully");
	extent.endTest(logger);
	extent.flush();
   	
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
   public void IncorrectOrgName()
   {
	   given()
	   
	   .header(SIGNATURE,"test").header("Content-Type","application/json")
	   
	   .body(Payloads.StartTransactionPayload)
	   
	   .when().post(Resources.IncorrectOrgName())
	   
	   .then().assertThat().statusCode(404).and().body("errorCode", equalTo("404")).and().body("errorMessage", equalTo("Organisation acme1 not present"))
	   
	   .extract().response();
	   
	   ExtentTest logger = extent.startTest("SuccessfulReportGeneration");
		logger.assignCategory("Functional");
		logger.assignAuthor("PerfiosTesters");
		
		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
		extent.endTest(logger);
		extent.flush();
   }
   
   
   @Test
   public void Incorrectpayload() throws Exception
   {
	   given()
	   
	   .header(SIGNATURE,"test").header("Content-Type","application/json")
	   
	   .body(Payloads.IncorrectPayload)
	   
	   .when().post(Resources.StartMediclaimJobAPI(organisation))
	   
	   .then().assertThat().statusCode(500)/*.and().body("status", equalTo("FAILED")).and().body("perfiosTransactionId", equalTo("no-job-id-created"))*/
	   
	   .extract().response();
	   
	   ExtentTest logger = extent.startTest("IncorrectPayload");
		logger.assignCategory("Functional");
		logger.assignAuthor("PerfiosTesters");
		
		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
		extent.endTest(logger);
		extent.flush();
   }
   
   
  @Test
  public void NoOrgName()
  {	
	  given()
	   
	   .header(SIGNATURE,"test").header("Content-Type","application/json")
	   
	   .body(Payloads.StartTransactionPayload)
	   
	   .when().post(Resources.NoOrgName())
	   
	   .then().assertThat().statusCode(404).and().body("errorCode", equalTo("404")).and().body("errorMessage", equalTo("Organisation null not present"))
	   
	   .extract().response();
	  
	  ExtentTest logger = extent.startTest("NoOrgName");
		logger.assignCategory("Functional");
		logger.assignAuthor("PerfiosTesters");
		
		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
		extent.endTest(logger);
		extent.flush();
	  
  }
  
  @Test
  public void CheckAllStatus() throws Exception
  {
	  Response response = given()
		
		.body(Payloads.StartTransactionPayload)
		
		.header(SIGNATURE,"test").header("Content-Type","application/json")
		
		.when().post(Resources.StartMediclaimJobAPI(organisation))
		
		.then().assertThat().statusCode(200).contentType(ContentType.JSON).and().body("success",equalTo(true)).and().body("status",equalTo("INIT")).and().body("statusMessage", equalTo("Job created.")).and().body("errorCode", equalTo("E_NO_ERROR"))
		
		.extract().response();
	  
	 String responseString = response.asString();
	 
	 JsonPath jp = new JsonPath(responseString);
	 
	 String transaction_id = jp.getString("perfiosTransactionId");
	 
	 String organisation = properties.getProperty("ORG");
	  
	 TimeUnit.SECONDS.sleep(2);
	 
	  given()
	  
	  .header(SIGNATURE, "test")
	  
	  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
	  
	  .then().assertThat().statusCode(200).and().body("status",equalTo("INIT"))
	  
	  .extract().response();	
	  
	  
	  given()
		
	  .header(SIGNATURE,"test").multiPart("bill", new File(System.getProperty("user.dir")+"/Samples/image.jpg")).
		
		when().put(Resources.DocUploadAPI(organisation, transaction_id)).
		
		then().assertThat().statusCode(200)
		
		.extract().response();
	  
	  
	  given()
	  
	  .header(SIGNATURE, "test")
	  
	  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
	  
	  .then().assertThat().statusCode(200).and().body("status",equalTo("DOCUMENT_UPLOAD_IN_PROGRESS"))
	  
	  .extract().response();
	  
	  
	  given()
	  
	  .header(SIGNATURE,"test").
	   	
	   when().put(Resources.ProcessAPI(organisation, transaction_id)).
	   
	   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
	   
	   extract().response();
	  
	  
	  given()
	  
	  .header(SIGNATURE, "test")
	  
	  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
	  
	  .then().assertThat().statusCode(200).and().body("status",equalTo("PROCESSING"))
	  
	  .extract().response();  
	  
	  ExtentTest logger = extent.startTest("CheckAllStatus");
		logger.assignCategory("Functional");
		logger.assignAuthor("PerfiosTesters");
		
		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
		extent.endTest(logger);
		extent.flush();
  }
  	@Test
  	public void ABMErrorCode() throws Exception {
  		   	
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
  		   
  		   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
  		   
  		   extract().response();
  		   
  		   
  		   String responseStringProcess = responseProcess.asString();
  		   
  		   System.out.println("responseStringProcess "+ responseStringProcess);
  		   
  		  // Minio.writeJsonFile(transaction_id);
  		   
  		   
  		   	
  		   	PublishingResponseToRedis.connectToJedis(Payloads.AmountBalanceMisMatchErrorCode(organisation,transaction_id));
  		   	
  		   	TimeUnit.SECONDS.sleep(5);
  		   	
  		   	given()
  			  
  			  .header(SIGNATURE, "test")
  			  
  			  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
  			  
  			  .then().assertThat().statusCode(200).body("status",equalTo("REJECTED")).and().body("errorCode",equalTo("REPORT_ERROR")).and().body("reportStatus", equalTo("SUMMARY_DETAILED_BALANCE_MISMATCH"))
  			  
  			  .extract().response();
  		   	
  		  ExtentTest logger = extent.startTest("ABMErrorCode");
  		logger.assignCategory("Functional");
  		logger.assignAuthor("PerfiosTesters");
  		
  		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
  		extent.endTest(logger);
  		extent.flush();
  	}
  	@Test
  	public void IncompleteDataErrorCode() throws Exception {
  		   	
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
  		   
  		   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
  		   
  		   extract().response();
  		   
  		   
  		   String responseStringProcess = responseProcess.asString();
  		   
  		   System.out.println("responseStringProcess "+ responseStringProcess);
  		   
  		  // Minio.writeJsonFile(transaction_id);
  		   
  		   
  		   	
  		   	PublishingResponseToRedis.connectToJedis(Payloads.IncompleteDataErrorCode(organisation,transaction_id));
  		   	
  		   	TimeUnit.SECONDS.sleep(5);
  		   	
  		   	given()
  			  
  			  .header(SIGNATURE, "test")
  			  
  			  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
  			  
  			  .then().assertThat().statusCode(200).body("status",equalTo("REJECTED")).and().body("errorCode",equalTo("REPORT_ERROR")).and().body("reportStatus", equalTo("INCOMPLETE_DATA"))
  			  
  			  .extract().response();
  		   	
  		  ExtentTest logger = extent.startTest("IncompleteDataErrorCode");
  		logger.assignCategory("Functional");
  		logger.assignAuthor("PerfiosTesters");
  		
  		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
  		extent.endTest(logger);
  		extent.flush();
}
  	
  	@Test
  	public void UnsupportedBillFormatErrorCode() throws Exception {
  		   	
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
  		   
  		   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
  		   
  		   extract().response();
  		   
  		   
  		   String responseStringProcess = responseProcess.asString();
  		   
  		   System.out.println("responseStringProcess "+ responseStringProcess);
  		   
  		  // Minio.writeJsonFile(transaction_id);
  		   
  		   
  		   	
  		   	PublishingResponseToRedis.connectToJedis(Payloads.UnsupportedBillFormatErrorCode(organisation,transaction_id));
  		   	
  		   	TimeUnit.SECONDS.sleep(5);
  		   	
  		   	given()
  			  
  			  .header(SIGNATURE, "test")
  			  
  			  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
  			  
  			  .then().assertThat().statusCode(200).body("status",equalTo("REJECTED")).and().body("errorCode",equalTo("REPORT_ERROR")).and().body("reportStatus", equalTo("UNSUPPORTED_BILL_FORMAT"))
  			  
  			  .extract().response();
  		   	
  		  ExtentTest logger = extent.startTest("UnsupportedBillFormatErrorCode");
  		logger.assignCategory("Functional");
  		logger.assignAuthor("PerfiosTesters");
  		
  		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
  		extent.endTest(logger);
  		extent.flush();
  	}
  	
  	@Test
  	public void SummaryDetailedBalanceMismatchErrorCode() throws Exception {
  		   	
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
  		   
  		   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
  		   
  		   extract().response();
  		   
  		   
  		   String responseStringProcess = responseProcess.asString();
  		   
  		   System.out.println("responseStringProcess "+ responseStringProcess);
  		   
  		  // Minio.writeJsonFile(transaction_id);
  		   
  		   
  		   	
  		   	PublishingResponseToRedis.connectToJedis(Payloads.SummaryDetailedBalanceMismatchErrorCode(organisation,transaction_id));
  		   	
  		   	TimeUnit.SECONDS.sleep(5);
  		   	
  		   	given()
  			  
  			  .header(SIGNATURE, "test")
  			  
  			  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
  			  
  			  .then().assertThat().statusCode(200).body("status",equalTo("REJECTED")).and().body("errorCode",equalTo("REPORT_ERROR")).and().body("reportStatus", equalTo("SUMMARY_DETAILED_BALANCE_MISMATCH"))
  			  
  			  .extract().response();
  		   	
  		  ExtentTest logger = extent.startTest("SummaryDetailedBalanceMismatchErrorCode");
  		logger.assignCategory("Functional");
  		logger.assignAuthor("PerfiosTesters");
  		
  		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
  		extent.endTest(logger);
  		extent.flush();
  	}
  	
  	@Test
  	public void MultipleBillErrorCode() throws Exception {
  		   	
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
  		   
  		   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
  		   
  		   extract().response();
  		   
  		   
  		   String responseStringProcess = responseProcess.asString();
  		   
  		   System.out.println("responseStringProcess "+ responseStringProcess);
  		   
  		  // Minio.writeJsonFile(transaction_id);
  		   
  		   
  		   	
  		   	PublishingResponseToRedis.connectToJedis(Payloads.MultipleBillErrorCode(organisation,transaction_id));
  		   	
  		   	TimeUnit.SECONDS.sleep(5);
  		   	
  		   	given()
  			  
  			  .header(SIGNATURE, "test")
  			  
  			  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
  			  
  			  .then().assertThat().statusCode(200).body("status",equalTo("REJECTED")).and().body("errorCode",equalTo("REPORT_ERROR")).and().body("reportStatus", equalTo("MULTIPLE_BILL"))
  			  
  			  .extract().response();
  		   	
  		  ExtentTest logger = extent.startTest("MultipleBillErrorCode");
  		logger.assignCategory("Functional");
  		logger.assignAuthor("PerfiosTesters");
  		
  		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
  		extent.endTest(logger);
  		extent.flush();
  	}
  	
  	@Test
  	public void ClaimAmountMismatchErrorCode() throws Exception {
  		   	
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
  		   
  		   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
  		   
  		   extract().response();
  		   
  		   
  		   String responseStringProcess = responseProcess.asString();
  		   
  		   System.out.println("responseStringProcess "+ responseStringProcess);
  		   
  		  // Minio.writeJsonFile(transaction_id);
  		   
  		   
  		   	
  		   	PublishingResponseToRedis.connectToJedis(Payloads.ClaimAmountMismatchErrorCode(organisation,transaction_id));
  		   	
  		   	TimeUnit.SECONDS.sleep(5);
  		   	
  		   	given()
  			  
  			  .header(SIGNATURE, "test")
  			  
  			  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
  			  
  			  .then().assertThat().statusCode(200).body("status",equalTo("REJECTED")).and().body("errorCode",equalTo("REPORT_ERROR")).and().body("reportStatus",equalTo("CLAIM_AMOUNT_MISMATCH"))
  			  
  			  .extract().response();
  		   	
  		  ExtentTest logger = extent.startTest("ClaimAmountMismatchErrorCode");
  		logger.assignCategory("Functional");
  		logger.assignAuthor("PerfiosTesters");
  		
  		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
  		extent.endTest(logger);
  		extent.flush();
  	}
  	
  	@Test
  	public void ClaimAmountMissingErrorCode() throws Exception {
  		   	
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
  		   
  		   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
  		   
  		   extract().response();
  		   
  		   
  		   String responseStringProcess = responseProcess.asString();
  		   
  		   System.out.println("responseStringProcess "+ responseStringProcess);
  		   
  		  // Minio.writeJsonFile(transaction_id);
  		   
  		   
  		   	
  		   	PublishingResponseToRedis.connectToJedis(Payloads.ClaimAmountMissingErrorCode(organisation,transaction_id));
  		   	
  		   	TimeUnit.SECONDS.sleep(5);
  		   	
  		   	given()
  			  
  			  .header(SIGNATURE, "test")
  			  
  			  .when().get(Resources.CheckStatusAPI(organisation,transaction_id))
  			  
  			  .then().assertThat().statusCode(200).body("status",equalTo("REJECTED")).and().body("errorCode",equalTo("REPORT_ERROR")).and().body("reportStatus",equalTo("CLAIM_AMOUNT_MISSING"))
  			  
  			  .extract().response();
  		   	
  		  ExtentTest logger = extent.startTest("ClaimAmountMissingErrorCode");
  		logger.assignCategory("Functional");
  		logger.assignAuthor("PerfiosTesters");
  		
  		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
  		extent.endTest(logger);
  		extent.flush();
  	}
  	
  	@Test
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
  			  
  			  .then().assertThat().statusCode(200).body("status",equalTo("REJECTED")).and().body("errorCode",equalTo("REPORT_ERROR")).and().body("reportStatus", equalTo("OTHER"))
  			  
  			  .extract().response();
  		   	
  		  ExtentTest logger = extent.startTest("OtherErrorCode");
  		logger.assignCategory("Functional");
  		logger.assignAuthor("PerfiosTesters");
  		logger.log(LogStatus.INFO, "PerfiosTransactionId for the test case is: "+transaction_id);
  		logger.log(LogStatus.PASS, "TestCase executed Succesfully");
  		extent.endTest(logger);
  		extent.flush();
  	}
  	
  	@Test(enabled = false)

	   public void SuccessfulReportGenerationCallbackFailure() throws Exception
	   {
	   	
	   	Response responseINIT=given()
	   	
	   	.body(Payloads.StartTransactionPayloadFailure)
	   	
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
	   }}



