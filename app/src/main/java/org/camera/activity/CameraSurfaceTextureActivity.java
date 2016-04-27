package org.camera.activity;

import com.example.camerapreview.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.ViewGroup.LayoutParams;  
import org.camera.camera.CameraWrapper;
import org.camera.camera.CameraWrapper.CamOpenOverCallback;
import org.camera.preview.*;

@SuppressLint("NewApi")
public class CameraSurfaceTextureActivity extends Activity implements CamOpenOverCallback{
	private static final String TAG = "CameraPreviewActivity";
	private CameraTexturePreview mCameraTexturePreview;
	private float mPreviewRate = -1f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_preview);
		initUI();
		initViewParams();
	}
	
	@Override  
    protected void onStart() {  
		Log.i(TAG, "onStart");
        super.onStart();  
        
        Thread openThread = new Thread() {
			@Override
			public void run() {
				CameraWrapper.getInstance().doOpenCamera(CameraSurfaceTextureActivity.this);
			}
		};
		openThread.start();
    } 
	
	private void initUI() {
		mCameraTexturePreview = (CameraTexturePreview) findViewById(R.id.camera_textureview);
	}
	
	private void initViewParams() { 
		LayoutParams params = mCameraTexturePreview.getLayoutParams();
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();  
        int screenWidth = displayMetrics.widthPixels;  
        int screenHeight = displayMetrics.heightPixels; 
        params.width = screenWidth;  
        params.height = screenHeight;   
        this.mPreviewRate = (float)screenHeight / (float)screenWidth; 
        mCameraTexturePreview.setLayoutParams(params);
	}

	@Override
	public void cameraHasOpened() {
		SurfaceTexture surface = this.mCameraTexturePreview.getSurfaceTexture();
		CameraWrapper.getInstance().doStartPreview(surface, mPreviewRate);
	}
}
