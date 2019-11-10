@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import com.sun.javafx.binding.StringFormatter
import java.io.File
import java.lang.StringBuilder

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */

fun divide(a: Int, b: Int): Int {
    var f = a
    while (f / b > 0) {
        if ((f / 10) / b > 0) f /= 10
        else break
    }
    return f
}

fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val map = substrings.map { it to 0 }.toMap().toMutableMap()
    val text = File(inputName).readLines().toString()
    for (word in substrings.toSet()) {
        val lw = word.toLowerCase()
        var index = 0
        if (Regex("""\$lw""").containsMatchIn(text.toLowerCase())) {
            var s1 = Regex("""\$lw""").find(text.toLowerCase(), index)
            while (s1 != null) {
                index = if (word.length > 1) s1.range.last + 1 - (word.length - 1)
                else s1.range.last + 1
                map[word] = map[word]!! + 1
                s1 = Regex("""\$lw""").find(text.toLowerCase(), index)
            }
        }
    }
    return map
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val text = File(inputName).readLines()
    File(outputName).bufferedWriter().use {
        for (line in text) {
            var s1 = line
            if (Regex("""(?<=[ЖжШшЧчЩщ])Я|я|Ю|ю|Ы|ы""").containsMatchIn(s1)) {
                s1 = Regex("""(?<=[ЖжШшЧчЩщ])Я""").replace(s1, "А")
                s1 = Regex("""(?<=[ЖжШшЧчЩщ])я""").replace(s1, "а")
                s1 = Regex("""(?<=[ЖжШшЧчЩщ])Ю""").replace(s1, "У")
                s1 = Regex("""(?<=[ЖжШшЧчЩщ])ю""").replace(s1, "у")
                s1 = Regex("""(?<=[ЖжШшЧчЩщ])Ы""").replace(s1, "И")
                s1 = Regex("""(?<=[ЖжШшЧчЩщ])ы""").replace(s1, "и")
            }
            it.write(s1)
            it.newLine()
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val text = File(inputName).readLines().map { it.trim() }
    val max = text.maxBy { it.length }?.length
    if (max == null)
        File(outputName).writeText("")
    File(outputName).bufferedWriter().use {
        for (line in text) {
            val cl = StringBuilder()
            val s = (max!! - line.length) / 2
            for (i in 0 until s) cl.append(" ")
            cl.append(line)
            it.write(cl.toString())
            it.newLine()
        }
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val text = File(inputName).readLines().map { it.trim() }
    val max = text.maxBy { it.length }?.length
    if (max == null)
        File(outputName).writeText("")
    File(outputName).bufferedWriter().use {
        for (line in text) {
            val all = line.split(Regex("""\s+"""))
            if (all.size == 1) {
                it.write(line)
                it.newLine()
            } else {
                val last = all.last()
                val words = all.dropLast(1)
                var lenght = Regex("""\s+""").replace(line, "").length
                var nw = words.size
                var s = (max!! - lenght) / nw
                if (s * nw + lenght < max) s++
                val ef = StringBuilder()
                for (word in words) {
                    ef.append(word)
                    for (ii in 0 until s) ef.append(" ")
                    lenght += s
                    nw--
                    if ((lenght + nw * s > max) && (lenght + nw * (s - 1) >= max)) s--
                }
                ef.append(last)
                it.write(ef.toString())
                it.newLine()
            }
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val text = File(inputName).readLines().map { it.toLowerCase().trim() }.toString()
    val list = Regex("""[A-Za-zА-Яа-яЁё]+""").findAll(text).map { it.value }.toList()
    val map = mutableMapOf<String, Int>()
    for (key in list) {
        if (map.containsKey(key)) map[key] = map[key]!! + 1
        else map[key] = 1
    }
    return map.toList().sortedByDescending { it.second }.take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val map = dictionary.map { it.key.toLowerCase() to it.value.toLowerCase() }.toMap()
    File(outputName).bufferedWriter().use {
        val text = File(inputName).readText()
        val fl = StringBuilder()
        for (char in text) {
            if (map.containsKey(char.toLowerCase())) {
                if (char.isUpperCase()) fl.append((map[char.toLowerCase()] ?: error("")).capitalize())
                else fl.append(map[char.toLowerCase()])
            } else fl.append(char)
        }
        it.write(fl.toString())
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        val list = mutableListOf<String>()
        for (line in File(inputName).readLines()) {
            val word = line.toLowerCase().toList()
            if (word.toSet().size == word.size) list.add(line)
        }
        if (list.isEmpty()) it.write("")
        else it.write(list.groupBy { i -> i.length }.toList().maxBy { ii -> ii.first }!!.second
            .joinToString(separator = ", ")
        )
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    File(outputName).bufferedWriter().use {
        val text = StringBuilder()
        val ans = lhv * rhv
        val l = ans.toString().length + 1
        var cl = l
        var p = 0
        text.append(" ".repeat(l - lhv.toString().length) + "$lhv\n")
        text.append("*" + " ".repeat(l - (rhv.toString().length + 1)) + "$rhv\n")
        text.append("-".repeat(l) + "\n")
        for (num in rhv.toString().reversed()) {
            val x = num.toString().toInt() * lhv
            var space = cl - x.toString().length
            if (space < 0) space = 0
            if (p > 0) text.append("+" + " ".repeat(space) + "$x\n")
            else {
                text.append(" ".repeat(space) + "$x\n")
                cl--
            }
            p = 1
            cl--
        }
        text.append("-".repeat(l) + "\n")
        text.append(" ".repeat(l - ans.toString().length) + "$ans")
        it.write(text.toString())
    }
}

/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    File(outputName).bufferedWriter().use {
        val text = StringBuilder()
        val ans = lhv / rhv
        var take = divide(lhv, rhv)
        var p = 0
        var div = (take / rhv) * rhv
        var res = take - div
        if (take.toString().length > div.toString().length) {
            text.append("$lhv | $rhv\n")
            text.append(
                " ".repeat(lhv.toString().length - 1 - div.toString().length) + "-$div"
                        + " ".repeat(lhv.toString().length - take.toString().length + 3) + "$ans\n"
            )
            p = 1
        } else {
            text.append(" $lhv | $rhv\n")
            text.append("-$div" + " ".repeat(lhv.toString().length - take.toString().length + 3) + "$ans\n")
        }
        if (p == 0) text.append("-".repeat(take.toString().length + 1) + "\n")
        else text.append("-".repeat(take.toString().length) + "\n")
        text.append(" ".repeat(take.toString().length + 1 - res.toString().length))
        var space = div.toString().length + 1 - res.toString().length
        if (lhv.toString().drop(res.toString().length) != "") {
            var min = take.toString().length
            while (min <= lhv.toString().length - 1) {
                take = res * 10 + lhv.toString()[min].toString().toInt()
                min++
                var l = take.toString().length
                if (res == 0) {
                    text.append("0$take\n")
                    l++
                } else text.append("$take\n")
                div = (take / rhv) * rhv
                res = take - div
                if (div.toString().length < l) {
                    text.append(
                        " ".repeat(space)
                                + " ".repeat(l - div.toString().length - 1) + "-$div\n"
                    )
                    text.append(" ".repeat(space) + "-".repeat(l) + "\n")
                    space += (l - res.toString().length)
                    text.append(" ".repeat(space))
                } else {
                    text.append(" ".repeat(space - 1) + "-$div\n")
                    text.append(" ".repeat(space - 1) + "-".repeat(l + 1) + "\n")
                    space += (l - res.toString().length)
                    text.append(" ".repeat(space))
                }
            }
            text.append("$res")
        } else text.append("$res")
        it.write(text.toString())
    }
}

