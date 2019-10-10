package com.assistiveapps.constitutiondbparser.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.assistiveapps.constitutiondbparser.R
import com.assistiveapps.constitutiondbparser.ui.tag_search.TagSearchActivity
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.CATEGORY_ALL
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.CATEGORY_AMENDMENTS
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.CATEGORY_PARTS
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.CATEGORY_PREAMBLE
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.CATEGORY_SCHEDULES
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
    }

    private fun setup() {
        setClickListeners()
    }

    private fun setClickListeners() {
        partTagSearchButton.setOnClickListener {
            searchNextActivityForTag(CATEGORY_PARTS)
        }

        scheduleTagSearchButton.setOnClickListener {
            searchNextActivityForTag(CATEGORY_SCHEDULES)
        }

        amendmentTagSearchButton.setOnClickListener {
            searchNextActivityForTag(CATEGORY_AMENDMENTS)
        }

        preambleTagSearchButton.setOnClickListener {
            searchNextActivityForTag(CATEGORY_PREAMBLE)
        }
        allTagSearchButton.setOnClickListener {
            searchNextActivityForTag(CATEGORY_ALL)
        }
    }

    private fun searchNextActivityForTag(categoryName: String) {
        getTag()?.let {
            startActivity(TagSearchActivity.newIntent(this, it, categoryName))
        }
    }

    private fun getTag(): String? {
        if(tagSearchEditText.text.isEmpty()) return null
        else return tagSearchEditText.text.toString()
    }
}
