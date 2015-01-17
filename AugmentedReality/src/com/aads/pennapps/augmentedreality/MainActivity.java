package com.aads.pennapps.augmentedreality;
import com.aads.pennapps.augmentedreality.resources.CameraPreview;

import 	android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.content.pm.PackageManager;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void clickButton(View view) {
    	Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show();
    	if(checkCameraHardware(this)) {
    		Intent intent = new Intent(this, CameraActivity.class);
    		startActivity(intent);
    		
    		
    	} else {
    		Toast.makeText(this, "Na na na na na!", Toast.LENGTH_SHORT).show();
    	}
    	
    }
   
	private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
	
	
}
