package com.aads.pennapps.augmentedreality.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class SendImageAsyncTask extends AsyncTask<Void, Integer, Long>{
	   private static final int PROGRESS_DIALOG = 0;
	   private static String TAG="SendImageAsyncTask";

	    public ProgressDialog dialog;
	    protected Long doInBackground(Void... params) {
	    		String url="http://158.130.163.142:8080/augment";
	            String photo = "/sdcard/Pictures/AugmentedReality/pk.jpg";
//	            DefaultHttpClient httpclient = new DefaultHttpClient();
//	            HttpPost httppost = new HttpPost(url);
//	            MultipartEntity mpEntity = new MultipartEntity(
//	                    HttpMultipartMode.BROWSER_COMPATIBLE);
//	            mpEntity.addPart("form_file", new FileBody(new File(photo), "image/jpeg"));
//	            httppost.setEntity(mpEntity);
//	            HttpResponse response;
//	            try {
//	                response = httpclient.execute(httppost);
//	                Log.d("ASYNCTASK", ""+response.getStatusLine().getStatusCode());
//	                HttpEntity resEntity = response.getEntity();
//	                if (resEntity != null) {
//	                	Log.d("ASYNCTASK", EntityUtils.toString(resEntity));
//	                    //resEntity.consumeContent();
//	                }
//	            } catch (Exception e) {
//	                e.printStackTrace();
//	                Log.e("ASYNCTASK", e.getMessage());
//	            } 
	            try {
	            	uploadPictureToServer(photo);
	            } catch (Exception e) {
	            	Log.e(TAG, e.getMessage());
	            }
	        return null;
	    }
	    
	    public static void uploadPictureToServer(String i_file) throws ClientProtocolException, IOException {
	        // TODO Auto-generated method stub   
	        HttpClient httpclient = new DefaultHttpClient();
	        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

	        HttpPost httppost = new HttpPost("http://158.130.163.142:8080/augment");
	        File file = new File(i_file);

	        MultipartEntity mpEntity = new MultipartEntity();
	        ContentBody cbFile = new FileBody(file, "image/jpeg");
	        mpEntity.addPart("form_file", cbFile);

	        httppost.setEntity(mpEntity);
	        Log.d(TAG, "executing request " + httppost.getRequestLine());
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity resEntity = response.getEntity();

	        Log.d(TAG, ""+response.getStatusLine());
	        if (resEntity != null) {
	          Log.d(TAG, "Response: "+EntityUtils.toString(resEntity));
	        }
	        if (resEntity != null) {
	          resEntity.consumeContent();
	        }

	        httpclient.getConnectionManager().shutdown();

	    }

	    protected void onPostExecute(Long unused) {
	        super.onPostExecute(unused);
	    }

	    protected void onPreExecute() {
	    }
}
