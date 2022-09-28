package dev.hong481.pixo.test.ui.screen.editphoto

import android.graphics.Canvas
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hong481.pixo.test.ui.base.viewmodel.BaseViewModel
import dev.hong481.pixo.test.util.SVGUtil
import java.io.InputStream
import javax.inject.Inject


@HiltViewModel
class EditPhotoViewModel @Inject constructor(
    private val svgUtil: SVGUtil
) : BaseViewModel() {

    companion object {
        const val TAG = "EditPhotoViewModel"
    }

    /**
     * SVG 렌더 테스트.
     */
    fun testSVGRender(canvas: Canvas, inputStream: InputStream) {
        svgUtil.renderSVGToCanvas(canvas = canvas, inputStream = inputStream)
    }
}