package com.manickchand.androidanimelist.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.androidanimelist.R
import com.manickchand.androidanimelist.models.Anime
import com.manickchand.androidanimelist.util.loadImageView
import kotlinx.android.synthetic.main.item_anime.view.*

class TopAnimesAdapter(context: Context,
                       list: List<Anime>,
                       val onItemClickListener:((anime:Anime) -> Unit) ) : RecyclerView.Adapter<TopAnimesAdapter.MyViewHolder?>() {

    private var mContext =context
    private var mList = list
    private val mlayoutInflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater //
    private lateinit var mView: View
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mView = mlayoutInflater.inflate(R.layout.item_anime,parent,false)
        return MyViewHolder(mView,onItemClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindAnime(mList[position])
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount() = mList.count()

    inner class MyViewHolder(itemView:View,
                             private val onItemClickListener: ((anime: Anime) -> Unit)) :RecyclerView.ViewHolder(itemView){

        private var ivAnime: ImageView = itemView.iv_anime
        private val tvTopScore = itemView.tv_top_score

        fun bindAnime(anime: Anime) {

            try {
                loadImageView(ivAnime, anime.image_url)
            }catch (e:Exception){
                e.stackTrace
            }

            tvTopScore.text = anime.score.toString()

            itemView.setOnClickListener{
                onItemClickListener.invoke(anime)
            }
        }

    }
}