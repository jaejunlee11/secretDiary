package com.bignerdranch.secretdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.FileInputStream
import java.io.IOException


class LoginActivity : AppCompatActivity() {
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        try {
            val login: FileInputStream
            val loginfName = "login_credentials.txt"
            login = openFileInput(loginfName)
            val txt = ByteArray(500)
            login.read(txt)
            login.close()
        } catch (e: IOException){
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
            finish()
        }
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            try {
                val password = editTextPassword.text.toString()
                val login: FileInputStream
                val loginfName = "login_credentials.txt"
                login = openFileInput(loginfName)
                val txt = ByteArray(500)
                login.read(txt)
                login.close()
                var check=String(txt).trim { it <= ' ' }
                if(check==password){
                    val nextIntent = Intent(this, MainActivity::class.java)
                    startActivity(nextIntent)
                    finish()
                }else{
                    Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException){
                val nextIntent = Intent(this, MainActivity::class.java)
                startActivity(nextIntent)
                finish()
            }
        }
    }

}