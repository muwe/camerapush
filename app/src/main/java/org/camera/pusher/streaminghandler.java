package org.camera.pusher;

/**
 * Created by aiven on 16/5/1.
 */
public class streaminghandler {

    /** Native methods, implemented in jni folder */
    public static native int NewStreamingHandler(String liveurl);
    //    public static native boolean StreamingHandlerInit(int object, byte[] HeadData);
//    public static native void StreamingHandlerSetSPs(int object, byte[] Data, int length);
//    public static native void StreamingHandlerSetPps(int object, byte[] Data, int length);
//    public static native void StreamingHandlerSendSPSAndPPs(int object);
//    public static native void StreamingHandlerPusherRawData(int object, byte[] Data, int length, int ts, int flag);
//    //    public static native float PitchAnalyzerGetPitch(int object, long StartTime, long Duration);
//    //    public static native int PitchAnalyzerGetFrequency(int object, long StartTime, long Duration);
//    public static native void DelStreamingHandler(int object);

    static {
        System.loadLibrary("rtmp");
        System.loadLibrary("StreamingPusher");
    }
}
