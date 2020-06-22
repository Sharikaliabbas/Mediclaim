package BulkUpload;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class csvWriter {

	static File file = null;
	static FileWriter fw = null;
	
	public static void setFile(String filePath){
		
		file = new File(filePath); 
		if(!(file.exists())) {
			
			try {
				file.createNewFile();
			//	fw.write("\"prodTransactionId\",\"newTransactionId\",\"executionStartTime\"");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		try {
			fw = new FileWriter(file);
			fw.write("\"prodTransactionId\",\"newTransactionId\",\"executionStartTime\"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public static void writeDataLineByLine(String prodTxnId, String newTxnId , String startTime) 
	{ 

		try { 
			// create FileWriter object with file as parameter 
			
			
			
			fw.write("\""+prodTxnId+"\",\""+newTxnId+"\",\""+startTime+"\"\n");

			
			
		} 
		catch (IOException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
	}
	
	
	public static void flushFile() {
		
		try {
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
