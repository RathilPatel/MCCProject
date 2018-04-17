package com.example.edwin.moodlev2

import android.app.Activity
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import com.squareup.okhttp.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import android.os.AsyncTask
import android.widget.*
import com.squareup.okhttp.Request
import android.widget.LinearLayout


interface OnUpdateListener {
    fun onUpdate(obj: String) {

    }
}


class MainActivity : Activity() {
    var api = DataAPI()


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
        spec.setContent(R.id.faqs_tab)
        spec.setIndicator("FAQs")
        mainTab.addTab(spec);

        loadBoardData()
        loadAllFaqs(api.getFaqs());

//        loadAllBoards();
        webview.loadUrl("http://frcrce.ac.in/");
        webview.setVerticalScrollBarEnabled(true);
        webview.setHorizontalScrollBarEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true)



    }


    fun loadBoardData() {

        val client = OkHttpClient()

        val request = Request.Builder()
                .url("https://webhooks.mongodb-stitch.com/api/client/v2.0/app/mcc-jnrsm/service/httpServer/incoming_webhook/webhook0?secret=test")
                .build()


        val asyncTask = object : AsyncTask<Void, Void, String>() {
            var listener: OnUpdateListener? = null

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

            fun setUpdateListener(listener: OnUpdateListener) {
                this.listener = listener
            }

            override fun onPostExecute(s: String?) {
                super.onPostExecute(s)
                if (s != null) {
//                    textView.setText(s)
//                    Log.i("data", s)
                    var lis = listener
                    if (lis != null) {
                        lis.onUpdate(s);
                    }
                }
            }
        }

        asyncTask.setUpdateListener(object : OnUpdateListener {
            override fun onUpdate(s: String) {
//                Log.i("DATA", "Hello" + s)
                loadAllBoards(s.trim('"').replace("\\\"","\"" )
                        .replace("\\\t","" )
                        .replace("\\\n","" )
                )
            }
        })

        asyncTask.execute()
    }


    fun loadAllBoards(boards: String) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        Log.i("DATA", boards);

        val valueX = JSONObject(boards);
        val value = valueX.getJSONArray    ("boards");

//        val boards = this.api.getBoards();

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

    fun loadAllFaqs(faqs: String)   {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        Log.i("DATA", faqs);

        val tagData = JSONObject(api.getTags());

        val value = JSONArray(faqs);
//        val value = valueX.getJSONArray    ("boards");

//        val boards = this.api.getBoards();
        for (i in 0..(value.length() - 1)) {
            val faq = value.getJSONObject(i);
            val q = faq.getString("q");
            val a = faq.getString("a");
            val tags = faq.getJSONArray("tags");

            Log.i("JSON", q)
            Log.i("JSON", a)
            Log.i("JSON", tags.toString())
            Log.i("JSON", "--------------")
            // Your code here

            val menuLayout = inflater.inflate((R.layout.faq_tab_item_layout), null)
            menuLayout.findViewById<TextView>(R.id.question).setText(q)
            menuLayout.findViewById<TextView>(R.id.answer).setText(a)
            for(i in 0..(tags.length() - 1))  {
                val tag = tags.getString(i);
                val dynamicTextView = TextView(this)
//                dynamicTextView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dynamicTextView.text = tag

//                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                lp.setMargins(2,2,2,2)
//                dynamicTextView.layoutParams = lp;
                dynamicTextView.background = this.getDrawable(R.drawable.tag_border)
//                dynamicTextView.setBackgroundColor(Color.parseColor(tagData.getJSONObject(tag).getString("color")));

                menuLayout.findViewById<LinearLayout>(R.id.tagList).addView(dynamicTextView);



            }
//            menuLayout.findViewById<ImageView>(R.id.tag).setBackgroundColor(Color.parseColor(tagColor));

            faqsList.addView(menuLayout);

            Log.i("JSON", menuLayout.toString())

        }
    }
}


