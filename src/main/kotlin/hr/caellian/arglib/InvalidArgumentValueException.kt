package hr.caellian.arglib

/**
 * Thrown when user gives an invalid argument value.
 *
 * [InvalidArgumentValueException] contains information about which argument has wrong value, wrong value in question
 * and a list of valid values in [invalidArgument], [invalidValue] and [validValues] fields respectively.
 *
 * This exception allows developers to handle incorrect argument values on their own as they please. Nonetheless, if
 * the exception isn't caught a helpful message will still be displayed to the user.
 *
 * @param invalidArgument argument containing invalid value.
 * @param invalidValue invalid value passed to parser.
 * @param validValues list of valid values.
 * @since 1.1.0
 */
class InvalidArgumentValueException(val invalidArgument: String, val invalidValue: String, val validValues: List<String>,
                                    message: String = "Argument '$invalidArgument' with '$invalidValue' value should have" +
                                        " one of following values: '${validValues.joinToString("', '")}'"): RuntimeException(message)
