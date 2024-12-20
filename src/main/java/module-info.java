module com.face_read_clock {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;
    requires pinyin4j;
    requires org.python.jython2;
    requires org.bytedeco.opencv;


    opens com.face_read_clock.pojo to javafx.base;
    opens com.face_read_clock.tools to javafx.base;
    opens com.face_read_clock.face to javafx.base;
    opens com.face_read_clock to javafx.fxml;
    opens com.face_read_clock.Controller to javafx.fxml;


    exports com.face_read_clock;
    exports com.face_read_clock.Controller;
    exports com.face_read_clock.pojo;
    exports com.face_read_clock.tools;
    exports com.face_read_clock.face;
}