package com.aads.pennapps.augmentedreality.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class SendImageAsyncTask extends AsyncTask<Object, Integer, Long>{
	private static final int PROGRESS_DIALOG = 0;
	private static String TAG="SendImageAsyncTask";
	public ProgressDialog dialog;

	private Camera mCamera;
	private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {

			Log.d(TAG, "Picture callback called");
			//Access storage to store
			File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_PICTURES), "AugmentedReality");
			if (! mediaStorageDir.exists()){
				if (! mediaStorageDir.mkdirs()){
					Log.d(TAG, "failed to create directory");
					return;
				}
			}
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"IMG_"+ timeStamp + ".jpg");

			if (mediaFile == null){
				Log.d(TAG, "Error creating media file, check storage permissions");
				return;
			}

			try {
				FileOutputStream fos = new FileOutputStream(mediaFile);
				fos.write(data);
				fos.close();
				
				Log.d(TAG, "Done writing image");
				mCamera.startPreview();
			} catch (FileNotFoundException e) {
				Log.d(TAG, "File not found: " + e.getMessage());
			} catch (IOException e) {
				Log.d(TAG, "Error accessing file: " + e.getMessage());
			}
		}
	};

	private void takeImage() {
		Log.d(TAG, "Taking image");
		mCamera.takePicture(null, null, mPicture);
	}
	
	protected Long doInBackground(Object... params) {
		String url="http://158.130.163.142:8080/augment";
		String photo = "/sdcard/Pictures/AugmentedReality/pk.jpg";
		try {
			mCamera = (Camera)params[0];
			if(mCamera==null)
				throw new Exception("Camera null");
			takeImage();
			//uploadPictureToServer(photo);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
	}

	public void uploadPictureToServer(String i_file) throws ClientProtocolException, IOException {
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
		//mCamera.startPreview();
	}

	protected void onPreExecute() {
	}
}
