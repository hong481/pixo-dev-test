package dev.hong481.pixo.test.util

import android.content.Context
import android.graphics.*
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.core.graphics.scale
import java.io.IOException
import java.lang.Integer.min
import kotlin.math.floor

/**
 * [BitmapUtil] Util 클래스.
 *
 * @author dev.hong481
 */
class BitmapUtil {

    companion object {
        const val TAG = "BitmapUtil"
    }
}

/**
 * [Bitmap] 정가운데 Crop.
 */
fun Bitmap.cropCenter(
    sideWight: Int? = null,
    sideHeight: Int? = null,
    filter: Boolean = true
): Bitmap {
    val minLength = min(width, height)
    val left = ((width / 2f) - (minLength / 2f)).toInt()
    val top = ((height / 2f) - (minLength / 2f)).toInt()
    var bitmap = Bitmap.createBitmap(this, left, top, minLength, minLength)
    if (sideWight != null && sideHeight != null) {
        bitmap = Bitmap.createScaledBitmap(bitmap, sideWight, sideHeight, filter)
        bitmap = bitmap.scale(sideWight, sideHeight, filter)
    }
    return bitmap
}


/**
 * [Bitmap] 비율 유지 크기 조정.
 */
fun Bitmap.scalePreserveRatio(
    destinationWidth: Int,
    destinationHeight: Int,
): Bitmap = if (destinationHeight > 0 && destinationWidth > 0) {

    // 원본 크기.
    val width = this.width
    val height = this.height

    // 원본 크기 비율 계산.
    val widthRatio = destinationWidth.toFloat() / width.toFloat()
    val heightRatio = destinationHeight.toFloat() / height.toFloat()

    var finalWidth = floor((width * widthRatio).toDouble()).toInt()
    var finalHeight = floor((height * widthRatio).toDouble()).toInt()

    if (finalWidth > destinationWidth || finalHeight > destinationHeight) {
        finalWidth = floor((width * heightRatio).toDouble()).toInt()
        finalHeight = floor((height * heightRatio).toDouble()).toInt()
    }

    // Bitmap 사이즈 변경.
    val scaledBitmap = Bitmap.createScaledBitmap(this, finalWidth, finalHeight, true)
    val scaledImage = Bitmap.createBitmap(destinationWidth, destinationHeight, this.config)
    val canvas = Canvas(scaledImage)

    // 백그라운드 색상 변경.
    canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), Paint().apply {
        color = Color.TRANSPARENT
        style = Paint.Style.FILL
    })

    val ratioBitmap = finalWidth.toFloat() / finalHeight.toFloat()
    val destinationRatio = destinationWidth.toFloat() / destinationHeight.toFloat()
    val left: Float = if (ratioBitmap >= destinationRatio) {
        0f
    } else {
        (destinationWidth - finalWidth).toFloat() / 2
    }
    val top: Float = if (ratioBitmap < destinationRatio) {
        0f
    } else {
        (destinationHeight - finalHeight).toFloat() / 2
    }
    canvas.drawBitmap(scaledBitmap, left, top, null)
    scaledImage
} else {
    this
}

/**
 *
 * [Bitmap] 특정 픽셀 trim.
 */
fun Bitmap.trim(
    @ColorInt color: Int = Color.TRANSPARENT
): Bitmap {
    var top = height
    var bottom = 0
    var right = width
    var left = 0
    var colored = IntArray(width) { color }
    var buffer = IntArray(width)
    for (y in 0 until top) {
        getPixels(buffer, 0, width, 0, y, width, 1)
        if (!colored.contentEquals(buffer)) {
            bottom = y
            break
        }
    }
    for (y in top - 1 downTo bottom) {
        getPixels(buffer, 0, width, 0, y, width, 1)
        if (!colored.contentEquals(buffer)) {
            top = y
            break
        }
    }
    val heightRemaining = top - bottom
    colored = IntArray(heightRemaining) { color }
    buffer = IntArray(heightRemaining)
    for (x in 0 until right) {
        getPixels(buffer, 0, 1, x, bottom, 1, heightRemaining)
        if (!colored.contentEquals(buffer)) {
            left = x
            break
        }
    }
    for (x in right - 1 downTo left) {
        getPixels(buffer, 0, 1, x, bottom, 1, heightRemaining)
        if (!colored.contentEquals(buffer)) {
            right = x
            break
        }
    }
    return Bitmap.createBitmap(this, left, bottom, right - left, top - bottom)
}

/**
 * [Bitmap] 오버레이.
 */
fun Bitmap.overlayBitmap(overlay: Bitmap): Bitmap? {
    val sourceWidth = this.width
    val sourceHeight = this.height
    val overlayWidth = overlay.width
    val overlayHeight = overlay.height
    val marginLeft = (sourceWidth * 0.5 - overlayWidth * 0.5).toFloat()
    val marginTop = (sourceHeight * 0.5 - overlayHeight * 0.5).toFloat()
    val overlayBitmap = Bitmap.createBitmap(sourceWidth, sourceHeight, this.config)
    val canvas = Canvas(overlayBitmap)

    canvas.drawBitmap(this, Matrix(), null)
    canvas.drawBitmap(overlay, marginLeft, marginTop, null)

    return overlayBitmap
}

/**
 * URI To Bitmap.
 */
@Throws(IOException::class)
fun Uri.uriToBitmap(context: Context): Bitmap = ImageDecoder.decodeBitmap(
    ImageDecoder.createSource(context.contentResolver, this)
) { decoder: ImageDecoder, _: ImageDecoder.ImageInfo?, _: ImageDecoder.Source? ->
    decoder.isMutableRequired = true
    decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
}
