package com.example.tic_tac_toc_offline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

var singleUser =false
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        singlePlayerBtn.setOnClickListener{
            singleUser= true
            val gameplayActivity = Intent(this,GamePlayActivity::class.java)
            startActivity(gameplayActivity)
        }

        multiplePlayerBtn2.setOnClickListener {
            singleUser =false
            val gameplayActivity = Intent(this,GamePlayActivity::class.java)
            startActivity(gameplayActivity)
        }

    }
}