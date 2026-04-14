# Face Read Clock - 人脸识别考勤系统

<div align="center">

[![Java](https://img.shields.io/badge/Java-23-blue.svg)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-17.0.6-green.svg)](https://openjfx.io/)
[![OpenCV](https://img.shields.io/badge/OpenCV-4.5.5-orange.svg)](https://opencv.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**基于 JavaFX + OpenCV 实现的企业级人脸识别考勤系统**

</div>

---

## 📋 目录

- [项目简介](#项目简介)
- [功能特性](#功能特性)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [快速开始](#快速开始)
- [使用指南](#使用指南)
- [数据库设计](#数据库设计)
- [注意事项](#注意事项)
- [常见问题](#常见问题)

---

## 🎯 项目简介

Face Read Clock 是一款轻量级的人脸识别考勤系统，采用 JavaFX 构建现代化桌面界面，结合 OpenCV 实现实时人脸检测与识别。系统支持学生信息管理、人脸图像采集、模型训练和自动化考勤打卡。

### 核心优势

- 🖥️ **跨平台桌面应用** - 基于 JavaFX，支持 Windows、macOS、Linux
- 👤 **实时人脸识别** - 利用 LBPH 算法实现快速准确的人脸识别
- 📊 **直观的数据管理** - TableView 展示学生和考勤信息
- 🔧 **模块化设计** - 清晰的 MVC 架构，易于扩展维护

---

## ✨ 功能特性

| 模块 | 功能描述 |
|------|----------|
| **学生管理** | 添加、修改、删除学生信息（学号、姓名、班级、性别等） |
| **人脸录入** | 通过摄像头采集学生人脸图像，支持批量采集 |
| **模型训练** | 自动训练 LBPH 人脸识别模型，支持增量更新 |
| **人脸考勤** | 实时视频流人脸检测，自动识别学生身份并记录打卡 |
| **打卡管理** | 重置打卡状态、查看打卡历史记录 |
| **数据持久化** | MySQL 数据库存储学生和考勤数据 |

---

## 🛠️ 技术栈

### 核心技术

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 23 | 应用开发语言 |
| JavaFX | 17.0.6 | 桌面应用 UI 框架 |
| OpenCV | 4.5.5-1.5.7 | 图像处理与人脸识别 (bytedeco) |
| MySQL | 8.0+ | 关系型数据库 |
| Maven | 3.6+ | 项目构建工具 |

### 关键依赖

```xml
<!-- JavaFX -->
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>17.0.6</version>
</dependency>

<!-- OpenCV 人脸识别 -->
<dependency>
    <groupId>org.bytedeco</groupId>
    <artifactId>opencv</artifactId>
    <version>4.5.5-1.5.7</version>
</dependency>

<!-- MySQL 数据库 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>

<!-- 中文转拼音 -->
<dependency>
    <groupId>com.belerweb</groupId>
    <artifactId>pinyin4j</artifactId>
    <version>2.5.1</version>
</dependency>
```

---

## 📁 项目结构

```
face_read_clock/
├── src/
│   └── main/
│       ├── java/com/face_read_clock/
│       │   ├── Main.java                    # 应用入口
│       │   │
│       │   ├── Controller/                  # 🎮 FXML 控制器层
│       │   │   ├── Main_C.java              # 主界面控制器
│       │   │   ├── AddStu_C.java            # 添加学生控制器
│       │   │   ├── ManageStu_C.java         # 学生管理控制器
│       │   │   ├── UpdateStu_C.java         # 修改学生控制器
│       │   │   ├── FaceRead_C.java         # 人脸考勤控制器
│       │   │   └── InputFace_C.java        # 人脸录入控制器
│       │   │
│       │   ├── face/                        # 🔍 人脸识别核心模块
│       │   │   ├── FaceMain.java            # 人脸识别主类 (级联分类器加载)
│       │   │   ├── face_read.java           # 人脸检测与识别
│       │   │   ├── face_data_train.java     # LBPH 模型训练
│       │   │   └── face_delete.java         # 人脸数据删除
│       │   │
│       │   ├── mapper/                      # 🗄️ 数据访问层
│       │   │   ├── ControllerMysql.java     # MySQL 连接管理
│       │   │   ├── insert_t_sql.java        # 插入数据
│       │   │   ├── select_f_sql.java        # 查询数据
│       │   │   ├── update_f_sql.java        # 更新数据
│       │   │   ├── delete_f_sql.java        # 删除数据
│       │   │   └── Re_to_studentsList.java  # 结果集转换
│       │   │
│       │   ├── pojo/                        # 📦 实体类
│       │   │   ├── stu.java                 # 学生实体
│       │   │   └── student.java             # 学生实体 (带 UI 控件)
│       │   │
│       │   └── tools/                       # 🔧 工具类
│       │       ├── draw_rect.java          # 绘制人脸框
│       │       ├── matToImage.java         # Mat → JavaFX Image 转换
│       │       ├── NameFirst.java          # 姓名转拼音首字母
│       │       └── check_stu_NotRepeat.java # 学生查重
│       │
│       └── resources/
│           └── com/face_read_clock/
│               ├── views/                   # 📄 FXML 界面文件
│               │   ├── main.fxml           # 主界面
│               │   ├── manage_stu.fxml     # 学生管理界面
│               │   ├── add_stu.fxml        # 添加学生界面
│               │   ├── update_stu.fxml     # 修改学生界面
│               │   ├── face_read.fxml      # 人脸考勤界面
│               │   └── input_face.fxml     # 人脸录入界面
│               │
│               ├── css/                      # 🎨 样式文件
│               │   └── test.css
│               │
│               ├── face_img/                 # 📷 人脸图像数据
│               │   ├── face/                # 原始人脸图像
│               │   ├── face.yml            # LBPH 训练模型
│               │   └── trainer.yml         # 备用模型
│               │
│               └── fonts/                    # 🔤 字体文件
│
├── opencv/
│   ├── opencv_java4100.dll                  # OpenCV Windows 原生库
│   ├── opencv-454.jar                       # OpenCV Java 绑定
│   └── opencv-454.jar.pack.gz              # 打包文件
│
├── pom.xml                                  # Maven 配置文件
├── mvnw / mvnw.cmd                         # Maven Wrapper 脚本
└── README.md                               # 项目文档
```

---

## 🚀 快速开始

### 环境要求

| 环境 | 要求 |
|------|------|
| JDK | 23+ (需要启用预览特性) |
| Maven | 3.6+ |
| MySQL | 8.0+ |
| 摄像头 | 支持 USB 摄像头 |
| 系统 | Windows 10/11 (64-bit) |

### 1. 克隆项目

```bash
git clone https://github.com/yourusername/face_read_clock.git
cd face_read_clock
```

### 2. 配置数据库

创建数据库 `student_info`：

```sql
CREATE DATABASE student_info CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

修改 `src/main/java/com/face_read_clock/mapper/ControllerMysql.java` 中的连接配置：

```java
private static final String URL = "jdbc:mysql://localhost:3306/student_info";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

### 3. 创建数据表

```sql
USE student_info;

CREATE TABLE IF NOT EXISTS stu_info (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stu_id VARCHAR(50) UNIQUE NOT NULL COMMENT '学号',
    stu_name VARCHAR(100) NOT NULL COMMENT '姓名',
    stu_class VARCHAR(100) COMMENT '班级',
    stu_sex VARCHAR(10) COMMENT '性别',
    stu_clock_time VARCHAR(50) COMMENT '打卡时间',
    stu_status VARCHAR(20) DEFAULT '未打卡' COMMENT '打卡状态',
    stu_face VARCHAR(10) DEFAULT '0' COMMENT '人脸录入状态'
);
```

### 4. 编译运行

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn clean javafx:run
```

或使用 Maven Wrapper：

```bash
# Windows
.\mvnw.cmd clean javafx:run

# Linux/macOS
./mvnw clean javafx:run
```

---

## 📖 使用指南

### 业务流程

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  添加学生   │───▶│  人脸录入   │───▶│  训练模型   │───▶│  人脸考勤   │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘
      │                  │                  │                  │
      ▼                  ▼                  ▼                  ▼
  输入学生信息      采集人脸图像      生成识别模型        实时识别打卡
```

### 功能操作

#### 1️⃣ 添加学生

1. 进入 **学生管理** 界面
2. 点击 **添加学生** 按钮
3. 填写学生信息（学号、姓名、班级、性别）
4. 点击保存

#### 2️⃣ 人脸录入

1. 在学生列表中找到目标学生
2. 点击 **录入人脸** 按钮
3. 对准摄像头，确保面部清晰
4. 系统自动采集多张人脸图像（建议采集 10-20 张）
5. 点击完成

#### 3️⃣ 训练模型

> 人脸录入后，系统会自动进行模型训练。如需手动训练，修改 `FaceMain.java` 中的训练逻辑。

#### 4️⃣ 人脸考勤

1. 进入 **人脸考勤** 界面
2. 确保摄像头正常工作
3. 学生面对摄像头，系统自动检测人脸
4. 识别成功后，显示打卡成功提示
5. 打卡状态自动更新到数据库

#### 5️⃣ 管理打卡

- **重置打卡**：清除所有学生的打卡状态
- **修改学生**：更新学生信息
- **删除学生**：删除学生及其人脸数据

---

## 🗄️ 数据库设计

### 表结构：stu_info

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 主键，自增 |
| stu_id | VARCHAR(50) | 学号，唯一 |
| stu_name | VARCHAR(100) | 姓名 |
| stu_class | VARCHAR(100) | 班级 |
| stu_sex | VARCHAR(10) | 性别 |
| stu_clock_time | VARCHAR(50) | 打卡时间 |
| stu_status | VARCHAR(20) | 打卡状态（未打卡/已打卡） |
| stu_face | VARCHAR(10) | 人脸录入状态 |

---

## ⚠️ 注意事项

1. **首次使用**
   - 必须先添加学生并录入人脸图像
   - 录入完成后系统会自动训练模型
   - 训练完成后才能进行人脸考勤

2. **环境要求**
   - 人脸识别需要充足的光线环境
   - 建议正面面对摄像头，避免侧脸或遮挡
   - 确保 OpenCV 本地库 (`opencv_java4100.dll`) 位于正确路径

3. **性能优化**
   - 摄像头帧率设置为约 17 FPS (60ms/帧)
   - 人脸识别置信度阈值为 60
   - 连续识别 10 次确认同一人脸才判定为打卡成功

4. **数据备份**
   - 定期备份 MySQL 数据库
   - 人脸图像和模型文件存储在 `resources/face_img/` 目录

---

## ❓ 常见问题

### Q1: 编译报错 "cannot find symbol"

确保 JDK 版本为 23+，并启用预览特性：
```xml
<compilerArgs>
    <arg>--enable-preview</arg>
</compilerArgs>
```

### Q2: 摄像头无法打开

1. 检查摄像头是否正常连接
2. 确认其他程序未占用摄像头
3. 检查 OpenCV 驱动是否正确加载

### Q3: 人脸识别准确率低

1. 增加人脸录入数量（建议 15-20 张）
2. 确保录入时光线均匀
3. 采集多种角度的人脸图像

### Q4: 模型训练失败

1. 确保 `face_img/face/` 目录存在
2. 检查人脸图像格式（需为 JPG）
3. 确保 OpenCV 配置正确

---

## 📝 项目截图

> 暂无截图，请运行项目后自行截图添加到此处。

---

## 📜 许可证

本项目采用 [MIT License](LICENSE) 开源许可证。

---

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

---

<div align="center">

**如果这个项目对你有帮助，请点个 Star ⭐**

</div>
