package com.example.cos30017assignment2_task2

object RoomData {

	val rooms = listOf(

		Room(
			name = "Heritage Apartment",
			location = "Old Town Area",
			stars = 5,
			size = 28,
			distance = "10 mins to City Center",
			roomTypes = mapOf(
				"Executive" to 1200.0,
				"Deluxe" to 700.0,
				"Superior" to 500.0
			),
			facilities = listOf(
				"Housekeeping",
				"Toiletries",
				"Wi-Fi",
				"Mini Bar"
			),
			iconResId = R.drawable.room1_icon,
			imageResId = R.drawable.room1
		),

		Room(
			name = "Ameron Hotel",
			location = "Shenton Way, Down Town",
			stars = 3,
			size = 25,
			distance = "25 mins to Subway",
			roomTypes = mapOf(
				"Deluxe" to 500.0,
				"Superior" to 415.0,
				"Single" to 300.0
			),
			facilities = listOf(
				"Housekeeping",
				"Toiletries",
				"Wi-Fi",
				"Refrigerator"
			),
			iconResId = R.drawable.room2_icon,
			imageResId = R.drawable.room2
		),

		Room(
			name = "Dorsett Studio Apartment",
			location = "City Center",
			stars = 4,
			size = 28,
			distance = "5 mins to Bus Station",
			roomTypes = mapOf(
				"Premier" to 900.0,
				"Deluxe" to 600.0,
				"Superior" to 415.0
			),
			facilities = listOf(
				"Kitchenette",
				"Toiletries",
				"Wi-Fi",
				"Refrigerator"
			),
			iconResId = R.drawable.room3_icon,
			imageResId = R.drawable.room3
		),

		Room(
			name = "Travelodge Harbourfront",
			location = "Harbourfront",
			stars = 3,
			size = 20,
			distance = "1.5km to City Center",
			roomTypes = mapOf(
				"Family Room" to 600.0,
				"Deluxe" to 400.0
			),
			facilities = listOf(
				"Breakfast",
				"Toiletries",
				"Wi-Fi",
				"Refrigerator"
			),
			iconResId = R.drawable.room4_icon,
			imageResId = R.drawable.room4
		),

		Room(
			name = "Clover Apartment",
			location = "East-West Coast",
			stars = 2,
			size = 19,
			distance = "10km to City Center",
			roomTypes = mapOf(
				"Deluxe" to 450.0,
				"Superior" to 370.0,
				"Single" to 200.0
			),
			facilities = listOf(
				"Toiletries",
				"Wi-Fi",
				"Drinking Water"
			),
			iconResId = R.drawable.room5_icon,
			imageResId = R.drawable.room5
		),

		Room(
			name = "Wonderloft Hostel",
			location = "China Town",
			stars = 3,
			size = 30,
			distance = "220 meters to public transportation",
			roomTypes = mapOf(
				"Premium Room" to 350.0,
				"Dormitory" to 160.0
			),
			facilities = listOf(
				"Wi-Fi",
				"Shower",
				"Laundry"
			),
			iconResId = R.drawable.room6_icon,
			imageResId = R.drawable.room6
		)
	)
}