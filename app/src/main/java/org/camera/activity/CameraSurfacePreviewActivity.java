package org.camera.activity;

import com.example.camerapreview.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import org.camera.camera.CameraWrapper;
import org.camera.camera.CameraWrapper.CamOpenOverCallback;
import org.camera.preview.*;

public class CameraSurfacePreviewActivity extends Activity implements CamOpenOverCallback{
	private static final String TAG = "CameraPreviewActivity";
	private CameraSurfacePreview cameraSurfacePreview;
	private float mPreviewRate = -1f;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_preview);
		initUI();
		initViewParams();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	protected void onDestroy(){
		Log.i(TAG, "onDestroy");
		super.onDestroy();
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override  
    protected void onStart() {  
		Log.i(TAG, "onStart");
        super.onStart();  
        
        Thread openThread = new Thread() {
			@Override
			public void run() {
				CameraWrapper.getInstance().doOpenCamera(CameraSurfacePreviewActivity.this);
			}
		};
		openThread.start();
    } 
	
	private void initUI() {
//		cameraSurfacePreview = (CameraSurfacePreview) findViewById(R.id.camera_surfaceview);
	}
	
	private void initViewParams() {
		LayoutParams params = cameraSurfacePreview.getLayoutParams();  
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();  
        int screenWidth = displayMetrics.widthPixels;  
        int screenHeight = displayMetrics.heightPixels; 
        params.width = screenWidth;  
        params.height = screenHeight;   
        this.mPreviewRate = (float)screenHeight / (float)screenWidth; 
        cameraSurfacePreview.setLayoutParams(params);  
	}

	@Override
	public void cameraHasOpened() {
		SurfaceHolder holder = this.cameraSurfacePreview.getSurfaceHolder();
		CameraWrapper.getInstance().doStartPreview(holder, mPreviewRate);
	}
}
