package hr.caellian.arglib

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParsedArgumentsTestJUnit5 {
    val TestFilePath = "./file.test"
    val parsedArguments = ParsedArguments()

    init {
        parsedArguments.options["booleanValid"] = "true"
        parsedArguments.options["booleanInvalid"] = "nope"
        parsedArguments.options["byteValid"] = "5"
        parsedArguments.options["byteInvalid"] = "128"
        parsedArguments.options["shortValid"] = "100"
        parsedArguments.options["shortInvalid"] = "33000"
        parsedArguments.options["integerValid"] = "33000"
        parsedArguments.options["integerInvalid"] = "notAnInteger"
        parsedArguments.options["floatValid"] = "13.23"
        parsedArguments.options["floatInvalid"] = "notAFloat"
        parsedArguments.options["doubleValid"] = "73.167"
        parsedArguments.options["doubleInvalid"] = "notADouble"
        parsedArguments.options["bigIntegerValid"] = "24654725486652473456576548547666843562"
        parsedArguments.options["bigIntegerInvalid"] = "1.1"
        parsedArguments.options["bigDecimalValid"] = "2.4654725486652473456576548547666843562"
        parsedArguments.options["bigDecimalInvalid"] = "notADecimal"
        parsedArguments.options["stringValid"] = "This is a proper String."
        parsedArguments.options["testFilePath"] = TestFilePath
        parsedArguments.options["urlValid"] = "http://this.is/a/test"
        parsedArguments.options["urlInvalid"] = "www.this.is.a.test.com"
        parsedArguments.options["uriValid"] = "data:test%20data"
        parsedArguments.options["uriInvalid"] = "notAnURI"
    }

    @Test
    fun booleanTest() {
        assertThat(parsedArguments.getBoolean("booleanValid"))
                .isTrue()

        assertThat(parsedArguments.getBoolean("booleanInvalid"))
                .isNull()

        assertThat(parsedArguments.getBoolean("booleanInvalid", false))
                .isFalse()
    }

    @Test
    fun byteTest() {
        assertThat(parsedArguments.getByte("byteValid"))
                .isEqualTo(5)

        assertThat(parsedArguments.getByte("byteInvalid"))
                .isNull()

        assertThat(parsedArguments.getByte("byteInvalid", 2))
                .isEqualTo(2)
    }

    @Test
    fun shortTest() {
        assertThat(parsedArguments.getShort("shortValid"))
                .isEqualTo(100)

        assertThat(parsedArguments.getShort("shortInvalid"))
                .isNull()

        assertThat(parsedArguments.getShort("shortInvalid", 52))
                .isEqualTo(52)
    }

    @Test
    fun integerTest() {
        assertThat(parsedArguments.getInteger("integerValid"))
                .isEqualTo(33000)

        assertThat(parsedArguments.getInteger("integerInvalid"))
                .isNull()

        assertThat(parsedArguments.getInteger("integerInvalid", 2352))
                .isEqualTo(2352)
    }

    @Test
    fun floatTest() {
        assertThat(parsedArguments.getFloat("floatValid"))
                .isEqualTo(13.23f)

        assertThat(parsedArguments.getFloat("floatInvalid"))
                .isNull()

        assertThat(parsedArguments.getFloat("floatInvalid", 5.23f))
                .isEqualTo(5.23f)
    }

    @Test
    fun doubleTest() {
        assertThat(parsedArguments.getDouble("doubleValid"))
                .isEqualTo(73.167)

        assertThat(parsedArguments.getDouble("doubleInvalid"))
                .isNull()

        assertThat(parsedArguments.getDouble("doubleInvalid", 123.321))
                .isEqualTo(123.321)
    }

    @Test
    fun bigIntegerTest() {
        assertThat(parsedArguments.getBigInteger("bigIntegerValid"))
                .isEqualTo(BigInteger("24654725486652473456576548547666843562"))

        assertThat(parsedArguments.getBigInteger("bigIntegerInvalid"))
                .isNull()

        assertThat(parsedArguments.getBigInteger("bigIntegerInvalid", BigInteger("35653785667687634487643568946")))
                .isEqualTo(BigInteger("35653785667687634487643568946"))
    }

    @Test
    fun bigDecimalTest() {
        assertThat(parsedArguments.getBigDecimal("bigDecimalValid"))
                .isEqualTo(BigDecimal("2.4654725486652473456576548547666843562"))

        assertThat(parsedArguments.getBigDecimal("bigDecimalInvalid"))
                .isNull()

        assertThat(parsedArguments.getBigDecimal("bigDecimalInvalid", BigDecimal("5624734568765.346576636467664356863")))
                .isEqualTo(BigDecimal("5624734568765.346576636467664356863"))
    }

    @Test
    fun stringTest() {
        assertThat(parsedArguments.getString("stringValid"))
                .isEqualTo("This is a proper String.")

        assertThat(parsedArguments.getString("stringInvalid"))
                .isNull()

        assertThat(parsedArguments.getString("stringInvalid", "Valid default."))
                .isEqualTo("Valid default.")
    }

    @Test
    fun fileTest() {
        val testFile = File(TestFilePath)
        testFile.deleteOnExit()
        if (testFile.createNewFile()) {
            assertThat(parsedArguments.getFile("testFilePath"))
                    .exists()
            testFile.delete()
        } else {
            System.err.println("Unable to perform 'fileTest' test, file couldn't be created!")
        }
    }

    @Test
    fun fileNameTest() {
        val testFile = File(TestFilePath)
        testFile.deleteOnExit()
        if (testFile.createNewFile()) {
            assertThat(parsedArguments.getFileName("testFilePath"))
                    .isEqualTo("file.test")
            testFile.delete()
        } else {
            System.err.println("Unable to perform 'fileTest' test, file couldn't be created!")
        }
    }

    @Test
    fun urlTest() {
        assertThat(parsedArguments.getURL("urlValid"))
                .isNotNull()

        assertThat(parsedArguments.getURL("urlInvValid"))
                .isNull()
    }

    @Test
    fun uriTest() {
        assertThat(parsedArguments.getURI("uriValid"))
                .isNotNull()

        assertThat(parsedArguments.getURI("uriInvValid"))
                .isNull()
    }

    @Test
    fun inputStreamTest() {
        val testFile = File(TestFilePath)
        testFile.createNewFile()
        testFile.deleteOnExit()
        val ins = parsedArguments.getInputStream("testFilePath")
        assertThat(ins).isNotNull()
        ins?.close()
        testFile.delete()
    }

    @Test
    fun outputStreamTest() {
        val testFile = File(TestFilePath)
        testFile.createNewFile()
        testFile.deleteOnExit()
        val ous = parsedArguments.getOutputStream("testFilePath")
        assertThat(ous).isNotNull()
        ous?.close()
        testFile.delete()
    }
}
