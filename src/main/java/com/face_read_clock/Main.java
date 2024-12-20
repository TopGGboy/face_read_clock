package com.face_read_clock;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static Stage main_stage;
    private static Stage add_stage;

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws IOException {
        Main.main_stage = stage;
//        changeView("views/manage_stu.fxml", "Face Read Clock");
//        changeView("views/face_read.fxml", "Face Read Clock");
        changeView("views/main.fxml", "Face Read Clock");
//        changeView("views/TEST.fxml", "Face Read Clock");
        stage.show();
    }

    public static void changeView(String fxml, String view_name) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (root != null) {
            main_stage.setScene(new Scene(root));
            main_stage.setTitle(view_name);
            main_stage.setResizable(false);
        }
    }

    public static void addView(String fxml, String view_name) {
        Main.add_stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
            add_stage.setScene(new Scene(root));
            add_stage.setTitle(view_name);
            add_stage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        add_stage.show();
    }

    public static void close_addview() {
        add_stage.close();
    }

}