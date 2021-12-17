package com.elsawy.popular.memes.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elsawy.popular.memes.paging.data.model.Meme
import com.elsawy.popular.memes.paging.databinding.MemeItemBinding

class MemesAdapter : PagingDataAdapter<Meme, RecyclerView.ViewHolder>(COMPARATOR) {

   override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      getItem(position).let {
         (holder as? MemeViewHolder)?.bind(it)
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return MemeViewHolder.create(parent)
   }

   companion object {

      private val COMPARATOR = object : DiffUtil.ItemCallback<Meme>() {
         override fun areItemsTheSame(oldItem: Meme, newItem: Meme): Boolean {
            return oldItem.id == newItem.id
         }

         override fun areContentsTheSame(oldItem: Meme, newItem: Meme): Boolean =
            oldItem == newItem
      }
   }

}


class MemeViewHolder(private val binding: MemeItemBinding) : RecyclerView.ViewHolder(binding.root) {

   fun bind(item: Meme?) {
      binding.memeName.text = item?.name ?: " it's null"
      val url = item?.url
      Glide.with(itemView.context).load(url).into(binding.memeImage)

      Log.d("Image", item?.name + " " + item?.id)
   }

   companion object {
      fun create(view: ViewGroup): MemeViewHolder {
         val inflater = LayoutInflater.from(view.context)
         val binding = MemeItemBinding.inflate(inflater, view, false)
         return MemeViewHolder(binding)
      }
   }
}