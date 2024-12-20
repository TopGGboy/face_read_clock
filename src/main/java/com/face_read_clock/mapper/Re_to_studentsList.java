package com.face_read_clock.mapper;

import com.face_read_clock.pojo.student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Re_to_studentsList {
    public static List<student> student_main(ResultSet resultSet) {
        List<student> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String stu_id = resultSet.getString("stu_id");
                System.out.println(stu_id);
                String stu_name = resultSet.getString("stu_name");
                String stu_status = resultSet.getString("stu_status");
                String stu_class = resultSet.getString("stu_class");
                String stu_sex = resultSet.getString("stu_sex");
                String stu_clock_time = resultSet.getString("stu_clock_time");
                String stu_face = resultSet.getString("stu_face");


                student s = new student(ID, stu_id, stu_name, stu_status, stu_class, stu_sex, stu_clock_time, stu_face);
                result.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
