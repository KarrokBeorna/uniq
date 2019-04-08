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

            val outputFile = output.readLines()
            assertEquals(listOf("BlackDesert"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }


    @Test
    fun u() {
        val output = File("output.txt")
        val argsU = arrayOf("-u", "-o", "output.txt", "src\\main\\Ex1.txt")
        main.main(argsU)
        try {

            val outputFile = output.readLines()
            assertEquals(listOf("BlackDesert", "blackdesert", "BLACKDESERT"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }


    @Test
    fun c() {
        val output = File("output.txt")
        val argsC = arrayOf("-c", "-o", "output.txt", "src\\main\\Ex1.txt")
        main.main(argsC)
        try {

            val outputFile = output.readLines()
            assertEquals(listOf("1 BlackDesert", "1 blackdesert", "1 BLACKDESERT"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }


    @Test
    fun s() {
        val output = File("output.txt")
        val argsS = arrayOf("-s", "3", "-o", "output.txt", "src\\main\\Ex2.txt")
        main.main(argsS)
        try {

            val outputFile = output.readLines()
            assertEquals(listOf("FufBDO"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }


    @Test
    fun us() {
        val output = File("output.txt")
        val argsS = arrayOf("-u", "-s", "3", "-o", "output.txt", "src\\main\\Ex3.txt")
        main.main(argsS)
        try {

            val outputFile = output.readLines()
            assertEquals(listOf("1506BDO", "2806BDO"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }


    @Test
    fun ius() {
        val output = File("output.txt")
        val argsS = arrayOf("-i", "-u", "-s", "3", "-o", "output.txt", "src\\main\\Ex4.txt")
        main.main(argsS)
        try {

            val outputFile = output.readLines()
            assertEquals(listOf("1506BDODimaS", "2806BDODiMaS"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }


    @Test
    fun ics() {
        val output = File("output.txt")
        val argsS = arrayOf("-i", "-c", "-s", "3", "-o", "output.txt", "src\\main\\Ex4.txt")
        main.main(argsS)
        try {

            val outputFile = output.readLines()
            assertEquals(listOf("1 1506BDODimaS", "2 0912BDODIMAS", "1 2806BDODiMaS"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }


    @Test
    fun ic() {
        val output = File("output.txt")
        val argsS = arrayOf("-i", "-c", "-o", "output.txt", "src\\main\\Ex5.txt")
        main.main(argsS)
        try {

            val outputFile = output.readLines()
            assertEquals(listOf("4 Black"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }
}