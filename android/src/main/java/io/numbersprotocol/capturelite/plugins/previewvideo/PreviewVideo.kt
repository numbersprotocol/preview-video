package io.numbersprotocol.capturelite.plugins.previewvideo

import android.util.Log

class PreviewVideo {
    fun echo(value: String?): String? {
        Log.i("Echo", value!!)
        return value
    }
}