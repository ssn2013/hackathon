package com.aads.pennapps.augmentedreality;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aads.pennapps.augmentedreality.resources.CameraPreview;
import com.aads.pennapps.augmentedreality.resources.SendImageAsyncTask;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CameraActivity extends ActionBarActivity{

	protected static final String TAG = "CameraActivity";
	private static Camera mCamera;
	private static CameraPreview mPreview;

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
				//new SendImageAsyncTask().execute(mCamera);
				//mCamera.startPreview();
				textOverlay();
			}
		}.start();
		Toast.makeText(this, "Timer started", Toast.LENGTH_SHORT).show();
		
		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
	}
	
	private void textOverlay() {
		TextView editText = (TextView)findViewById(R.id.overlay_textview);
		editText.setText("HEllow WORLD!!");
		RelativeLayout relativeLayoutOverlay = (RelativeLayout)findViewById(R.id.text_overlay_layout1);
		relativeLayoutOverlay.bringToFront();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		try {
			mCamera.reconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mCamera.release();
	}
	
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
