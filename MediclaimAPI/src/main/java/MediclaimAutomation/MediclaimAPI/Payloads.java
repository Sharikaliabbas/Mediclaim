package MediclaimAutomation.MediclaimAPI;

public class Payloads {
	
	
	public static String SuccessfulReportGeneration(String organisation,String transaction_id) {
	
		return "{\"organisationName\":\""+organisation+"\",\"perfiosTransactionId\":\""+transaction_id+"\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\""+organisation+"\",\"rejected\":false,\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\""+transaction_id+"/"+transaction_id+".xml\",\"fileName\":\""+transaction_id+".xml\"}],\"scannedFiles\":[{\"id\":254,\"numberOfPages\":1234,\"scannedImageQuality\":\"HIGH\"}]}]}";
	}
	
	public static String AmountBalanceMisMatchErrorCode(String organisation,String transaction_id) {
		return "{\"organisationName\":\""+organisation+"\",\"perfiosTransactionId\":\""+transaction_id+"\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\""+organisation+"\",\"errorCode\":\"E_AMOUNT_BALANCE_MISMATCH\",\"rejected\":true,\"remarks\":\"automation reject test case\",\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\""+transaction_id+"/"+transaction_id+".json\",\"fileName\":\""+transaction_id+".json\"}],\"scannedFiles\":[{\"id\":995,\"numberOfPages\":3,\"scannedImageQuality\":\"HIGH\"}]}]}";
	}
	
	public static String IncompleteDataErrorCode(String organisation,String transaction_id) {
		return "{\"organisationName\":\""+organisation+"\",\"perfiosTransactionId\":\""+transaction_id+"\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\""+organisation+"\",\"errorCode\":\"E_INCOMPLETE_DATA\",\"rejected\":true,\"remarks\":\"automation reject test case\",\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\""+transaction_id+"/"+transaction_id+".json\",\"fileName\":\""+transaction_id+".json\"}],\"scannedFiles\":[{\"id\":995,\"numberOfPages\":3,\"scannedImageQuality\":\"HIGH\"}]}]}";
	}
	
	public static String UnsupportedBillFormatErrorCode(String organisation,String transaction_id) {
		return "{\"organisationName\":\""+organisation+"\",\"perfiosTransactionId\":\""+transaction_id+"\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\""+organisation+"\",\"errorCode\":\"UNSUPPORTED_BILL_FORMAT\",\"rejected\":true,\"remarks\":\"automation reject test case\",\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\""+transaction_id+"/"+transaction_id+".json\",\"fileName\":\""+transaction_id+".json\"}],\"scannedFiles\":[{\"id\":995,\"numberOfPages\":3,\"scannedImageQuality\":\"HIGH\"}]}]}";
	}
	
	public static String SummaryDetailedBalanceMismatchErrorCode(String organisation,String transaction_id) {
		return "{\"organisationName\":\""+organisation+"\",\"perfiosTransactionId\":\""+transaction_id+"\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\""+organisation+"\",\"errorCode\":\"E_BALANCE_SHEET_MISMATCH\",\"rejected\":true,\"remarks\":\"automation reject test case\",\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\""+transaction_id+"/"+transaction_id+".json\",\"fileName\":\""+transaction_id+".json\"}],\"scannedFiles\":[{\"id\":995,\"numberOfPages\":3,\"scannedImageQuality\":\"HIGH\"}]}]}";
	}
	
	public static String MultipleBillErrorCode(String organisation,String transaction_id) {
		return "{\"organisationName\":\""+organisation+"\",\"perfiosTransactionId\":\""+transaction_id+"\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\""+organisation+"\",\"errorCode\":\"E_DATA_MULTIPLE_ENTITIES\",\"rejected\":true,\"remarks\":\"automation reject test case\",\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\""+transaction_id+"/"+transaction_id+".json\",\"fileName\":\""+transaction_id+".json\"}],\"scannedFiles\":[{\"id\":995,\"numberOfPages\":3,\"scannedImageQuality\":\"HIGH\"}]}]}";
	}
	
	public static String ClaimAmountMismatchErrorCode(String organisation,String transaction_id) {
		return "{\"organisationName\":\""+organisation+"\",\"perfiosTransactionId\":\""+transaction_id+"\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\""+organisation+"\",\"errorCode\":\"CLAIM_AMOUNT_MISMATCH\",\"rejected\":true,\"remarks\":\"automation reject test case\",\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\""+transaction_id+"/"+transaction_id+".json\",\"fileName\":\""+transaction_id+".json\"}],\"scannedFiles\":[{\"id\":995,\"numberOfPages\":3,\"scannedImageQuality\":\"HIGH\"}]}]}";
	}
	
	public static String ClaimAmountMissingErrorCode(String organisation,String transaction_id) {
		return "{\"organisationName\":\""+organisation+"\",\"perfiosTransactionId\":\""+transaction_id+"\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\""+organisation+"\",\"errorCode\":\"CLAIM_AMOUNT_MISSING\",\"rejected\":true,\"remarks\":\"automation reject test case\",\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\""+transaction_id+"/"+transaction_id+".json\",\"fileName\":\""+transaction_id+".json\"}],\"scannedFiles\":[{\"id\":995,\"numberOfPages\":3,\"scannedImageQuality\":\"HIGH\"}]}]}";
	}
	
