package com.face_read_clock.face;

import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;


import javax.swing.text.StyledEditorKit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


//对人脸数据进行训练
public class face_data_train {
    private static CascadeClassifier FaceRe;
    private static List<Mat> faceImages;
    private static int StuID;
    private static String modelFilePath = "src/main/resources/com/face_read_clock/face_img/face.yml";
    private static String imagesDirPath = "src/main/resources/com/face_read_clock/face_img/face/";
    private static Mat labels;

    //传入的人脸，是已经裁剪的人脸图片
    public static Boolean run(List<Mat> faceImages, int StuId) {
        face_data_train.FaceRe = FaceMain.FaceRe();
        face_data_train.faceImages = faceImages;
        face_data_train.StuID = StuId;

        //通过StuID来保存人脸到本地
        saveface_to_file();

        trainFaces(true);
        return true;
    }

    private static void saveface_to_file() {
        for (int i = 0; i < faceImages.size(); i++) {
            String toFile = "src/main/resources/com/face_read_clock/face_img/face/" + StuID + "_" + i + ".jpg";
            Mat image = faceImages.get(i);
            RectVector faceRect = new RectVector();
            FaceRe.detectMultiScale(image, faceRect);
            // 如果检测到人脸，保存图像并重新赋值给图像
            if (faceRect.size() > 0) {
                Rect rect = faceRect.get(0);
                Mat face = new Mat(image, rect);
                opencv_imgcodecs.imwrite(toFile, face);
                faceImages.set(i, face);
            }
        }
    }


    public static void readImagesFromLocal() {
        faceImages = new ArrayList<>();
        labels = new Mat();

        File dir = new File(imagesDirPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".jpg"));

        if (files != null) {
            for (File file : files) {
                Mat image = opencv_imgcodecs.imread(file.getAbsolutePath());
                if (image.empty()) {
                    System.out.println("无法读取图片: " + file.getAbsolutePath());
                    continue;
                }

                // 假设文件名格式为 StuID_index.jpg
                String[] parts = file.getName().split("_");
                if (parts.length == 2) {
                    int stuID = Integer.parseInt(parts[0]);
                    labels.push_back(new Mat(1, 1, opencv_core.CV_32SC1, new Scalar(stuID)));
                    faceImages.add(image);
                }
            }
        }

        trainFaces(false);
    }

    public static void trainFaces(boolean isNew) {
        FaceRecognizer recognizer = LBPHFaceRecognizer.create();
        try {
            recognizer.read(modelFilePath);
        } catch (Exception e) {
            System.out.println("未找到模型文件，正在重新训练...");
        }

        MatVector croppedFaces = new MatVector();
        Mat labels_ = new Mat();

        for (Mat image : faceImages) {
            Mat grayImage = new Mat();
            opencv_imgproc.cvtColor(image, grayImage, opencv_imgproc.COLOR_BGR2GRAY);
            croppedFaces.push_back(grayImage);
        }


        if (!isNew) {
            labels_ = labels;
            recognizer.train(croppedFaces, labels_);
        } else {
            for (int i = 0; i < croppedFaces.size(); i++) {
                labels_.push_back(new Mat(1, 1, opencv_core.CV_32SC1, new Scalar(StuID)));
            }
            recognizer.update(croppedFaces, labels_);
        }

        // 保存更新后的模型
        recognizer.write(modelFilePath);
    }

}
