package dev.hong481.pixo.test.ui.base.fragment

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    protected lateinit var binding: T
        private set

    /**
     * [NavController]
     */
    protected val navController: NavController by lazy {
        NavHostFragment.findNavController(this)
    }

    /**
     * [AssetManager]
     */
    protected val assetManager: AssetManager? by lazy {
        context?.resources?.assets
    }

    /**
     * [DataBindingUtil] inflate.
     */
    private fun inflate(inflater: LayoutInflater, container: ViewGroup?): T {
        return DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        initBinding(inflater, container)
        initView()
        initViewModel()
        return binding.root
    }

    /**
     * Binding 초기화.
     */
    open fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = inflate(inflater, container)
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