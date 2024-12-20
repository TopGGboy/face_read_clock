package com.face_read_clock.tools;

import com.face_read_clock.mapper.select_f_sql;
import com.face_read_clock.pojo.student;

import java.util.List;

//检测学生是否重复
public class check_stu_NotRepeat {
    public static boolean check(student stu) {
        List<student> students = select_f_sql.select_All_stu();
        for (student s : students) {
            if (s.equals(stu)) {
                return false; //重复的
            }
        }
        return true; //不重复
    }
}
