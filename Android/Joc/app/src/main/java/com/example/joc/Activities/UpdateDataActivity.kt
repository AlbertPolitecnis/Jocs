package com.example.joc.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.example.joc.Activities.MainActivity.accedirSocket.socket
import com.example.joc.Objects.User
import com.example.joc.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.concurrent.thread

class UpdateDataActivity : AppCompatActivity() {

    var psswd = ""
    var times = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)

        var boolean: Boolean
        val btnGoBack = findViewById<ImageButton>(R.id.BtnGoBackOptions)
        val txtUpdateUserName = findViewById<TextInputEditText>(R.id.TxtRegisterUsername)
        val layout = findViewById<TextInputLayout>(R.id.textInputLayoutRegisterPassword)
        val txtUpdatePassword = findViewById<TextInputEditText>(R.id.TxtRegisterPassword)
        val layConfirmPass = findViewById<TextInputLayout>(R.id.textInputLayoutRegisterCheckPasswd)
        val txtRegisterCheckPasswd = findViewById<TextInputEditText>(R.id.TxtRegisterCheckPasswd)
        val txtRegisterName = findViewById<TextInputEditText>(R.id.TxtRegisterName)
        val txtRegisterSurnames = findViewById<TextInputEditText>(R.id.TxtRegisterSurnames)
        val txtRegisterCC = findViewById<TextInputEditText>(R.id.TxtRegisterCC)
        val txtRegisterMail = findViewById<TextInputEditText>(R.id.TxtRegisterMail)
        val btnConfirm = findViewById<Button>(R.id.BtnConfirmUpdate)

        Thread {
            boolean = socket?.recieveString()?.toBoolean() ?: false
            if (boolean) {

                val nickName = socket?.recieveString()
                val name = socket?.recieveString()
                val surname = socket?.recieveString()
                val count = socket?.recieveString()
                val mail = socket?.recieveString()

                runOnUiThread {
                    txtUpdateUserName.isEnabled = false

                    txtUpdateUserName.setText(nickName)
                    txtRegisterName.setText(name)
                    txtRegisterSurnames.setText(surname)
                    txtRegisterCC.setText(count)
                    txtRegisterMail.setText(mail)
                }

            } else {

                runOnUiThread {
                    txtUpdateUserName.isEnabled = true
                }
            }
        }.start()

        btnConfirm.setOnClickListener {
            Thread {
                try {
                    if (times <= -1) {
                        val num = socket?.recieveInt()
                        if (num == 1) {
                            times = 1
                            if (allFilled()) {
                                if (txtUpdatePassword.text.toString() == txtRegisterCheckPasswd.text.toString()) {
                                    socket?.sendString(txtUpdateUserName.text.toString())
                                    if (!socket?.recieveString().toBoolean()) {
                                        socket?.sendString(txtRegisterName.text.toString())
                                        socket?.sendString(txtUpdatePassword.text.toString())
                                        socket?.sendString(txtRegisterSurnames.text.toString())
                                        socket?.sendString(txtRegisterCC.text.toString())
                                        socket?.sendString(txtRegisterMail.text.toString())
                                    } else {
                                        val response = socket?.recieveString()

                                        runOnUiThread {
                                            Toast.makeText(
                                                this@UpdateDataActivity,
                                                response,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                } else {
                                    runOnUiThread {
                                        Toast.makeText(
                                            this@UpdateDataActivity,
                                            "Les contrasenyes no coincideixen",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } else {
                                runOnUiThread {
                                    Toast.makeText(
                                        this@UpdateDataActivity,
                                        "Els camps han d'estar plens",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else if (num == 2) {
                            times = 2
                            socket?.sendInt(1)

                            if (!txtUpdatePassword.text.isNullOrEmpty() && txtUpdatePassword.text.toString()
                                    .equals(txtRegisterCheckPasswd.text)
                            ) {
                                if (allFilled()) {
                                    socket?.sendString(txtRegisterName.text.toString())
                                    socket?.sendString(txtRegisterSurnames.text.toString())
                                    socket?.sendString(txtRegisterMail.text.toString())
                                    socket?.sendString(txtRegisterCC.text.toString())

                                    socket?.sendString("true")

                                    socket?.sendString(txtUpdatePassword.text.toString())
                                }
                            } else {
                                if (allFilledNoPasswd()) {
                                    socket?.sendString(txtRegisterName.text.toString())
                                    socket?.sendString(txtRegisterSurnames.text.toString())
                                    socket?.sendString(txtRegisterMail.text.toString())
                                    socket?.sendString(txtRegisterCC.text.toString())

                                    socket?.sendString("false")
                                }
                            }
                        }
                    }
                    else
                    {
                        if (times == 1) {

                            if (allFilled()) {
                                if (txtUpdatePassword.text.toString() == txtRegisterCheckPasswd.text.toString()) {
                                    socket?.sendString(txtUpdateUserName.text.toString())
                                    if (socket?.recieveString().toBoolean()) {
                                        socket?.sendString(txtRegisterName.text.toString())
                                        socket?.sendString(txtRegisterSurnames.text.toString())
                                        socket?.sendString(txtRegisterCC.text.toString())
                                        socket?.sendString(txtRegisterMail.text.toString())
                                        socket?.sendString(txtUpdatePassword.text.toString())
                                    } else {
                                        val response = socket?.recieveString()

                                        runOnUiThread {
                                            Toast.makeText(
                                                this@UpdateDataActivity,
                                                response,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                } else {
                                    runOnUiThread {
                                        Toast.makeText(
                                            this@UpdateDataActivity,
                                            "Les contrasenyes no coincideixen",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } else {
                                runOnUiThread {
                                    Toast.makeText(
                                        this@UpdateDataActivity,
                                        "Els camps han d'estar plens",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else if (times == 2) {
                            socket?.sendInt(1)

                            if (!txtUpdatePassword.text.isNullOrEmpty() && txtUpdatePassword.text.toString()
                                    .equals(txtRegisterCheckPasswd.text.toString())
                            ) {
                                if (allFilled()) {
                                    socket?.sendString(txtRegisterName.text.toString())
                                    socket?.sendString(txtRegisterSurnames.text.toString())
                                    socket?.sendString(txtRegisterMail.text.toString())
                                    socket?.sendString(txtRegisterCC.text.toString())

                                    socket?.sendString("true")

                                    socket?.sendString(txtUpdatePassword.text.toString())
                                }
                            } else {
                                if (allFilledNoPasswd()) {
                                    socket?.sendString(txtRegisterName.text.toString())
                                    socket?.sendString(txtRegisterSurnames.text.toString())
                                    socket?.sendString(txtRegisterMail.text.toString())
                                    socket?.sendString(txtRegisterCC.text.toString())

                                    socket?.sendString("false")
                                }
                            }
                        }
                    }

                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@UpdateDataActivity, e.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }.start()
        }


        btnGoBack.setOnClickListener()
        {
            Thread {
                try {
                    if (times < -1) {
                        socket?.recieveInt()
                    }
                    socket?.sendInt(0)
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
            finish()

        }

    }

    private fun allFilledNoPasswd(): Boolean {
        var result = false
        val txtUpdateUserName = findViewById<TextInputEditText>(R.id.TxtRegisterUsername)
        val txtRegisterName = findViewById<TextInputEditText>(R.id.TxtRegisterName)
        val txtRegisterSurnames = findViewById<TextInputEditText>(R.id.TxtRegisterSurnames)
        val txtRegisterCC = findViewById<TextInputEditText>(R.id.TxtRegisterCC)
        val txtRegisterMail = findViewById<TextInputEditText>(R.id.TxtRegisterMail)

        if (
            !txtUpdateUserName.text.isNullOrEmpty() && !txtUpdateUserName.text.toString()
                .contains(":") && !txtUpdateUserName.text.toString().contains(";") &&
            !txtRegisterName.text.isNullOrEmpty() && !txtRegisterName.text.toString()
                .contains(":") && !txtRegisterName.text.toString().contains(";") &&
            !txtRegisterSurnames.text.isNullOrEmpty() && !txtRegisterSurnames.text.toString()
                .contains(":") && !txtRegisterSurnames.text.toString().contains(";") &&
            !txtRegisterCC.text.isNullOrEmpty() && !txtRegisterCC.text.toString()
                .contains(":") && !txtRegisterCC.text.toString().contains(";") &&
            !txtRegisterMail.text.isNullOrEmpty() && !txtRegisterMail.text.toString()
                .contains(":") && !txtRegisterMail.text.toString().contains(";")
        ) {
            result = true
        }


        return result
    }

    private fun allFilled(): Boolean {

        var result = false
        val txtUpdateUserName = findViewById<TextInputEditText>(R.id.TxtRegisterUsername)
        val txtUpdatePassword = findViewById<TextInputEditText>(R.id.TxtRegisterPassword)
        val txtRegisterCheckPasswd = findViewById<TextInputEditText>(R.id.TxtRegisterCheckPasswd)
        val txtRegisterName = findViewById<TextInputEditText>(R.id.TxtRegisterName)
        val txtRegisterSurnames = findViewById<TextInputEditText>(R.id.TxtRegisterSurnames)
        val txtRegisterCC = findViewById<TextInputEditText>(R.id.TxtRegisterCC)
        val txtRegisterMail = findViewById<TextInputEditText>(R.id.TxtRegisterMail)

        if (
            !txtUpdateUserName.text.isNullOrEmpty() && !txtUpdateUserName.text.toString()
                .contains(":") && !txtUpdateUserName.text.toString().contains(";") &&
            !txtUpdatePassword.text.isNullOrEmpty() && !txtUpdatePassword.text.toString()
                .contains(":") && !txtUpdatePassword.text.toString().contains(";") &&
            !txtRegisterCheckPasswd.text.isNullOrEmpty() && !txtRegisterCheckPasswd.text.toString()
                .contains(":") && !txtRegisterCheckPasswd.text.toString().contains(";") &&
            !txtRegisterName.text.isNullOrEmpty() && !txtRegisterName.text.toString()
                .contains(":") && !txtRegisterName.text.toString().contains(";") &&
            !txtRegisterSurnames.text.isNullOrEmpty() && !txtRegisterSurnames.text.toString()
                .contains(":") && !txtRegisterSurnames.text.toString().contains(";") &&
            !txtRegisterCC.text.isNullOrEmpty() && !txtRegisterCC.text.toString()
                .contains(":") && !txtRegisterCC.text.toString().contains(";") &&
            !txtRegisterMail.text.isNullOrEmpty() && !txtRegisterMail.text.toString()
                .contains(":") && !txtRegisterMail.text.toString().contains(";")
        ) {
            result = true
        }


        return result
    }
}