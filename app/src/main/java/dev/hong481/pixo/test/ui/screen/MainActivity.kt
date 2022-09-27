package dev.hong481.pixo.test.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.databinding.ActivityMainBinding
import dev.hong481.pixo.test.ui.base.activity.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val viewModel: MainViewModel by viewModels()

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViewModel()
        initNav()
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initViewModel() {
        // todo Request Permission.
    }

    private fun initNav() = navController.addOnDestinationChangedListener { _, destination, _ ->
        binding.actionBarAlbumList.root.visibility =
            if (destination.id == R.id.albumListFragment) View.VISIBLE else View.GONE

        binding.actionBarPhoto.root.visibility =
            if (destination.id == R.id.photoPickerFragment || destination.id == R.id.editPhotoFragment) View.VISIBLE else View.GONE

        when (destination.id) {
            R.id.albumListFragment -> {
                Log.d(TAG, "destination. albumListFragment")
            }
            R.id.photoPickerFragment -> {
                Log.d(TAG, "destination. photoPickerFragment")
            }
            R.id.editPhotoFragment -> {
                Log.d(TAG, "destination. editPhotoFragment")
            }
        }
        viewModel.setCurrentDestination(destination.id)
    }

}