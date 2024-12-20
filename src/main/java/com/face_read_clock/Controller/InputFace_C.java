package com.face_read_clock.Controller;


import com.face_read_clock.Main;
import com.face_read_clock.face.FaceMain;
import com.face_read_clock.face.face_data_train;
import com.face_read_clock.mapper.select_f_sql;
import com.face_read_clock.mapper.update_f_sql;
import com.face_read_clock.pojo.student;
import com.face_read_clock.tools.draw_rect;
import com.face_read_clock.tools.matToImage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InputFace_C {
    @FXML
    public ImageView ImgView;
    private VideoCapture cap;
    private boolean begin_input;
    private ScheduledExecutorService timer;
    private int fps = 0;
    private int number = 0;
    private static int stuID;
    private static List<Mat> faces = new ArrayList<>();
    private static ExecutorService inputServies = Executors.newSingleThreadExecutor();
    public static List<student> students;


    @FXML
    public void initialize() {
        students = select_f_sql.select_All_stu();

        cap = FaceMain.open_cap();
        if (!cap.isOpened()) {
            System.out.println("摄像头打开失败");
        }
        // 创建一个定时任务来捕获视频帧并更新ImageView
        timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(() -> {
            Mat frame = new Mat();
            cap.read(frame);

            if (begin_input) {
                Boolean have_face = draw_rect.run(frame);
                inputServies.execute(() -> input_Control(frame, stuID, have_face));
            }

            // 将OpenCV的Mat对象转换为JavaFX的Image对象
            if (!frame.empty()) {
                Image image = matToImage.run(frame);
                Platform.runLater(() -> ImgView.setImage(image));
            }
        }, 0, 33, TimeUnit.MILLISECONDS); //每秒30针
    }


    public void input_Control(Mat image, int stuID, Boolean have_face) {
        if (fps % 20 == 0) {
            if (have_face) {
                faces.add(image);
                number++;
            }
        }
        fps++;
        System.out.println(number);

        boolean train_ok = false;
        if (number == 5) {
            train_ok = face_data_train.run(faces, stuID);
            number = 0;
            begin_input = false;
        }


        if (train_ok) {
            update_stu(stuID);
        }
    }

    public static void update_stu(Integer stuID) {
        for (student s : students) {
            if (s.getID() == stuID) {
                student stu = new student(s.getStuId(), s.getStuName(), s.getStuStatus(), s.getStuClass(), s.getStuSex(), s.getStuClockTime(), "已录入");
                update_f_sql.update_stu(stu);

            }
        }
    }


    @FXML
    public void begin_input() {
        begin_input = true;
        System.out.println("hello");
    }

    public static void setStuId(int stuId) {
        stuID = stuId;
    }

    @FXML
    public void return_Ma() {
        cap.release();
        inputServies.shutdown();
        Main.changeView("views/manage_stu.fxml", "Face_log_clock");
    }
}

