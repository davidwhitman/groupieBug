package com.example.dwhitman.playground

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.list.*
import kotlinx.android.synthetic.main.my_item.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.util.*

/**
 * TODO Add class description.
 */
class GroupieActivity : FragmentActivity() {
    private lateinit var adapter: GroupAdapter<ViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list)

        myrecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this@GroupieActivity.adapter = GroupAdapter<ViewHolder>().apply {
                setHasStableIds(true)
            }
            adapter = this@GroupieActivity.adapter
        }

        val section = Section(MyItem(id = 999, text = "header"))

        adapter.add(section)
        section.update(listOf(MyItem(id = 1, text = "1")))

        launch {
            delay(1000)
            withContext(UI) {
                section.update(listOf(MyItem(id = 2, text = "2"), MyItem(id = 1, text = "1")))
            }
        }
    }

    private class MyItem(id: Long, private val text: String) : Item(id) {
        override fun getLayout(): Int = R.layout.my_item

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.myItem_text.text = text
        }

        // The following functions aren't necessary but demonstrate that they don't help with the bug.
        override fun hashCode(): Int {
            return Objects.hash(text, id)
        }

        override fun equals(other: Any?): Boolean {
            return other is MyItem && other.hashCode() == this.hashCode()
        }

        override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
            return other is MyItem && other.id == this.id
        }
    }
}