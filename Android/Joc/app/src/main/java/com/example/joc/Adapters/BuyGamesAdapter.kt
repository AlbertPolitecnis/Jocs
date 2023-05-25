package com.example.joc.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.joc.Objects.Game
import com.example.joc.Objects.UserGame
import com.example.joc.R

class BuyGamesAdapter(val context: Context, val games: MutableList<Game>) :
    RecyclerView.Adapter<BuyGamesAdapter.TripViewHolder>(),
    View.OnClickListener {
    private val layout = R.layout.adapter_buy_games
    private var clickListener: View.OnClickListener? = null

    class TripViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var imgBuyGame: ImageView
        var lblBuyGameName: TextView
        var lblPriceTarifa: TextView
        var lblPriceGame: TextView

        init {
            imgBuyGame = view.findViewById(R.id.ImgBuyGame)
            lblBuyGameName = view.findViewById(R.id.LblNameBuyGame)
            lblPriceTarifa = view.findViewById(R.id.LblTarifaPrice)
            lblPriceGame = view.findViewById(R.id.LblGamePrice)
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

    fun bindTrip(holder: TripViewHolder, game: Game) {
        holder.imgBuyGame?.setImageBitmap(BitmapFactory.decodeFile(context.getFilesDir().toString() + "/" + game.img))
        holder.lblBuyGameName.text = game.nameGame
        holder.lblPriceTarifa.text = "Tarifa " + game.priceTarifa + " €"
        holder.lblPriceGame.text = "Partida " + game.priceGame + " €"
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickListener = listener;
    }

    override fun onClick(view: View) {
        clickListener?.onClick(view)
    }

}