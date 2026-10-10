# JDK 25 特性实验项目

这是一个用于练习 Java 语言特性和标准库 API 的小型项目，使用 JDK 25 与 Gradle Wrapper 构建。

## 环境要求

- JDK 25
- 无需预先安装 Gradle；项目包含 Gradle Wrapper

## 示例内容

当前示例分布在两个源文件中：

- `AnonymousClassesToLambdas.java` 使用 `this::save` 将实例方法注册为按钮动作监听器，并展示紧凑源文件形式。
- `com.example.jdk25.App` 汇集了模式匹配、记录模式、sealed 类型、穷尽 `switch`、字符串 `switch`、集合操作及 Stream API 示例；入口使用实例 `main` 方法。

这些代码用于演示和实验，不构成完整的 UI 或业务实现。测试任务已配置为使用 JUnit Platform。

## 构建和运行

在 Windows PowerShell 中执行：

```powershell
.\gradlew.bat --version
.\gradlew.bat test
.\gradlew.bat run
```

在 macOS 或 Linux 中执行：

```shell
./gradlew --version
./gradlew test
./gradlew run
```

`test` 任务通过 JUnit Platform 运行测试。`run` 任务运行 `com.example.jdk25.App`。

## GitHub Actions

推送代码、创建或更新 Pull Request，以及手动触发时，GitHub Actions 会运行 `clean build`，并使用 `aquasecurity/trivy-action` 扫描依赖。构建会上传 JAR；测试报告（HTML 与 JUnit XML）以及依赖扫描报告（JSON 与 SARIF）作为工作流产物保存。

扫描会读取仓库中的 `gradle.lockfile`，包含测试依赖，并在发现可修复的 HIGH 或 CRITICAL 漏洞时使扫描任务失败；报告仍会尝试上传。修改依赖后，运行 `./gradlew dependencies --write-locks` 更新锁文件并提交。

## 项目结构

```text
.
├── gradle/wrapper/       # Gradle Wrapper 配置和 JAR
├── gradle.lockfile       # Trivy 使用的已解析依赖版本
├── src/main/java/        # Java 示例源文件
├── src/test/java/        # 测试源文件
├── build.gradle          # Java、工具链和依赖配置
└── settings.gradle       # Gradle 项目名称
```

添加预览特性实验时，建议为该实验单独配置并记录所需的预览编译和运行参数，避免影响默认构建及其他示例。
