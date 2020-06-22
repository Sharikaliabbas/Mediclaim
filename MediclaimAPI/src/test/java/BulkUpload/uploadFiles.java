package BulkUpload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import MediclaimAutomation.MediclaimAPI.apiTest;


public class uploadFiles {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		String reportBody = "";
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   
		 //  System.out.println(dtf.format(now));
		
		File report = new File(System.getProperty("user.dir")+"/report.html");
		
		if(!(report.exists())) {
			
			try {
				report.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(report);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		
		File f = new File(System.getProperty("user.dir")+"/Samples");
		
		File[] listOfFiles = f.listFiles();
		
		String baseURL="";
		try {
			baseURL = apiTest.PropertyFileAndBaseURL();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bw.write(tableGenerator.generateIssueTable(listOfFiles.length,baseURL));
		
		csvWriter.setFile(System.getProperty("user.dir")+"/report.csv");
		
		for(File sample : listOfFiles) {
			
			String newTxnId = "";
			
			 String sampleTxnFolderPath = sample.getAbsolutePath();
			 
			// System.out.println("path : "+sampleTxnFolderPath);
			 
			 String prodTxnId = sampleTxnFolderPath.substring(sampleTxnFolderPath.lastIndexOf("\\")+1);
			 
			 System.out.println("here : "+prodTxnId);
			 
			 try {
				 LocalDateTime now = LocalDateTime.now();  
				String timeNow = dtf.format(now);
				newTxnId = apiTest.initiateTransaction();
				csvWriter.writeDataLineByLine(prodTxnId, newTxnId, timeNow);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 File txnFolder = new File(sampleTxnFolderPath);
			 
			 File[] filesToUpload = txnFolder.listFiles();
			 
			 for(File uploadFile : filesToUpload) {
				 System.out.println("Uploading file : "+uploadFile.getAbsolutePath().substring(uploadFile.getAbsolutePath().lastIndexOf("\\")+1));
				 apiTest.uploadStatement(uploadFile.getAbsolutePath());
				 Thread.sleep(1000);
			 }
			 
			 Thread.sleep(5000);
			 
			 apiTest.processTransaction();
			 Thread.sleep(5000);
			 
			System.out.println(apiTest.checkStatus());
			 System.out.println("new txn id : "+newTxnId);
			 reportBody += tableGenerator.issueDetailsTableBody(prodTxnId, newTxnId);
		//	 csvWriter.writeDataLineByLine();
			
		}
		
		csvWriter.flushFile();
		
		
		bw.write(reportBody);
		bw.write(tableGenerator.endTable());
		bw.flush();
		bw.close();fw.close();
		
	}

}
