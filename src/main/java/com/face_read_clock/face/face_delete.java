package com.face_read_clock.face;

import java.io.File;

public class face_delete {

    private static int StuID;

    public static void main(String[] args) {
        run(1);
    }

    public static void run(int StuID) {
        face_delete.StuID = StuID;
        delete_face();
        face_data_train.readImagesFromLocal();
    }

    public static void delete_face() {
        String img_dir = "src/main/resources/com/face_read_clock/face_img/face/";
        File directory = new File(img_dir);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String[] parts = fileName.split("_");
                    String id = parts[0];
                    if (id.equals(String.valueOf(StuID))) {
                        file.delete();
                    }
                }
            }
        }
    }
}
