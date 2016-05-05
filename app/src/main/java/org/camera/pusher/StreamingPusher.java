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
    private int mHandler = 0;

    public boolean Init(String liveurl){
        mHandler = streaminghandler.NewStreamingHandler(liveurl);

        if(0 == mHandler){
            Log.e("TAG", "New Streaming handler fail!");
//            return false;
        }

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

        return true;
    }

    public void SetPPS(byte[] buffer, int length){
        streaminghandler.StreamingHandlerSetPps(mHandler, buffer, length);
    }


    public void SetSPS(byte[] buffer, int length){
        streaminghandler.StreamingHandlerSetSPs(mHandler, buffer, length);
    }

    public void SendPPSandSPS(){
        streaminghandler.StreamingHandlerSendSPSAndPPs(mHandler);
    }

    public void Start(){
        mRuning = true;
        mProcessThread.start();
    }

    public void Stop(){
        mRuning = false;
        try {
            mProcessThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        if(mHandler != 0){
            streaminghandler.DelStreamingHandler(mHandler);
            mHandler = 0;
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

                Log.i(TAG, "Processing::size=" + tmpPackage.size + ",presentationTimeUs=" +
                        tmpPackage.presentationTimeUs + ",flags=" + tmpPackage.flags);
                ProcessRawData(tmpPackage);

            }

        }

    }

    private void ProcessRawData(YUVPackage Package){


        if(mRuning){
            streaminghandler.StreamingHandlerPusherRawData(mHandler, Package.buffer, Package.size,
                    Package.presentationTimeUs,Package.flags);
        }





        if(true == mDumpRawData && mRuning) {
            try {
                mFileOutputStream.write(Package.buffer);
                Log.i(TAG, "output data size -- > " + Package.size);
            } catch (IOException ioe) {
                Log.w(TAG, "failed writing debug data to file");
                throw new RuntimeException(ioe);
            }
        }
    }

}
