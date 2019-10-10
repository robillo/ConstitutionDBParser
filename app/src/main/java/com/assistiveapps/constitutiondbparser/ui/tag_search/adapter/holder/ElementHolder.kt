package com.assistiveapps.constitutiondbparser.ui.tag_search.adapter.holder

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import com.assistiveapps.constitutiondbparser.data.model.ReadElement
import kotlinx.android.synthetic.main.row_read_element.view.*
import java.lang.StringBuilder

class ElementHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private lateinit var readElement: ReadElement

    fun setElement(readElement: ReadElement) {
        this.readElement = readElement

        renderViews()
    }

    private fun renderViews() {
        itemView.elementId.text = getString(readElement.id.toString())
        itemView.elementTitle.text = getString(readElement.title)
        itemView.elementSubtitle.text = getString(readElement.subtitle)
        itemView.elementCategory.text = getString(readElement.categoryName)
        itemView.elementTags.text = getTagsString(readElement.tags)
        itemView.elementContent.text = Html.fromHtml(getString(readElement.content), Html.FROM_HTML_MODE_COMPACT)
        itemView.elementShortDescription.text = getString(readElement.shortDescription)
    }

    private fun getString(string: String?): String {
        string?.let {
            return it
        } ?: run {
            return "null"
        }
    }

    private fun getTagsString(tagsList: List<String>?): String? {
        tagsList?.let {
            val builder = StringBuilder("[")
            for(s: String in tagsList) {
                builder.append(s).append(", ")
            }
            builder.append("]")
            return builder.toString()

        } ?: run {
            return "[]"
        }
    }
}