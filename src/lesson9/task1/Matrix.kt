@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import java.lang.StringBuilder

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> =
    if (height > 0 && width > 0) MatrixImpl(height, width, e) else throw IllegalArgumentException()


/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    private var inf = mutableListOf<MutableList<E>>()

    init {
        require(height > 0 && width > 0)
        inf = MutableList(height) { MutableList(width) { e } }
    }

    override fun get(row: Int, column: Int): E = inf[row][column]

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        inf[row][column] = value
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?): Boolean {
        if (other is MatrixImpl<*> && height == other.height && width == other.width) {
            for (i in 0 until height) {
                for (ii in 0 until width) {
                    if (get(i, ii) != other[i, ii]) return false
                }
            }
            return true
        } else return false
    }

    override fun toString(): String {
        val text = StringBuilder()
        for (i in 0 until height - 1) {
            for (ii in 0 until width - 1) {
                text.append(this[i, ii])
                text.append(", ")
            }
        }
        text.append(this[height - 1, width - 1])
        return text.toString()
    }
}

