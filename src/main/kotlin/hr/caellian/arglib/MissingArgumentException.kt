package hr.caellian.arglib

/**
 * Thrown when user fails to specify required arguments.
 *
 * [MissingArgumentException] contains a list of arguments which weren't specified by the user in [missingArguments]
 * field.
 *
 * @param missingArguments list of missing arguments.
 * @since 1.1.0
 */
class MissingArgumentException(val missingArguments: List<String>, message: String = "Missing arguments: ${missingArguments.joinToString(", ")}"): RuntimeException(message)
