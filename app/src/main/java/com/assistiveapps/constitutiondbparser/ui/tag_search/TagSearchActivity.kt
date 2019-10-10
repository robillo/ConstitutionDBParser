package com.assistiveapps.constitutiondbparser.ui.tag_search

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.assistiveapps.constitutiondbparser.R
import com.assistiveapps.constitutiondbparser.data.model.ReadElement
import com.assistiveapps.constitutiondbparser.ui.tag_search.adapter.ElementAdapter
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.ALL_END_INDEX
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.ALL_START_INDEX
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.AMENDMENTS_END_INDEX
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.AMENDMENTS_START_INDEX
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.CATEGORY_ALL
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.CATEGORY_AMENDMENTS
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.CATEGORY_PARTS
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.CATEGORY_PREAMBLE
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.CATEGORY_SCHEDULES
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.CHARSET_UTF_8
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.EXTRA_CATEGORY_NAME
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.EXTRA_TAG_NAME
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.JSON_READ_ELEMENTS
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.MAIN_DB_PATH
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.PARTS_END_INDEX
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.PARTS_START_INDEX
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.PREAMBLE_INDEX
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.SCHEDULES_END_INDEX
import com.assistiveapps.constitutiondbparser.utils.Constants.Companion.SCHEDULES_START_INDEX
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_tag_search.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.lang.StringBuilder
import java.nio.charset.Charset

class TagSearchActivity : AppCompatActivity() {

    private lateinit var gson: Gson
    private lateinit var tagName: String
    private lateinit var categoryName: String
    private lateinit var elementAdapter: ElementAdapter
    private lateinit var databaseInputStream: InputStream

    private var endIndex: Int = 0
    private var startIndex: Int = 0

    private var elementsList: MutableList<ReadElement> = ArrayList()
    private var tagElementsList: MutableList<ReadElement> = ArrayList()
    private var stringBuilder: StringBuilder = StringBuilder()

    companion object {
        fun newIntent(context: Context, tagName: String, categoryName: String): Intent {
            val intent = Intent(context, TagSearchActivity::class.java)
            intent.putExtra(EXTRA_TAG_NAME, tagName)
            intent.putExtra(EXTRA_CATEGORY_NAME, categoryName)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_search)

        setup()
    }

    private fun setup() {
        getIntentData()
        initVariables()
        updateIndicesForCategory()
        setElementAdapter()
        loadReadElements()
        searchElementsForTag()
    }

    private fun updateIndicesForCategory() {
        when(categoryName) {
            CATEGORY_PARTS -> {
                startIndex = PARTS_START_INDEX
                endIndex = PARTS_END_INDEX
            }
            CATEGORY_ALL -> {
                startIndex = ALL_START_INDEX
                endIndex = ALL_END_INDEX
            }
            CATEGORY_PREAMBLE -> {
                startIndex = PREAMBLE_INDEX
                endIndex = PREAMBLE_INDEX
            }
            CATEGORY_SCHEDULES -> {
                startIndex = SCHEDULES_START_INDEX
                endIndex = SCHEDULES_END_INDEX
            }
            CATEGORY_AMENDMENTS -> {
                startIndex = AMENDMENTS_START_INDEX
                endIndex = AMENDMENTS_END_INDEX
            }
        }
    }

    private fun setElementAdapter() {
        elementsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        elementAdapter = ElementAdapter(comparator())
        elementsRv.adapter = elementAdapter
    }

    private fun getIntentData() {
        tagName = intent.getStringExtra(EXTRA_TAG_NAME)
        categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME)
    }

    private fun initVariables() {
        gson = Gson()
        databaseInputStream = assets.open(MAIN_DB_PATH)

        tagNameTv.text = tagName
    }

    private fun searchElementsForTag() {
        for(i: Int in 0 until elementsList.size) {
            elementsList[i].content?.let {
                if(it.contains(tagName) || it.contains(tagName.toUpperCase()) || it.contains(firstLetterCapsString(tagName))) {
                    tagElementsList.add(elementsList[i])
                    stringBuilder.append(elementsList[i].id.toString().plus(", "))
                }
            }
        }

        Log.e("tag", "$stringBuilder")

        elementAdapter.submitList(tagElementsList)
    }

    private fun firstLetterCapsString(string: String): String {
        val builder = StringBuilder()
        builder.append(string[0].toUpperCase())
        for(i: Int in 1 until string.length)
            builder.append(string[i])

        return builder.toString()
    }

    private fun loadReadElements() {
        try {
            inflateElementsList(getJsonElementsArray(), startIndex, endIndex)
        }
        catch (e: IOException) {
            Toast.makeText(this, getString(R.string.sorry_boss), Toast.LENGTH_SHORT).show()
        }
    }

    private fun inflateElementsList(readElements: JSONArray, start: Int, end: Int) {
        for(elementId: Int in start..end)
            elementsList.add(
                gson.fromJson(
                    readElements.getJSONObject(elementId).toString(),
                    ReadElement::class.java)
            )
    }

    private fun getJsonElementsArray(): JSONArray {
        val json: String?
        val buffer = ByteArray(databaseInputStream.available())
        databaseInputStream.read(buffer)
        databaseInputStream.close()
        json = String(buffer, Charset.forName(CHARSET_UTF_8))

        val jsonObject = JSONObject(json)
        return jsonObject.getJSONArray(JSON_READ_ELEMENTS)
    }

    fun comparator(): DiffUtil.ItemCallback<ReadElement> {
        return object : DiffUtil.ItemCallback<ReadElement>() {
            override fun areItemsTheSame(oldItem: ReadElement, newItem: ReadElement): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ReadElement, newItem: ReadElement): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }
}
