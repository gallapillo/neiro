package perteceptron

import kotlin.random.Random


private const val INPUT_SIZE = 2
private const val LEARNING_RATE = 0.1f
private const val EPOCHS = 100

/**
 * Класс перцептрона - простейшей нейронной сети с одним слоем.
 *
 * @param inputsSize количество входных признаков (по умолчанию INPUT_SIZE = 2)
 * @param learningRate скорость обучения - определяет размер шага при корректировке весов (по умолчанию 0.1)
 * @param epochs количество эпох обучения - сколько раз сеть увидит весь обучающий набор (по умолчанию 100)
 */
class Perceptron(
    private val inputsSize: Int = INPUT_SIZE,       // Размерность входных данных
    private val learningRate: Float = LEARNING_RATE, // Скорость обучения (обычно 0.01-0.1)
    private val epochs: Int = EPOCHS                // Количество проходов по данным
) {
    // Весовые коэффициенты нейрона (+1 для bias - смещения)
    private val weights: FloatArray = FloatArray(inputsSize + 1) {
        // Инициализация весов случайными значениями в диапазоне [-1, 1]
        Random.nextFloat() * 2f - 1f
        // Это помогает избежать симметричности в обучении
    }

    /**
     * Функция активации (ступенчатая функция Хевисайда)
     *
     * @param weightedSum взвешенная сумма входов
     * @return 1 если weightedSum >= 0, иначе 0
     */
    private fun activationFunction(weightedSum: Float): Int {
        return if (weightedSum >= 0) 1 else 0
        // Для бинарной классификации подходит пороговая функция
    }

    /**
     * Установка новых весов (в основном для тестирования)
     *
     * @param newWeights массив новых весовых коэффициентов
     */
    internal fun setWeights(newWeights: FloatArray) {
        // Копируем новые веса в массив weights
        System.arraycopy(newWeights, 0, weights, 0, newWeights.size)
    }

    /**
     * Прогнозирование выхода на основе входных данных
     *
     * @param inputs входной вектор (размер должен совпадать с inputsSize)
     * @return результат активации (0 или 1)
     */
    fun predict(inputs: FloatArray): Int {
        var weightedSum = 0f // Накопленная взвешенная сумма

        // Умножаем каждый вход на соответствующий вес и суммируем
        for (i in 0 until inputsSize) {
            weightedSum += inputs[i] * weights[i]
        }

        // Добавляем смещение (bias) - это дополнительный вес без входа
        weightedSum += weights[inputsSize]

        // Применяем функцию активации
        return activationFunction(weightedSum)
    }

    /**
     * Обучение перцептрона на тренировочных данных
     *
     * @param trainingData массив тренировочных примеров
     * @param labels правильные ответы для тренировочных данных
     * @param dataSize количество тренировочных примеров
     */
    fun train(
        trainingData: Array<FloatArray>, // Обучающая выборка
        labels: IntArray,                // Метки классов (0 или 1)
        dataSize: Int                    // Размер выборки
    ) {
        // Проходим по данным указанное количество эпох
        for (epoch in 0 until epochs) {
            // Для каждого примера в обучающей выборке
            for (i in 0 until dataSize) {
                // Получаем предсказание для текущего примера
                val prediction = predict(trainingData[i])

                // Вычисляем ошибку: разница между ожидаемым и полученным
                val error = labels[i] - prediction

                // Корректируем веса по правилу обучения перцептрона:
                // новый вес = старый вес + скорость_обучения * ошибка * входное_значение
                for (j in 0 until inputsSize) {
                    weights[j] += learningRate * error * trainingData[i][j]
                }

                // Корректируем вес смещения (bias)
                // Bias можно рассматривать как вес с постоянным входом = 1
                weights[inputsSize] += learningRate * error
            }

            // Здесь можно добавить логирование процесса обучения
            // Например, вывод ошибки на текущей эпохе
        }
    }
}