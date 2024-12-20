package com.face_read_clock.tools;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.bytedeco.opencv.opencv_core.Mat;


public class matToImage {
    // 将摄像头资源转化成javafx可以用的图片
    public static Image run(Mat frame) {
        if (frame.empty()) {
            return null;
        }

        int width = frame.cols();
        int height = frame.rows();
        int channels = frame.channels();
        // 根据图像数据类型和大小创建用于存储数据的数组
        byte[] sourcePixels = new byte[(int) (frame.total() * frame.elemSize())];
        frame.data().get(sourcePixels);

        // Create a WritableImage
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();

        // Convert BGR to BGRA and write to the image
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int index = row * width * channels + col * channels;
                byte blue = sourcePixels[index];
                byte green = sourcePixels[index + 1];
                byte red = sourcePixels[index + 2];
                // 将颜色值转换为适合JavaFX的Color类型，并处理数据类型范围（确保是0-255）
                pixelWriter.setColor(col, row, Color.rgb(red & 0xFF, green & 0xFF, blue & 0xFF));
            }
        }
        return image;
    }
}