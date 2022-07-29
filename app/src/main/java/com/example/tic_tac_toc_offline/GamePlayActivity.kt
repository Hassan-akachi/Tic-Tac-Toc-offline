package com.example.tic_tac_toc_offline

import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_game_play.*
import kotlin.system.exitProcess

var playTurn = true

class GamePlayActivity : AppCompatActivity() {
    var player1count = 0
    var player2count = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play)

        resentBtn.setOnClickListener {
            reset()
        }
    }

    private fun reset() { //reset the game
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1
        for (i in 1..9) {
            var buttonSelcted: Button?
            buttonSelcted = when (i) {
                1 -> button1
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                7 -> button7
                8 -> button8
                9 -> button9
                else -> {
                    button1
                }

            }
            buttonSelcted.isEnabled = true // making the button  clickable
            buttonSelcted.text = ""
            player1Score.text = "player1 :- $player1count"
            player2Score.text = "plplayer2 :- $player2count"
        }
    }

    fun buttonClick(view: View) {
        if (playTurn) {
            var but = view as Button //make a view into button
            var cellId = 0
            when (but) {
                button1 -> cellId = 1
                button2 -> cellId = 2
                button3 -> cellId = 3
                button4 -> cellId = 4
                button5 -> cellId = 5
                button6 -> cellId = 6
                button7 -> cellId = 7
                button8 -> cellId = 8
                button9 -> cellId = 9

            }
            playTurn = false
            Handler().postDelayed(Runnable { playTurn = true }, 600) //
            playeNow(but, cellId)
        }
    }

    private fun playeNow(buttonSelected: Button, currCell: Int) {

        val audioSelectButton =
            MediaPlayer.create(this, R.raw.pop)// create instance of the audio file
        if (activeUser == 1) {// single user/player
            buttonSelected.text = "X"
            buttonSelected.setTextColor(Color.parseColor("#ECBCBC"))
            buttonSelected.textSize = 25F
            player1.add(currCell)
            emptyCells.add(currCell)
            audioSelectButton.start()// play audio file
            buttonSelected.isEnabled = false
            Handler().postDelayed(Runnable { audioSelectButton.release() }, 200)
            val checkWinner = checkWinner()
            if (checkWinner == 1) {
                Handler().postDelayed(Runnable { reset() }, 4000)

            } else if (singleUser) {
                Handler().postDelayed(Runnable { robot() }, 500)
            } else {
                activeUser = 2
            }
        } else {//multiple player
            buttonSelected.text = "O"
            audioSelectButton.start()
            buttonSelected.textSize = 25F
            buttonSelected.setTextColor(Color.parseColor("#D22BB804"))
            activeUser = 1
            player2.add(currCell)
            emptyCells.add(currCell)
            Handler().postDelayed(Runnable { audioSelectButton.release() }, 200)
            buttonSelected.isEnabled = false
            val checkWinner = checkWinner()
            if (checkWinner == 1) {
                Handler().postDelayed(Runnable { reset() }, 4000)
            }
        }
    }

    private fun robot() {
        val rand = (1..9).random()
        if (emptyCells.contains(rand)) {
            robot()
        } else {
            val buttonSelected = when (rand) {
                1 -> button1
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                7 -> button7
                8 -> button8
                9 -> button9
                else -> {
                    button1
                }
            }
            emptyCells.add(rand)
            val audioSelectButton =
                MediaPlayer.create(this, R.raw.pop)// create instance of the audio file
            audioSelectButton.start()
            Handler().postDelayed(Runnable { audioSelectButton.release() }, 500)
            buttonSelected.text = "O"
            buttonSelected.setTextColor(Color.parseColor("#D22BB804"))
            buttonSelected.textSize = 25F
            player2.add(rand)
            buttonSelected.isEnabled = false
            var checkWinner = checkWinner()
            if (checkWinner == 1) {
                Handler().postDelayed(Runnable { reset() }, 2000)
            }

        }
    }


    private fun checkWinner(): Int {
        val audiowinningSound = MediaPlayer.create(this, R.raw.notification)
        if ((player1.contains(1) && player1.contains(2) && player1.contains(3)) //condition if player 1 wins
            || (player1.contains(1) && player1.contains(4) && player1.contains(7))
            || (player1.contains(1) && player1.contains(5) && player1.contains(9))
            || (player1.contains(4) && player1.contains(5) && player1.contains(6))
            || (player1.contains(7) && player1.contains(8) && player1.contains(9))
            || (player1.contains(3) && player1.contains(6) && player1.contains(9))
            || (player1.contains(2) && player1.contains(5) && player1.contains(8))
            || (player1.contains(3) && player1.contains(5) && player1.contains(7))

        ) {
            player1count += 1
            buttonDisable()
            audiowinningSound.start()
            disableReset()
            Handler().postDelayed(Runnable { audiowinningSound.release() }, 3000)
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("player 1 wins \n\n Do you want to play again")
            build.setPositiveButton("ok") { dailog, which ->
                audiowinningSound.release()
                reset()
            }
            build.setNegativeButton("Exit") { dailog, which ->
                audiowinningSound.release()
                exitProcess(1)
            }
            Handler().postDelayed(
                Runnable { build.show() },
                2000
            ) //build.show must be called after a build function like alert dialog for its show e.g toastMessage
            return 1
        } else if ((player2.contains(1) && player2.contains(2) && player2.contains(3)) //condition if player 2 wins
            || (player2.contains(1) && player2.contains(4) && player2.contains(7))
            || (player2.contains(1) && player2.contains(5) && player2.contains(9))
            || (player2.contains(4) && player2.contains(5) && player2.contains(6))
            || (player2.contains(7) && player2.contains(8) && player2.contains(9))
            || (player2.contains(3) && player2.contains(6) && player2.contains(9))
            || (player2.contains(2) && player2.contains(5) && player2.contains(8))
            || (player2.contains(3) && player2.contains(5) && player2.contains(7))
        ) {

            player2count += 1
            buttonDisable()
            audiowinningSound.start()
            disableReset()
            Handler().postDelayed(Runnable { audiowinningSound.release() }, 3000)
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("player 2 wins \n\n Do you want to play again")
            build.setPositiveButton("ok") { dailog, which ->
                audiowinningSound.release()
                reset()
            }
            build.setNegativeButton("Exit") { dailog, which ->
                audiowinningSound.release()
                exitProcess(1)
            }
            Handler().postDelayed(
                Runnable { build.show() },
                2000
            ) //build.show must be called after a build function like alert dialog for its show e.g toastMessage
            return 1
        } else if (emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) // the game is Draw or Tie
            && emptyCells.contains(4) && emptyCells.contains(5) && emptyCells.contains(6)
            && emptyCells.contains(7) && emptyCells.contains(8) && emptyCells.contains(9)
        ) {

            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("its a Tie")
            build.setPositiveButton("ok") { dailog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dailog, which ->
                exitProcess(1)
            }
            build.show()  //build.show must be called after a build function like alert dialog for its show e.g toastMessage
            return 1
        }
        println("weird cond just occur")
        return 0
    }

    private fun disableReset() {
        resentBtn.isEnabled = false
        Handler().postDelayed(Runnable { resentBtn.isEnabled = true }, 2000)
    }

    private fun buttonDisable() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1
        for (i in 1..9) {
            var buttonSelcted: Button?
            buttonSelcted = when (i) {
                1 -> button1
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                7 -> button7
                8 -> button8
                9 -> button9
                else -> {
                    button1
                }

            }
            buttonSelcted.isEnabled = true // making the button  clickable
            buttonSelcted.text = ""
            player1Score.text = "player1 : $player1count"
            player2Score.text = "plplayer2 : $player2count"
        }
    }
}