# 🎮 Arkanoid JavaFX

Một phiên bản **Arkanoid/Breakout** được xây dựng bằng **JavaFX**, bao
gồm hiệu ứng mượt mà, nhiều màn chơi, hệ thống điểm cao, âm thanh, hoạt
ảnh và hệ thống vật lý bóng--thanh đỡ.

## 🚀 Tính năng nổi bật

### 🧱 Gameplay

-   Điều khiển thanh đỡ bằng **phím ← →**
-   Nhấn **SPACE** để bắn bóng
-   Hệ thống va chạm chính xác giữa:
    -   Bóng
    -   Gạch (Brick)
    -   Thanh đỡ (Paddle)
    -   Item rơi xuống
-   Các loại gạch khác nhau (BrickType)
-   Item hỗ trợ: Fast, Expand, Slow, Damage Boost,...

### 🎬 Hiệu ứng -- Animation

-   Hiệu ứng phá gạch (*BrickParticle*)
-   Đuôi bóng (*BallTrailEffect*)
-   Hiệu ứng khi item xuất hiện/rơi (*ItemFast, ItemDeath,
    BlinkingEffect*)
-   Fade/Scale/Slide transitions cho UI

### 🖥️ Giao diện (UI)

-   Menu chính với video nền
-   Màn lựa chọn level
-   Màn hướng dẫn (How to play)
-   Màn cài đặt
-   Màn Pause game
-   Màn End Game
-   Hiệu ứng mở popup bằng Fade + Scale + Slide

### 🏆 Bảng xếp hạng (High Score)

-   3 chế độ: **Easy**, **Medium**, **Hard**
-   Mỗi chế độ có 10 điểm cao nhất
-   Tự động đọc/ghi từ file `HighScores.txt`
-   Hiển thị theo 3 cột dọc trong UI

## 📁 Cấu trúc thư mục

    src/
     └── java/
         ├── animation/
         ├── level/
         ├── manager/
         ├── object/
         ├── org.example/
         ├── Ranking/
         ├── render/
         └── screens/

    resources/
     ├── assets/
     ├── style.css
     ├── level_1.csv
     ├── level_2.csv
     └── level_3.csv

## 🏗️ Cách chạy trò chơi

### 1. Yêu cầu

-   Java **17+**
-   JavaFX **17+**

### 2. Chạy bằng IntelliJ

    Run → org.example.Main

### 3. Chạy qua terminal

    java --module-path "path_to_javafx/lib" --add-modules javafx.controls,javafx.media -jar Arkanoid.jar

## 📸 Video Demo
🎥 [Xem video demo trên Google Drive](https://drive.google.com/file/d/1nu0aJ7P8pmfXgFv9iBrMqVYVacayxamd/view?usp=sharing)

## 👥 Lập trình viên
- Lê Duy Hảo
- Bùi Thị Thanh Hường
- Đỗ Thị Dung
- Nguyễn Tuấn Anh
