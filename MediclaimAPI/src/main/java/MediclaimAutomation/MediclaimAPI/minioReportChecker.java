package MediclaimAutomation.MediclaimAPI;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class minioReportChecker{
	
	static {
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/drivers/chromedriver.exe");
	}
	
	public static void reportCheckerAcme(String transaction_id) throws Exception{
		
		Properties properties = new Properties();
		
		FileInputStream fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\MediclaimAutomation\\MediclaimAPI\\properties.file");

		properties.load(fis);
		
		ChromeOptions options = new ChromeOptions();
		
        options.addArguments("headless");
        
        options.addArguments("window-size=1200x600");
        
        WebDriver driver = new ChromeDriver(options);
        
		driver.get("https://waxwing.hinagro.com:9000/minio/login");
		
		driver.findElement(By.xpath("//input[@id='accessKey']")).sendKeys("IRQLJO7WB3KA06XAZGWW");
		
		driver.findElement(By.xpath("//input[@id='secretKey']")).sendKeys("mPknu+IstCHhRxDcZPVWCbI3NMynDxRgYDNMMHn8");
		
		driver.findElement(By.xpath("//i[@class='fas fa-sign-in-alt']")).click();
		
		driver.get("https://waxwing.hinagro.com:9000/minio/"+properties.getProperty("ORG")+"/"+transaction_id+"/");
		
		driver.manage().window().maximize();
		
		driver.navigate().refresh();
		
		TimeUnit.SECONDS.sleep(10);
		
		driver.findElement(By.xpath("//a[contains(text(),'Report_"+transaction_id+".xls')]")).isDisplayed();
		
		System.out.println("Report is Present under Minio");
		
		driver.close();
		
	}

public static void reportCheckerMediAssist(String transaction_id) throws Exception{
		
		Properties properties = new Properties();
		
		FileInputStream fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\MediclaimAutomation\\MediclaimAPI\\properties.file");

		properties.load(fis);
		
		ChromeOptions options = new ChromeOptions();
		
        options.addArguments("headless");
        
        options.addArguments("window-size=1200x600");
        
        WebDriver driver = new ChromeDriver(options);
        
		driver.get("https://waxwing.hinagro.com:9000/minio/login");
		
		driver.findElement(By.xpath("//input[@id='accessKey']")).sendKeys("IRQLJO7WB3KA06XAZGWW");
		
		driver.findElement(By.xpath("//input[@id='secretKey']")).sendKeys("mPknu+IstCHhRxDcZPVWCbI3NMynDxRgYDNMMHn8");
		
		driver.findElement(By.xpath("//i[@class='fas fa-sign-in-alt']")).click();
		
		driver.get("https://waxwing.hinagro.com:9000/minio/"+properties.getProperty("ORG")+"/"+transaction_id+"/");
		
		driver.manage().window().maximize();
		
		driver.navigate().refresh();
		
		TimeUnit.SECONDS.sleep(5);
		
		String filename = driver.findElement(By.xpath("//a[contains(text(),'Report_"+transaction_id+".json')]")).getText();
		
		System.out.println("filename: "+filename);
		
		driver.close();
		
	}
}
