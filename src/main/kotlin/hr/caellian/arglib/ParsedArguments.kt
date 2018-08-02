package hr.caellian.arglib

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.math.BigDecimal
import java.math.BigInteger
import java.net.MalformedURLException
import java.net.URI
import java.net.URL

/**
 * ParsedArguments contains a map of parsed arguments and their values. The map can be accessed directly but
 * [ParsedArguments] class also provides convenience methods to directly turn arguments into expected types.
 * If any of those convenience methods fail in any way, <code>null</code> is returned which can be handled however is
 * appropriate.
 *
 * @since 1.0.0
 */
class ParsedArguments internal constructor() {
    val options = mutableMapOf<String, String> ()

    /**
     * Returns value of boolean argument or flag.
     *
     * @param key name of the argument.
     * @since 1.1.0
     */
    fun getBoolean(key: String): Boolean? {
        return options[key]?.let {
            ArgLib.parseBoolean(it)
        }
    }

    /**
     * Returns value of boolean argument or flag.
     * 
     * @param key name of the argument.
     * @param default value to return if argument is invalid or missing.
     * @since 1.0.0
     */
    fun getBoolean(key: String, default: Boolean): Boolean {
        return getBoolean(key) ?: default
    }

    /**
     * Returns byte value of given argument.
     * 
     * @param key name of the argument.
     * @since 1.1.0
     */
    fun getByte(key: String): Byte? {
        return options[key]?.toByteOrNull()
    }

    /**
     * Returns byte value of given argument.
     *
     * @param key name of the argument.
     * @param default value to return if argument is invalid or missing.
     * @since 1.0.0
     */
    fun getByte(key: String, default: Byte): Byte {
        return getByte(key) ?: default
    }

    /**
     * Returns short value of given argument.
     *
     * @param key name of the argument.
     * @since 1.1.0
     */
    fun getShort(key: String): Short? {
        return options[key]?.toShortOrNull()
    }

    /**
     * Returns short value of given argument.
     * 
     * @param key name of the argument.
     * @param default value to return if argument is invalid or missing.
     * @since 1.0.0
     */
    fun getShort(key: String, default: Short): Short {
        return getShort(key) ?: default
    }

    /**
     * Returns integer value of given argument.
     *
     * @param key name of the argument.
     * @since 1.1.0
     */
    fun getInteger(key: String): Int? {
        return options[key]?.toIntOrNull()
    }

    /**
     * Returns integer value of given argument.
     * 
     * @param key name of the argument.
     * @param default value to return if argument is invalid or missing.
     * @since 1.0.0
     */
    fun getInteger(key: String, default: Int): Int {
        return getInteger(key) ?: default
    }

    /**
     * Returns float value of given argument.
     * 
     * @param key name of the argument.
     * @since 1.0.0
     */
    fun getFloat(key: String): Float? {
        return options[key]?.toFloatOrNull()
    }

    /**
     * Returns float value of given argument.
     *
     * @param key name of the argument.
     * @param default value to return if argument is invalid or missing.
     * @since 1.1.0
     */
    fun getFloat(key: String, default: Float): Float {
        return getFloat(key) ?: default
    }

    /**
     * Returns double value of given argument.
     * 
     * @param key name of the argument.
     * @since 1.0.0
     */
    fun getDouble(key: String): Double? {
        return options[key]?.toDoubleOrNull()
    }

    /**
     * Returns double value of given argument.
     *
     * @param key name of the argument.
     * @param default value to return if argument is invalid or missing.
     * @since 1.1.0
     */
    fun getDouble(key: String, default: Double): Double {
        return getDouble(key) ?: default
    }

    /**
     * Returns integer value of given argument in form of [BigInteger].
     * 
     * @param key name of the argument.
     * @since 1.0.0
     */
    fun getBigInteger(key: String): BigInteger? {
        return options[key]?.toBigIntegerOrNull()
    }

    /**
     * Returns integer value of given argument in form of [BigInteger].
     *
     * @param key name of the argument.
     * @param default value to return if argument is invalid or missing.
     * @since 1.1.0
     */
    fun getBigInteger(key: String, default: BigInteger): BigInteger {
        return getBigInteger(key) ?: default
    }

