package hr.caellian.arglib

import kotlin.Cloneable

/**
 * ParserConfiguration class contains fields which can be modified in order to tell the parser how to treat given
 * arguments.
 *
 * @since 1.0.0
 */
class ParserConfiguration : Cloneable {
    /**
     * Map of ( argument -> value ).
     * Default values of arguments if their arguments aren't given to [ArgLib.parse] method.
     *
     * @since 1.0.0
     */
    @JvmField
    val defaults = mutableMapOf<String, String>()

    /**
     * Map containing a list of valid case insensitive values for arguments. If given value is not contained
     * in the list corresponding to the argument it will be ignored. If [ignoreInvalidValues] is set to <code>true<code/>
     * an exception will be thrown.
     *
     * @since 1.1.0
     */
    @JvmField
    val validValues = mutableMapOf<String, List<String>>()

    /**
     * Determines whether invalid values should be ignored or a [InvalidArgumentValueException] should be thrown.
     */
    @JvmField
    var ignoreInvalidValues = true

    /**
     * These represent boolean arguments which are set to true if found and to false otherwise.
     *
     * If arguments in this list are also defined in defaults and they are parsed in arguments, their default value
     * will be inverted. This behaviour allows boolean flags to represent false value.
     *
     * @since 1.0.0
     */
    @JvmField
    val booleanFlags = mutableListOf<String>()

    /**
     * Map of ( alias -> argument ).
     * All aliases are replaced by corresponding argument in [ParsedArguments].
     *
     * @since 1.0.0
     */
    @JvmField
    val aliases = mutableMapOf<String, String>()

    /**
     * If any of [required] arguments isn't provided [IllegalArgumentException] will be thrown.
     * Use defaults instead of this whenever possible.
     *
     * @since 1.0.0
     */
    @JvmField
    val required = mutableListOf<String>()
}
