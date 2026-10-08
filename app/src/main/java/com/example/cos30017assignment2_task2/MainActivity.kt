package com.example.cos30017assignment2_task2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

	private lateinit var recyclerViewRooms: RecyclerView
	private lateinit var roomAdapter: RoomAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// Find RecyclerView from activity_main.xml
		recyclerViewRooms = findViewById(R.id.recyclerViewRooms)

		// Set the room list to a vertical layout
		recyclerViewRooms.layoutManager = LinearLayoutManager(this)

		// Create adapter and handle room selection
		roomAdapter = RoomAdapter(RoomData.rooms) { selectedRoom ->
			openBookingActivity(selectedRoom)
		}

		// Attach adapter to RecyclerView
		recyclerViewRooms.adapter = roomAdapter
	}

	/**
	 * Opens BookingActivity and passes the selected Room.
	 */
	private fun openBookingActivity(room: Room) {
		val intent = Intent(this, BookingActivity::class.java)

		intent.putExtra("room", room)

		startActivity(intent)
	}
}