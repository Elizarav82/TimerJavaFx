package view

interface TimerView {
    fun updateTime(minutes: Int, seconds: Int)
    fun onTimerFinished()
}