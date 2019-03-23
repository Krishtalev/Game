package com.example.game

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.game.*
import android.os.CountDownTimer
import android.view.Display
import android.graphics.Point
import android.view.Surface


class TheGame: AppCompatActivity(), View.OnClickListener {
    lateinit var button_mole:Button

    var holes:List<LinearLayout> = listOf()
    var checkOnFree:Array<Boolean> = arrayOf(true, true, true, true, true)

    var wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT
    var params_for_button:LinearLayout.LayoutParams=LinearLayout.LayoutParams(wrapContent,wrapContent)

    var indexOfHole=0
    var points=0
    var duration=60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOrientation()
        setContentView(R.layout.game)

        textView_score.text=getString(R.string.score,points)
        createListOfHoles()
        setTimer()
    }

    fun setOrientation(){
        val display:Display=getWindowManager().getDefaultDisplay()
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
        val angle=display.rotation

        if (width>height)
            if (angle==Surface.ROTATION_90)
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            else
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE)
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    fun createListOfHoles(){
        holes = listOf(
            linearLayout_hole_first,
            linearLayout_hole_second,
            linearLayout_hole_third,
            linearLayout_hole_fourth,
            linearLayout_hole_fifth
        )
    }

    private fun setTimer(){
        val timer = object:CountDownTimer(duration*1000L,1000) {
            override fun onTick(millisUntilFinished: Long) {
                duration--
                textView_time.text=getString(R.string.time,duration)
                if ((duration%5==0 && duration>30) || (duration>15 && duration<=30 && duration%3==0) || (duration<=15 && duration%2 == 0))
                    spawnMole()
            }
            override fun onFinish() {
                textView_time.text=getString(R.string.time,0)
                jojo.visibility=View.VISIBLE
                jojo.setOnClickListener {
                    toMainMenu()
                }
            }
        }

        timer.start()
    }

    fun spawnMole(){
        indexOfHole = (0..4).random()
        while(checkOnFree[indexOfHole]!=true) indexOfHole = (0..4).random()
        createMoleButton(indexOfHole)
        holes[indexOfHole].addView(button_mole)
        checkOnFree[indexOfHole]=false
        setRemoveTimer(indexOfHole)
    }

    fun createMoleButton(i: Int){
        button_mole=Button(this)
        button_mole.text=i.toString()
        button_mole.textSize=0f
        button_mole.layoutParams=params_for_button
        button_mole.setOnClickListener(this)
        button_mole.setBackgroundResource(R.drawable.mole)
    }

    fun setRemoveTimer(i:Int){
        val timer2 = object:CountDownTimer(3000,1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish(){
                holes[i].removeAllViews()
                checkOnFree[i]=true
            }
        }
        timer2.start()
    }

    override fun onClick(v: View) {
        val deletedButton = v as Button
        holes[deletedButton.text.toString().toInt()].removeAllViews()
        checkOnFree[deletedButton.text.toString().toInt()]=true
        points++;
        textView_score.text=getString(R.string.score,points)
    }

    fun toMainMenu() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
