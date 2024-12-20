//package com.face_read_clock.tools;
//
//import com.face_read_clock.face.FaceMain;
//
//import org.opencv.core.*;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.objdetect.CascadeClassifier;
//
//
//
//public class draw_rect {
//    private static CascadeClassifier FaceRe;
//    private static Mat image;
//    private static boolean have_face;
//
//    //画框
//    public static boolean run(Mat image) {
//        draw_rect.image = image;
//        draw_rect.FaceRe = FaceMain.FaceRe();
//        draw_rect.have_face = false;
//
//        draw();
//        return have_face;
//    }
//
//    //画框
//    public static void draw() {
//        // 创建一个矩形向量来存储检测到的人脸
//        MatOfRect FaceRect = new MatOfRect();
//        // 检测人脸
//        FaceRe.detectMultiScale(image, FaceRect);
//        // 获取检测到的人脸矩形
//        Rect[] facesArray = FaceRect.toArray();
//
//        // 遍历检测到的人脸
//        for (int i = 0; i < facesArray.length; i++) {
//            Rect rect = facesArray[i];
//            // 在图像上绘制矩形框
//            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width,
//                    rect.y + rect.height), new Scalar(0, 255, 0), 2);
//            have_face = true;
//        }
//    }
//}


package com.face_read_clock.tools;


import com.face_read_clock.face.FaceMain;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.global.opencv_imgproc;


public class draw_rect {
    private static CascadeClassifier FaceRe;
    private static Mat image;
    private static boolean have_face;

    //画框
    public static boolean run(Mat image) {
        draw_rect.image = image;
        //加载人脸识别分类器
        draw_rect.FaceRe = FaceMain.FaceRe();

        draw_rect.have_face = false;

        draw();
        return have_face;
    }

    //画框
    public static boolean draw() {
        RectVector faceRect = new RectVector();

        // 检测图像中的人脸
        FaceRe.detectMultiScale(image, faceRect);
        // 如果检测到人脸，设置haveFace为true，并在图像上画框
        if (faceRect.size() > 0) {
            have_face = true;
            for (int i = 0; i < faceRect.size(); i++) {
                Rect rect = faceRect.get(i);
                // 获取矩形框的左上角坐标和右下角坐标
                Point pt1 = new Point(rect.x(), rect.y());
                Point pt2 = new Point(rect.x() + rect.width(), rect.y() + rect.height());
                opencv_imgproc.rectangle(image, pt1, pt2, new Scalar(0.0, 255.0, 0.0, 255.0), 2, 1, 0);
            }
            return true;
        }
        return false;
    }
}


