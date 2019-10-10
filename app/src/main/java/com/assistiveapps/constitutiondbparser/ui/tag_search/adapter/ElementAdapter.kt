package com.assistiveapps.constitutiondbparser.ui.tag_search.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.assistiveapps.constitutiondbparser.R
import com.assistiveapps.constitutiondbparser.data.model.ReadElement
import com.assistiveapps.constitutiondbparser.ui.tag_search.adapter.holder.ElementHolder

class ElementAdapter(comparator: DiffUtil.ItemCallback<ReadElement>):
        ListAdapter<ReadElement, ElementHolder>(comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementHolder {
        return ElementHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_read_element, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ElementHolder, position: Int) {
        getItem(position)?.let {
            holder.setElement(it)
        }
    }
}