# COS30017 Assignment 2 - Task 2

**English** | [中文](README.zh-CN.md)

A simple Android hotel booking app written in Kotlin. Pick a hotel from a list, choose your dates and room type, and see the total price. After you press **Book Now**, your booking is shown on the home page.

## Overview

The app has two screens:

1. **Home screen (`MainActivity`)** - shows a scrollable list of hotels. Once you have made a booking, the booking details appear on this screen.
2. **Booking screen (`BookingActivity`)** - shows the hotel you tapped. Here you choose a check-in date, a check-out date and a room type. The total price updates automatically.

### How to use the app

1. Tap a hotel in the list.
2. Select a **check-in date** and a **check-out date**.
3. Select a **room type** from the drop-down menu.
4. Check the **total price** (number of nights x room rate, in RM).
5. Tap **Book Now**. You are taken back to the home screen, where the booking information is displayed.

### Booking rules

- Check-in cannot be before today.
- Check-out must be after check-in.
- **Book Now** stays disabled until both dates and a room type are valid.
- Dates are displayed as `dd/MM/yyyy`.

## Requirements

- [Git](https://git-scm.com/downloads)
- [Android Studio](https://developer.android.com/studio)
- An Android emulator or a physical Android phone (Android 7.0 / API 24 or higher)
- An internet connection for the first build (Gradle downloads the tools it needs)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/print1Username/COS30017-Assignment-2--Task-2.git
cd COS30017-Assignment-2--Task-2
```

### 2. Open the project

1. Open Android Studio.
2. Choose **File > Open** and select the `COS30017-Assignment-2--Task-2` folder.
3. Wait for **Gradle Sync** to finish (this can take a few minutes the first time).

### 3. Run the app

1. Select an emulator or connect a phone with USB debugging turned on.
2. Click the green **Run** button in Android Studio.

## Project Structure

```
COS30017-Assignment-2--Task-2/
├── app/
│   ├── build.gradle.kts            # App-level build settings
│   └── src/main/
│       ├── AndroidManifest.xml     # Declares the app's screens
│       ├── java/com/example/cos30017assignment2_task2/
│       │   ├── MainActivity.kt     # Home screen: hotel list + booking information
│       │   ├── BookingActivity.kt  # Booking screen: dates, room type, total price
│       │   ├── RoomAdapter.kt      # Shows each hotel as a row in the list
│       │   ├── Room.kt             # Data class: one hotel's details
│       │   ├── Booking.kt          # Data class: one completed booking
│       │   └── RoomData.kt         # The hotel data used by the app
│       └── res/
│           ├── layout/             # Screen designs (XML)
│           ├── drawable/           # Images and icons
│           └── values/             # Text, colours and themes
├── gradle/                         # Gradle wrapper and library versions
├── build.gradle.kts                # Project-level build settings
├── settings.gradle.kts             # Project name and modules
├── gradlew / gradlew.bat           # Gradle wrapper scripts
└── README.md
```

## Code at a Glance

| File | What it does |
| --- | --- |
| `RoomData.kt` | Holds the list of hotels (name, location, stars, room types and prices, facilities). Add or edit hotels here. |
| `Room.kt` | Describes what information one hotel has. |
| `Booking.kt` | Describes what information one booking has. |
| `RoomAdapter.kt` | Turns the hotel list into rows on the home screen and reports which one was tapped. |
| `MainActivity.kt` | Shows the hotel list, opens the booking screen, and displays the booking that comes back. |
| `BookingActivity.kt` | Handles date selection, room type selection, price calculation and the **Book Now** button. |

### How data moves between screens

```
MainActivity  --(selected Room)-->  BookingActivity
MainActivity  <--(finished Booking)--  BookingActivity
```

Both `Room` and `Booking` are marked `Parcelable`, which lets Android pass them between screens inside an `Intent`.

## Tech Stack

- Language: Kotlin
- Min SDK: 24, Target / Compile SDK: 37
- UI: XML layouts, `RecyclerView`, Material Components
- Build tool: Gradle (Kotlin DSL)