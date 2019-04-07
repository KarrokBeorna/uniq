package main

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.io.File
import java.util.*


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
                val fileLines = File(iF).readLines()
                for (str in fileLines)
                    fileLinesOrCmd.add(str)
            } catch (e:Exception){
                println("Некорректное имя входного файла")
                return emptyList()
            }
        } else {
            while (cmdInput() != "стоп") fileLinesOrCmd.add(cmdInput())
        }
        return fileLinesOrCmd
    }


    private fun cmdInput(): String = readLine()!!


    private fun outputData(entry: List<String>) {

        if (oF.isNotEmpty()) {
            try {
                val outputFile = File(oF).bufferedWriter()
                for (line in entry) {
                    outputFile.write(line)
                    outputFile.newLine()
                }
                outputFile.close()
            } catch(e: Exception) {
                println("Некорректное имя выходного файла")
            }
        } else
            for (str in entry)
                println(str)
    }


    private fun checkIAndS(fString: String, sString: String): Boolean {
        val fS = fString.removeRange(0, skip)
        val sS = sString.removeRange(0, skip)

        return if (ignore) {
            fS.toLowerCase() == sS.toLowerCase()
        } else fS == sS
    }


    private fun launcher(): List<String> {
        val almostAnswer = mutableListOf<String>()
        var str = 0
        val last = lines.last()
        var count = 0
        if (unique) {
            val temp = lines
            for (i in 0 until lines.size)
                for (k in i until lines.size){
                    if (checkIAndS(lines[k], lines[i]) && k != i) temp.remove(lines[k])
                }
            return temp
        } else {
            if (countStr) {
                for (i in 0 until lines.size) {
                    val out = lines[str]
                    if (checkIAndS(out, lines[i])) {
                        count++
                        if (i == lines.size - 1) almostAnswer.add("$count $out")
                    }
                    else {
                        almostAnswer.add("$count $out")
                        str = i
                        count = 1
                        if (i == lines.size - 1) almostAnswer.add("$count $last")
                    }
                }
            } else {
                for (i in 0 until lines.size) {
                    if (!checkIAndS(lines[str], lines[i])) {
                        almostAnswer.add(lines[str])
                        str = i
                    }
                    else if (i == lines.size -1) almostAnswer.add(lines[str])
                }
            }
        }
        return almostAnswer
    }
}