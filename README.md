# Face Read Clock - 人脸识别考勤系统

基于 JavaFX + OpenCV 实现的人脸识别考勤系统，支持学生信息管理、人脸录入、实时人脸识别考勤等功能。

## 功能特性

- **学生管理**: 添加、修改、删除学生信息
- **人脸录入**: 支持摄像头采集人脸图像并训练识别模型
- **人脸考勤**: 实时识别学生身份并记录考勤
- **数据库存储**: MySQL 数据库持久化存储学生和考勤数据

## 技术栈

| 技术 | 版本 |
|------|------|
| Java | 23 |
| JavaFX | 17.0.6 |
| OpenCV | 4.5.5-1.5.7 |
| MySQL | 8.0.33 |
| Maven | 3.x |

## 项目结构

```
src/
├── main/
│   ├── java/com/face_read_clock/
│   │   ├── Main.java                    # 应用入口
│   │   ├── Controller/                  # FXML 控制器
│   │   │   ├── Main_C.java              # 主界面控制器
│   │   │   ├── AddStu_C.java            # 添加学生控制器
│   │   │   ├── ManageStu_C.java         # 学生管理控制器
│   │   │   ├── UpdateStu_C.java         # 更新学生控制器
│   │   │   ├── FaceRead_C.java          # 人脸识别控制器
│   │   │   └── InputFace_C.java         # 人脸录入控制器
│   │   ├── face/                        # 人脸识别核心模块
│   │   │   ├── FaceMain.java            # 人脸识别主类
│   │   │   ├── face_read.java           # 人脸识别实现
│   │   │   ├── face_data_train.java     # 人脸模型训练
│   │   │   └── face_delete.java         # 人脸数据删除
│   │   ├── mapper/                      # 数据库操作
│   │   │   ├── ControllerMysql.java     # MySQL 连接
│   │   │   ├── insert_t_sql.java        # 插入数据
│   │   │   ├── select_f_sql.java        # 查询数据
│   │   │   ├── update_f_sql.java        # 更新数据
│   │   │   └── delete_f_sql.java        # 删除数据
│   │   ├── pojo/                        # 实体类
│   │   │   ├── stu.java
│   │   │   └── student.java
│   │   └── tools/                       # 工具类
│   │       ├── draw_rect.java          # 绘制矩形
│   │       ├── matToImage.java         # Mat 转 Image
│   │       ├── NameFirst.java          # 姓名转拼音
│   │       └── check_stu_NotRepeat.java # 检查学生重复
│   └── resources/
│       └── com/face_read_clock/
│           └── views/                   # FXML 界面文件
│               ├── main.fxml
│               ├── manage_stu.fxml
│               ├── add_stu.fxml
│               ├── update_stu.fxml
│               ├── face_read.fxml
│               └── input_face.fxml
opencv/
└── opencv_java4100.dll                  # OpenCV 本地库
```

## 快速开始

### 1. 环境要求

- JDK 23+
- Maven 3.6+
- MySQL 8.0+
- Windows 10/11

### 2. 数据库配置

创建数据库 `face_clock`：

```sql
CREATE DATABASE face_clock CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

修改 `src/main/java/com/face_read_clock/mapper/ControllerMysql.java` 中的数据库连接信息：

```java
private static final String URL = "jdbc:mysql://localhost:3306/face_clock";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

### 3. 编译运行

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn clean javafx:run
```

## 使用说明

1. **主界面**: 系统启动后显示主界面，可选择进入学生管理或人脸考勤
2. **添加学生**: 输入学生信息（学号、姓名、班级等），并通过摄像头采集人脸图像
3. **人脸录入**: 选择已有学生，采集多张人脸照片用于训练模型
4. **训练模型**: 点击训练按钮，生成人脸识别模型
5. **人脸考勤**: 开启摄像头，实时识别学生并记录考勤信息

## 注意事项

- 首次使用需先添加学生并录入人脸图像，然后训练模型才能进行识别
- 人脸识别需要充足的光线环境，以确保识别效果
- 确保 OpenCV 本地库 (`opencv_java4100.dll`) 位于正确路径

## 许可证

MIT License
