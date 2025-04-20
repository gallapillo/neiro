package perteceptron.sample

import perteceptron.Perceptron

fun main() {
    val perceptron = Perceptron()

    // Обучающие данные
    val trainingData = arrayOf(
        floatArrayOf(100f, 2f),  // Нормализованные данные / 1000 и /10
        floatArrayOf(200f, 15f),
        floatArrayOf(50f, 1f),
        floatArrayOf(300f, 10f)
    )

    val labels = intArrayOf(0, 1, 0, 1) // Метки: 1 — "спам", 0 — "не спам"

    // Обучение перцептрона
    perceptron.train(trainingData, labels, 4)

    // Тестирование перцептрона
    println("Testing the perceptron:")
    val testData = arrayOf(
        floatArrayOf(150f, 3f),  // Письмо из 150 символов, 3 ключевых слова
        floatArrayOf(250f, 16f),  // Письмо из 250 символов, 6 ключевых слов
        floatArrayOf(80f, 2f),    // Письмо из 80 символов, 2 ключевых слова
        floatArrayOf(100f, 0f)
    )

    for (data in testData) {
        val output = perceptron.predict(data)
        println("Input: [${data[0]}, ${data[1]}] -> Output: $output (${if (output == 1) "Spam" else "Not Spam"})")
    }
}