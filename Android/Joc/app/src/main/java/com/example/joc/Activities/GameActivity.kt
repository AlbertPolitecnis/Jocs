package com.example.joc.Activities

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.joc.Activities.MainActivity.accedirSocket.socket
import com.example.joc.Objects.UserGame
import com.example.joc.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val imgGame = findViewById<ShapeableImageView>(R.id.ImgGame)
        val txtGamesLeft = findViewById<TextView>(R.id.TxtGamesLeft)
        val btnPlay = findViewById<Button>(R.id.BtnPlay)
        val btnGoBack = findViewById<ImageButton>(R.id.BtnGoBackGame)

        val intent = getIntent()

        val game = intent.getSerializableExtra("Game") as UserGame

        imgGame.setImageBitmap(BitmapFactory.decodeFile(this.getFilesDir().toString() + "/" +  game.img))

        if (game.isTarifaPlana)
        {
            txtGamesLeft.text = "Tens partides il·limitades"
        }
        else
        {
            txtGamesLeft.text = game.numPartides.toString();
        }

        btnPlay.setOnClickListener()
        {
            if (!game.isTarifaPlana && game.numPartides > 0) {
                game.numPartides--

                txtGamesLeft.text = game.numPartides.toString()
            }
            else if (game.numPartides <= 0 && !game.isTarifaPlana)
            {
                Toast.makeText(this, "No et queden partides", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this, "Has jugat a " + game.gameName, Toast.LENGTH_SHORT).show()
            }
        }

        btnGoBack.setOnClickListener()
        {
            Thread {
                try {
                    socket?.sendString(game.isTarifaPlana.toString())
                    socket?.sendInt(game.numPartides)
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
            val intent = Intent(this, GamesListActivity::class.java)
            intent.putExtra("Play", game)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}