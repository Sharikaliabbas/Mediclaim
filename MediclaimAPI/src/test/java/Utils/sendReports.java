package Utils;

import org.testng.annotations.Test;

import MediclaimAutomation.MediclaimAPI.Base;
import MediclaimAutomation.MediclaimAPI.SendReports;

public class sendReports extends Base{

	@Test
	public void sendReportsToPeople() throws Exception {
		
		if(properties.getProperty("ORG").equals("acme")) {
		
		SendReports.sendReportToDesiredPeopleAcme();
		}
		else {
			SendReports.sendReportToDesiredPeopleMA();
		}
	}
	
}
