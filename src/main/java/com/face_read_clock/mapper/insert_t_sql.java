package com.face_read_clock.mapper;

import com.face_read_clock.pojo.student;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class insert_t_sql {
    public static void insert_One_stu(student stu) {
        Connection con = ControllerMysql.connect_stu();
        try {
            if (con != null) {
                String sql = "insert into student (stu_id, stu_name, stu_status, stu_class, stu_sex, stu_face, stu_clock_time) values (?,?,?,?,?,?,?)";
                PreparedStatement pre = con.prepareStatement(sql);
                pre.setString(1, stu.getStuId());
                pre.setString(2, stu.getStuName());
                pre.setString(3, stu.getStuStatus());
                pre.setString(4, stu.getStuClass());
                pre.setString(5, stu.getStuSex());
                pre.setString(6, stu.getStuFace());
                pre.setString(7, stu.getStuClockTime());
                pre.executeUpdate();
                pre.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
