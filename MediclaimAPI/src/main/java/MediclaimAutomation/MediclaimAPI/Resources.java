package MediclaimAutomation.MediclaimAPI;




public class Resources{
	
	public static String StartMediclaimJobAPI(String OrgName)
	
	{
		return "/api/v1/mediclaim/"+OrgName;
	}
	
	public static String DocUploadAPI(String organisation, String transaction_id)
	{
		return "/api/v1/mediclaim/"+organisation+"/"+transaction_id;
	}

	public static String ProcessAPI(String organisation,String transaction_id)
	{
		return "/api/v1/mediclaim/"+organisation+"/"+transaction_id+"/process";
	}
	

	public static String DownloadAPI(String transaction_id) 
	{
		return "/api/v1/mediclaim/acme/"+transaction_id+"/xls/report";
	}
	
	public static String DownloadAPI_MA(String transaction_id) 
	{
		return "/api/v1/mediclaim/mediassist/"+transaction_id+"/json/report";
	}
	
	public static String CheckStatusAPI(String organisation,String transaction_id)
	{
		return "/api/v1/mediclaim/"+organisation+"/"+transaction_id+"/status";
	}
	
	
	public static String IncorrectOrgName()
	{
		return "/api/v1/mediclaim/acme1";
	}
	
	public static String NoOrgName()
	{
		return "/api/v1/mediclaim/";
	}
	
	public static String getOrgs() {
		return "/api/v1/admin/orgs";
	}
	
	public static String getAllSetting() {
		return "/api/v1/admin/orgs/testorg/settings/";
	}
	
	public static String getGivenSettingReportingConfig() {
		return "/api/v1/admin/orgs/testorg/settings/ReportingConfig";
	}
	
	public static String getGivenSettingReportingType() {
		return "/api/v1/admin/orgs/testorg/settings/ReportingType";
	}
	
	public static String getGivenSettingCategorizationConfig() {
		return "/api/v1/admin/orgs/testorg/settings/CategorizationConfig";
	}
	
	public static String getGivenSettingRetentionPeriod() {
		return "/api/v1/admin/orgs/testorg123/settings/RetentionPeriod";
	}
	
	public static String saveAllSettings() {
		return "/api/v1/admin/orgs/testmedi/settings";
	}
	
	public static String getJobs() {
		return "/api/v1/admin/jobs";
	}
	public static String retry(String retryTxnId) {
		return "/api/v1/admin/jobs/retry/"+retryTxnId;
	}
	
	public static String cancel(String cancelTxnId) {
		return "/api/v1/admin/jobs/retry/"+cancelTxnId;
	}
	
	public static String saveSettings_ReportingConfig() {
		return "/api/v1/admin/orgs/testmedi/settings/CategorizationConfig";
	}
	
	
}

