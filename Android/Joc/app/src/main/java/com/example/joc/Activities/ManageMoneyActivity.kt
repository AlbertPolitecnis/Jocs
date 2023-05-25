package com.example.joc.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.joc.Activities.MainActivity.accedirSocket.socket
import com.example.joc.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ManageMoneyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_money)

        val btnAddMoney = findViewById<Button>(R.id.BtnAddMoney)
        val btnRetrieveMoney = findViewById<Button>(R.id.BtnRetrieveMoney)
        var addMoney = true
        var default = 2
        val btnGoBack = findViewById<ImageButton>(R.id.BtnGoBackManMoney)
        val btnConfirm = findViewById<Button>(R.id.BtnOptionDiners)
        val txtAddMoney = findViewById<TextInputEditText>(R.id.TxtAddMoney)

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

        btnAddMoney.setOnClickListener()
        {
            btnConfirm.text = getString(R.string.afegir)
            btnConfirm.setBackgroundResource(R.drawable.gradient_blue)
            addMoney = true
            default = 2
        }

        btnRetrieveMoney.setOnClickListener()
        {
            btnConfirm.text = getString(R.string.retirar)
            btnConfirm.setBackgroundResource(R.drawable.gradient_red)
            addMoney = false
            default = 1
        }

        btnConfirm.setOnClickListener()
        {
            Thread {
                try {
                    if (!addMoney) {
                        if (!txtAddMoney.text.isNullOrEmpty()) {
                            socket?.sendInt(default)
                            socket?.sendDouble(txtAddMoney.text.toString().toDouble())
                            val response = socket?.recieveString()

                            runOnUiThread {
                                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                            }

                            txtAddMoney.text = null
                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                    this,
                                    "Has d'indicar quants diners vols retirar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        if (!txtAddMoney.text.isNullOrEmpty()) {
                            socket?.sendInt(default)
                            socket?.sendDouble(txtAddMoney.text.toString().toDouble())
                            val response = socket?.recieveString()

                            runOnUiThread {
                                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                            }

                            txtAddMoney.text = null
                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                    this,
                                    "Has d'indicar quants diners vols retirar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }.start()
        }

    }
}