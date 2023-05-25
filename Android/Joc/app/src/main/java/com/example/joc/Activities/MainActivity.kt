package com.example.joc.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.joc.Activities.MainActivity.accedirSocket.socket
import com.example.joc.Classes.MySocket
import com.example.joc.Objects.User
import com.example.joc.R
import com.google.android.material.textfield.TextInputEditText
import java.lang.Exception
import java.net.Socket

class MainActivity : AppCompatActivity() {

    object accedirSocket {
        var socket: MySocket? = null
    }

    private lateinit var txtUsername: TextInputEditText
    private lateinit var txtPasswd: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtUsername = findViewById(R.id.TxtUsername)
        txtPasswd = findViewById(R.id.TxtPasswd)

        val btnLogIn = findViewById<Button>(R.id.BtnLogIn)
        val lblRegister = findViewById<TextView>(R.id.LblRegister)

        btnLogIn.setOnClickListener {
            if (validOptions()) {
                socket = null
                Thread {
                    try {
                        socket = MySocket(Socket("192.168.1.134", MySocket.PORT))
                        socket?.sendInt(2)
                        socket?.sendString(txtUsername.text.toString())
                        socket?.sendString(txtPasswd.text.toString())
                        val exists = socket!!.recieveInt()

                        if (exists!! >= 0) {
                            runOnUiThread {
                                val intent = Intent(this, MenuActivity::class.java)
                                startActivity(intent)
                            }
                        }
                        else
                        {
                            runOnUiThread {
                                Toast.makeText(this, "User does not exists", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            socket?.close()
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this, e.printStackTrace().toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }.start()
            }
        }

        lblRegister.setOnClickListener {
            socket = null
            Thread {
                try {
                    socket = MySocket(Socket("192.168.1.134", MySocket.PORT))
                    socket?.sendInt(1)
                    runOnUiThread {
                        val intent = Intent(this, UpdateDataActivity::class.java)
                        startActivity(intent)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
    }

    private fun validOptions(): Boolean {
        val username = txtUsername.text.toString()
        val password = txtPasswd.text.toString()

        return if (username.isNotEmpty() && password.isNotEmpty()) {
            true
        } else {
            Toast.makeText(this, "Has de omplir els camps", Toast.LENGTH_SHORT).show()
            false
        }
    }
}
