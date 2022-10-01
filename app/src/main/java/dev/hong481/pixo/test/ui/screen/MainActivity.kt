package dev.hong481.pixo.test.ui.screen


import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.data.model.Album
import dev.hong481.pixo.test.databinding.ActivityMainBinding
import dev.hong481.pixo.test.ui.base.activity.BaseActivity
import dev.hong481.pixo.test.ui.screen.albumlist.AlbumListFragment
import dev.hong481.pixo.test.ui.screen.editphoto.EditPhotoFragment
import dev.hong481.pixo.test.util.base.livedata.EventObserver

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private const val READ_EXTERNAL_STORAGE_REQUEST = 0x1045
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
        initActionBarPhoto()
    }

    override fun initViewModel() {
        viewModel.eventLiveData.observe(this, EventObserver {
            handleViewEvent(it)
            viewModel.setIsVisibleOverlayButton(false)
        })
        viewModel.eventOverlay.observe(this, EventObserver {
            doOverlay()
        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    doAlbumListRefresh()
                    viewModel.setPermissionAllow(true)
                } else {
                    val showRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    if (!showRationale) {
                        viewModel.setPermissionAllow(false)
                    }
                }
            }
        }
    }

    /**
     * 권한 요청.
     */
    override fun requestPermission() {
        if (!haveStoragePermission()) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, READ_EXTERNAL_STORAGE_REQUEST)
        } else {
            viewModel.setPermissionAllow(true)
        }
    }

    /**
     * 외부 저장소 권한 확인.
     */
    private fun haveStoragePermission() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PERMISSION_GRANTED


    /**
     * [NavController] 초기 설정.
     */
    private fun initNav() = navController.addOnDestinationChangedListener { _, destination, args ->
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
                    val selectedAlbum: Album? =
                        (args?.getSerializable(Album.KEY_ALBUM) as? Album)?.also {
                            viewModel.setSelectedAlbum(it)
                        }
                    when (destination.id) {
                        // Back
                        R.id.photoPickerFragment -> {
                            this.imgBtnBack.setOnClickListener {
                                viewModel.actionMoveToAlbumListEvent()
                            }
                        }
                        // Back
                        R.id.editPhotoFragment -> this.imgBtnBack.setOnClickListener {
                            viewModel.actionMoveToPhotoPickerEvent(
                                album = selectedAlbum ?: return@setOnClickListener
                            )
                        }
                    }
                }
            }
        }
    }

    private fun initActionBarPhoto() {
        binding.actionBarPhoto.btnOverlay.setOnClickListener {
            viewModel.doEventOverlay()
        }
    }

    private fun doOverlay() {
        val navHostFragment: Fragment? = supportFragmentManager.primaryNavigationFragment
        val fragment: Fragment = navHostFragment?.childFragmentManager?.fragments?.get(0) ?: return
        if (fragment is EditPhotoFragment) {
            fragment.canvasToGallery {
                viewModel.actionMoveToAlbumListEvent()
            }
        }
    }

    private fun doAlbumListRefresh() {
        val navHostFragment: Fragment? = supportFragmentManager.primaryNavigationFragment
        val fragment: Fragment = navHostFragment?.childFragmentManager?.fragments?.get(0) ?: return
        if (fragment is AlbumListFragment) {
            fragment.refreshAlbumList()
        }
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
            navController.navigate(R.id.photoPickerFragment, Bundle().apply {
                putSerializable(Album.KEY_ALBUM, event.selectedAlbum)
            })
        }
        else -> Unit
    }


}