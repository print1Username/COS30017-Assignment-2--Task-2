package com.example.cos30017assignment2_task2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
	val name: String,
	val location: String,
	val stars: Int,
	val size: Int,
	val distance: String,
	val roomTypes: Map<String, Double>,
	val facilities: List<String>,
	val iconResId: Int,
	val imageResId: Int
) : Parcelable