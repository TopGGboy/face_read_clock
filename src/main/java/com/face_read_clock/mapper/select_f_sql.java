package com.face_read_clock.mapper;

import com.face_read_clock.pojo.student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class select_f_sql {

    public static List<student> select_All_stu() {
        Connection con = ControllerMysql.connect_stu();
        List<student> result = null;
        try {
            if (con != null) {
                Statement statement = con.createStatement();
                String sql = "select * from student";
                ResultSet re = statement.executeQuery(sql);

                //将查询结果封装到student对象里
                result = new ArrayList<>();
                if (re != null) {
                    result = Re_to_studentsList.student_main(re);
                }
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
