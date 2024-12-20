package com.face_read_clock.mapper;

import java.sql.*;


public class ControllerMysql {
    //查询所有学生信息
    public static Connection connect_stu() {
        String url = "jdbc:mysql://localhost:3306/student_info";
        String user = "root";
        String password = "root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                return connection;
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
