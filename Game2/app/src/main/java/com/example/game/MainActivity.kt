package com.example.game

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout


class MainActivity : AppCompatActivity(), OnTouchListener {
    lateinit var button_to_game:Button
    lateinit var background:LinearLayout

    var x: Float = 0.toFloat()
    var sX: Float = 0.toFloat()
    var fX: Float = 0.toFloat()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val params_for_linearLayout:LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

        background=LinearLayout(this)
        background.orientation=LinearLayout.VERTICAL
        background.setOnTouchListener(this)
        background.setBackgroundResource(R.drawable.grass)

        setContentView(background, params_for_linearLayout)

        addBut()
    }

    private fun addBut(){
        val params_for_button = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params_for_button.gravity=Gravity.CENTER

        button_to_game=Button(this)
        button_to_game.gravity=Gravity.CENTER
        button_to_game.setTextSize(26f)
        button_to_game.setTextColor(getResources().getColor(R.color.colorAccent))
        button_to_game.text="Начать игру"
        button_to_game.setOnClickListener {
            toGame()
        }

        background.addView(button_to_game,params_for_button)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        x = event.x

        when (event.action) {
            MotionEvent.ACTION_DOWN // нажатие
            -> {
                sX= x
            }
            MotionEvent.ACTION_UP // отпускание
            -> {
                fX = x
                if (sX-fX>70) {                     //смещение координат
                    toGame()
                }
            }
        }
        return true
    }
    fun toGame(){
        val intent=Intent(this,TheGame::class.java)
        startActivity(intent)
    }
}


