package MediclaimAutomation.MediclaimAPI;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public class Base {


	FileInputStream fis;
	
	protected Properties properties;
	
	@BeforeClass
	public void getAPIHost() throws Exception {
		
		properties = new Properties();
		
		fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\MediclaimAutomation\\MediclaimAPI\\properties.file");
	
		properties.load(fis);
		
		RestAssured.baseURI=properties.getProperty("HOST");
	}
}
