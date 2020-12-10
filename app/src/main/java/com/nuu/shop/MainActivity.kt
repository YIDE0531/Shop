package com.nuu.shop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row_function.view.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private val RC_SIGNUP = 100
    private val RC_NICKNAME = 101
    private val auth = FirebaseAuth.getInstance()
    //RecyclerView
    val functions = listOf("Camera",
        "Invite friend",
        "Parking",
        "Download coupons",
        "News",
        "Movies",
        "B",
        "News",
        "News",
        "News",
        "Bus")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        auth.addAuthStateListener { 
            authChanged(auth)
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        //Spinner
        val colors = arrayOf("Red", "Green", "Blue")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d(TAG, "onItemSelected: ${colors[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                
            }

        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)//是否固定大小
        recycler.adapter = FunctionAdapter()
    }

    inner class FunctionAdapter():RecyclerView.Adapter<FunctionHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_function, parent, false)
            return FunctionHolder(view)
        }

        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.nameTest.text = functions[position]
            holder.itemView.setOnClickListener {
                functionClicked(holder, position)
            }
        }

        override fun getItemCount(): Int {
            return functions.size
        }
    }

    class FunctionHolder(view: View): RecyclerView.ViewHolder(view){
        var nameTest: TextView = view.tv_name
    }

    private fun functionClicked(holder: FunctionHolder, position: Int) {
        Log.d(TAG, "functionClicked: $position", )
        when(position){
            1 -> startActivity(Intent(this, ContactActivity::class.java))
            2 -> startActivity(Intent(this, ParkingActivity::class.java))
            5 -> startActivity(Intent(this, MovieActivity::class.java))
            10 -> startActivity(Intent(this, BusActivity::class.java))

        }
    }

    override fun onResume() {
        super.onResume()
//        tv_nick_name.text = getNickName()
        FirebaseDatabase.getInstance()
            .getReference("users")
            .child(auth.currentUser!!.uid)
            .child("nickname")
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    tv_nick_name.text = snapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun authChanged(auth: FirebaseAuth) {
        //TODO: fofo
        if(auth.currentUser == null){
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, RC_SIGNUP)
        }else{
            Log.d(TAG, "authChanged: ${auth.currentUser?.uid}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGNUP){
            if(resultCode == RESULT_OK){
                val intent = Intent(this, NickNameActivity::class.java)
                startActivityForResult(intent, RC_NICKNAME)
            }else if(resultCode == RESULT_CANCELED){
                finish()
            }
        }else if(requestCode == RC_NICKNAME){
            
        }
    }
}