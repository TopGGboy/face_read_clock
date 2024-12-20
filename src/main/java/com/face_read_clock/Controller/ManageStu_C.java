package com.face_read_clock.Controller;

import com.face_read_clock.Main;
import com.face_read_clock.mapper.delete_f_sql;
import com.face_read_clock.mapper.select_f_sql;
import com.face_read_clock.mapper.update_f_sql;
import com.face_read_clock.pojo.student;
import com.face_read_clock.tools.check_stu_NotRepeat;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.List;

public class ManageStu_C {
    @FXML
    public TableView<student> StudentView;

    @FXML
    public TableColumn<student, String> StuId;

    @FXML
    public TableColumn<student, String> StuName;

    @FXML
    public TableColumn<student, String> StuStatus;

    @FXML
    public TableColumn<student, String> StuClass;

    @FXML
    public TableColumn<student, String> StuSex;

    @FXML
    public TableColumn<student, String> StuClockTime;

    @FXML
    public TableColumn<student, String> StuFace;

    @FXML
    public void initialize() {
        showData();
    }

    @FXML
    public void showData() {
        List<student> students = select_f_sql.select_All_stu();

        StuId.setCellValueFactory(new PropertyValueFactory<>("StuId"));
        StuName.setCellValueFactory(new PropertyValueFactory<>("StuName"));
        StuStatus.setCellValueFactory(new PropertyValueFactory<>("StuStatus"));
        StuClass.setCellValueFactory(new PropertyValueFactory<>("StuClass"));
        StuSex.setCellValueFactory(new PropertyValueFactory<>("StuSex"));
        StuClockTime.setCellValueFactory(new PropertyValueFactory<>("StuClockTime"));
        StuFace.setCellValueFactory(new PropertyValueFactory<>("StuFace"));

        StudentView.setItems(FXCollections.observableList(students));
    }


    @FXML
    //删除学生
    public void delete_stu() {
        student stu = StudentView.getSelectionModel().getSelectedItem();
        //查重
        if (!check_stu_NotRepeat.check(stu)) {
            delete_f_sql.delete_One_stu(stu);
        } else {
            System.out.println("该学生不存在");
        }
        showData();

    }

    @FXML
    //修改学生界面
    public void update_stu() {
        student stu = StudentView.getSelectionModel().getSelectedItem();
        if (stu != null) {
            UpdateStu_C.setStu(stu);
            Main.addView("views/update_stu.fxml", "修改学生");
        }
    }

    @FXML
    //添加学生界面
    public void add_view() {
        Main.addView("views/add_stu.fxml", "添加学生");
    }


    @FXML
    public void return_main() {
        Main.changeView("views/main.fxml", "Face_log_clock");
    }

    @FXML
    //录入人脸
    public void input_face() {
        student stu = StudentView.getSelectionModel().getSelectedItem();
        if (stu != null) {
            int StuID = stu.getID();
            InputFace_C.setStuId(StuID);
        }
        Main.changeView("views/input_face.fxml", "录入人脸");
    }

    public void delete_clock() {
        //创建确认框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认重置");
        alert.setHeaderText(null);
        alert.setContentText("确定要重置打卡状态吗？");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                //用户点击了确认
                set_clock();
                //重新载入
                Main.changeView("views/manage_stu.fxml", "face clock");
            }
        });
    }

    public void set_clock() {
        List<student> students = select_f_sql.select_All_stu();
        for (student stu : students) {
            stu.setStuStatus("未打卡");
            update_f_sql.update_stu(stu);
        }

    }

}