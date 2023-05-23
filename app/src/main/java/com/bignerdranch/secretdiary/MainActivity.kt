package com.bignerdranch.secretdiary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var dp : DatePicker
    private lateinit var edtDiary: EditText
    private lateinit var btnWrite: Button
    private lateinit var fileName: String
    private lateinit var btLock : Button
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "간단한 일기장"
        dp = findViewById(R.id.datepicker)
        edtDiary = findViewById(R.id.edtdiary)
        btnWrite = findViewById(R.id.btnw)
        btLock = findViewById(R.id.btlock)
        val cal: Calendar = Calendar.getInstance()
        val cYear: Int = cal.get(Calendar.YEAR)
        val cMonth: Int = cal.get(Calendar.MONTH)
        val cDay: Int = cal.get(Calendar.DAY_OF_MONTH)
        val login: FileInputStream
        val loginfName = "login_credentials.txt"
        try {
            login = openFileInput(loginfName)
            val txt = ByteArray(500)
            login.read(txt)
            login.close()
        } catch (e: IOException){
            btLock.setText("Lock설정")
        }
        dp!!.init(
            cYear, cMonth, cDay
        ) { view, year, monthOfYear, dayOfMonth ->
            fileName = (Integer.toString(year) + "_"
                    + Integer.toString(monthOfYear + 1) + "_"
                    + Integer.toString(dayOfMonth) + ".txt")
            val str = readDiary(fileName)
            edtDiary!!.setText(str)
            btnWrite.setEnabled(true)
        }

        //다이어리쓰기
        btnWrite.setOnClickListener(View.OnClickListener{
                try {
                    val outfs: FileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
                    val str = edtDiary!!.text.toString()
                    outfs.write(str.toByteArray())
                    outfs.close()
                    Toast.makeText(applicationContext, fileName + "이 저장됨", Toast.LENGTH_SHORT)
                        .show()
                } catch (e: IOException) {
            }
        })

        btLock.setOnClickListener(View.OnClickListener {
            val nextIntent = Intent(this, ChangeSecretNumber::class.java)
            startActivity(nextIntent)
            finish()
        })
    }

    //일기내용읽어서 반환
    fun readDiary(fName: String?): String? {
        var diaryStr: String? = null
        val infs: FileInputStream
        try {
            infs = openFileInput(fName)
            val txt = ByteArray(500)
            infs.read(txt)
            infs.close()
            diaryStr = String(txt).trim { it <= ' ' }
            btnWrite?.setText("일기 수정하기")
        } catch (e: IOException) {
            edtDiary!!.hint = "일기를 쓰세요"
            btnWrite?.setText("새로운 일기 저장")
        }
        return diaryStr
    }
}