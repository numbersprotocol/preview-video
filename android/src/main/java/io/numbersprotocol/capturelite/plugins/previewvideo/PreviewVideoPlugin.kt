package io.numbersprotocol.capturelite.plugins.previewvideo

import android.graphics.RectF
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import org.json.JSONObject

@CapacitorPlugin(name = "PreviewVideo")
class PreviewVideoPlugin : Plugin() {
    private var previewVideos: HashMap<String, PreviewVideo> = HashMap()

    override fun load() {
        super.load()

//        this.bridge.webView.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                if (null != event) {
//                    val touchX = event.x
//                    val touchY = event.y
//
//                    previewVideos.forEach { entry ->
//                        val videoPreview = entry.value
//
//                        val videoPreviewRect = videoPreview.getBounds()
//                        if (videoPreviewRect.contains(touchX.toInt(), touchY.toInt())) {
//                            videoPreview.dispatchTouchEvent(event)
//                            return true
//                        }
//
//                    }
//
//                }
//
//                return v?.onTouchEvent(event) ?: true
//            }
//        })
    }

    @PluginMethod
    fun echo(call: PluginCall) {
        val value = call.getString("value")
        val ret = JSObject()
        ret.put("value", value)
        call.resolve(ret)
    }

    override fun handleOnStart() {
        super.handleOnStart()
        previewVideos.forEach { it.value.onStart() }
    }

    override fun handleOnResume() {
        super.handleOnResume()
        previewVideos.forEach { it.value.onResume() }
    }

    override fun handleOnPause() {
        super.handleOnPause()
        previewVideos.forEach { it.value.onPause() }

    }

    override fun handleOnStop() {
        super.handleOnStop()
        previewVideos.forEach { it.value.onStop() }
    }

    override fun handleOnDestroy() {
        super.handleOnDestroy()
        previewVideos.forEach { it.value.onDestroy() }
    }

    @PluginMethod
    fun create(call: PluginCall) {
        try {
            val id = call.getString("id")

            if (id.isNullOrEmpty()) {
                throw InvalidPreviewVideoIdError()
            }

            val src = call.getString("src")
            if (src.isNullOrEmpty()) {
                throw InvalidArgumentsError("config src is missing")
            }

            val configObject = call.getObject("config")
                ?: throw InvalidArgumentsError("config object is missing")

            val config = PreviewVideoConfig(configObject)

            if(previewVideos.contains(id)) {
                val oldPreviewVideo = previewVideos.remove(id)
                oldPreviewVideo?.destroy()
            }

            val newPreviewVideo = PreviewVideo(id, src, config, this)
            previewVideos[id] = newPreviewVideo

            call.resolve()
        } catch (e: PreviewVideoError) {
            handleError(call, e)
        } catch (e: Exception) {
            handleError(call, e)
        }
    }

    @PluginMethod
    fun destroy(call: PluginCall) {
        try {
            val id = call.getString("id")

            if (id.isNullOrEmpty()) {
                throw InvalidPreviewVideoIdError()
            }

            var removedPreviewVideo = previewVideos.remove(id)
                ?: throw PreviewVideoNotFoundError()
            removedPreviewVideo.destroy()

            call.resolve()
        } catch (e: PreviewVideoError) {
            handleError(call, e)
        } catch (e: Exception) {
            handleError(call, e)
        }
    }

    @PluginMethod
    fun onScroll(call: PluginCall) {
        try {
            val id = call.getString("id")
            if (id.isNullOrEmpty()) {
                throw InvalidPreviewVideoIdError()
            }

            val previewVideo = previewVideos[id] ?: throw PreviewVideoNotFoundError()

            val boundsObj = call.getObject("previewVideoBounds")
                ?: throw InvalidArgumentsError("previewVideoBounds object is missing")

            val bounds = boundsObjectToRect(boundsObj)

            previewVideo.updateRender(bounds)

            call.resolve()
        } catch (e: PreviewVideoError) {
            handleError(call, e)
        } catch (e: Exception) {
            handleError(call, e)
        }
    }

    private fun handleError(call: PluginCall, e: Exception) {
        val error: PreviewVideoErrorObject = getErrorObject(e)
        Log.w(TAG, error.toString())
        call.reject(error.message, error.code.toString(), e)
    }

    private fun handleError(call: PluginCall, e: PreviewVideoError) {
        val error: PreviewVideoErrorObject = getErrorObject(e)
        Log.w(TAG, error.toString())
        call.reject(error.message, error.code.toString())
    }

    private fun boundsObjectToRect(jsonObject: JSONObject): RectF {
        if (!jsonObject.has("width")) {
            throw InvalidArgumentsError("PreviewVideoConfig object is missing the required 'width' property")
        }
        if (!jsonObject.has("height")) {
            throw InvalidArgumentsError("PreviewVideoConfig object is missing the required 'height' property")
        }
        if (!jsonObject.has("x")) {
            throw InvalidArgumentsError("PreviewVideoConfig object is missing the required 'x' property")
        }
        if (!jsonObject.has("y")) {
            throw InvalidArgumentsError("PreviewVideoConfig object is missing the required 'y' property")
        }

        val width = jsonObject.getInt("width")
        val height = jsonObject.getInt("height")
        val x = jsonObject.getInt("x")
        val y = jsonObject.getInt("y")

        return RectF(x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + height).toFloat())
    }


    companion object {
        private const val TAG = "PreviewCameraFragment"
    }
}