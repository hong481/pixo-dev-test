package dev.hong481.pixo.test.util

import android.graphics.Canvas
import android.graphics.RectF
import android.util.Log
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject


/**
 * [SVG] 유틸 클래스.
 *
 * @author dev.hong481
 * @link <a href="https://github.com/BigBadaboom/androidsvg">
 *     https://github.com/BigBadaboom/androidsvg</a>
 */
class SVGUtil @Inject constructor() {

    companion object {
        const val TAG = "SVGParser"
    }

    /**
     * SVG 정보를 Canvas로 렌더링.
     * [InputStream] -> [Canvas]
     *
     * @param canvas Target Canvas.
     * @param inputStream SVG File InputStream.
     * @return SVG Render Canvas.
     */
    @Throws(SVGParseException::class, IOException::class)
    fun renderSVGToCanvas(canvas: Canvas, inputStream: InputStream, viewPort: RectF) {
        val svg = SVG.getFromInputStream(inputStream)
        if (svg.documentHeight == -1f) {
            Log.d(TAG, "svgRenderToCanvas. svg.documentHeight is problem. return.")
            return
        }
        svg.renderToCanvas(canvas, viewPort)
    }


    /**
     * SVG 정보를 Canvas로 렌더링.
     * [InputStream] -> [Canvas]
     *
     * @param canvas Target Canvas.
     * @param inputStream SVG File InputStream.
     * @return SVG Render Canvas.
     */
    @Throws(SVGParseException::class, IOException::class)
    fun renderViewToCanvas(viewId: String, canvas: Canvas, inputStream: InputStream) {
        val svg = SVG.getFromInputStream(inputStream)
        if (svg.documentHeight == -1f) {
            Log.d(TAG, "svgRenderToCanvas. svg.documentHeight is problem. return.")
            return
        }
        svg.renderViewToCanvas(viewId, canvas)
    }
}