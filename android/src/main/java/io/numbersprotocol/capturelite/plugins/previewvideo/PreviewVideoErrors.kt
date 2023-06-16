package io.numbersprotocol.capturelite.plugins.previewvideo

import org.json.JSONObject

enum class PreviewVideoErrors {
    UNHANDLED_ERROR,
    INVALID_PREVIEW_VIDEO_ID,
    PREVIEW_VIDEO_NOT_FOUND,
    INVALID_ARGUMENTS,
    PREVIEW_VIDEO_NOT_AVAILABLE
}

class PreviewVideoErrorObject(
    val code: Int,
    val message: String,
    val extra: HashMap<String, Any> = HashMap()
) {
    private fun asJSONObject(): JSONObject {
        val returnJSONObject = JSONObject()

        returnJSONObject.put("code", code)
        returnJSONObject.put("message", message)
        returnJSONObject.put("extra", extra)

        return returnJSONObject
    }

    override fun toString(): String {
        return this.asJSONObject().toString()
    }
}

fun getErrorObject(err: PreviewVideoError): PreviewVideoErrorObject {
    return when (err) {
        is InvalidArgumentsError -> {
            PreviewVideoErrorObject(
                err.getErrorCode(),
                "Invalid Arguments Provided: ${err.message}."
            )
        }

        is InvalidPreviewVideoIdError -> {
            PreviewVideoErrorObject(err.getErrorCode(), "Missing or invalid preview Video id.")
        }

        is PreviewVideoNotFoundError -> {
            PreviewVideoErrorObject(err.getErrorCode(), "Preview Video not found for provided id.")
        }

        is PreviewVideoNotAvailableError -> {
            PreviewVideoErrorObject(err.getErrorCode(), "Preview Video is not available.")
        }

        else -> {
            PreviewVideoErrorObject(err.getErrorCode(), "Unhandled Error: ${err.message}.")
        }
    }
}

fun getErrorObject(err: Exception): PreviewVideoErrorObject {
    return PreviewVideoErrorObject(0, "Unknown Error: ${err.message}")
}

open class PreviewVideoError(message: String? = "") : Throwable(message) {
    open fun getErrorCode(): Int {
        return PreviewVideoErrors.UNHANDLED_ERROR.ordinal
    }
}

open class InvalidPreviewVideoIdError(message: String? = "") : PreviewVideoError(message) {
    override fun getErrorCode(): Int {
        return PreviewVideoErrors.INVALID_PREVIEW_VIDEO_ID.ordinal
    }
}

open class PreviewVideoNotFoundError(message: String? = "") : PreviewVideoError(message) {
    override fun getErrorCode(): Int {
        return PreviewVideoErrors.PREVIEW_VIDEO_NOT_FOUND.ordinal
    }
}

open class InvalidArgumentsError(message: String? = "") : PreviewVideoError(message) {
    override fun getErrorCode(): Int {
        return PreviewVideoErrors.INVALID_ARGUMENTS.ordinal
    }
}

open class PreviewVideoNotAvailableError(message: String? = "") : PreviewVideoError(message) {
    override fun getErrorCode(): Int {
        return PreviewVideoErrors.PREVIEW_VIDEO_NOT_AVAILABLE.ordinal
    }
}

