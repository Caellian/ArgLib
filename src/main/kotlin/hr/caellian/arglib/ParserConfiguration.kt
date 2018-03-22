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
     * Default values of arguments if their arguments aren't given to [ArgLib.parse] method.
     *
     * @since 1.0.0
     */
    val defaults = mutableMapOf<String, String>()

    /**
     * These represent boolean arguments which are set to true if found and to false otherwise.
     *
     * @since 1.0.0
     */
    val booleanFlags = mutableListOf<String>()

    /**
     * Map of ( alias -> argument ).
     * All aliases are replaced by corresponding argument in [ParsedArguments].
     *
     * @since 1.0.0
     */
    val aliases = mutableMapOf<String, String>()

    /**
     * If any of [required] arguments isn't provided [IllegalArgumentException] will be thrown.
     * Use defaults instead of this whenever possible.
     *
     * @since 1.0.0
     */
    val required = mutableListOf<String>()
}