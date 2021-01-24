package com.example.drinksrecipes.modules.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinksrecipes.R
import com.example.drinksrecipes.modules.room.DrinksTableModel
import com.example.drinksrecipes.utils.InternalStorageProvider
import kotlinx.android.synthetic.main.drink_item_row.view.*



class DrinksFavRcvAdapter(
    private val items: ArrayList<DrinksTableModel>,
    private var context: Context
) :
    RecyclerView.Adapter<DrinksFavRcvAdapter.ViewHolder>() {
    private var listener: RecyclerViewItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.drink_item_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int): DrinksTableModel {
        return items[position]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var list = items[position]
        holder.title.text = list?.title
        holder.cb.isChecked = list?.isAlcoholic == context.getString(R.string.alcoholic)
        val image = InternalStorageProvider(context).loadBitmap(list.img.toString())
        Glide.with(context).load(image).into(holder.img)
        holder.desc.text = list?.desc


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.fav.setImageDrawable(
                context.resources.getDrawable(
                    R.drawable.ic_baseline_star_24,
                    null
                )
            )
        } else {
            holder.fav.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_star_24
                )
            )
        }
    }


    fun onAdapterClickListener(recyclerviewItemClickListener: RecyclerViewItemClickListener) {

        this.listener = recyclerviewItemClickListener

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.tvTitle
        var desc: TextView = itemView.tvDesc
        var img: ImageView = itemView.ivDrink
        var fav: ImageView = itemView.ivFav
        var cb: CheckBox = itemView.cbAlcohol


    }
}
