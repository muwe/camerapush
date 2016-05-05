package org.camera.activity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;

import org.camera.camera.CameraWrapper;
import org.camera.camera.CameraWrapper.CamOpenOverCallback;
import org.camera.preview.*;

public class CameraSurfacePreviewActivity extends Activity implements CamOpenOverCallback,Button.OnClickListener{
	private static final String TAG = "CameraPreviewActivity";
	private CameraSurfacePreview cameraSurfacePreview;
	private float mPreviewRate = -1f;
	private Button mBtnStart;
	private Button mBtnStop;
	private Button mBtnSwitch;
	private SurfaceHolder mSurfaceHolder;
	private SurfaceView mSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_preview);
		initViewParams();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	}

	@Override  
    protected void onStart() {
		Log.i(TAG, "onStart");
		super.onStart();
	}

	private void start(){
		Thread openThread = new Thread() {
			@Override
			public void run() {
				CameraWrapper.getInstance().doOpenCamera(CameraSurfacePreviewActivity.this);
			}
		};
		openThread.start();
	}

	private void stop(){
		Thread openThread = new Thread() {
			@Override
			public void run() {
				CameraWrapper.getInstance().doStopCamera();
			}
		};
		openThread.start();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy(){
		Log.i(TAG, "onDestroy");
		super.onDestroy();
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	}

	private void initViewParams() {
//		LayoutParams params = cameraSurfacePreview.getLayoutParams();
//		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
//        int screenWidth = displayMetrics.widthPixels;
//        int screenHeight = displayMetrics.heightPixels;
//        params.width = screenWidth;
//        params.height = screenHeight;
//        this.mPreviewRate = (float)screenHeight / (float)screenWidth;
//        cameraSurfacePreview.setLayoutParams(params);

		mSurfaceView=(SurfaceView)this.findViewById(R.id.surfaceview);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);



		mBtnStart = (Button) findViewById(R.id.btn_start);
		mBtnStart.setOnClickListener(this);
		mBtnStop = (Button) findViewById(R.id.btn_stop);
		mBtnStop.setOnClickListener(this);
		mBtnSwitch = (Button) findViewById(R.id.btn_switch);
		mBtnSwitch.setOnClickListener(this);
	}

	@Override
	public void cameraHasOpened() {
		CameraWrapper.getInstance().doStartPreview(mSurfaceHolder, mPreviewRate);
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_start:
				start();
				break;
			case R.id.btn_stop:
				stop();
				break;
			case R.id.btn_switch:
//				switch();
				break;
		}
	}

}
