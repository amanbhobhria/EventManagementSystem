package com.example.eventmanagementsystem.adapter
//
//
//import android.content.Context
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.eventmanagementsystem.model.EventsModel
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//
//
//class EventsAdapter(list: List<EventsModel>, context: Context) :
//    RecyclerView.Adapter<aman.bycsaloons.saloonpartner.adapter.BookingAdapter.BookingViewHolder>() {
//    var list: List<EventsModel>
//    var context: Context
//    var firebaseDatabase: FirebaseDatabase
//    var reference: DatabaseReference
//
//    init {
//        this.list = list
//        this.context = context
//        firebaseDatabase = FirebaseDatabase.getInstance()
//        reference = firebaseDatabase.getReference("bookings")
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): aman.bycsaloons.saloonpartner.adapter.BookingAdapter.BookingViewHolder {
//        val view: View = LayoutInflater.from(parent.context)
//            .inflate(R.layout.bookings_preview_design, parent, false)
//        return aman.bycsaloons.saloonpartner.adapter.BookingAdapter.BookingViewHolder(view)
//    }
//
//    override fun onBindViewHolder(
//        holder: aman.bycsaloons.saloonpartner.adapter.BookingAdapter.BookingViewHolder,
//        position: Int
//    ) {
//        holder.userName.setText(list[position].getUserName())
//        holder.status.setText(list[position].getStatus())
//        if (Common.vType.equalsIgnoreCase("hmvendor")) {
//            holder.userCity.setText(list[position].getUserCity())
//            holder.userAddress.setText(list[position].getUserAddress())
//        }
//        val bookId = "B.ID " + list[position].getBookingId()
//        holder.bookingId.setText(bookId)
//        val dateS = "Date " + list[position].getDateSlot()
//        holder.dateSlot.setText(dateS)
//        val timeS = "Time " + list[position].getTimeSlot()
//        holder.timeSlot.setText(timeS)
//        val price = "Price Paid ₹" + list[position].getPricePaid()
//        holder.pricePaid.setText(price)
//        holder.item.setOnClickListener(View.OnClickListener { v: View? ->
//            val bookingModel = BookingModel()
//            bookingModel.setBookingId(list[position].getBookingId())
//            bookingModel.setUserName(list[position].getUserName())
//            bookingModel.setUserCity(list[position].getUserCity())
//            bookingModel.setUserAddress(list[position].getUserAddress())
//            bookingModel.setStatus(list[position].getStatus())
//            bookingModel.setDateSlot(list[position].getDateSlot())
//            bookingModel.setTimeSlot(list[position].getTimeSlot())
//            bookingModel.setPricePaid(list[position].getPricePaid())
//            bookingModel.setUserPhone(list[position].getUserPhone())
//            bookingModel.setItemList(list[position].getItemList())
//            Common.currentBooking = bookingModel
//            val intent = Intent(context, BookingDetailsActivity::class.java)
//            context.startActivity(intent)
//        })
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var item: LinearLayout
//        var userName: TextView
//        var userCity: TextView
//        var userAddress: TextView
//        var status: TextView
//        var bookingId: TextView
//        var dateSlot: TextView
//        var timeSlot: TextView
//        var pricePaid: TextView
//
//        init {
//            item = itemView.findViewById(R.id.bookingsPreviewTemplate)
//            userName = itemView.findViewById(R.id.userNamePr)
//            userCity = itemView.findViewById(R.id.cityPr)
//            userAddress = itemView.findViewById(R.id.addressPr)
//            status = itemView.findViewById(R.id.statusPr)
//            dateSlot = itemView.findViewById(R.id.dateSlotPrv)
//            timeSlot = itemView.findViewById(R.id.timeSlotPrv)
//            pricePaid = itemView.findViewById(R.id.pricePaidPrv)
//            bookingId = itemView.findViewById(R.id.bookingIdPrv)
//        }
//    }
//}