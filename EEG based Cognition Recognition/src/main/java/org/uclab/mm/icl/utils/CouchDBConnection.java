package org.uclab.mm.icl.utils;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.lightcouch.CouchDbClient;

public class CouchDBConnection {
	CouchDbClient dbClient;
	public CouchDBConnection(String dbName, boolean createDbIfNotExist, String protocol, String host, int port, String username, String password) {
		dbClient = new CouchDbClient(dbName, createDbIfNotExist, protocol, host, port, username, password);
	}
	public void saveData(int project_id, int participant_id, int task_id, String datatype, String label, String Text, double score) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("project", participant_id);
		map.put("participant",participant_id);
		map.put("taskid", task_id);
		map.put("datatype", datatype);
		map.put("streamdatatime", getTime());
		map.put("emotion", label);
		map.put("Text", Text);
		map.put("score", score);
		dbClient.save(map);	
	}
	
	public static String getTime() {
		String result  ="";
		Date today = new Date();
		LocalDateTime now = LocalDateTime.now();
		int millis = now.get(ChronoField.MILLI_OF_SECOND); 
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
		result = date.format(today) + "T" +time.format(today)+"."+millis+"Z";
		return result;
	}
	public static String getId() {
		String result  = String.valueOf(System.currentTimeMillis());
		System.out.println(result);
		return result;
	}


}
