package MediclaimAutomation.MediclaimAPI;
import redis.clients.jedis.Jedis;

public class PublishingResponseToRedis {
	
	public static Long connectToJedis(String payload) {
		
		// Connecting to Redis server on localhost
		Jedis jedis = new Jedis("spoonbill.hinagro.com");
		System.out.println("Connection to server sucessfully");
		jedis.auth("falcon123");
		Long publishResponse = jedis.publish("mediclaim_response_queue_qa_auto", payload);
		jedis.close();
		return publishResponse;
	}
	
	/*public static void main(String[] args) throws InterruptedException {
		String payLoad = "{\"organisationName\":\"acme\",\"perfiosTransactionId\":\"PKRFSF47MBYWI09RIVBW8\",\"processingType\":\"MEDICLAIM\",\"bucketName\":\"acme\",\"rejected\":false,\"digitizedData\":[{\"convertedFile\":[{\"fileObject\":\"PKRFSF47MBYWI09RIVBW8/PKRFSF47MBYWI09RIVBW8.json\",\"fileName\":\"PMDNFX6WSSNVV2G3OVD0D .json\"}],\"scannedFiles\":[{\"id\":2739,\"numberOfPages\":5,\"scannedImageQuality\":\"NOT_APPLICABLE\"},{\"id\":2738,\"numberOfPages\":5,\"scannedImageQuality\":\"MEDIUM\"}]}]}";
		System.out.println(connectToJedis(payLoad));
	}*/
}