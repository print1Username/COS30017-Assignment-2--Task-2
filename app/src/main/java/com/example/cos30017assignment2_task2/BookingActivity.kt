package com.example.cos30017assignment2_task2

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit
import android.content.res.ColorStateList
import android.graphics.Color

class BookingActivity : AppCompatActivity() {

	private lateinit var ivBookingRoomImage: ImageView
	private lateinit var tvRoomSize: TextView
	private lateinit var tvRoomDistance: TextView
	private lateinit var tvFacilities: TextView
	private lateinit var checkInDateContainer: View
	private lateinit var tvCheckInDate: TextView
	private lateinit var checkOutDateContainer: View
	private lateinit var tvCheckOutDate: TextView
	private lateinit var spinnerRoomType: Spinner
	private lateinit var tvTotal: TextView
	private lateinit var btnBookNow: Button

	private var checkInCalendar: Calendar? = null
	private var checkOutCalendar: Calendar? = null
	private var room: Room? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_booking)

		// Get the selected Room passed from MainActivity.
		room = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			intent.getParcelableExtra("room", Room::class.java)
		} else {
			@Suppress("DEPRECATION")
			intent.getParcelableExtra("room")
		}

		initViews()
		populateRoomDetails()
		setupDatePickerListeners()
		setupSpinner()
		setupBookNowButton()

		// Book Now is disabled when the page is first opened.
		updateBookNowState()
	}

	private fun initViews() {
		ivBookingRoomImage = findViewById(R.id.ivBookingRoomImage)
		tvRoomSize = findViewById(R.id.tvRoomSize)
		tvRoomDistance = findViewById(R.id.tvRoomDistance)
		tvFacilities = findViewById(R.id.tvFacilities)

		checkInDateContainer = findViewById(R.id.checkInDateContainer)
		tvCheckInDate = findViewById(R.id.tvCheckInDate)

		checkOutDateContainer = findViewById(R.id.checkOutDateContainer)
		tvCheckOutDate = findViewById(R.id.tvCheckOutDate)

		spinnerRoomType = findViewById(R.id.spinnerRoomType)
		tvTotal = findViewById(R.id.tvTotal)
		btnBookNow = findViewById(R.id.btnBookNow)
	}

	private fun populateRoomDetails() {
		val currentRoom = room ?: return

		// Display the selected hotel's image.
		ivBookingRoomImage.setImageResource(currentRoom.imageResId)

		// Display room information.
		tvRoomSize.text = "Room Size: ${currentRoom.size} meter²"
		tvRoomDistance.text = currentRoom.distance

		// Display all facilities.
		tvFacilities.text = currentRoom.facilities.joinToString(
			separator = "    •    "
		) { it }
	}

	private fun setupDatePickerListeners() {

		// Check-in date picker.
		checkInDateContainer.setOnClickListener {

			val today = getTodayCalendar()
			val initialDate = checkInCalendar ?: today

			val dialog = DatePickerDialog(
				this,
				{ _, year, month, dayOfMonth ->

					val selectedDate = createDate(
						year,
						month,
						dayOfMonth
					)

					// Past dates should never be accepted.
					if (selectedDate.before(today)) {
						tvCheckInDate.error =
							"Please select today or a future date."
						return@DatePickerDialog
					}

					checkInCalendar = selectedDate

					/*
					 * If the new check-in date is the same as
					 * or after the existing check-out date,
					 * clear the check-out date.
					 */
					if (
						checkOutCalendar != null &&
						!checkOutCalendar!!.after(selectedDate)
					) {
						checkOutCalendar = null
						tvCheckOutDate.text = "Select date"
					}

					tvCheckInDate.text = formatDate(selectedDate)

					calculateTotal()
					updateBookNowState()
				},
				initialDate.get(Calendar.YEAR),
				initialDate.get(Calendar.MONTH),
				initialDate.get(Calendar.DAY_OF_MONTH)
			)

			// Disable all dates before today.
			dialog.datePicker.minDate = today.timeInMillis

			dialog.show()
		}

		// Check-out date picker.
		checkOutDateContainer.setOnClickListener {

			val today = getTodayCalendar()

			/*
			 * If check-in has been selected,
			 * check-out must be at least one day later.
			 */
			val minimumDate = if (checkInCalendar != null) {
				getNextDay(checkInCalendar!!)
			} else {
				today
			}

			val initialDate = checkOutCalendar ?: minimumDate

			val dialog = DatePickerDialog(
				this,
				{ _, year, month, dayOfMonth ->

					val selectedDate = createDate(
						year,
						month,
						dayOfMonth
					)

					// Check-out must be after check-in.
					if (
						checkInCalendar != null &&
						!selectedDate.after(checkInCalendar)
					) {
						tvCheckOutDate.error =
							"Check-out date must be after check-in date."
						return@DatePickerDialog
					}

					checkOutCalendar = selectedDate

					tvCheckOutDate.text = formatDate(selectedDate)

					calculateTotal()
					updateBookNowState()
				},
				initialDate.get(Calendar.YEAR),
				initialDate.get(Calendar.MONTH),
				initialDate.get(Calendar.DAY_OF_MONTH)
			)

			// Disable dates before the valid check-out date.
			dialog.datePicker.minDate = minimumDate.timeInMillis

			dialog.show()
		}
	}

	private fun setupSpinner() {
		val currentRoom = room ?: return

		/*
		 * Create Spinner options from the selected hotel's
		 * roomTypes data.
		 *
		 * Example:
		 * Executive - RM 1200.00
		 * Deluxe - RM 700.00
		 * Superior - RM 500.00
		 */
		val roomTypeOptions = currentRoom.roomTypes.map { (type, rate) ->
			"$type - RM %.2f".format(rate)
		}

		val adapter = ArrayAdapter(
			this,
			android.R.layout.simple_spinner_item,
			roomTypeOptions
		)

		adapter.setDropDownViewResource(
			android.R.layout.simple_spinner_dropdown_item
		)

		spinnerRoomType.adapter = adapter

		spinnerRoomType.onItemSelectedListener =
			object : AdapterView.OnItemSelectedListener {

				override fun onItemSelected(
					parent: AdapterView<*>?,
					view: View?,
					position: Int,
					id: Long
				) {
					calculateTotal()
					updateBookNowState()
				}

				override fun onNothingSelected(
					parent: AdapterView<*>?
				) {
					calculateTotal()
					updateBookNowState()
				}
			}
	}

	/**
	 * Calculates the booking total.
	 *
	 * Total = number of nights × selected room rate.
	 */
	private fun calculateTotal() {
		val currentRoom = room ?: return
		val checkIn = checkInCalendar
		val checkOut = checkOutCalendar

		// Dates must both be selected.
		if (checkIn == null || checkOut == null) {
			tvTotal.text = "RM 0.00"
			return
		}

		// Check-out must be after check-in.
		if (!checkOut.after(checkIn)) {
			tvTotal.text = "RM 0.00"
			return
		}

		// Calculate number of nights.
		val difference =
			checkOut.timeInMillis - checkIn.timeInMillis

		val nights = TimeUnit.MILLISECONDS.toDays(difference)

		// Get selected room type.
		val selectedPosition =
			spinnerRoomType.selectedItemPosition

		val roomTypeNames =
			currentRoom.roomTypes.keys.toList()

		if (selectedPosition !in roomTypeNames.indices) {
			tvTotal.text = "RM 0.00"
			return
		}

		val selectedRoomType =
			roomTypeNames[selectedPosition]

		val roomRate =
			currentRoom.roomTypes[selectedRoomType] ?: 0.0

		// Total = number of nights × room rate.
		val total = nights * roomRate

		tvTotal.text = "RM %.2f".format(total)
	}

	/**
	 * Enables Book Now only when all required
	 * booking information is valid.
	 *
	 * Required:
	 * 1. Check-in date
	 * 2. Check-out date
	 * 3. Check-out after check-in
	 * 4. Valid room type and room rate
	 */
	private fun updateBookNowState() {
		val currentRoom = room

		if (currentRoom == null) {
			btnBookNow.isEnabled = false
			setBookButtonColor(false)
			return
		}

		val checkIn = checkInCalendar
		val checkOut = checkOutCalendar

		// Both dates must be selected.
		if (checkIn == null || checkOut == null) {
			btnBookNow.isEnabled = false
			setBookButtonColor(false)
			return
		}

		// Check-out must be after check-in.
		if (!checkOut.after(checkIn)) {
			btnBookNow.isEnabled = false
			setBookButtonColor(false)
			return
		}

		// Check whether the selected room type is valid.
		val selectedPosition = spinnerRoomType.selectedItemPosition
		val roomTypeNames = currentRoom.roomTypes.keys.toList()

		if (selectedPosition !in roomTypeNames.indices) {
			btnBookNow.isEnabled = false
			setBookButtonColor(false)
			return
		}

		val selectedRoomType = roomTypeNames[selectedPosition]
		val roomRate = currentRoom.roomTypes[selectedRoomType] ?: 0.0

		// Enable Book Now when all required information is valid.
		val isValid = roomRate > 0.0

		btnBookNow.isEnabled = isValid
		setBookButtonColor(isValid)
	}

	private fun setBookButtonColor(enabled: Boolean) {
		val color = if (enabled) {
			Color.parseColor("#5F04F3")
		} else {
			Color.parseColor("#BDBDBD")
		}

		btnBookNow.backgroundTintList = ColorStateList.valueOf(color)
	}

	private fun setupBookNowButton() {
		btnBookNow.setOnClickListener {

			val currentRoom =
				room ?: return@setOnClickListener

			val checkIn =
				checkInCalendar ?: return@setOnClickListener

			val checkOut =
				checkOutCalendar ?: return@setOnClickListener

			// Final validation before creating the booking.
			if (!checkOut.after(checkIn)) {
				return@setOnClickListener
			}

			val roomTypeNames =
				currentRoom.roomTypes.keys.toList()

			val selectedPosition =
				spinnerRoomType.selectedItemPosition

			if (selectedPosition !in roomTypeNames.indices) {
				return@setOnClickListener
			}

			val selectedRoomType =
				roomTypeNames[selectedPosition]

			val roomRate =
				currentRoom.roomTypes[selectedRoomType]
					?: return@setOnClickListener

			val nights = TimeUnit.MILLISECONDS.toDays(
				checkOut.timeInMillis -
						checkIn.timeInMillis
			)

			if (nights <= 0) {
				return@setOnClickListener
			}

			val total = nights * roomRate

			// Create the Booking Parcelable object.
			val booking = Booking(
				roomName = currentRoom.name,
				roomType = selectedRoomType,
				checkIn = formatDate(checkIn),
				checkOut = formatDate(checkOut),
				numberOfNights = nights,
				roomRate = roomRate,
				total = total,
				imageResId = currentRoom.imageResId
			)

			// Return the booking to MainActivity.
			val resultIntent = Intent().apply {
				putExtra("booking", booking)
			}

			setResult(RESULT_OK, resultIntent)
			finish()
		}
	}

	/**
	 * Returns today's date with the time reset to midnight.
	 */
	private fun getTodayCalendar(): Calendar {
		return Calendar.getInstance().apply {
			set(Calendar.HOUR_OF_DAY, 0)
			set(Calendar.MINUTE, 0)
			set(Calendar.SECOND, 0)
			set(Calendar.MILLISECOND, 0)
		}
	}

	/**
	 * Creates a Calendar object from the selected date.
	 */
	private fun createDate(
		year: Int,
		month: Int,
		day: Int
	): Calendar {
		return Calendar.getInstance().apply {
			set(year, month, day, 0, 0, 0)
			set(Calendar.MILLISECOND, 0)
		}
	}

	/**
	 * Returns the day after the supplied date.
	 */
	private fun getNextDay(date: Calendar): Calendar {
		return Calendar.getInstance().apply {
			timeInMillis = date.timeInMillis
			add(Calendar.DAY_OF_MONTH, 1)
		}
	}

	/**
	 * Formats dates consistently for display and Booking data.
	 */
	private fun formatDate(date: Calendar): String {
		return SimpleDateFormat(
			"dd/MM/yyyy",
			Locale.getDefault()
		).format(date.time)
	}


}
