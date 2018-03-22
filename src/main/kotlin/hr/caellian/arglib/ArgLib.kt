package hr.caellian.arglib

/**
 * ArgLib object exposes two variants of [parse] method which create [ParsedArguments] objects.
 * Both [parse] methods accept [ParserConfiguration] which dictates rules parser will follow while parsing arguments.
 */
object ArgLib {
    /**
     * Parses given argument array and returns [ParsedArguments] which can be then used to recognise parameter options.
     *
     * @since 1.0.0
     */
    @JvmOverloads
    @JvmStatic
    fun parse(args: Array<String>, config: ParserConfiguration = ParserConfiguration()): ParsedArguments = parse(args.toList(), config)

    /**
     * Parses given argument list and returns [ParsedArguments] which can be then used to recognise parameter options.
     *
     * @since 1.0.0
     */
    @JvmOverloads
    @JvmStatic
    fun parse(args: List<String>, config: ParserConfiguration = ParserConfiguration()): ParsedArguments {
        val result = ParsedArguments()
        result.options.putAll(config.defaults)

        var value = false
        var of: String? = null

        var buildingString = false
        var stringChar = '"'
        var builder = ""

        args.forEach {
            if (buildingString) {
                if (!it.endsWith("\\$stringChar") && it.endsWith(stringChar)) {
                    builder += " ${it.substring(0, it.lastIndex)}"

                    of?.let { of ->
                        result.options[of] = builder
                    }

                    builder = ""
                    value = false
                    of = null
                } else {
                    builder += " $it"
                }
            } else if (!value) {
                val clean = it.removePrefix("-")

                if (config.booleanFlags.contains(clean) ||
                        config.aliases[clean]?.let { config.booleanFlags.contains(it) } == true) {
                    result.options[clean] = "true"
                    value = false
                    of = null
                } else {
                    of = if (config.aliases.contains(clean)) {
                        config.aliases[clean]
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
                        result.options[of] = if (it.startsWith("\"") && it.endsWith("\"")
                                || it.startsWith("'") && it.endsWith("'")) {
                            it.substring(1, it.lastIndex)
                        } else {
                            it
                        }
                    }

                    value = false
                    of = null
                }
            }
        }

        // If any bool flags haven't been set to true, set them to false
        config.booleanFlags.forEach { result.options.putIfAbsent(it, false.toString()) }

        // Check if any required arguments are missing and name them.
        val missing = config.required.filter { !result.options.containsKey(it) }
        if (missing.isNotEmpty()) {
            throw IllegalArgumentException("Missing arguments: ${missing.joinToString(", ")}")
        }

        return result
    }
}