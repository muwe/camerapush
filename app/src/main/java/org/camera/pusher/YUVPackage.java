package org.camera.pusher;

/**
 * Created by aiven on 16/4/27.
 */
public class YUVPackage {

    public byte[] buffer;
    public int size;
    public long presentationTimeUs;
    public int flags;


    public void YUVPackage(){
        size = 0;
        presentationTimeUs = 0;
        flags = 0;
        buffer = null;
    }

    public void set(
            byte[] newbuffer, int newSize, long newTimeUs, int newFlags) {
        buffer = newbuffer;
        size = newSize;
        presentationTimeUs = newTimeUs;
        flags = newFlags;
    }



}
