package dev.hong481.pixo.test.ui.view.canvas

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import androidx.core.graphics.scale
import dev.hong481.pixo.test.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class OverlayCanvas constructor(
    context: Context, attrs: AttributeSet
) : androidx.appcompat.widget.AppCompatImageView(
    context, attrs
) {

    companion object {
        const val TAG = "OverlayCanvas"
    }

    /**
     * 사진 [Bitmap].
     */
    private var photoBitmap: Bitmap? = null

    /**
     * 초기화.
     */
    fun init(photoUri: Uri) {
        setPhotoBitmap(photoUri)
    }

    private fun setPhotoBitmap(uri: Uri) {
        photoBitmap = uri.uriToBitmap(context)
        setImageBitmap(photoBitmap)
    }

    /**
     * 오버레이.
     * @param done CallBack.
     */
    fun overlay(bitmap: Bitmap, done: (() -> Unit)? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "overlay. $bitmap")
            try {
                val photoBitmap = photoBitmap ?: return@launch
                // todo Trim 속도가 Slow 하므로 개선 필요.
                val trimBitmap = bitmap.trim(Color.TRANSPARENT)

                val scaledBitmap = trimBitmap.scalePreserveRatio(photoBitmap.width, photoBitmap.width)
                val finalBitmap = photoBitmap.overlayBitmap(scaledBitmap)
                CoroutineScope(Dispatchers.Main).launch {
                    setImageBitmap(finalBitmap)
                    done?.let {
                        it()
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "overlay. error: ${e.stackTraceToString()}")
            }
        }
    }


}