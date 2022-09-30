package dev.hong481.pixo.test.ui.view.canvas

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import dev.hong481.pixo.test.util.cropCenter
import dev.hong481.pixo.test.util.scalePreserveRatio
import dev.hong481.pixo.test.util.uriToBitmap

class OverlayCanvas constructor(
    context: Context, attrs: AttributeSet
) : View(
    context, attrs
) {
    /**
     * 사진 [Bitmap].
     */
    var photoBitmap: Bitmap? = null
        private set

    /**
     * 초기화.
     */
    fun init(photoUri: Uri) {
        setPhotoBitmap(photoUri)
    }

    private fun setPhotoBitmap(uri: Uri) {
        photoBitmap = uri.uriToBitmap(context)

    }

    /**
     * 오버레이.
     */
    fun overlay() {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.run {
            photoBitmap?.let { bitMap ->
                drawBitmap(bitMap.scalePreserveRatio(width, height), 0f, 0f, null)
            }
        }
    }
}