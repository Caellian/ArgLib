package hr.caellian.arglib

/**
 * ArgLib object exposes two variants of [parse] method which create [ParsedArguments] objects.
 * Both [parse] methods accept [ParserConfiguration] which dictates rules parser will follow while parsing arguments.
 *
 * Arguments passed to ArgLib must be tokenized. An array consisting of one string containing all arguments will not be
 * handled properly. ArgLib cam support strings spread out through array elements if they are enclosed in either single
 * or double quotation marks.
 */
object ArgLib {
    /**
     * Current version of the library.
     */
    val Version = "1.1.0"

    /**
     * Parses given argument array and returns [ParsedArguments] which can be then used to recognise parameter options.
     *
     * @param arguments array containing arguments.
     * @param configuration configuration object defining parser behaviour.
     * @since 1.0.0
     */
    @JvmOverloads
    @JvmStatic
    fun parse(arguments: Array<String>, configuration: ParserConfiguration = ParserConfiguration()): ParsedArguments = parse(arguments.toList(), configuration)

    /**
     * Parses given argument list and returns [ParsedArguments] which can be then used to recognise parameter options.
     *
     * @param arguments array containing arguments.
     * @param configuration configuration object defining parser behaviour.
     * @since 1.0.0
     */
    @JvmOverloads
    @JvmStatic
    fun parse(arguments: List<String>, configuration: ParserConfiguration = ParserConfiguration()): ParsedArguments {
        val result = ParsedArguments()
        result.options.putAll(configuration.booleanFlags.associate { it to "false" })
        result.options.putAll(configuration.defaults.filterNot { configuration.booleanFlags.contains(it.key) })

        val invalidValues = mutableMapOf<String, String>()
        var value = false
        var of: String? = null

        var buildingString = false
        var stringChar = '"'
        var builder = ""

        arguments.forEach {
            if (buildingString) {
                if (!it.endsWith("\\$stringChar") && it.endsWith(stringChar)) {
                    builder += " ${it.substring(0, it.lastIndex)}"

                    of?.let { of ->
                        when (configuration.validValues[of]) {
                            null -> result.options[of] = builder
                            else -> {
                                configuration.validValues[of]?.let {
                                    if (!it.map { it.toLowerCase() }.contains(builder.toLowerCase())) {
                                        invalidValues[of] = builder
                                    } else {
                                        result.options[of] = builder
                                    }
                                }
                            }
                        }
                    }

                    builder = ""
                    value = false
                    of = null
                } else {
                    builder += " $it"
                }
            } else if (!value) {
                val clean = it.removePrefix("-")

                if (configuration.booleanFlags.contains(clean) ||
                        configuration.aliases[clean]?.let { configuration.booleanFlags.contains(it) } == true) {
                    result.options[clean] = if (parseBoolean(configuration.defaults[clean] ?: "true") == false) {
                        "false"
                    } else {
                        "true"
                    }
                    value = false
                    of = null
                } else {
                    of = if (configuration.aliases.contains(clean)) {
                        configuration.aliases[clean]
                    } else {
                        clean
                    }

                    value = true
                }
            } else {
                if (it.startsWith("\"") && !it.endsWith("\"")) {
                    buildingString = true
                    stringChar = '"'
                    builder += it.substring(1)
                } else if (it.startsWith("'") && !it.endsWith("'")) {
                    buildingString = true
                    stringChar = '\''
                    builder += it.substring(1)
                } else {

                    of?.let { of ->
                        when (configuration.validValues[of]) {
                            null -> {
                                result.options[of] = if (it.startsWith("\"") && it.endsWith("\"")
                                        || it.startsWith("'") && it.endsWith("'")) {
                                    it.substring(1, it.lastIndex)
                                } else {
                                    it
                                }
                            }
                            else -> {
                                configuration.validValues[of]?.let { valid ->
                                    if (!valid.map { it.toLowerCase() }.contains(it.toLowerCase())) {
                                        invalidValues[of] = if (it.startsWith("\"") && it.endsWith("\"")
                                                || it.startsWith("'") && it.endsWith("'")) {
                                            it.substring(1, it.lastIndex)
                                        } else {
                                            it
                                        }
                                    } else {
                                        result.options[of] = if (it.startsWith("\"") && it.endsWith("\"")
                                                || it.startsWith("'") && it.endsWith("'")) {
                                            it.substring(1, it.lastIndex)
                                        } else {
                                            it
                                        }
                                    }
                                }
                            }
                        }
                    }

                    value = false
                    of = null
                }
            }
        }

        // Handle invalid argument values if necessary
        if (!configuration.ignoreInvalidValues) {
            invalidValues.forEach { argument, current ->
                throw InvalidArgumentValueException(argument, current, configuration.validValues[argument]!!)
            }
        }

        // Check if any required arguments are missing and name them.
        val missing = configuration.required.filter { !result.options.containsKey(it) }
        if (missing.isNotEmpty()) {
            throw MissingArgumentException(missing)
        }

        return result
    }

    internal fun parseBoolean(value: String): Boolean? {
        return when (value.toLowerCase()) {
            "true" -> true
            "false" -> false
            "yes" -> true
            "no" -> false
            "t" -> true
            "f" -> false
            "y" -> true
            "n" -> false
            "1" -> true
            "0" -> false
            else -> null
        }
    }
}
