package com.face_read_clock.Controller;

import com.face_read_clock.Main;
import com.face_read_clock.mapper.update_f_sql;
import com.face_read_clock.pojo.student;
import com.face_read_clock.tools.check_stu_NotRepeat;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class UpdateStu_C {
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

    //选中的学生信息
    private static student stu;

    public static void setStu(student stu) {
        UpdateStu_C.stu = stu;
    }


    @FXML
    //初始化
    public void initialize() {
        StuSex.getItems().addAll("男", "女");
        StuStatus.getItems().addAll("已打卡", "未打卡", "迟到");

        StuId.setText(stu.getStuId());
        StuName.setText(stu.getStuName());
        StuStatus.setValue(stu.getStuStatus());
        StuSex.setValue(stu.getStuSex());
        StuFace.setValue(stu.getStuFace());
        StuClass.setText(stu.getStuClass());
        StuClockTime.setText(stu.getStuClockTime());
    }


    public void query() {
        student stu = new student(StuId.getText(), StuName.getText(), StuStatus.getValue(), StuClass.getText(),
                StuSex.getValue(), StuClockTime.getText(), StuFace.getValue());
        //更新学生信息
        //查重
        if (!check_stu_NotRepeat.check(stu)) {
            update_f_sql.update_stu(stu);
        } else {
            System.out.println("该学号不存在");
        }


        //关闭更新学生界面
        Main.close_addview();
        //回到管理学生界面
        Main.changeView("views/manage_stu.fxml", "Face Read Clock");
    }

    public void cancel() {
        Main.close_addview();
    }

    @FXML
    private void showConfirmationDialog() {
        // 创建确认对话框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setHeaderText(null);
        alert.setContentText("确定要删除该人脸吗？");

        // 显示对话框并等待用户响应
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // 用户点击了确认
                delete_face();
            }
        });
    }

    public void delete_face() {
        student stu = new student(StuId.getText(), StuName.getText(), "未打卡", StuClass.getText(),
                StuSex.getValue(), StuClockTime.getText(), "未录入");
        update_f_sql.update_stu(stu);
        StuFace.setValue("未录入");
        StuStatus.setValue("未打卡");
    }
}
