package org.camera.preview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import org.camera.camera.*;

@SuppressLint("NewApi")
public class CameraTexturePreview extends TextureView implements TextureView.SurfaceTextureListener {
	private final String TAG = "CameraTexturePreview";
	Context mContext;  
    SurfaceTexture mSurface; 
	public CameraTexturePreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;  
	    this.setSurfaceTextureListener(this);  
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		Log.i(TAG, "onSurfaceTextureAvailable()");
		this.mSurface = surface;  
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
		Log.i(TAG, "onSurfaceTextureSizeChanged()");  
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		Log.i(TAG, "onSurfaceTextureDestroyed()");  
		CameraWrapper.getInstance().doStopCamera();
		return false;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//		Log.i(TAG, "onSurfaceTextureUpdated()");  
	}

	public SurfaceTexture getSurfaceTexture() {
		return this.mSurface;
	}
}
