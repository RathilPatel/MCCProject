
package com.example.edwin.moodlev2

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TabHost
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.RelativeLayout
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.squareup.okhttp.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import android.os.AsyncTask.execute
import android.os.AsyncTask
import com.squareup.okhttp.Request


class MainActivity : Activity() {
    var api =  DataAPI()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainTab.setup();

        //Tab 1
        var spec = mainTab.newTabSpec("Boards")
        spec.setContent(R.id.tab1)
        spec.setIndicator("Boards")

        mainTab.addTab(spec)

        //Tab 2
        spec = mainTab.newTabSpec("Moodle")
        spec.setContent(R.id.tab2)
        spec.setIndicator("Moodle")
        mainTab.addTab(spec)


        //Tab 3
        spec = mainTab.newTabSpec("FAQs")
        spec.setContent(R.id.tab3)
        spec.setIndicator("FAQs")
        mainTab.addTab(spec);

        loadBoardData()

        loadAllBoards();




    }

    fun loadBoardData() {

        val client = OkHttpClient()

        val request = Request.Builder()
                .url("https://webhooks.mongodb-stitch.com/api/client/v2.0/app/mcc-jnrsm/service/httpServer/incoming_webhook/webhook0?secret=test")
                .build()

        val  asyncTask =  object :  AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void): String? {
                try {
                    val response = client.newCall(request).execute()
                    return if (!response.isSuccessful) {
                        null
                    } else response.body().string()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }

            }

            override fun onPostExecute(s: String?) {
                super.onPostExecute(s)
                if (s != null) {
//                    textView.setText(s)
                    Log.i("data", s)
                }
            }
        }

        asyncTask.execute()
    }

    fun loadAllBoards() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val boards = this.api.getBoards();

        val value: JSONArray  = JSONArray(boards);


        for (i in 0..(value.length() - 1)) {
            val board = value.getJSONObject(i);
            val name = board.getString("name");
            val lastMessage = board.getString("lastMessage");
            val tagColor = board.getString("tagColor");

            Log.i("JSON", name)
            Log.i("JSON", lastMessage)
            Log.i("JSON", tagColor)
            Log.i("JSON", "--------------")
            // Your code here

            val menuLayout = inflater.inflate((R.layout.board_tab_item_layout), null)
            menuLayout.findViewById<TextView>(R.id.name).setText(name)
            menuLayout.findViewById<TextView>(R.id.lastMessage).setText(lastMessage)
                        menuLayout.findViewById<ImageView>(R.id.tag).setBackgroundColor(Color.parseColor(tagColor));

            boardItemArea1.addView(menuLayout);
            Log.i("JSON", menuLayout.toString())

        }

    }
}

