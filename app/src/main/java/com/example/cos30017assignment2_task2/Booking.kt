package com.example.cos30017assignment2_task2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Booking(
	val roomName: String,
	val roomType: String,
	val checkIn: String,
	val checkOut: String,
	val numberOfNights: Long,
	val roomRate: Double,
	val total: Double
) : Parcelable