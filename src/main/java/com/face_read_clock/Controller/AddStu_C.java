package com.face_read_clock.Controller;

import com.face_read_clock.Main;
import com.face_read_clock.mapper.insert_t_sql;
import com.face_read_clock.pojo.student;
import com.face_read_clock.tools.check_stu_NotRepeat;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddStu_C {

    @FXML
    public TextField StuId;
    @FXML
    public TextField StuName;
    @FXML
    public ChoiceBox<String> StuStatus;
    @FXML
    public ChoiceBox<String> StuSex;
    @FXML
    public ChoiceBox<String> StuFace;
    @FXML
    public TextField StuClass;
    @FXML
    public TextField StuClockTime;

    static {
        String opencvPath = System.getProperty("user.dir") + "/opencv/opencv_java4100.dll";
        System.load(opencvPath);
    }

    @FXML
    public void initialize() {
        StuStatus.getItems().addAll("未打卡");
        StuSex.getItems().addAll("男", "女");
        StuFace.getItems().addAll("未录入");

        StuStatus.setValue("未打卡");
        StuSex.setValue("男");
        StuFace.setValue("未录入");
    }

    @FXML
    //添加学生信息到数据库
    public void AddStuToSql() {
        student stu = new student(StuId.getText(), StuName.getText(), StuStatus.getValue(), StuClass.getText(),
                StuSex.getValue(), StuClockTime.getText(), StuFace.getValue());
        //查重
        if (check_stu_NotRepeat.check(stu)) {
            insert_t_sql.insert_One_stu(stu);
            //关闭添加学生信息界面
            Main.close_addview();
            //回到管理学生界面
            Main.changeView("views/manage_stu.fxml", "Face Read Clock");
        } else {
            System.out.println("该学号已存在");
        }
    }


    @FXML
    //取消添加学生信息
    public void cancel() {
        Main.close_addview();
    }
}
