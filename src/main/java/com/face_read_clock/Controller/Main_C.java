package com.face_read_clock.Controller;


import com.face_read_clock.Main;
import com.face_read_clock.mapper.select_f_sql;
import com.face_read_clock.pojo.student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.List;

public class Main_C {
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

        StudentView.setItems(FXCollections.observableList(students));
    }


    public void Update_data() {
        showData();
    }

    public void ManageStu() {
        Main.changeView("views/manage_stu.fxml", "Manage Student");
    }

    public void Begin_Clock() {
        Main.changeView("views/face_read.fxml", "Clock");
    }
}