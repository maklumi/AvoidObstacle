package com.obstacleavoid.util.debug

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import com.badlogic.gdx.utils.Logger


object DebugCameraConfig {

    private val log: Logger = Logger(DebugCameraController::class.java.simpleName, Logger.DEBUG)

    // names/keys in key-value map or names of our values inside json file
    private const val MAX_ZOOM_IN = "maxZoomIn"
    private const val MAX_ZOOM_OUT = "maxZoomOut"
    private const val MOVE_SPEED = "moveSpeed"
    private const val ZOOM_SPEED = "zoomSpeed"

    private const val LEFT_KEY = "leftKey"
    private const val RIGHT_KEY = "rightKey"
    private const val UP_KEY = "upKey"
    private const val DOWN_KEY = "downKey"

    private const val ZOOM_IN_KEY = "zoomInKey"
    private const val ZOOM_OUT_KEY = "zoomOutKey"
    private const val RESET_KEY = "resetKey"
    private const val LOG_KEY = "logKey"

    // defaults
    private const val DEFAULT_LEFT_KEY: Int = Input.Keys.A
    private const val DEFAULT_RIGHT_KEY: Int = Input.Keys.D
    private const val DEFAULT_UP_KEY: Int = Input.Keys.W
    private const val DEFAULT_DOWN_KEY: Int = Input.Keys.S

    private const val DEFAULT_ZOOM_IN_KEY: Int = Input.Keys.COMMA
    private const val DEFAULT_ZOOM_OUT_KEY: Int = Input.Keys.PERIOD

    private const val DEFAULT_RESET_KEY: Int = Input.Keys.BACKSPACE
    private const val DEFAULT_LOG_KEY: Int = Input.Keys.ENTER

    private const val DEFAULT_MOVE_SPEED = 20.0f
    private const val DEFAULT_ZOOM_SPEED = 2.0f
    private const val DEFAULT_MAX_ZOOM_IN = 0.20f
    private const val DEFAULT_MAX_ZOOM_OUT = 30f

    // other
    private const val FILE_PATH = "debug/debugCameraConfig.json"

    // attributes
    var maxZoomIn = 0f
    var maxZoomOut = 0f
    var moveSpeed = 0f
    var zoomSpeed = 0f

    private var leftKey = 0
    private var rightKey = 0
    private var upKey = 0
    private var downKey = 0

    private var zoomInKey = 0
    private var zoomOutKey = 0

    private var resetKey = 0
    private var logKey = 0

    private val fileHandle: FileHandle = Gdx.files.internal(FILE_PATH)

    init {
        if (fileHandle.exists()) {
            load()
            log.info(toString())
        } else {
            log.info("Using defaults file does not exist= $FILE_PATH")
            setupDefaults()
        }
    }

    private fun load() {
        try {
            val reader = JsonReader()
            val root = reader.parse(fileHandle)
            maxZoomIn = root.getFloat(MAX_ZOOM_IN, DEFAULT_MAX_ZOOM_IN)
            maxZoomOut = root.getFloat(MAX_ZOOM_OUT, DEFAULT_MAX_ZOOM_OUT)
            moveSpeed = root.getFloat(MOVE_SPEED, DEFAULT_MOVE_SPEED)
            zoomSpeed = root.getFloat(ZOOM_SPEED, DEFAULT_ZOOM_SPEED)
            leftKey = getInputKeyValue(root, LEFT_KEY, DEFAULT_LEFT_KEY)
            rightKey = getInputKeyValue(root, RIGHT_KEY, DEFAULT_RIGHT_KEY)
            upKey = getInputKeyValue(root, UP_KEY, DEFAULT_UP_KEY)
            downKey = getInputKeyValue(root, DOWN_KEY, DEFAULT_DOWN_KEY)
            zoomInKey = getInputKeyValue(root, ZOOM_IN_KEY, DEFAULT_ZOOM_IN_KEY)
            zoomOutKey = getInputKeyValue(root, ZOOM_OUT_KEY, DEFAULT_ZOOM_OUT_KEY)
            resetKey = getInputKeyValue(root, RESET_KEY, DEFAULT_RESET_KEY)
            logKey = getInputKeyValue(root, LOG_KEY, DEFAULT_LOG_KEY)
        } catch (e: Exception) {
            log.error("Error loading $FILE_PATH using defaults.", e)
            setupDefaults()
        }
    }

    private fun setupDefaults() {
        maxZoomIn = DEFAULT_MAX_ZOOM_IN
        maxZoomOut = DEFAULT_MAX_ZOOM_OUT
        moveSpeed = DEFAULT_MOVE_SPEED
        zoomSpeed = DEFAULT_ZOOM_SPEED
        leftKey = DEFAULT_LEFT_KEY
        rightKey = DEFAULT_RIGHT_KEY
        upKey = DEFAULT_UP_KEY
        downKey = DEFAULT_DOWN_KEY
        zoomInKey = DEFAULT_ZOOM_IN_KEY
        zoomOutKey = DEFAULT_ZOOM_OUT_KEY
        resetKey = DEFAULT_RESET_KEY
        logKey = DEFAULT_LOG_KEY
    }

    fun isLeftPressed(): Boolean = Gdx.input.isKeyPressed(leftKey)
    fun isRightPressed(): Boolean = Gdx.input.isKeyPressed(rightKey)
    fun isUpPressed(): Boolean = Gdx.input.isKeyPressed(upKey)
    fun isDownPressed(): Boolean = Gdx.input.isKeyPressed(downKey)
    fun isZoomInPressed(): Boolean = Gdx.input.isKeyPressed(zoomInKey)
    fun isZoomOutPressed(): Boolean = Gdx.input.isKeyPressed(zoomOutKey)
    fun isResetPressed(): Boolean = Gdx.input.isKeyJustPressed(resetKey)
    fun isLogPressed(): Boolean = Gdx.input.isKeyJustPressed(logKey)


    override fun toString(): String {
        val separator = System.getProperty("line.separator")
        return "DebugCameraConfig : " + separator +
                "\tmaxZoomIn= " + maxZoomIn + separator +
                "\tmaxZoomOut= " + maxZoomOut + separator +
                "\tmoveSpeed= " + moveSpeed + separator +
                "\tzoomSpeed= " + zoomSpeed + separator +
                "\tleftKey= " + Input.Keys.toString(leftKey) + separator +
                "\trightKey= " + Input.Keys.toString(rightKey) + separator +
                "\tupKey= " + Input.Keys.toString(upKey) + separator +
                "\tdownKey= " + Input.Keys.toString(downKey) + separator +
                "\tzoomInKey= " + Input.Keys.toString(zoomInKey) + separator +
                "\tzoomOutKey= " + Input.Keys.toString(zoomOutKey) + separator +
                "\tresetKey= " + Input.Keys.toString(resetKey) + separator +
                "\tlogKey= " + Input.Keys.toString(logKey)
    }

    private fun getInputKeyValue(jsonValue: JsonValue, name: String, defaultInput: Int): Int {
        // get value with name from jsonValue (name-value map) if it does not exist use default
        val keyString = jsonValue.getString(name, Input.Keys.toString(defaultInput))
        // convert String into keycode
        return Input.Keys.valueOf(keyString)
    }
}