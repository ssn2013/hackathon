package com.aads.pennapps.augmentedreality.resources;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class SendImageAsyncTask extends AsyncTask<Void, Integer, Long>{
	   private static final int PROGRESS_DIALOG = 0;

	    public ProgressDialog dialog;
	    protected Long doInBackground(Void... params) {
	    		String url="http://158.130.163.142:8080/augment";
	            String photo = "/sdcard/Pictures/AugmentedReality/pk.jpg";
	            DefaultHttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost(url);
	            MultipartEntity mpEntity = new MultipartEntity(
	                    HttpMultipartMode.BROWSER_COMPATIBLE);
	            mpEntity.addPart("form_file", new FileBody(new File(photo), "image/jpeg"));
	            httppost.setEntity(mpEntity);
	            HttpResponse response;
	            try {
	                response = httpclient.execute(httppost);
	                Log.d("ASYNCTASK", ""+response.getStatusLine().getStatusCode());
	                HttpEntity resEntity = response.getEntity();
	                if (resEntity != null) {
	                	Log.d("ASYNCTASK", EntityUtils.toString(resEntity));
	                    //resEntity.consumeContent();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	                Log.e("ASYNCTASK", e.getMessage());
	            } 
	        return null;
	    }

	    protected void onPostExecute(Long unused) {
	        super.onPostExecute(unused);
	    }

	    protected void onPreExecute() {
	    }
}
