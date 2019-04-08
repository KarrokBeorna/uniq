package main

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.io.File


fun main(args: Array<String>){
    ArgParser(args).parseInto(::Uniq).start()
}

class Uniq (parser: ArgParser) {

    private val ignore by parser.flagging("-i", help = "Игнорирование регистра" )

    private val unique by parser.flagging("-u", help = "Уникальные строки" )

    private val countStr by parser.flagging("-c", help = "Кол-во повторяющихся строк" )

    private val skip by parser.storing("-s", help = "Игнорирование первых N символов" ){ toInt() }.default(0)

    private val oF by parser.storing("-o",help = "Файл назначения").default("")

    private val iF by parser.positional( "Имя входного файла").default("")

    private val lines = mutableListOf<String>()


    fun start(){
        lines += inputData()
        val answer = launcher()
        outputData(answer)
    }

    private fun inputData(): List<String> {

        val fileLinesOrCmd = mutableListOf<String>()
        if (iF.isNotEmpty()) {
            try {
                fileLinesOrCmd += File(iF).readLines()
            } catch (e:Exception){
                println("Некорректное имя входного файла")
                return emptyList()
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
                val output = File(oF).bufferedWriter()
                entry.forEach {output.write(it); output.newLine()}
                output.close()
            } catch(e: Exception) {
                println("Некорректное имя выходного файла")
            }
        } else
            for (str in entry) println(str)
    }


    private fun checkIS(fString: String, sString: String): Boolean {
        val fS = fString.removeRange(0, skip)
        val sS = sString.removeRange(0, skip)

        return if (ignore) {
            fS.toLowerCase() == sS.toLowerCase()
        } else fS == sS
    }


    private fun launcher(): List<String> {
        val almostAnswer = mutableListOf<String>()
        if (unique) {
            for (i in 0 until lines.size) {
                when (i) {
                    0 -> if (!checkIS(lines[i], lines[i + 1])) almostAnswer.add(lines[i])
                    lines.size - 1 -> if (!checkIS(lines[i - 1], lines[i])) almostAnswer.add(lines[i])
                    else -> if (!checkIS(lines[i], lines[i + 1]) && !checkIS(lines[i - 1], lines[i])) almostAnswer.add(lines[i])
                }
            }
        } else {
            var str = 0
            val last = lines.last()
            if (countStr) {
                var count = 0
                for (i in 0 until lines.size) {
                    val out = lines[str]                                             //вынужден оставить как
                    if (checkIS(out, lines[i])) {                                    //"$count $out", так и
                        count++                                                      //"$count $last", потому что out и
                        if (i == lines.size - 1) almostAnswer.add("$count $out")     //last могут быть разными,
                    }                                                                //следовательно, и вывод будет
                    else {                                                           //разным
                        almostAnswer.add("$count $out")
                        str = i
                        count = 1
                        if (i == lines.size - 1) almostAnswer.add("$count $last")
                    }
                }
            } else {
                for (i in 0 until lines.size) {
                    if (!checkIS(lines[str], lines[i])) {
                        almostAnswer.add(lines[str])
                        str = i
                        if (i == lines.size - 1) almostAnswer.add(lines[i])
                    }
                    else if (i == lines.size - 1) almostAnswer.add(lines[str])
                }
            }
        }
        return almostAnswer
    }
}