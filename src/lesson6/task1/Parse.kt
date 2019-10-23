@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.lang.IllegalStateException
import java.lang.IndexOutOfBoundsException

fun skip(com: List<Char>, j: Int): Int {
    var x = j + 1
    var count = 1
    while (count > 0) {
        if (com[x] == '[') count++
        if (com[x] == ']') count--
        x++
    }
    return x - 1
}

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}


fun month1(a: String): String = when (a.toLowerCase()) {
    "января" -> "1"
    "февраля" -> "2"
    "марта" -> "3"
    "апреля" -> "4"
    "мая" -> "5"
    "июня" -> "6"
    "июля" -> "7"
    "августа" -> "8"
    "сентября" -> "9"
    "октября" -> "10"
    "ноября" -> "11"
    "декабря" -> "12"
    else -> a
}

fun month2(a: String): String = when (a.toLowerCase()) {
    "01" -> "января"
    "02" -> "февраля"
    "03" -> "марта"
    "04" -> "апреля"
    "05" -> "мая"
    "06" -> "июня"
    "07" -> "июля"
    "08" -> "августа"
    "09" -> "сентября"
    "10" -> "октября"
    "11" -> "ноября"
    "12" -> "декабря"
    else -> a
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val ps = str.split(" ")
    if (ps.size == 3) {
        try {
            val day = ps[0].toInt()
            val month = month1(ps[1]).toInt()
            val year = ps[2].toInt()
            if (day > lesson2.task2.daysInMonth(month, year) || (day <= 0) || (month !in 1..12) || (year < 0))
                return ""
            return String.format("%02d.%02d.%d", day, month, year)
        } catch (e: Exception) {
            return ""
        }
    } else return ""
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val p = digital.split(".").toMutableList()
    if (p.size == 3) {
        try {
            p[0] = p[0].toInt().toString()
            p[1] = month2(p[1])
            if (dateStrToDigit(p.joinToString(separator = " ")) == digital) return p.joinToString(separator = " ")
        } catch (e: Exception) {
            return ""
        }

    }
    return ""
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    if (Regex("""[a-zA-z~!@#${'$'}%^&*]|\((?!\d)""").containsMatchIn(phone)) return ""
    return Regex("""[^0123456789+]""").replace(phone, "")
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (Regex("""[^\s\-%0123456789]""").containsMatchIn(jumps) ||
        !Regex("""[0123456789]""").containsMatchIn(jumps)
    ) return -1
    val p = Regex("""\s(?=\s+|%|-)|\s$|[%-]""").replace(jumps, "").split(" ")
    var m = 0
    for (i in p) {
        if (i.toInt() > m) m = i.toInt()
    }
    return m
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {

    if (Regex("""[^\s\-%0123456789+]""").containsMatchIn(jumps) ||
        !Regex("""[0123456789+]""").containsMatchIn(jumps) || jumps == ""
    ) return -1
    val regex = Regex("""\d+(?=\s\+)""").findAll(jumps)
    val p = regex.map { it.value }.joinToString(separator = " ").split(" ")
    var m = 0
    for (i in p) {
        if (i.toInt() > m) m = i.toInt()
    }
    return m
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (Regex("""[^\d+\-\s]|\d+(?=\s+\d|\+-)""").containsMatchIn(expression)
        || (Regex("""\+(?=\s+\+|\s+-)|-(?=\s+-|\s+\+)|\+(?=[+\-])|-(?=[-+])""")).containsMatchIn(expression)
        || (!Regex("""^\d+""").containsMatchIn(expression)
                || !Regex("""\d+$""").containsMatchIn(expression))
    )
        throw IllegalArgumentException("wrong format")
    val p = Regex("""^\d+|(?<=\+\s)\d+""").findAll(expression)
    val m = Regex("""(?<=-\s)\d+""").findAll(expression)
    val plus = p.map { it.value }.joinToString(separator = " ").split(" ")
    val minus = m.map { it.value }.joinToString(separator = " ").split(" ")
    if (minus == listOf("")) return plus.fold(0, { sum, i -> sum + i.toInt() })
    return plus.fold(0, { sum, i -> sum + i.toInt() }) - minus.fold(0, { sum, i -> sum + i.toInt() })
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var ans = 0
    val ex = str.toLowerCase()
    if (ex == "") return -1
    val w = ex.split(" ")
    try {
        for (word in w) {
            if (Regex("""\$word(?=\s\$word)""").containsMatchIn(ex)) {
                ans++
                return Regex("""\$word(?=\s\$word)""").find(ex)!!.range.first
            }
        }
    } catch (e: Exception) {
        for (word in w) {
            if (Regex("""[$word](?=\s[$word])""").containsMatchIn(ex)) {
                return Regex("""[$word](?=\s[$word])""").find(ex)!!.range.first
            }
        }
        return -1
    }
    if (ans == 0) {
        for (word in w) {
            if (Regex("""[$word](?=\s[$word])""").containsMatchIn(ex)) {
                return Regex("""[$word](?=\s[$word])""").find(ex)!!.range.first
            }
        }
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val list = description.split("; ")
    val m = mutableMapOf<String, Double>()
    try {
        for (item in list) {
            val i = item.split(" ")
            if (i[1].toDouble() < 0) return ""
            m[i[0]] = i[1].toDouble()
        }
    } catch (e: Exception) {
        return ""
    }
    var max = 0.0
    var a = ""
    for ((n, p) in m) {
        if (p >= max) {
            max = p
            a = n
        }
    }
    return a
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var rom = roman
    var arabic = 0
    val rl = listOf("CM", "M", "CD", "D", "XC", "C", "XL", "L", "IX", "X", "IV", "V", "I")
    val al = listOf(900, 1000, 400, 500, 90, 100, 40, 50, 9, 10, 4, 5, 1)
    if (Regex("""[^MCDXLIV]""").containsMatchIn(roman)) return -1
    for (i in rl.indices) {
        val w = rl[i]
        if (Regex(w).containsMatchIn(rom)) {
            val p1 = Regex(w).findAll(rom)
            arabic += al[i] * p1.map { it.value }.toList().size
            rom = Regex(w).replace(rom, "")
        }
    }
    return if (arabic == 0) -1 else arabic
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var lim = limit
    var i = cells / 2
    val com = commands.toCharArray().toMutableList()
    var j = 0
    val hm = mutableListOf<Int>()
    var cj = 0
    val c = mutableListOf<Int>()
    for (ii in 0 until cells) {
        c.add(0)
    }
    if (Regex("""[^0123456789+\[\]\-\s><]""").containsMatchIn(commands))
        throw IllegalArgumentException("wrong format")
    val ex1 = Regex("""[^\[\]]""").replace(commands, "")
    var ex2 = ex1
    while (Regex("""\[(?=])|(?<=\[)]""").containsMatchIn(ex2)) {
        val ex3 = Regex("""\[(?=])|(?<=\[)]""").replace(ex2, "")
        ex2 = ex3
    }
    if (Regex("""[\[\]]""").containsMatchIn(ex2)) throw IllegalArgumentException("wrong format")
    try {
        while (lim > 0 && j < com.size && j >= 0) {
            if (com[j] == '[' && c[i] == 0) {
                cj = j
                hm.add(cj)
                j = skip(com, j)
            }
            if (com[j] == '[' && c[i] != 0) {
                cj = j
                hm.add(cj)
            }
            if (com[j] == ']' && c[i] == 0) {
                if (hm.isNotEmpty()) hm.remove(hm[hm.lastIndex])
                if (hm.isNotEmpty()) cj = hm[hm.lastIndex]
            }
            if (com[j] == ']' && c[i] != 0) j = cj
            when (com[j]) {
                '+' -> c[i] += 1
                '-' -> c[i] -= 1
                '>' -> i += 1
                '<' -> i -= 1
            }
            if (i < 0 || i > cells - 1) throw IllegalStateException("error")
            j++
            lim--
        }
    } catch (e: IndexOutOfBoundsException) {
        throw IllegalArgumentException("wrong format")
    }
    return c
}

