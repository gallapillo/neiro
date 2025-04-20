import perteceptron.Perceptron
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class PerceptronTest {
    private lateinit var perceptron: Perceptron

    @Before
    fun setUp() {
        perceptron = Perceptron()
    }

    @Test
    fun `predict should return 1 when weighted sum is positive`() {
        // Устанавливаем веса вручную: [1, 1, -0.5] (последний - bias)
        perceptron.setWeights(floatArrayOf(1f, 1f, -0.5f))

        // Входные данные, которые дадут положительный взвешенный сумма (1*1 + 1*1 - 0.5 = 1.5 > 0)
        val inputs = floatArrayOf(1f, 1f)
        val result = perceptron.predict(inputs)

        assertEquals(1, result)
    }

    @Test
    fun `predict should return 0 when weighted sum is negative`() {
        // Устанавливаем веса вручную: [1, 1, -2.5] (последний - bias)
        perceptron.setWeights(floatArrayOf(1f, 1f, -2.5f))

        // Входные данные, которые дадут отрицательный взвешенный сумма (1*1 + 1*1 - 2.5 = -0.5 < 0)
        val inputs = floatArrayOf(1f, 1f)
        val result = perceptron.predict(inputs)

        assertEquals(0, result)
    }

    @Test
    fun `train should learn AND function`() {
        // Данные для обучения функции AND
        val trainingData = arrayOf(
            floatArrayOf(0f, 0f),
            floatArrayOf(0f, 1f),
            floatArrayOf(1f, 0f),
            floatArrayOf(1f, 1f)
        )
        val labels = intArrayOf(0, 0, 0, 1)

        // Обучаем перцептрон
        perceptron.train(trainingData, labels, trainingData.size)

        // Проверяем, что перцептрон научился правильно классифицировать
        assertEquals(0, perceptron.predict(floatArrayOf(0f, 0f)))
        assertEquals(0, perceptron.predict(floatArrayOf(0f, 1f)))
        assertEquals(0, perceptron.predict(floatArrayOf(1f, 0f)))
        assertEquals(1, perceptron.predict(floatArrayOf(1f, 1f)))
    }

    @Test
    fun `train should learn OR function`() {
        // Данные для обучения функции OR
        val trainingData = arrayOf(
            floatArrayOf(0f, 0f),
            floatArrayOf(0f, 1f),
            floatArrayOf(1f, 0f),
            floatArrayOf(1f, 1f)
        )
        val labels = intArrayOf(0, 1, 1, 1)

        // Обучаем перцептрон
        perceptron.train(trainingData, labels, trainingData.size)

        // Проверяем, что перцептрон научился правильно классифицировать
        assertEquals(0, perceptron.predict(floatArrayOf(0f, 0f)))
        assertEquals(1, perceptron.predict(floatArrayOf(0f, 1f)))
        assertEquals(1, perceptron.predict(floatArrayOf(1f, 0f)))
        assertEquals(1, perceptron.predict(floatArrayOf(1f, 1f)))
    }
}
