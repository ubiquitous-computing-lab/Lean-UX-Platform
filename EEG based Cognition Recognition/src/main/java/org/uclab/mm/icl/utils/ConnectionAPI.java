package org.uclab.mm.icl.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionAPI {
   public static final String transferEncoding = "chunked";
   public static final String contentType = "application/octet-stream";
   public static final String X_DSS_Service = "DICTATION";
   public static final String key = "KakaoAK ae00e1cd489d75f2ac5f0d9b58f4f211";
   public static final String cacheControl = "no-cache, must-revalidate";
   public static final String pragma = "no-cache";
   
   
   public static String STT(AudioInputStream fileContent) throws IOException {      
      //init URL setting
      URL url = null;
      HttpURLConnection conn = null;
      AudioInputStream compact = convertSampleRate(fileContent, 16000);
      byte[] speeches = toByteArray(compact);
      
      try {
         url = new URL("https://kakaoi-newtone-openapi.kakao.com/v1/recognize"); // 
      
      } catch(MalformedURLException e) {
         e.printStackTrace();
      }
      
      //POST request
      try {
         conn = (HttpURLConnection)url.openConnection();
         conn.setRequestProperty("Transfer-Encoding", transferEncoding);
         conn.setRequestProperty("Content-Type", contentType);
         conn.setRequestProperty("X-DSS-Service", X_DSS_Service);
         conn.setRequestProperty("Cache-Control", cacheControl);
         conn.setRequestProperty("Pragma", pragma); 
         conn.setRequestProperty("Authorization", key);
         conn.setDoOutput(true);
         conn.getOutputStream().write(speeches); // POST 
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      try {
         System.out.println("conn.getResponseCode = " + conn.getResponseCode());
      } catch (IOException e2) {
         e2.printStackTrace();
      }
      

      InputStream inputStream = null;
      try {
         inputStream = new BufferedInputStream(conn.getInputStream());
      } catch (IOException e1) {
         e1.printStackTrace();
      }
      BufferedReader reader = null;
      try {
         reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
      } catch (UnsupportedEncodingException e1) {
         e1.printStackTrace();
      }
      
      String line = "";
      String best = "No Result";
      try {
    	  while((line = reader.readLine()) != null) {
    		  if(line.startsWith("{")) {
	    		  JSONObject json = new JSONObject(line);
	    		  String type = json.getString("type");
	    		  if(type.equals("finalResult")) {
	    			  best = json.getString("value");
	    			  break;
	    		  }
    		  }
    	  }
      } catch (IOException e) {
    	  e.printStackTrace();
      }
       
      try {
    	  
      } catch (Exception e) {
    	  e.printStackTrace();
      }
      System.out.println(String.format("Best result : %s", best));
      return best;
   }
   
   public static byte[] toByteArray(AudioInputStream ais) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int len;
      
      while((len = ais.read(buffer)) != -1) {
         baos.write(buffer, 0, len);
      }
      
      return baos.toByteArray();
   }
   
   public static AudioInputStream convertSampleRate(AudioInputStream in, float sampleRate) {
      AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
      AudioInputStream out = AudioSystem.getAudioInputStream(format, in);
      return out;
   }
   
   public static double predict(String text) {
      URL url = null;
      try {
         url = new URL("http://163.180.116.140:10000/post");
      } catch (MalformedURLException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
      System.out.println(String.format("Sending 'POST' request to URL : %s, parameter : %s", url, text));
        HttpURLConnection http = null;
      try {
         http = (HttpURLConnection) url.openConnection();
      } catch (IOException e1) {
         e1.printStackTrace();
      }

        http.setDefaultUseCaches(false);
        http.setDoInput(true);
        http.setDoOutput(true);
        try {
         http.setRequestMethod("POST");
      } catch (ProtocolException e1) {
         e1.printStackTrace();
      }

        http.setRequestProperty("content-type", "application/json");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("text", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(jsonObject.toString());

        OutputStreamWriter outStream = null;
      try {
         outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
        PrintWriter writer = new PrintWriter(outStream);
        writer.write(buffer.toString());
        writer.flush();

        InputStreamReader tmp = null;
      try {
         tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
        BufferedReader reader = new BufferedReader(tmp);
        StringBuilder builder = new StringBuilder();
        String str;
        try {
         while ((str = reader.readLine()) != null) {
             builder.append(str);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
        System.out.println("Predict : " + builder.toString());
        return Double.parseDouble(builder.toString());
   }
}