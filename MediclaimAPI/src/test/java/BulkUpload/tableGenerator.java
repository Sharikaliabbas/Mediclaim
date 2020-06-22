package BulkUpload;

public class tableGenerator {
	
	public static String generateIssueTable(int countOfOrgs,String host) {
		
		return "<html>\r\n" + 
				"<body>\r\n" + 
				"<p style=\"font-weight: bold;font-size: 25px;\">MediClaimAPI Test Results</p>\r\n" + 
				"<p style=\"font-weight: bold;font-size: 20px;\">Total number of Transactions : "+countOfOrgs+"</p>\r\n" +
				"<p style=\"font-weight: bold;font-size: 20px;\">Executed on : "+host+"</p>\r\n" + 
				"<p style=\"font-weight: bold;font-size: 20px;\">Execution details : </p>\r\n" + 
				"<table style=\"border: 1px solid;font-size: 20px;\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"font-weight: bold;font-size: 20px;\"><td style=\"border: 1px solid;\">Prod Transaction ID</td><td style=\"border: 1px solid;\">New Transaction ID</td></tr>";
		
	}
	
	public static String endTable() {
		
		return  "</tbody>\r\n" + 
				"</table>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		
	}
	
	public static String issueDetailsTableBody(String prodTxnId , String newTxnId) {
		
		return "<tr><td style=\"border: 1px solid;\">"+prodTxnId+"</td><td style=\"border: 1px solid;\">"+newTxnId+"</td></tr>";
		
	}

}
