plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjfx:javafx-controls:25")
    implementation("org.openjfx:javafx-fxml:25")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    // Thay bằng class Main chứa hàm main() của bạn
    mainClass.set("screens.MainMenuScreen")
}

javafx {
    version = "22" // hoặc "22" nếu bạn dùng JDK 22
    modules = listOf("javafx.controls", "javafx.fxml")
}

tasks.test {
    useJUnitPlatform()
}

