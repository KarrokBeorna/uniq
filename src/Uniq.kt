import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.io.File


fun main(args: Array<String>){
    ArgParser(args).parseInto(::Uniq).start()
}

class Uniq (parser: ArgParser) {

    private val ignore by parser.flagging("-i", help = "Игнорирование регистра")

    private val unique by parser.flagging("-u", help = "Уникальные строки")

    private val countStr by parser.flagging("-c", help = "Кол-во повторяющихся строк")

    private val skip by parser.storing("-s", help = "Игнорирование первых N символов") { toInt() }.default(0)

    private val oF by parser.storing("-o", help = "Файл назначения").default("")

    private val iF by parser.positional("Имя входного файла").default("")

    private val lines = mutableListOf<String>()


    fun start() {
        lines += inputData()
        outputData(launcher())
    }

    private fun inputData(): List<String> {
        val fileLinesOrCmd = mutableListOf<String>()
        if (iF.isNotEmpty()) {
            try {
                fileLinesOrCmd += File(iF).readLines()
            } catch (e: Exception) {
                println("Некорректное имя входного файла")
            }
        } else {
            var cmdInput = readLine()
            while (cmdInput != null) {
                fileLinesOrCmd.add(cmdInput)
                cmdInput = readLine()
            }
        }
        return fileLinesOrCmd
    }


    private fun outputData(entry: List<String>) {
        if (oF.isNotEmpty()) {
            try {
                File(oF).bufferedWriter().use {out -> entry.forEach { out.write("$it\n") }}
            } catch (e: Exception) {
                println("Некорректное имя выходного файла")
            }
        } else
            for (str in entry) println(str)
    }


    private fun checkIS(fString: String, sString: String): Boolean {
        val fS = fString.drop(skip)
        val sS = sString.drop(skip)

        return if (ignore) {
            fS.toLowerCase() == sS.toLowerCase()
        } else fS == sS
    }


    private val almostAnswer = mutableListOf<String>()

    private fun unique(): List<String> {
        if (!checkIS(lines[0], lines[1])) almostAnswer.add(lines[0])
        if (lines.size > 2) {
            for (i in 1..lines.size - 2) {
                if (!checkIS(lines[i], lines[i + 1]) && !checkIS(lines[i - 1], lines[i])) almostAnswer.add(lines[i])
            }
        }
        if (!checkIS(lines[lines.size - 1], lines[lines.size - 2])) almostAnswer.add(lines[lines.size - 1])
        return almostAnswer
    }

    private fun countStr(): List<String> {
        val last = lines.last()
        var count = 1
        for (i in 0..lines.size - 2) {
            val out = lines[i]
            if (checkIS(out, lines[i + 1])) {
                count++
            } else {
                almostAnswer.add("$count $out")
                count = 1
            }
            if (i == lines.size - 2) almostAnswer.add("$count $last")
        }
        return almostAnswer
    }


    private fun launcher(): List<String> {
        when (lines.size) {
            0 -> return emptyList()
            1 -> if (countStr) almostAnswer.add("1 ${lines[0]}") else return lines
            else -> {
                val last = lines.last()
                when {
                    unique -> return unique()
                    countStr -> return countStr()
                    else ->
                        for (i in 0..lines.size - 2) {
                            if (!checkIS(lines[i], lines[i + 1])) {
                                almostAnswer.add(lines[i])
                            } else if (i == lines.size - 2) almostAnswer.add(last)
                        }
                    }
                }
            }
        return almostAnswer
    }
}