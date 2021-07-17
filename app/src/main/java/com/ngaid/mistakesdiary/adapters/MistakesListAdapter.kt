package com.ngaid.mistakesdiary.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.ngaid.mistakesdiary.MistakeEditionView
import com.ngaid.mistakesdiary.R
import com.ngaid.mistakesdiary.model.ShortMistake
import com.ngaid.mistakesdiary.toColor

class MistakesListAdapter(
    private val context: Context,
    private val mistakes: List<ShortMistake>,
    private val inflater: LayoutInflater
) : BaseAdapter() {
    override fun getCount(): Int {
        return mistakes.size
    }

    override fun getItem(position: Int): Any {
        return mistakes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val h: ViewHolder
        val v: View

        //usual viewHolder & view initialization
        if (view == null) {
            v = inflater.inflate(R.layout.mistake_item, parent, false)
            h = ViewHolder(v)
            v.tag = h
        } else {
            v = view
            h = v.tag as ViewHolder
        }

        val dbMistake = mistakes[position]

        h.mistakeTV.text = dbMistake.description
        when (dbMistake.type) {
            0 -> h.mistakeTV.setBackgroundColor(R.color.lightMistake.toColor())
            1 -> h.mistakeTV.setBackgroundColor(R.color.mediumMistake.toColor())
            2 -> h.mistakeTV.setBackgroundColor(R.color.hardMistake.toColor())
        }
        h.editBtn.setOnClickListener {
            editMistake(dbMistake.id)
        }

        return v
    }

    private fun editMistake(id: Int) {
        val intent = Intent(context, MistakeEditionView::class.java)
        intent.putExtra(MistakeEditionView.ID, id)
        context.startActivity(intent)
    }

    private class ViewHolder(view: View) {
        val mistakeTV: TextView = view.findViewById(R.id.mistakeItemTV)
        val editBtn: ImageButton = view.findViewById(R.id.editBtn)
    }
}