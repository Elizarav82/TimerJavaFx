import controller.TimerController
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.Stage
import model.TimerData
import view.TimerView


class TimerApp : Application(), TimerView {
    val controller = TimerController(this)
    val timerLabel = Label("00:00").apply { style = "-fx-font-size: 36;" }
    val inputField = TextField().apply {
        promptText = "Секунды"
        maxWidth = 100.0
    }

    override fun start(stage: Stage) {
        val buttons = listOf(
            Button("Старт").apply {
                setOnAction {
                    startTimer()
                    style = "-fx-font-size: 14;"
                }
            },
            Button("Пауза").apply {
                setOnAction {
                    controller.pausedTimer()
                    style = "-fx-font-size: 14;"
                }
            },

            Button("Стоп").apply {
                setOnAction {
                    controller.resetTimer()
                    style = "-fx-font-size: 14;"
                }
            },
        )
        val root = VBox(15.0, timerLabel, inputField).apply {
            children.addAll(buttons)
            alignment = Pos.CENTER
        }
        stage.scene = Scene(root, 350.0, 350.0)
        stage.title = "Таймер"
        stage.show()
    }

    private fun startTimer() {
        val seconds = inputField.text.toIntOrNull() ?: 0
        controller.timerData = TimerData(seconds / 60, seconds % 60)
        controller.start { min, sec -> timerLabel.text = String.format("%02d:%02d", min, sec) }
    }

    override fun updateTime(minutes: Int, seconds: Int) {
        timerLabel.text = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onTimerFinished() {
        timerLabel.text = "Готово"
    }

}

fun main() {
    Application.launch(TimerApp::class.java)
}