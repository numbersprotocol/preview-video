package io.numbersprotocol.capturelite.plugins.previewvideo

import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.getcapacitor.Bridge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PreviewVideo(
    val id: String,
    val src: String,
    val config: PreviewVideoConfig,
    val delegate: PreviewVideoPlugin
) {
    private var playerView: PlayerView
    private var playWhenReady: Boolean = true
    private var playbackPosition: Long = 0

    init {
        val bridge = delegate.bridge
        playerView = PlayerView(bridge.context)
        playerView.setBackgroundColor(Color.BLACK)
        initPlayer()
    }

    private fun initPlayer() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.Main).launch {
                initExoPlayer()
                render()
            }

            job.join()
        }
    }

    private fun initExoPlayer() {
        this.playerView.player = ExoPlayer.Builder(delegate.bridge.context).build().also {
            it.setMediaItem(MediaItem.fromUri(src), playbackPosition)
            it.playWhenReady = playWhenReady
            it.repeatMode = Player.REPEAT_MODE_ONE
            it.prepare()
        }
    }

    private fun releaseExoPlayer() {
        this.playerView.player?.let {
            playbackPosition = it.currentPosition
            playWhenReady = it.playWhenReady
            it.release()
        }
        this.playerView.player = null
    }

    private fun render() {
        runBlocking {
            CoroutineScope(Dispatchers.Main).launch {
                val bridge = delegate.bridge
                val playerViewParent = FrameLayout(bridge.context)
                playerViewParent.minimumHeight = bridge.webView.height
                playerViewParent.minimumWidth = bridge.webView.width

                val layoutParams =
                    FrameLayout.LayoutParams(
                        getScaledPixels(bridge, config.width),
                        getScaledPixels(bridge, config.height)
                    )
                layoutParams.leftMargin = getScaledPixels(bridge, config.x)
                layoutParams.topMargin = getScaledPixels(bridge, config.y)

                playerViewParent.tag = id

                playerView.layoutParams = layoutParams
                playerViewParent.addView(playerView)

                (bridge.webView.parent as ViewGroup).addView(playerViewParent)

                bridge.webView.bringToFront()
                bridge.webView.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    fun updateRender(updatedBounds: RectF) {
        this.config.x = updatedBounds.left.toInt()
        this.config.y = updatedBounds.top.toInt()
        this.config.width = updatedBounds.width().toInt()
        this.config.height = updatedBounds.height().toInt()

        runBlocking {
            CoroutineScope(Dispatchers.Main).launch {
                val playerRect = getScaledRect(delegate.bridge, updatedBounds)
                this@PreviewVideo.playerView.x = playerRect.left
                this@PreviewVideo.playerView.y = playerRect.top
            }
        }
    }

    fun dispatchTouchEvent(event: MotionEvent) {
        val offsetViewBounds = getBounds()

        val relativeTop = offsetViewBounds.top
        val relativeLeft = offsetViewBounds.left

        event.setLocation(event.x - relativeLeft, event.y - relativeTop)
        playerView.dispatchTouchEvent(event)
    }

    fun destroy() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.Main).launch {
                val bridge = delegate.bridge

                val viewToRemove: View? =
                    ((bridge.webView.parent) as ViewGroup).findViewWithTag(id)
                if (null != viewToRemove) {
                    ((bridge.webView.parent) as ViewGroup).removeView(viewToRemove)
                }

                releaseExoPlayer()
            }

            job.join()
        }
    }

    fun getBounds(): Rect {
        return Rect(
            getScaledPixels(delegate.bridge, config.x).toInt(),
            getScaledPixels(delegate.bridge, config.y).toInt(),
            getScaledPixels(delegate.bridge, config.x + config.width).toInt(),
            getScaledPixels(delegate.bridge, config.y + config.height).toInt()
        )
    }

    private fun getScaledPixels(bridge: Bridge, pixels: Int): Int {
        // Get the screens' density scale
        val scale = bridge.activity.resources.displayMetrics.density
        // Convert the pds to pixels, based on density scale
        return (pixels * scale + 0.5f).toInt()
    }

    private fun getScaledPixelsF(bridge: Bridge, pixels: Float): Float {
        // Get the screen's density scale
        val scale = bridge.activity.resources.displayMetrics.density
        // Convert the dps to pixels, based on density scale
        return (pixels * scale + 0.5f)
    }

    private fun getScaledRect(bridge: Bridge, rectF: RectF): RectF {
        return RectF(
            getScaledPixelsF(bridge, rectF.left),
            getScaledPixelsF(bridge, rectF.top),
            getScaledPixelsF(bridge, rectF.right),
            getScaledPixelsF(bridge, rectF.bottom),
        )
    }

    fun onStart() {
        // Prepare the ExoPlayer and start playback
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            initExoPlayer()
        }
    }

    fun onResume() {
        // Resume playback when the Activity is resumed
        if ((Build.VERSION.SDK_INT <= Build.VERSION_CODES.M || playerView.player == null)) {
            initExoPlayer()
        }
    }

    fun onPause() {
        // Pause playback when the Activity is paused
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            releaseExoPlayer()
        }
    }

    fun onStop() {
        // Stop and release the ExoPlayer when the Activity is stopped
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            releaseExoPlayer()
        }

    }

    fun onDestroy() {
        // Perform any additional cleanup or resource release if needed
    }


}