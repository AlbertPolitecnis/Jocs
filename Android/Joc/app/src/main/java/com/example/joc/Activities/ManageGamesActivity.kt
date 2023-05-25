package com.example.joc.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.joc.Activities.MainActivity.accedirSocket.socket
import com.example.joc.Adapters.BuyGamesAdapter
import com.example.joc.Adapters.GamesAdapter
import com.example.joc.Objects.Game
import com.example.joc.Objects.UserGame
import com.example.joc.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ManageGamesActivity : AppCompatActivity() {

    private lateinit var games: MutableList<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_games)

        games = mutableListOf()
        updateList()
    }

    private fun updateList() {
        val btnGoBack = findViewById<ImageButton>(R.id.BtnGoBackManGames)
        var selected = -1
        var buying = false
        val lstGames = findViewById<RecyclerView>(R.id.LstAllGames)
        val btnGames = findViewById<Button>(R.id.BtnPartides)
        val btnTarifa = findViewById<Button>(R.id.BtnTarifa)
        val txtInputLay = findViewById<TextInputLayout>(R.id.TxtInputLayoutGames)
        val txtNumGames = findViewById<TextInputEditText>(R.id.TxtNumGames)
        val btnAcept = findViewById<Button>(R.id.BtnAccept)

        val thread = Thread {
            try {
                val itin = socket?.recieveInt()
                var i = 0
                do {
                    val gameData = socket?.recieveString()?.split(":")
                    if (gameData != null && gameData.size == 4) {
                        val gameName = gameData[0]
                        val img = gameData[1]
                        val tarifaPrice = gameData[2].toDouble()
                        val gamePrice = gameData[3].toDouble()
                        val game = Game(gameName, img, tarifaPrice, gamePrice)
                        runOnUiThread {
                            games.add(game)
                        }
                    }
                    i++
                } while (i < itin!!)
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        thread.start()

        val adapter = BuyGamesAdapter(this, games)

        lstGames.layoutManager = LinearLayoutManager(this)
        lstGames.adapter = adapter

        btnGoBack.setOnClickListener()
        {
            Thread {
                try {
                    socket?.sendInt(0)
                    val response = socket?.recieveString()
                    runOnUiThread {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
            finish()
        }

        adapter.setOnClickListener()
        { itemView ->
            Thread {
                try {
                    selected = lstGames.getChildAdapterPosition(itemView) + 1
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }

        btnGames.setOnClickListener()
        {
            txtInputLay.visibility = View.VISIBLE
            btnAcept.visibility = View.VISIBLE

        }

        btnTarifa.setOnClickListener()
        {
            txtInputLay.visibility = View.INVISIBLE
            btnAcept.visibility = View.INVISIBLE

            Thread {
                try {
                    if (selected > -1) {
                        socket?.sendInt(selected)
                        socket?.sendInt(2)
                        val response = socket?.recieveString()

                        runOnUiThread {
                            Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Selecciona un joc abans", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }.start()
        }

        btnAcept.setOnClickListener()
        {
            Thread {
                try {
                    if (selected > -1) {
                        if (!txtNumGames.text.toString().isNullOrEmpty()) {
                            socket?.sendInt(selected)
                            socket?.sendInt(1)

                            socket?.sendInt(txtNumGames.text.toString().toInt())
                            val response = socket?.recieveString()

                            runOnUiThread {
                                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                    this,
                                    "Primer has d'indicar quants jocs vols comprar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }.start()
        }
    }
}