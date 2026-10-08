# COS30017 Assignment 2 - Task 2

[English](README.md) | **中文**

一个用 Kotlin 编写的简单 Android 酒店预订应用。从列表中选择酒店，选好日期和房型，就能看到总价。点击 **Book Now** 后，预订信息会显示在首页。

## 简单概述

应用一共有两个页面：

1. **首页（`MainActivity`）** - 显示可滚动的酒店列表。完成预订后，预订详情会显示在这个页面上。
2. **预订页（`BookingActivity`）** - 显示你点击的酒店。在这里选择入住日期、退房日期和房型，总价会自动更新。

### 使用方法

1. 在列表中点击一家酒店。
2. 选择**入住日期**和**退房日期**。
3. 在下拉菜单中选择**房型**。
4. 查看**总价**（晚数 x 房型单价，单位 RM）。
5. 点击 **Book Now**，返回首页，预订信息会显示出来。

### 预订规则

- 入住日期不能早于今天。
- 退房日期必须晚于入住日期。
- 日期和房型都有效之前，**Book Now** 按钮不可点击。
- 日期显示格式为 `dd/MM/yyyy`。

## 环境要求

- [Git](https://git-scm.com/downloads)
- [Android Studio](https://developer.android.com/studio)
- Android 模拟器，或一台 Android 真机（Android 7.0 / API 24 及以上）
- 第一次构建时需要联网（Gradle 会下载所需工具）

## 快速开始

### 1. 克隆仓库

```bash
git clone https://github.com/print1Username/COS30017-Assignment-2--Task-2.git
cd COS30017-Assignment-2--Task-2
```

### 2. 打开项目

1. 打开 Android Studio。
2. 选择 **File > Open**，选中 `COS30017-Assignment-2--Task-2` 文件夹。
3. 等待 **Gradle Sync** 完成（第一次可能需要几分钟）。

### 3. 运行应用

1. 选择一个模拟器，或者连接已开启 USB 调试的手机。
2. 点击 Android Studio 中绿色的 **Run** 按钮。

## 程序目录

```
COS30017-Assignment-2--Task-2/
├── app/
│   ├── build.gradle.kts            # 应用模块的构建设置
│   └── src/main/
│       ├── AndroidManifest.xml     # 声明应用的页面
│       ├── java/com/example/cos30017assignment2_task2/
│       │   ├── MainActivity.kt     # 首页：酒店列表 + 预订信息
│       │   ├── BookingActivity.kt  # 预订页：日期、房型、总价
│       │   ├── RoomAdapter.kt      # 把每家酒店显示为列表中的一行
│       │   ├── Room.kt             # 数据类：一家酒店的信息
│       │   ├── Booking.kt          # 数据类：一次完成的预订
│       │   └── RoomData.kt         # 应用使用的酒店数据
│       └── res/
│           ├── layout/             # 页面设计（XML）
│           ├── drawable/           # 图片和图标
│           └── values/             # 文字、颜色和主题
├── gradle/                         # Gradle wrapper 和依赖库版本
├── build.gradle.kts                # 项目级构建设置
├── settings.gradle.kts             # 项目名称和模块
├── gradlew / gradlew.bat           # Gradle wrapper 脚本
└── README.zh-CN.md
```

## 代码速览

| 文件 | 作用 |
| --- | --- |
| `RoomData.kt` | 存放酒店列表（名称、位置、星级、房型与价格、设施）。想增删或修改酒店就改这里。 |
| `Room.kt` | 描述一家酒店包含哪些信息。 |
| `Booking.kt` | 描述一次预订包含哪些信息。 |
| `RoomAdapter.kt` | 把酒店列表变成首页上的一行行内容，并告诉程序点击了哪一家。 |
| `MainActivity.kt` | 显示酒店列表、打开预订页，并显示返回的预订结果。 |
| `BookingActivity.kt` | 处理日期选择、房型选择、价格计算和 **Book Now** 按钮。 |

### 页面之间如何传递数据

```
MainActivity  --(选中的 Room)-->  BookingActivity
MainActivity  <--(完成的 Booking)--  BookingActivity
```

`Room` 和 `Booking` 都标记为 `Parcelable`，这样 Android 就可以通过 `Intent` 在页面之间传递它们。

## 技术栈

- 语言：Kotlin
- 最低 SDK：24，目标 / 编译 SDK：37
- 界面：XML 布局、`RecyclerView`、Material Components
- 构建工具：Gradle（Kotlin DSL）