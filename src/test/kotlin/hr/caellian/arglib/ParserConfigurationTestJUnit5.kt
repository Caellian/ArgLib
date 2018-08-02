package hr.caellian.arglib

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ParserConfigurationTestJUnit5 {
    val conf = ParserConfiguration()

    @Test
    fun defaultTest() {
        conf.defaults["test"] = "true"

        val parsed = ArgLib.parse(emptyList(), conf)
        assertThat(parsed.getBoolean("test", false))
                .isTrue()
    }

    @Test
    fun validValuesTest() {
        conf.validValues["test"] = listOf("A", "B", "C")

        var parsed = ArgLib.parse(listOf("-test", "A"), conf)

        assertThat(parsed.getString("test", "B"))
                .isEqualTo("A")

        parsed = ArgLib.parse(listOf("-test", "D"), conf)

        assertThat(parsed.getString("test"))
                .isNull()

        conf.ignoreInvalidValues = false

        try {
            parsed = ArgLib.parse(listOf("-test", "D"), conf)

            fail("Expected 'InvalidArgumentValueException' to be thrown.")
        } catch (e: InvalidArgumentValueException) {
            assertThat(e).isInstanceOf(InvalidArgumentValueException::class.java)
        }
    }

    @Test
    fun booleanFlagsTest() {
        conf.booleanFlags += "test"

        var parsed = ArgLib.parse(listOf("-test"), conf)

        assertThat(parsed.getBoolean("test", false))
                .isTrue()

        conf.defaults["test"] = "false"

        parsed = ArgLib.parse(listOf("-test"), conf)

        assertThat(parsed.getBoolean("test", true))
                .isFalse()
    }

    @Test
    fun aliasesTest() {
        conf.aliases["alias"] = "test"

        val parsed = ArgLib.parse(listOf("-alias", "true"), conf)

        assertThat(parsed.getBoolean("test", false))
                .isTrue()
    }

    @Test
    fun requiredTest() {
        conf.required += "test"

        try {
            val parsed = ArgLib.parse(emptyList(), conf)

            fail("Expected 'MissingArgumentException' to be thrown.")
        } catch (e: MissingArgumentException) {
            assertThat(e).isInstanceOf(MissingArgumentException::class.java)
        }
    }
}
