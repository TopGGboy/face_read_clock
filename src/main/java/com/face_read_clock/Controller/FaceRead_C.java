package com.face_read_clock.Controller;

import com.face_read_clock.Main;
import com.face_read_clock.face.FaceMain;
import com.face_read_clock.face.face_read;

import com.face_read_clock.mapper.select_f_sql;
import com.face_read_clock.mapper.update_f_sql;
import com.face_read_clock.pojo.student;

import com.face_read_clock.tools.NameFirst;
import com.face_read_clock.tools.matToImage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.*;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FaceRead_C {
    @FXML
    public ImageView ImgView;
    private VideoCapture cap;
    private ScheduledExecutorService timer;
    private ExecutorService faceClockExecutor = Executors.newSingleThreadExecutor();
    private static int number = 0;    //计时器
    public static List<student> students;
    public static Map<Integer, Integer> map = new HashMap<>();  //记录识别到的人脸
    public static List<Integer> clocked_StuID = new ArrayList<>(); //记录已经打卡过的人脸ID
    public static Map<Integer, String> stuName_stuID = new HashMap<>();  //记录识别到的人脸


    public void init_stuName() {
        students = select_f_sql.select_All_stu();
        for (student s : students) {
            if (Objects.equals(s.getStuStatus(), "已打卡")) {
                clocked_StuID.add(s.getID());
            }
            String StuName = NameFirst.run(s.getStuName());
            stuName_stuID.put(s.getID(), StuName);
        }
    }

    @FXML
    public void initialize() {
        init_stuName();
        cap = FaceMain.open_cap();
        // 创建一个定时任务来捕获视频帧并更新ImageView
        timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(() -> {
            Mat frame = new Mat();
            cap.read(frame);

            List<Object> StuID = face_read.run(frame);
            faceClockExecutor.execute(() -> face_clock(StuID));


            // 将OpenCV的Mat对象转换为JavaFX的Image对象
            if (!frame.empty()) {
                Image image = matToImage.run(frame);
                Platform.runLater(() -> ImgView.setImage(image));
            }
        }, 0, 60, TimeUnit.MILLISECONDS); //每秒30针
    }


    public static void show_clock_info(int stuID) {
        String Name = null;
        for (student stu : students) {
            if (stu.getID() == stuID) {
                Name = stu.getStuName();
            }
        }

        String finalName = Name;
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("打卡成功");
            alert.setHeaderText(null);
            alert.setContentText("学生" + finalName + "打卡成功");

            alert.showAndWait();
        });
    }


    public static void face_clock(List<Object> StuID) {
        number++;
        for (Object s : StuID) {
            if (map.containsKey(s)) {
                map.put((Integer) s, map.get(s) + 1);
            } else {
                map.put((Integer) s, 1);
            }
        }
        List<Integer> toRemove = new ArrayList<>(); // 用于收集需要删除的键
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= 10 && !clocked_StuID.contains(entry.getKey())) {
                number = 50;
                //修改数据库
                update_stu(entry.getKey());
//                System.out.println("学生" + entry.getKey() + "打卡成功");
                show_clock_info(entry.getKey());
                clocked_StuID.add(entry.getKey());
                System.out.println(StuID);
                toRemove.add(entry.getKey()); // 将需要删除的键添加到列表中
            }
        }
        // 在遍历结束后删除收集到的键
        for (Integer key : toRemove) {
            map.remove(key);
        }
        if (number == 50) {
            map.clear();
            number = 0;
        }
    }

    public static void update_stu(Integer StuID) {
        for (student s : students) {
            if (s.getID() == StuID) {
                student stu = new student(s.getStuId(), s.getStuName(), "已打卡", s.getStuClass(), s.getStuSex(), s.getStuClockTime(), s.getStuFace());
                update_f_sql.update_stu(stu);
            }
        }
    }


    @FXML
    public void return_Main() {
        cap.release();
        faceClockExecutor.shutdown();
        Main.changeView("views/main.fxml", "Face_log_clock");
    }
}
