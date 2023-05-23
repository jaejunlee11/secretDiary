package com.bignerdranch.secretdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.IOException


class ChangeSecretNumber : AppCompatActivity() {
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_secret_number)

        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val password = editTextPassword.text.toString()

            saveLoginCredentials( password)
        }
    }

    private fun saveLoginCredentials(password: String) {
        val filename = "login_credentials.txt"

        try {
            openFileOutput(filename, MODE_PRIVATE).use { fos ->
                val data = "$password"
                fos.write(data.toByteArray())
                Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                val nextIntent = Intent(this, MainActivity::class.java)
                startActivity(nextIntent)
                finish()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "로그인 정보를 저장하는 동안 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
