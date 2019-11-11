package com.orange_infinity.onlinepay.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.daggerConfigurations.MyApplication
import com.orange_infinity.onlinepay.entities.model.Ticket
import com.orange_infinity.onlinepay.ui.presenter.MainActivityPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: MainActivityPresenter

    var tickets: List<Ticket> = listOf(
        Ticket("Маленький", 10), Ticket("Средний", 15), Ticket("Большой", 25),
        Ticket("Комфорт", 50), Ticket("Комфорт+", 75), Ticket("Бизнес", 100),
        Ticket("Элита", 250), Ticket("Мажор", 500), Ticket("Мажор+", 500),
        Ticket("Мажор-Бизнес", 750), Ticket("Мажор-Элита", 800), Ticket("Проездной", 1500)
    )

    private lateinit var ticketRecyclerView: RecyclerView
    private lateinit var ticketAdapter: TicketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )

        (application as MyApplication).appComponent.inject(this)
        setUpRecycler()

        presenter.sendSignInInfoToServer()
        presenter.updateProgram()

//        val intent = Intent(this, SuccessPayedActivity::class.java)
//        startActivity(intent)
    }

    override fun onBackPressed() {
    }

    private fun setUpRecycler() {
        ticketAdapter = TicketAdapter(tickets)
        ticketRecyclerView = findViewById(R.id.layoutRecycler)
        ticketRecyclerView.layoutManager = GridLayoutManager(this, 2)
        ticketRecyclerView.adapter = ticketAdapter
    }

    private fun updateUi() {

    }

    // -----------------------------------------------------------------------------------------------------------------

    private inner class TicketHolder(val ticketView: View) : RecyclerView.ViewHolder(ticketView) {

        fun bind(ticket: Ticket) {

        }
    }

    private inner class TicketAdapter(private var tickets: List<Ticket>) : RecyclerView.Adapter<TicketHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketHolder {
            val layoutInflater = LayoutInflater.from(this@MainActivity)
            val view = layoutInflater.inflate(R.layout.ticket_list, parent, false)

            return TicketHolder(view)
        }

        override fun onBindViewHolder(holder: TicketHolder, position: Int) {
            val ticket = tickets[position]
            holder.bind(ticket)
        }

        override fun getItemCount(): Int = tickets.size
    }
}
