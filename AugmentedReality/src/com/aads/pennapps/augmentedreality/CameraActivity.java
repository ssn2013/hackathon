package com.aads.pennapps.augmentedreality;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aads.pennapps.augmentedreality.resources.CameraPreview;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;


public class CameraActivity extends ActionBarActivity{

	protected static final String TAG = "CameraActivity";
	private static Camera mCamera;
	private static CameraPreview mPreview;
	
	private static Camera.PictureCallback mPicture = new Camera.PictureCallback() {

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
	        Log.d(TAG, "Picture callback called");
	        
	        if (mediaFile == null){
	            Log.d(TAG, "Error creating media file, check storage permissions");
	            return;
	        }

	        try {
	            FileOutputStream fos = new FileOutputStream(mediaFile);
	            fos.write(data);
	            fos.close();
	        } catch (FileNotFoundException e) {
	            Log.d(TAG, "File not found: " + e.getMessage());
	        } catch (IOException e) {
	            Log.d(TAG, "Error accessing file: " + e.getMessage());
	        }
	    }
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_preview);

		// Create an instance of Camera
		mCamera = getCameraInstance();
        //start timer
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
			}
			
			@Override
			public void onFinish() {
				Toast.makeText(CameraActivity.this, "Timer ended" , Toast.LENGTH_SHORT).show();
				if(mCamera==null)
					Log.e("EXCEPTIONInCAMERA", "mCamera");
				if(mPicture==null)
					Log.e("EXCEPTIONInCAMERA", "mPicture");
				mCamera.takePicture(null, null, mPicture);
			}
		}.start();
		Toast.makeText(this, "Timer started", Toast.LENGTH_SHORT).show();
		
		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
	}
	
//	@Override
//	public void onResume() {
//		super.onResume();
//		mCamera = getCameraInstance();
//	}
//
//	@Override
//	public void onPause() {
//		super.onPause();
//		mCamera.release();
//	}
//	
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
	
}
