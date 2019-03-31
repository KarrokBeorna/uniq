package main

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.io.File
import java.util.*


fun main(args: Array<String>)   {
    ArgParser(args).parseInto(::Uniq).start()
}

class Uniq (parser: ArgParser) {

    private val i by parser.flagging("-i", help = "Игнорирование регистра" )

    private val u by parser.flagging("-u", help = "Уникальные строки" )

    private val c by parser.flagging("-c", help = "Кол-во повторяющихся строк" )

    private val s by parser.storing("-s", help = "Игнорирование первых N символов" ){ toInt() }.default(0)

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
                System.out.println("Некорректное имя входного файла")
                return emptyList()
            }
        } else {
            var cmdIn = ""
            while (cmdIn != "stop")
                cmdIn = cmdInput()
            fileLinesOrCmd.add(cmdIn)
        }
        return fileLinesOrCmd
    }


    private fun cmdInput(): String {
        val input = Scanner(System.`in`)
        return input.nextLine()
    }


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
                System.out.println("Некорректное имя выходного файла")
            }
        } else
            for (str in entry)
                System.out.println(str)
    }


    private fun checkIAndS(fString: String, sString: String): Boolean {
        val fS = fString.removeRange(0, s)
        val sS = sString.removeRange(0, s)
        return if (i) {
            fS.toLowerCase() == sS.toLowerCase()
        } else fS == sS
    }

    private fun copyCheck(index: Int, str: String): Boolean {
        val check = true
        for(i in 0 until lines.size) {
            if (checkIAndS(str, lines[i]) && index != i) !check; break
        }
        return check
    }

    private fun launcher(): List<String> {
        val almostAnswer = mutableListOf<String>()
        var str = lines[0]
        var count = 1
        if (u) {
            for (i in 0 until lines.size) {
                if (copyCheck(i, lines[i])) almostAnswer.add(lines[i])
            }
        } else {
            if (c) {
                for (line in lines) {
                    if (checkIAndS(str, line)) count++
                    else {
                        almostAnswer.add("$count $str")
                        str = line
                        count = 1
                    }
                }
            } else almostAnswer += lines
        }
        return almostAnswer
    }
}

