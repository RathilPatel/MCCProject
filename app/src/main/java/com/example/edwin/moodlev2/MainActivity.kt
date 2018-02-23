
package com.example.edwin.moodlev2

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TabHost
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainTab.setup();

        //Tab 1
        var spec = mainTab.newTabSpec("Boards")
        spec.setContent(R.id.tab1)
        spec.setIndicator("Tab One")
        mainTab.addTab(spec)

        //Tab 2
        spec = mainTab.newTabSpec("Moodle")
        spec.setContent(R.id.tab2)
        spec.setIndicator("Tab Two")
        mainTab.addTab(spec)


        //Tab 3
        spec = mainTab.newTabSpec("FAQs")
        spec.setContent(R.id.tab3)
        spec.setIndicator("Tab Two")
        mainTab.addTab(spec)

    }
}
