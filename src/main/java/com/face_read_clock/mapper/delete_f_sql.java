package com.face_read_clock.mapper;

import com.face_read_clock.pojo.student;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class delete_f_sql {
    public static void delete_One_stu(student stu) {
        Connection con = ControllerMysql.connect_stu();
        int result = 0;
        try {
            if (con != null) {
                String sql = "delete from student where stu_id = ?";
                PreparedStatement pre = con.prepareStatement(sql);
                pre.setString(1, stu.getStuId());
                pre.executeUpdate();
                pre.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
