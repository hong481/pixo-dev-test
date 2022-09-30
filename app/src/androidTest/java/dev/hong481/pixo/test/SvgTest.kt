package dev.hong481.pixo.test

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import android.graphics.Canvas
import android.graphics.RectF
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.hong481.pixo.test.util.SVGUtil

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SvgTest {

    companion object {
        const val TAG = "SvgTest"

        const val TEST_SVG_ASSET_PATH = "svg/001.svg"
        const val TEST_BITMAP_WIDTH = 500
        const val TEST_BITMAP_HEIGHT = 500


    }

    private val appContext: Context by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext
    }

    private val assetManager: AssetManager by lazy {
        appContext.resources.assets
    }

    private val svgUtil = SVGUtil()

    /**
     * SVG Util 테스트.
     */
    @Test
    fun testSvgUtil() {
        try {
            val bitmap =
                Bitmap.createBitmap(TEST_BITMAP_WIDTH, TEST_BITMAP_HEIGHT, Bitmap.Config.ARGB_8888)
            val emptyBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

            Log.d(TAG, "testSvgUtil. before svg render. bitmap:${bitmap}")

            svgUtil.renderSVGToCanvas(
                Canvas(bitmap), assetManager.open(TEST_SVG_ASSET_PATH), RectF(
                    0f,
                    0f,
                    100f,
                    100f
                )
            )

            Log.d(TAG, "testSvgUtil. after svg render. bitmap:${bitmap}")

            if (bitmap.sameAs(emptyBitmap)) {
                Log.d(TAG, "testSvgUtil. bitmap is empty.")
            } else {
                Log.d(TAG, "testSvgUtil. bitmap update done.")
            }

        } catch (e: Exception) {
            Log.d(TAG, "testSvgUtil. e: ${e.stackTraceToString()}")
        }
    }
}