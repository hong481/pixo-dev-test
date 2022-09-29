package dev.hong481.pixo.test.ui.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    protected lateinit var binding: T
        private set

    /**
     * [DataBindingUtil] setContentView.
     */
    private fun setContentView(): T {
        return DataBindingUtil.setContentView(this, getLayoutRes())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission()

        initBinding()
        initView()
        initViewModel()
    }

    /**
     * 권한 요청.
     */
    abstract fun requestPermission()

    /**
     * Binding 초기화.
     */
    open fun initBinding() {
        binding = setContentView()
        binding.lifecycleOwner = this
    }

    /**
     * View 초기화.
     */
    abstract fun initView()

    /**
     * ViewModel 초기화.
     */
    abstract fun initViewModel()


}