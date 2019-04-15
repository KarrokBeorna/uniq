import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class UniqTest {

    @Test
    fun i() {
        val output = File("output.txt")
        val argsI = arrayOf("-i", "-o", "output.txt", "test\\TestExamples\\Ex1.txt")
        main(argsI)
        try {

            val outputFile = output.readLines()
            assertEquals(listOf("BLACKDESERT"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }


    @Test
    fun u() {
        val output = File("output.txt")
        val argsU = arrayOf("-u", "-o", "output.txt", "test\\TestExamples\\Ex1.txt")
        main(argsU)
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
        val argsC = arrayOf("-c", "-o", "output.txt", "test\\TestExamples\\Ex1.txt")
        main(argsC)
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
        val argsS = arrayOf("-s", "3", "-o", "output.txt", "test\\TestExamples\\Ex2.txt")
        main(argsS)
        try {

            val outputFile = output.readLines()
            assertEquals(listOf("WoTBDO"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }


    @Test
    fun us() {
        val output = File("output.txt")
        val argsS = arrayOf("-u", "-s", "3", "-o", "output.txt", "test\\TestExamples\\Ex3.txt")
        main(argsS)
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
        val argsS = arrayOf("-i", "-u", "-s", "3", "-o", "output.txt", "test\\TestExamples\\Ex4.txt")
        main(argsS)
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
        val argsS = arrayOf("-i", "-c", "-s", "3", "-o", "output.txt", "test\\TestExamples\\Ex4.txt")
        main(argsS)
        try {

            val outputFile = output.readLines()
            assertEquals(listOf("1 1506BDODimaS", "2 0212BDOdimas", "1 2806BDODiMaS"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }


    @Test
    fun ic() {
        val output = File("output.txt")
        val argsS = arrayOf("-i", "-c", "-o", "output.txt", "test\\TestExamples\\Ex5.txt")
        main(argsS)
        try {

            val outputFile = output.readLines()
            assertEquals(listOf("4 blacK"), outputFile)

        } catch (e: Exception) {
            println("gg...")
        }
        output.delete()
    }
}