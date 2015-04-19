package edu.asu.swat;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;

public class HttpRequest {
	public static String resultStr = "";
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("Error!" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("Error!"+e);
            e.printStackTrace();
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


	public String doGet(String theUrl) {
		// TODO Auto-generated method stub
		final DefaultHttpClient httpClient = new DefaultHttpClient();
		final String url = theUrl.trim();
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	//synchronized(this) {  
		        try {
		            //Your code goes here
		        	HttpGet httpGet = new HttpGet(url.replace("+", "%20").replace("%27", "%27%27"));
		        	//HttpGet httpGet = new HttpGet(url);
		        	try {
		        		HttpResponse getResponse = httpClient.execute(httpGet);
//		        		if(getResponse.getStatusLine().getStatusCode() == 200)    
//		                {   
		                  resultStr = EntityUtils.toString(getResponse.getEntity());  
		                 
//		                }else{
//		                	resultStr="";
//		                }
		        	} catch (ClientProtocolException e) {
		        		// TODO Auto-generated catch block
		        		e.printStackTrace();
		        		resultStr="";
		        	} catch (IOException e) {
		        		// TODO Auto-generated catch block
		        		e.printStackTrace();
		        		resultStr="";
		        	}
		        } catch (Exception e) {
		            e.printStackTrace();
		            resultStr="";
		        }
		    	}
		    //}
		});
		//synchronized(thread){
		thread.start(); 
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//thread.run();
		//}
		return null;
	}
	
	public String doPost(String url, List<NameValuePair> params){
		final DefaultHttpClient httpClient = new DefaultHttpClient();
		final String urlInPost = url.trim();
		final List<NameValuePair> paramsInPost = params;
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	//synchronized(this) {  
		        try {
		            //Your code goes here
		        	HttpPost httpPost = new HttpPost(urlInPost);
		        	httpPost.setEntity(new UrlEncodedFormEntity(paramsInPost, "UTF-8"));
		        	try {
		        		HttpResponse getResponse = httpClient.execute(httpPost);
		                resultStr = EntityUtils.toString(getResponse.getEntity());  
		                 
		        	} catch (ClientProtocolException e) {
		        		// TODO Auto-generated catch block
		        		e.printStackTrace();
		        		resultStr="";
		        	} catch (IOException e) {
		        		// TODO Auto-generated catch block
		        		e.printStackTrace();
		        		resultStr="";
		        	}
		        } catch (Exception e) {
		            e.printStackTrace();
		            resultStr="";
		        }
		    	}
		    //}
		});
		//synchronized(thread){
		thread.start(); 
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//thread.run();
		//}
		return null;
	}
	
	public String doPostWithFile(String url, List<NameValuePair> params, final Bitmap bmp){
		final HttpClient httpClient = new DefaultHttpClient();
		final String urlInPost = url.trim();
		final List<NameValuePair> paramsInPost = params;
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	//synchronized(this) {  
		        try {
		            //Your code goes here
		        	HttpPost httpPost = new HttpPost(urlInPost);
		        	MultipartEntity reqEntity = new MultipartEntity();
//		        	reqEntity.addPart(params.get(0));
		        	//reqEntity.addPart("someFile", new FileBody("/some/file"));

		        	for(NameValuePair item:paramsInPost){
		        		reqEntity.addPart(item.getName(),new StringBody(item.getValue()));
		        	}
		        	ByteArrayOutputStream stream = new ByteArrayOutputStream();
		        	bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		        	byte[] byteArray = stream.toByteArray();
		        	ByteArrayBody body  = new ByteArrayBody(byteArray, paramsInPost.get(0).getValue() + ".jpg");
		        	reqEntity.addPart("coverfile",body);
		        	httpPost.setEntity(reqEntity);
		        	//httpPost.setEntity(new UrlEncodedFormEntity(paramsInPost, "UTF-8"));
		        	bmp.recycle();
		        	try {
		        		HttpResponse getResponse = httpClient.execute(httpPost);
		                resultStr = EntityUtils.toString(getResponse.getEntity());  
		                 
		        	} catch (ClientProtocolException e) {
		        		// TODO Auto-generated catch block
		        		e.printStackTrace();
		        		resultStr="";
		        	} catch (IOException e) {
		        		// TODO Auto-generated catch block
		        		e.printStackTrace();
		        		resultStr="";
		        	}
		        } catch (Exception e) {
		            e.printStackTrace();
		            resultStr="";
		        }
		    	}
		    //}
		});
		//synchronized(thread){
		thread.start(); 
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//thread.run();
		//}
		return null;
	}

}