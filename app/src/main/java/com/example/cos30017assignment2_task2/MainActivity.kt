package com.example.cos30017assignment2_task2

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

		// Create adapter using room data
		roomAdapter = RoomAdapter(RoomData.rooms)

		// Attach adapter to RecyclerView
		recyclerViewRooms.adapter = roomAdapter
	}
}