package com.example.joc.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.joc.Activities.MainActivity.accedirSocket.socket
import com.example.joc.Classes.MySocket
import com.example.joc.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnGoBack = findViewById<ImageButton>(R.id.BtnGoBackOptions)
        val btnPlay = findViewById<CardView>(R.id.cardViewPlay)
        val btnGames = findViewById<CardView>(R.id.cardViewGames)
        val btnMoney = findViewById<CardView>(R.id.cardViewMoney)
        val btnUser = findViewById<CardView>(R.id.cardViewUser)

        val intent = getIntent()

        btnGoBack.setOnClickListener {
            Thread {
                try {
                    socket?.sendInt(0)
                    val response = socket?.recieveString()

                    runOnUiThread {
                        Toast.makeText(this@MenuActivity, response?: "Es  null", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MenuActivity,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } finally {
                    socket?.close()
                }
            }.start()

            finish()
        }

        btnPlay.setOnClickListener {
            Thread {
                try {
                    socket?.sendInt(1)

                    runOnUiThread {
                        val intentPass = Intent(this@MenuActivity, GamesListActivity::class.java)
                        startActivity(intentPass)
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MenuActivity,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }.start()
        }

        btnGames.setOnClickListener {
            Thread {
                try {
                    socket?.sendInt(2)

                    runOnUiThread {
                        val intentPass = Intent(this@MenuActivity, ManageGamesActivity::class.java)
                        startActivity(intentPass)
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MenuActivity,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }.start()
        }

        btnMoney.setOnClickListener {
            Thread {
                try {
                    socket?.sendInt(3)

                    runOnUiThread {
                        val intentPass = Intent(this@MenuActivity, ManageMoneyActivity::class.java)
                        startActivity(intentPass)
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MenuActivity,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }.start()
        }

        btnUser.setOnClickListener {
            Thread {
                try {
                    socket?.sendInt(4)

                    runOnUiThread {
                        val intentPass = Intent(this@MenuActivity, UpdateDataActivity::class.java)
                        startActivity(intentPass)
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MenuActivity,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }.start()
        }


    }
}