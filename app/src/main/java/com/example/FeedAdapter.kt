package com.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.net.RandomUser
import com.example.util.CircleTransformation
import com.socotech.swa.R
import com.squareup.picasso.Picasso


class FeedAdapter(
    private var onclick: View.OnClickListener,
    private var results: ArrayList<RandomUser> = ArrayList()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun add(t: RandomUser) {
        results.add(t)
    }

    fun clear() {
        // clear results
        results.clear()
        // notify of change
        super.notifyDataSetChanged()
    }

    fun isEmpty(): Boolean {
        return results.isEmpty()
    }

    // Return the size of your data set (invoked by the layout manager)
    override fun getItemCount(): Int {
        return results.size + 1 // results plus footer
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < results.size) {
            ViewType.Result.ordinal
        } else {
            ViewType.Footer.ordinal
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (this.getItemViewType(viewType) == ViewType.Result.ordinal) {
            // create a new view
            val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            v.setOnClickListener(onclick)
            // set the view's size, margins, paddings and layout parameters
            ResultViewHolder(v)
        } else {
            // create a new view
            val v = LayoutInflater.from(parent.context).inflate(R.layout.footer, parent, false)
            FooterViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ResultViewHolder) {
            val result = results[position]
            val city = result.location.city
            val state = result.location.state
            val lastName = result.name.last
            val firstName = result.name.first
            holder.name?.text = "$firstName $lastName"
            holder.location?.text = "$city, $state"
            holder.itemView.tag = result
            Picasso.get().load(result.picture.large).transform(CircleTransformation()).into(holder.avatar)
        } else if (holder is FooterViewHolder) {
            // noop
        }
    }

    internal enum class ViewType {
        Result,
        Footer
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ResultViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView? = v.findViewById(R.id.name)
        var avatar: ImageView? = v.findViewById(R.id.avatar)
        var location: TextView? = v.findViewById(R.id.location)
    }

    class FooterViewHolder(v: View) : RecyclerView.ViewHolder(v)
}
