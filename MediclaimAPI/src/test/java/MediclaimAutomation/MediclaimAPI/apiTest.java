package MediclaimAutomation.MediclaimAPI;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class apiTest{
	
	private static final String SIGNATURE = "signature";
	
	static FileInputStream fis = null;
	
	static Properties properties= new Properties();
	
	static String organisation = "";
	static String transaction_id = "";

	public static String PropertyFileAndBaseURL() throws Exception
	{
		fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\MediclaimAutomation\\MediclaimAPI\\properties.file");
        
		properties.load(fis);
        
		String baseURL = properties.getProperty("HOST");
		
		RestAssured.baseURI = baseURL;
		
		return baseURL;
	}


	   public static String initiateTransaction() throws Exception
	   {
	   	

		organisation = properties.getProperty("ORG");
		   
	   	Response responseINIT=given()
	   	
	   	.body(Payloads.StartTransactionPayload)
	   	
	   	.header(SIGNATURE,"test").header("Content-Type","application/json")
	   	
	   	.when().post(Resources.StartMediclaimJobAPI(organisation))
	   	
	   	.then().assertThat().statusCode(200).contentType(ContentType.JSON).and().body("success",equalTo(true)).and().body("status",equalTo("INIT")).and().body("statusMessage", equalTo("Job created.")).and().body("errorCode", equalTo("E_NO_ERROR"))
	   
	   	.extract().response();
	   	
	   	

	   	
	   	String responseStringINIT = responseINIT.asString();
	   	
	//   	System.out.println("responseStringINIT "+ responseStringINIT);
	   	
	   	JsonPath jp = new JsonPath(responseStringINIT);
	   	
	   	transaction_id = jp.getString("perfiosTransactionId");
	   	
	   	return transaction_id;
	   }
	   	
	   	//-----------------------------------------------//
	   	
	   	public static void uploadStatement(String path) {
	   		
	   		System.out.println(path);
	  	Response responseDocUpload = given().header(SIGNATURE,"test").
	   	
	  	multiPart("bill", new File(path)).
	   	
	 	when().put(Resources.DocUploadAPI(organisation, transaction_id)).
	   	
	 	then().assertThat().statusCode(200)
	   	
	 	.extract().response();
	   	
	  	
	  	String responseStringDocUpload = responseDocUpload.asString();
	   	
	  	System.out.println("responseStringDocUpload "+ responseStringDocUpload);
	  	
	   }
	   	
	   	//--------------------------------------------------//
	  	
	  
	  public static void processTransaction() {	
	  	
	   Response responseProcess = given().header(SIGNATURE,"test").
	   	
	   when().put(Resources.ProcessAPI(organisation, transaction_id)).
	   
	   then().assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("status", equalTo("PROCESSING")).and().body("statusMessage",equalTo("All files sent for processing.")).and().body("errorCode", equalTo("E_NO_ERROR")).
	   
	   extract().response();
	   
	   
	   String responseStringProcess = responseProcess.asString();
	   
	   System.out.println("responseStringProcess "+ responseStringProcess);
	   
	   }
	  
	  public static String checkStatus() {	
		  	
		   Response responseProcess = given().header(SIGNATURE,"test").get(Resources.CheckStatusAPI(organisation,transaction_id));
		   
		   String responseStringProcess = responseProcess.asString();
		   
		   System.out.println("responseStringProcess "+ responseStringProcess);
		   
		   return responseStringProcess;
		   
		   }
}
