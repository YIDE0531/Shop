package com.nuu.shop

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row_bus.view.*
import org.jetbrains.anko.*
import java.net.URL

class BusActivity : AppCompatActivity(), AnkoLogger {
    private lateinit var busess: Bus
    private lateinit var context: Context
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus)
        context = this
        dialog = setProgressDialog(this, "資料處理中..")
        //dialog.show()
        doAsync {
            val url = URL("https://data.tycg.gov.tw/opendata/datalist/datasetMeta/download?id=b3abedf0-aeae-4523-a804-6e807cbad589&rid=bf55b21a-2b7c-4ede-8048-f75420344aed")
            val json = url.readText()
            val url2 = URL("https://data.tycg.gov.tw/opendata/datalist/datasetMeta/download?id=b3abedf0-aeae-4523-a804-6e807cbad589&rid=bf55b21a-2b7c-4ede-8048-f75420344aed")
            val json2 = url2.readText()
            val url3 = URL("https://data.tycg.gov.tw/opendata/datalist/datasetMeta/download?id=b3abedf0-aeae-4523-a804-6e807cbad589&rid=bf55b21a-2b7c-4ede-8048-f75420344aed")
            val json3 = url3.readText()
            uiThread {
                Toast.makeText(this@BusActivity, "3333333", Toast.LENGTH_SHORT).show()
                parseGson(json)
            }
        }
    }

    private fun parseGson(json: String) {
        busess = Gson().fromJson<Bus>(json, Bus::class.java)
        info { busess.datas.size }
        busess.datas.forEach{
            info { "${it.BusID}" }
            info { "${it.RouteID}" }
            info { "${it.Speed}" }
        }
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)//是否固定大小
        recycler.adapter = BusAdapter()
        dialog.cancel()
    }

    inner class BusAdapter(): RecyclerView.Adapter<BusHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_bus, parent, false)
            return BusHolder(view)
        }

        override fun onBindViewHolder(holder: BusHolder, position: Int) {
            holder.bindBus(busess, position)
        }

        override fun getItemCount(): Int {
            return busess.datas?.size?:0
        }
    }

    inner class BusHolder(view: View): RecyclerView.ViewHolder(view){
        var tvBusTitle: TextView = view.tv_bus_id
        var tvRouteId: TextView = view.tv_route_id
        var tvSpeed: TextView = view.tv_speed

        fun bindBus(bus: Bus, position: Int){
            tvBusTitle.text = "BusID：${bus.datas[position].BusID}"
            tvRouteId.text =  "RouteID：${bus.datas[position].RouteID}"
            tvSpeed.text = "Speed ：${bus.datas[position].Speed}"
        }
    }
}

data class Bus(
    val datas: List<Data>
)

data class Data(
    val Azimuth: String,
    val BusID: String,
    val BusStatus: String,
    val DataTime: String,
    val DutyStatus: String,
    val GoBack: String,
    val Latitude: String,
    val Longitude: String,
    val ProviderID: String,
    val RouteID: String,
    val Speed: String,
    val ledstate: String,
    val sections: String
)