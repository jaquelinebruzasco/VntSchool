package com.jaquelinebruzasco.vntschool_aula11

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(
    private val list: List<Music>,
    private val onItemClicked: (Music) -> Unit
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = list[position]
        holder.bind(music)
        holder.itemView.setOnClickListener {
            onItemClicked(music)
        }
    }

    override fun getItemCount(): Int = list.size

    class MusicViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(music: Music) {
            view.apply {
                findViewById<TextView>(R.id.tv_music_name).text = music.name
                findViewById<TextView>(R.id.tv_time).text = music.time
            }
        }
    }


}