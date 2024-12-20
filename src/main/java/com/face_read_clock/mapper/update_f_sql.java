package com.face_read_clock.mapper;

import com.face_read_clock.pojo.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class update_f_sql {
    public static void update_stu(student stu) {
        Connection con = ControllerMysql.connect_stu();
        try {
            if (con != null) {
                String sql = "update student set  stu_name = ?, stu_status = ?, stu_class = ?, stu_sex = ?, stu_clock_time = ?, stu_face = ? where stu_id = ?";
                PreparedStatement pre = con.prepareStatement(sql);
                pre.setString(1, stu.getStuName());
                pre.setString(2, stu.getStuStatus());
                pre.setString(3, stu.getStuClass());
                pre.setString(4, stu.getStuSex());
                pre.setString(5, stu.getStuClockTime());
                pre.setString(6, stu.getStuFace());
                pre.setString(7, stu.getStuId());
                pre.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
