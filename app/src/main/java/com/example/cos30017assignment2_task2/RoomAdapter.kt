package com.example.cos30017assignment2_task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class RoomAdapter(
	private val rooms: List<Room>,
	private val onRoomClick: (Room) -> Unit
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

	class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		val roomImage: ImageView = itemView.findViewById(R.id.ivRoomImage)
		val roomName: TextView = itemView.findViewById(R.id.tvRoomName)
		val roomLocation: TextView = itemView.findViewById(R.id.tvRoomLocation)
		val roomStars: TextView = itemView.findViewById(R.id.tvRoomStars)
		val roomPrice: TextView = itemView.findViewById(R.id.tvRoomPrice)
	}

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): RoomViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_room, parent, false)

		return RoomViewHolder(view)
	}

	override fun onBindViewHolder(
		holder: RoomViewHolder,
		position: Int
	) {
		val room = rooms[position]

		// Display room information
		holder.roomImage.setImageResource(room.imageResId)
		holder.roomName.text = room.name
		holder.roomLocation.text = room.location
		holder.roomStars.text = "${room.stars} stars"

		// Display the lowest available room rate
		val lowestRate = room.roomTypes.values.minOrNull() ?: 0.0
		holder.roomPrice.text = String.format(
			Locale.getDefault(),
			"RM %.0f",
			lowestRate
		)

		// Handle room click
		holder.itemView.setOnClickListener {
			onRoomClick(room)
		}
	}

	override fun getItemCount(): Int {
		return rooms.size
	}
}