    /**
     * Returns decimal value of given argument in form of [BigDecimal].
     * 
     * @param key name of the argument.
     * @since 1.0.0
     */
    fun getBigDecimal(key: String): BigDecimal? {
        return options[key]?.toBigDecimalOrNull()
    }

    /**
     * Returns decimal value of given argument in form of [BigDecimal].
     *
     * @param key name of the argument.
     * @param default value to return if argument is invalid or missing.
     * @since 1.1.0
     */
    fun getBigDecimal(key: String, default: BigDecimal): BigDecimal {
        return getBigDecimal(key) ?: default
    }

    /**
     * Returns string value of given argument.
     * 
     * @param key name of the argument.
     * @since 1.0.0
     */
    fun getString(key: String): String? {
        return options[key]
    }

    /**
     * Returns string value of given argument.
     *
     * @param key name of the argument.
     * @param default value to return if argument is invalid or missing.
     * @since 1.1.0
     */
    fun getString(key: String, default: String): String {
        return getString(key) ?: default
    }

    /**
     * Returns value of given argument constructed to [File] object.
     * If [File] is invalid, instead of [NullPointerException] being thrown,
     * file constructed from default if specified or <code>null</code> is returned.
     *
     * @param key name of the argument.
     * @param default path name to create [File] object from if argument is not given.
     * @since 1.0.0
     */
    @JvmOverloads
    fun getFile(key: String, default: String? = null): File? {
        return try {
            if (options.containsKey(key)) File(options[key]) else File(default)
        } catch (npe: NullPointerException) {
            null
        }
    }

    /**
     * Returns name of the file specified in argument corresponding to given name.
     * If file doesn't exist or is invalid, default or <code>null</code> is returned.
     * 
     * @param key name of the argument.
     * @param default value to return if argument is invalid or missing.
     * @since 1.0.0
     */
    @JvmOverloads
    fun getFileName(key: String, default: String? = null): String? {
        return getFile(key)?.name ?: default
    }

    /**
     * Returns value of given argument in form of [URL].
     * 
     * @param key name of the argument.
     * @param default value to try to construct [URL] from if argument is missing.
     * @since 1.0.0
     */
    @JvmOverloads
    fun getURL(key: String, default: String? = null): URL? {
        return try {
            URL(options[key] ?: default)
        } catch (npe: NullPointerException) {
            null
        } catch (mue: MalformedURLException) {
            null
        }
    }

    /**
     * Returns value of given argument in form of [URI].
     * 
     * @param key name of the argument.
     * @param default value to try to construct [URI] from if argument is missing.
     * @since 1.0.0
     */
    @JvmOverloads
    fun getURI(key: String, default: String? = null): URI? {
        return try {
            URI(options[key] ?: default)
        } catch (npe: NullPointerException) {
            null
        } catch (mue: MalformedURLException) {
            null
        }
    }

    /**
     * Opens [FileInputStream] on file given in argument. If the file doesn't
     * exist or the argument or default points to a directory, <code>null</code>
     * is returned.
     *
     * @exception SecurityException if a security manager exists and its
     * <code>checkRead</code> method denies read access to the file.
     *
     * @param key name of the argument.
     * @param default path name to [File] to open [FileInputStream] from if argument is not given.
     * @since 1.0.0
     */
    @JvmOverloads
    fun getInputStream(key: String, default: String? = null): FileInputStream? {
        return try {
            getFile(key, default)?.inputStream()
        } catch (npe: NullPointerException) {
            null
        } catch (mue: FileNotFoundException) {
            null
        }
    }


    /**
     * Opens [FileOutputStream] on file given in argument. If the file doesn't
     * exist or the argument or default points to a directory, <code>null</code>
     * is returned.
     *
     * @exception SecurityException if a security manager exists and its
     * <code>checkWrite</code> method denies write access to the file.
     *
     * @param key name of the argument.
     * @param default path name to [File] to open [FileOutputStream] from if argument is not given.
     * @since 1.0.0
     */
    @JvmOverloads
    fun getOutputStream(key: String, default: String? = null): FileOutputStream? {
        return try {
            getFile(key, default)?.outputStream()
        } catch (mue: NullPointerException) {
            null
        } catch (mue: FileNotFoundException) {
            null
        }
    }
}
