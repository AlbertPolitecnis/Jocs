package com.example.joc.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.joc.Objects.UserGame
import com.example.joc.R

class GamesAdapter(val context: Context, val games: MutableList<UserGame>) :
    RecyclerView.Adapter<GamesAdapter.TripViewHolder>(),
    View.OnClickListener
{
    private val layout = R.layout.adapter_games_list
    private var clickListener: View.OnClickListener? = null

    class TripViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var imgGames: ImageView
        var lblGameName: TextView
        var lblGameOption: TextView

        init {
            imgGames = view.findViewById(R.id.ImgGame)
            lblGameName = view.findViewById(R.id.LblNameGame)
            lblGameOption = view.findViewById(R.id.LblOptions)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return TripViewHolder(view);
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val game = games[position]
        bindTrip(holder, game)
    }

    override fun getItemCount() = games.size

    fun bindTrip(holder: TripViewHolder, game: UserGame) {
        holder.imgGames?.setImageBitmap(BitmapFactory.decodeFile(context.getFilesDir().toString() + "/" +  game.img))
        holder.lblGameName.text = game.gameName
        if (game.isTarifaPlana) {
            holder.lblGameOption.text = "Tarifa plana disponible"
        }
        else
        {
            holder.lblGameOption.text = game.numPartides.toString() + " partides"
        }
    }

    fun setOnClickListener (listener: View.OnClickListener) {
        clickListener = listener;
    }

    override fun onClick(view: View) {
        clickListener?.onClick(view)
    }

}