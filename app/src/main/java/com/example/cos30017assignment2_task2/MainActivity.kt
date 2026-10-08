package com.example.cos30017assignment2_task2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

	private lateinit var recyclerViewRooms: RecyclerView
	private lateinit var roomAdapter: RoomAdapter

	// Booking Information views
	private lateinit var bookingInformation: LinearLayout
	private lateinit var roomImage: ImageView
	private lateinit var roomTypeText: TextView
	private lateinit var checkInDateText: TextView
	private lateinit var checkOutDateText: TextView
	private lateinit var totalAmountText: TextView

	/*
	 * Activity Result API
	 *
	 * Opens BookingActivity and receives the completed
	 * Booking Parcelable object when the user presses Book Now.
	 */
	private val bookingResultLauncher =
		registerForActivityResult(
			ActivityResultContracts.StartActivityForResult()
		) { result ->

			if (result.resultCode == RESULT_OK) {

				val booking = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
					result.data?.getParcelableExtra(
						"booking",
						Booking::class.java
					)
				} else {
					@Suppress("DEPRECATION")
					result.data?.getParcelableExtra<Booking>("booking")
				}

				booking?.let {
					displayBookingInformation(it)
				}
			}
		}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		initBookingInformation()
		setupRecyclerView()
	}

	/**
	 * Initialise Booking Information views.
	 */
	private fun initBookingInformation() {

		bookingInformation = findViewById(R.id.bookingInformation)

		roomImage = findViewById(R.id.roomImage)
		roomTypeText = findViewById(R.id.roomTypeText)
		checkInDateText = findViewById(R.id.checkInDateText)
		checkOutDateText = findViewById(R.id.checkOutDateText)
		totalAmountText = findViewById(R.id.totalAmountText)
	}

	/**
	 * Set up the RecyclerView and its click listener.
	 */
	private fun setupRecyclerView() {

		recyclerViewRooms = findViewById(R.id.recyclerViewRooms)

		// Display rooms vertically.
		recyclerViewRooms.layoutManager =
			LinearLayoutManager(this)

		// Create adapter and handle room selection.
		roomAdapter = RoomAdapter(RoomData.rooms) { selectedRoom ->
			openBookingActivity(selectedRoom)
		}

		// Attach adapter.
		recyclerViewRooms.adapter = roomAdapter
	}

	/**
	 * Opens BookingActivity using the Activity Result API.
	 */
	private fun openBookingActivity(room: Room) {

		val intent = Intent(
			this,
			BookingActivity::class.java
		)

		intent.putExtra("room", room)

		bookingResultLauncher.launch(intent)
	}

	/**
	 * Displays the completed booking information.
	 */
	private fun displayBookingInformation(booking: Booking) {

		// Display the selected hotel's image.
		roomImage.setImageResource(booking.imageResId)

		// Display booking details.
		roomTypeText.text =
			"Room Type: ${booking.roomType}"

		checkInDateText.text =
			"Check-In Date: ${booking.checkIn}"

		checkOutDateText.text =
			"Check-Out Date: ${booking.checkOut}"

		totalAmountText.text =
			"Total Amount: RM %.2f".format(booking.total)

		// Make the Booking Information section visible.
		bookingInformation.visibility = View.VISIBLE
	}
}