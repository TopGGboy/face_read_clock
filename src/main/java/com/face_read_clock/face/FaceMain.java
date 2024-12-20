package com.face_read_clock.face;


import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

public class FaceMain {

    static {
        String opencvPath = System.getProperty("user.dir") + "/opencv/opencv_java4100.dll";
        System.load(opencvPath);
    }

    public static VideoCapture open_cap() {
        //开启摄像头
        VideoCapture cap = new VideoCapture(0);
        return cap;
    }

    public static CascadeClassifier FaceRe() {
        //加载人脸识别分类器
        String FaceRe_Path = System.getProperty("user.dir") + "/opencv/haarcascade_frontalface_alt2.xml";
        return new CascadeClassifier(FaceRe_Path);
    }

}

