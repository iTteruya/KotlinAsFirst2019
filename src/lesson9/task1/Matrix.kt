@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import java.lang.StringBuilder

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell<T, U>(val row: Int, val column: Int)

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

    operator fun get(cell: Cell<Any?, Any?>): E

    fun getOrDefault(row: Int, column: Int, def: E): E {
        return if (row in 0 until height && column in 0 until width) get(row, column)
        else def
    }

    fun findNeighbour(r: Int, c: Int, value: E, exc: E): Triple<Boolean, Int, Int> {
        if (getOrDefault(r + 1, c, exc) == value) return Triple(true, r + 1, c)
        if (getOrDefault(r, c + 1, exc) == value) return Triple(true, r, c + 1)
        if (getOrDefault(r - 1, c, exc) == value) return Triple(true, r - 1, c)
        if (getOrDefault(r, c - 1, exc) == value) return Triple(true, r, c - 1)
        return Triple(false, -1, -1)
    }

    fun gRow(r: Int): List<E> {
        val l = mutableListOf<E>()
        for (i in 0 until width) {
            l.add(get(r, i))
        }
        return l
    }

    fun gCom(c: Int): List<E> {
        val l = mutableListOf<E>()
        for (i in 0 until height) {
            l.add(get(i, c))
        }
        return l
    }

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell<Any?, Any?>, value: E)
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
    private var inf: MutableList<MutableList<E>>

    init {
        require(height > 0 && width > 0)
        inf = MutableList(height) { MutableList(width) { e } }
    }

    override fun get(row: Int, column: Int): E = inf[row][column]

    override fun get(cell: Cell<Any?, Any?>): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        inf[row][column] = value
    }

    override fun set(cell: Cell<Any?, Any?>, value: E) = set(cell.row, cell.column, value)

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
        for (i in 0 until height) {
            for (ii in 0 until width - 1) {
                text.append(this[i, ii])
                text.append(" ")
            }
            text.append(this[i, width - 1])
            text.append("\n")
        }
        return text.toString()
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + inf.hashCode()
        return result
    }
}

