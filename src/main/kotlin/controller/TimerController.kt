package controller

import javafx.animation.AnimationTimer
import model.TimeState
import model.TimerData
import view.TimerView

class TimerController(val view: TimerView) {
    var timerData = TimerData(0, 0)
    private var timer: AnimationTimer? = null

    fun start(onTick: (Int, Int) -> Unit) {
        if (timerData.minutes == 0 && timerData.seconds == 0) {
            throw IllegalArgumentException("Время не может быть нулевым!")
        }
        timerData.currentState = TimeState.RUNNING

        timer = object : AnimationTimer() {
            private var lastTick = 0L
            override fun handle(now: Long) {
                if (now - lastTick >= 1_000_000_000) {
                    lastTick = now
                    updateTime()
                    onTick(timerData.minutes, timerData.seconds)
                }
            }
        }.also { it.start() }
    }

    fun updateTime() {
        if (timerData.seconds == 0) {
            if (timerData.minutes == 0) {
                view.onTimerFinished()
                resetTimer()
                return
            }
            timerData.minutes--
            timerData.seconds = 59
        } else {
            timerData.seconds--
        }
        view.updateTime(timerData.minutes, timerData.seconds)
    }

    fun pausedTimer() {
        timerData.currentState = TimeState.PAUSED
        timer?.stop()
    }

    fun resetTimer() {
        timerData = TimerData(0, 0, TimeState.STOPPED)
        timer?.stop()
        view.updateTime(0, 0)
    }
}