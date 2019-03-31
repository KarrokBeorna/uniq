package main

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

internal class UniqTest{

    @Test
    fun i() {
        val output = File("output.txt")
        val argsI = arrayOf("-i", "-o", "output.txt", "src\\main\\Ex1.txt")
        main.main(argsI)
        try {
            val outputFile = FileReader(output)
            val outBuf = BufferedReader(outputFile)
            outputFile.close()

            val inputFile = FileReader("src\\main\\Ex1")
            val inBuf = BufferedReader(inputFile)
            inputFile.close()
            assertEquals(outBuf, inBuf)
        } catch (e: Exception) {
            System.out.println("gg...")
        }
        output.delete()
    }
}