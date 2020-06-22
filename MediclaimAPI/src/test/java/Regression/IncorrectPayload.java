package Regression;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.Payloads;
import MediclaimAutomation.MediclaimAPI.Resources;

public class IncorrectPayload extends Base{

   @Test
   public void Incorrectpayload() throws Exception
   {
	   String organisation = properties.getProperty("ORG");
	   
	   given()
	   
	   .header("signature","test").header("Content-Type","application/json")
	   
	   .body(Payloads.IncorrectPayload)
	   
	   .when().post(Resources.StartMediclaimJobAPI(organisation))
	   
	   .then().assertThat().statusCode(422).and().body("errorCode", equalTo("422 UNPROCESSABLE_ENTITY")).and().body("errorMessage", equalTo("{NotBlank=HospitalName should not be Empty}"))
	   
	   .extract().response();

   }
 }



