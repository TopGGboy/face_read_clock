package com.face_read_clock.face;


import com.face_read_clock.Controller.FaceRead_C;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;


import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// 人脸信息识别类
public class face_read {
    static {
        String opencvPath = System.getProperty("user.dir") + "/opencv/opencv_java4100.dll";
        System.load(opencvPath);
    }

    private static FaceRecognizer faceRecognizer;
    private static CascadeClassifier FaceRe;
    private static Map<Integer, String> face_ID_Map = new HashMap<>();

    public static void main(String[] args) {
        // 加载人脸识别模型
        face_read.FaceRe = new CascadeClassifier(System.getProperty("user.dir") + "/opencv2/haarcascade_frontalface_alt2.xml");
        face_read.faceRecognizer = LBPHFaceRecognizer.create();
        face_read.faceRecognizer.read("src/main/resources/com/face_read_clock/face_img/trainer.yml");

        // 读取待识别的人脸图像
        Mat testImage = opencv_imgcodecs.imread("src/main/resources/com/face_read_clock/face_img/k2347103118/face_1.jpg");
        Mat graytestImage = new Mat();
        opencv_imgproc.cvtColor(testImage, graytestImage, opencv_imgproc.COLOR_BGR2GRAY);

        RectVector faceRect = new RectVector();
        FaceRe.detectMultiScale(graytestImage, faceRect);
        for (int i = 0; i < faceRect.size(); i++) {
            Rect rect = faceRect.get(i);
            Mat faceROI = new Mat(graytestImage, rect);

            int[] label = new int[1];
            double[] confidence = new double[1];

            faceRecognizer.predict(faceROI, label, confidence);

            if (confidence[0] > 50) {
                System.out.println("识别到的标签: " + label[0] + ", 置信度: " + confidence[0]);
                System.out.println("不是一个人");
            } else {
                System.out.println("识别到的标签: " + label[0] + ", 置信度: " + confidence[0]);
            }
        }
    }


    public static List<Object> run(Mat image) {
        // 加载人脸识别模型
        face_read.FaceRe = FaceMain.FaceRe();
        face_read.faceRecognizer = LBPHFaceRecognizer.create();
        face_read.face_ID_Map = FaceRead_C.stuName_stuID;
        face_read.faceRecognizer.read("src/main/resources/com/face_read_clock/face_img/face.yml");

        // 读取待识别的人脸图像
        Mat graytestImage = new Mat();
        opencv_imgproc.cvtColor(image, graytestImage, opencv_imgproc.COLOR_BGR2GRAY);

        RectVector faceRect = new RectVector();
        FaceRe.detectMultiScale(graytestImage, faceRect);

        boolean faceDetected = false;
        List<Object> stuIDs = new ArrayList<>();

        for (int i = 0; i < faceRect.size(); i++) {
            Rect rect = faceRect.get(i);
            Mat faceROI = new Mat(graytestImage, rect);
            int[] label = new int[1];
            double[] confidence = new double[1];

            faceRecognizer.predict(faceROI, label, confidence);

            if (confidence[0] > 80) {
                Point pt1 = new Point(rect.x(), rect.y());
                Point pt2 = new Point(rect.x() + rect.width(), rect.y() + rect.height());
                opencv_imgproc.rectangle(image, pt1, pt2, new Scalar(0.0, 255.0, 0.0, 255.0), 2, 1, 0);

                Point textPoint = new Point(rect.x(), rect.y() - 10);

                String text = "unknown";


                opencv_imgproc.putText(image, text, textPoint, opencv_imgproc.FONT_HERSHEY_SIMPLEX, 0.9, new Scalar(0.0, 255.0, 0.0, 255.0));
//                System.out.println("识别到的标签: " + label[0] + ", 置信度: " + confidence[0]);
//                System.out.println("不是一个人");
            } else {
                Point pt1 = new Point(rect.x(), rect.y());
                Point pt2 = new Point(rect.x() + rect.width(), rect.y() + rect.height());
                opencv_imgproc.rectangle(image, pt1, pt2, new Scalar(0.0, 255.0, 0.0, 255.0), 2, 1, 0);

                Point textPoint = new Point(rect.x(), rect.y() - 10);

                String text = face_ID_Map.get(label[0]);

                opencv_imgproc.putText(image, text, textPoint, opencv_imgproc.FONT_HERSHEY_SIMPLEX, 0.9, new Scalar(0.0, 255.0, 0.0, 255.0));


                stuIDs.add(label[0]);
//                System.out.println("识别到的标签: " + label[0] + ", 置信度: " + confidence[0]);
            }
        }
        return stuIDs;
    }
}
