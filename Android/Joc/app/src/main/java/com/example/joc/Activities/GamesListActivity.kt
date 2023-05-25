package com.example.joc.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.joc.Activities.MainActivity.accedirSocket.socket
import com.example.joc.Adapters.GamesAdapter
import com.example.joc.Objects.UserGame
import com.example.joc.R

class GamesListActivity : AppCompatActivity() {

    private lateinit var games: MutableList<UserGame>

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val moded =
                    result.data?.getSerializableExtra("Play") as UserGame
                changeListItem(moded)
                updateList()
            } else if (result.resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "No package added or modified", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)

        games = mutableListOf()
        updateList()
    }

    private fun updateList() {
        val btnGoBack = findViewById<ImageButton>(R.id.BtnGoBackGamesList)
        val lstGames = findViewById<RecyclerView>(R.id.LstGames)

        val thread = Thread {
            try {
                val itin = socket?.recieveInt()
                var i = 0
                do {
                    val gameData = socket?.recieveString()?.split(":")
                    if (gameData != null && gameData.size == 4) {
                        val gameName = gameData[0]
                        val img = gameData[1]
                        val tarifaPlana = gameData[2].toBoolean()
                        val numPartides = gameData[3].toInt()
                        val game = UserGame(gameName, img, tarifaPlana, numPartides)
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

        val adapter = GamesAdapter(this, games)

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
            val intentPass = Intent(this, GameActivity::class.java)
            val game = games[lstGames.getChildAdapterPosition(itemView)]
            Thread {
                try {
                    socket?.sendInt(lstGames.getChildAdapterPosition(itemView) + 1)
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
            intentPass.putExtra("Game", game)
            getResult.launch(intentPass)
        }
    }

    private fun changeListItem(moded: UserGame) {
        for (i in games.indices) {
            if (games[i].gameName == moded.gameName) {
                games[i] = moded
            }
        }
    }
}