package dev.hong481.pixo.test.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.databinding.ActivityMainBinding
import dev.hong481.pixo.test.ui.base.activity.BaseActivity
import dev.hong481.pixo.test.util.base.livedata.EventObserver

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    private val viewModel: MainViewModel by viewModels()

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun initBinding() {
        super.initBinding()
        binding.viewModel = viewModel
    }

    override fun initView() {
        initNav()
    }

    override fun initViewModel() {
        // todo Request Permission.

        viewModel.eventLiveData.observe(this, EventObserver {
            handleViewEvent(it)
        })
    }

    /**
     * [NavController] 초기 설정.
     */
    private fun initNav() = navController.addOnDestinationChangedListener { _, destination, _ ->
        binding.actionBarAlbumList.root.visibility =
            if (destination.id == R.id.albumListFragment) View.VISIBLE else View.GONE

        binding.actionBarPhoto.root.visibility =
            if (destination.id == R.id.photoPickerFragment || destination.id == R.id.editPhotoFragment) View.VISIBLE else View.GONE

        when (destination.id) {
            R.id.albumListFragment -> {
                Log.d(TAG, "destination. albumListFragment")
            }
            R.id.photoPickerFragment, R.id.editPhotoFragment -> {
                Log.d(
                    TAG, "destination. ${
                        when (destination.id) {
                            R.id.photoPickerFragment -> "photoPickerFragment"
                            R.id.editPhotoFragment -> " editPhotoFragment"
                            else -> ""
                        }
                    }"
                )
                binding.actionBarPhoto.run {
                    this.imgBtnBack.setOnClickListener {
                        when (destination.id) {
                            R.id.photoPickerFragment -> viewModel.actionMoveToAlbumListEvent()
                            R.id.editPhotoFragment -> viewModel.actionMoveToPhotoPickerEvent()
                        }
                    }
                    // todo Set Album Title
                    this.tvTitle.text = ""
                }
            }
        }
        viewModel.setCurrentDestination(destination.id)
    }

    /**
     * Activity단 Event 핸들링.
     * (ActionBar)
     *
     * Fragment단 Lifecycle에서 이벤트 처리시 FragmentViewModel에서
     * Event 핸들링을 해야합니다.
     */
    private fun handleViewEvent(event: MainViewModel.ViewEvent) = when (event) {
        is MainViewModel.ViewEvent.ActionMoveToAlbumList -> {
            navController.navigate(R.id.albumListFragment)
        }
        is MainViewModel.ViewEvent.ActionMoveToPhotoPicker -> {
            navController.navigate(R.id.photoPickerFragment)
        }
        is MainViewModel.ViewEvent.ActionMoveToPhotoEditor -> {
            navController.navigate(R.id.editPhotoFragment)
        }
    }


}