	public static String OtherErrorCode(String organisation,String transaction_id) {
		return "{\"organisationName\":\""+organisation+"\",\"perfiosTransactionId\":\""+transaction_id+"\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\""+organisation+"\",\"errorCode\":\"E_OTHER\",\"rejected\":true,\"remarks\":\"automation reject test case\",\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\""+transaction_id+"/"+transaction_id+".json\",\"fileName\":\""+transaction_id+".json\"}],\"scannedFiles\":[{\"id\":995,\"numberOfPages\":3,\"scannedImageQuality\":\"HIGH\"}]}]}";
	}
	
	public static String PoorQuality(String organisation,String transaction_id) {
		return "{\"organisationName\":\""+organisation+"\",\"perfiosTransactionId\":\""+transaction_id+"\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\""+organisation+"\",\"errorCode\":\"LOW_QUALITY\",\"rejected\":true,\"remarks\":\"automation reject test case\",\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\""+transaction_id+"/"+transaction_id+".json\",\"fileName\":\""+transaction_id+".json\"}],\"scannedFiles\":[{\"id\":995,\"numberOfPages\":3,\"scannedImageQuality\":\"HIGH\"}]}]}";
	}
	
	public static String TooManyPagesErrorCode(String organisation,String transaction_id) {
		return "{\"organisationName\":\""+organisation+"\",\"perfiosTransactionId\":\""+transaction_id+"\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\""+organisation+"\",\"errorCode\":\"BILL_TOO_MANY_PAGES\",\"rejected\":true,\"remarks\":\"automation reject test case\",\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\""+transaction_id+"/"+transaction_id+".json\",\"fileName\":\""+transaction_id+".json\"}],\"scannedFiles\":[{\"id\":995,\"numberOfPages\":3,\"scannedImageQuality\":\"HIGH\"}]}]}";
	}
	
	
	public static final String IncorrectPayload = "{\r\n" + 
			"    \"callbackUrl\": \"http://redhead.hinagro.com:5000/api/callbackfailed\",\r\n" + 
			"    \"claimAmount\": 20000.00,\r\n" + 
			"    \"claimType\": \"REIMBURSEMENT\",\r\n" + 
			"    \"clientTransactionId\": \"TXN1234567\",\r\n" + 
			"    \"hospitalCode\": \"1234\"\r\n" + 
			"}";
	
	public static final String StartTransactionPayload = "{\"callbackUrl\": \"http://redhead.hinagro.com:5000/api/callbacksuccess\","+"\"claimType\": \"REIMBURSEMENT\","+"\"clientTransactionId\": \"TXN1234567\","+"\"hospitalCode\": \"1234\","+"\"hospitalName\": \"SAKARA\"}";

	public static final String StartTransactionPayloadFailure = "{\"callbackUrl\": \"http://redhead.hinagro.com:5000/api/callbackfailed\","+"\"claimType\": \"REIMBURSEMENT\","+"\"clientTransactionId\": \"TXN1234567\","+"\"hospitalCode\": \"1234\","+"\"hospitalName\": \"SAKARA\"}";

	public static final String AddingOrgsPayload = "{\r\n" + 
			"    \"orgName\": \"acme\",\r\n" + 
			"    \"enabled\": true,\r\n" + 
			"    \"isBillingEnabled\": true,\r\n" + 
			"    \"passphrase\": \"sharikTest1234\"\r\n" + 
			"}";
	
	public static final String SaveAllSettings = "{\r\n" + 
			"	\"CategorizationConfig\": \"{\\\"orgToCategoryMapping\\\":{\\\"file\\\":\\\"/perfios/conf/mediclaim-config/acme-settings.txt\\\",\\\"isOrgCategorizationEnabled\\\":\\\"true\\\"}}\",\r\n" + 
			"	\"ReportingType\": \"{\\\"type\\\":[\\\"json\\\"]}\",\r\n" + 
			"	\"ReportingConfig\": \"{\\\"xls\\\":{\\\"xlsTemplate\\\":\\\"ExcelTemplate.xls\\\"},\\\"xml\\\":{\\\"xsltFile\\\":\\\"mediassist.xsl\\\"},\\\"json\\\":{\\\"xsltFile\\\":\\\"mediassist.xsl\\\",\\\"xmlToJson\\\":true},\\\"defaultConfig\\\":{\\\"reportType\\\":\\\"XML\\\"}}\",\r\n" + 
			"	\"RetentionPeriod\": \"{\\\"noOfDays\\\":1,\\\"savePointDate\\\":\\\"May 27, 2020 9:42:43 AM\\\",\\\"isDataRetentionEnabled\\\":true}\"\r\n" + 
			"}";

	public static final String getJobsPayload = "{\"jobStatusList\":[\"ERROR_GENERATING_REPORT\"]}";
}
