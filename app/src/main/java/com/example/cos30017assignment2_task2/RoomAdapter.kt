package com.example.cos30017assignment2_task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoomAdapter(
	private val rooms: List<Room>
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

	// ViewHolder stores the views used by each room item
	class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		val roomImage: ImageView = itemView.findViewById(R.id.ivRoomImage)
		val roomName: TextView = itemView.findViewById(R.id.tvRoomName)
		val roomLocation: TextView = itemView.findViewById(R.id.tvRoomLocation)
		val roomStars: TextView = itemView.findViewById(R.id.tvRoomStars)
		val roomPrice: TextView = itemView.findViewById(R.id.tvRoomPrice)
	}

	// Creates a new room item using item_room.xml
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): RoomViewHolder {

		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_room, parent, false)

		return RoomViewHolder(view)
	}

	// Displays room data inside the item layout
	override fun onBindViewHolder(
		holder: RoomViewHolder,
		position: Int
	) {

		val room = rooms[position]

		// Set hotel image
		holder.roomImage.setImageResource(room.iconResId)

		// Set hotel name
		holder.roomName.text = room.name

		// Set location
		holder.roomLocation.text = room.location

		// Set star rating
		holder.roomStars.text = "${room.stars} stars"

		// Find the cheapest room type
		val minimumPrice = room.roomTypes.values.minOrNull() ?: 0.0

		// Display minimum price
		holder.roomPrice.text = "RM %.0f".format(minimumPrice)
	}

	// Returns the number of rooms
	override fun getItemCount(): Int {
		return rooms.size
	}
}