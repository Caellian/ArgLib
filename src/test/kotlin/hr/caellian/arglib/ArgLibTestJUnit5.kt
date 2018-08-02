package hr.caellian.arglib

import org.junit.jupiter.api.TestInstance
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ArgLibTestJUnit5 {

    @Test
    fun parse() {
        val args = listOf("-test", "successful", "-boolTest")

        val conf = ParserConfiguration()
        conf.aliases["test"] = "aliased"
        conf.booleanFlags += "boolTest"
        val parsed = ArgLib.parse(args, conf)

        // If both of these assertions work, parse method definitely works.
        assertThat(parsed.getString("aliased", "unsuccessful")).isEqualTo("successful")
        assertThat(parsed.getBoolean("boolTest", false)).isTrue()
    }
}
