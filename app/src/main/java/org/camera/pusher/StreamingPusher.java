package org.camera.pusher;

import android.util.Log;

import org.camera.encode.VideoEncoderFromBuffer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by aiven on 16/4/27.
 */
public class StreamingPusher {
    private String TAG = "StreamingPusher";


    private FileOutputStream mFileOutputStream=null;
    private boolean mDumpRawData = true;
    private Thread mProcessThread = null;
    private String mrawfilename = "/sdcard/Movies/yuvdump.h264";
    private boolean mRuning = true;
    private Queue<YUVPackage> mQueue = null;


    public void Init(){

        if(true == mDumpRawData){
            try {
                mFileOutputStream = new FileOutputStream(mrawfilename);
            } catch (IOException e) {
                System.out.println(e);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        mQueue = new LinkedList<YUVPackage>();
        mProcessThread = new Thread() {
            @Override
            public void run() {
                Processing();
            }
        };
        mProcessThread.setName("StreamingPusherThread");
    }

    public void Start(){
        mRuning = true;
        mProcessThread.start();
    }

    public void Stop(){
        mRuning = false;
//        mProcessThread.stop();

    }

    public void Uninit(){

//        mProcessThread.stop();
        if(true == mDumpRawData) {
            try {
                mFileOutputStream.close();
            } catch (IOException e) {
                System.out.println(e);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        mQueue = null;
    }

    public void InsetData(YUVPackage newPackage){
        mQueue.add(newPackage);
    }

    private void Processing(){

        YUVPackage tmpPackage = null;
        while (mRuning){

            tmpPackage = mQueue.poll();
            if(tmpPackage == null){

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{

                Log.i(TAG, "Processing::size=" + tmpPackage.size+",presentationTimeUs="+
                        tmpPackage.presentationTimeUs+",flags="+tmpPackage.flags);
                DumpRawData(tmpPackage.buffer);
            }

        }

    }

    private void DumpRawData(byte[] outData){

        if(true == mDumpRawData) {
            try {
                mFileOutputStream.write(outData);
                Log.i(TAG, "output data size -- > " + outData.length);
            } catch (IOException ioe) {
                Log.w(TAG, "failed writing debug data to file");
                throw new RuntimeException(ioe);
            }
        }
    }

}
