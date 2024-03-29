package info.firozansari.movieapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import info.firozansari.movieapp.R
import info.firozansari.movieapp.databinding.ActivityMainBinding

/**
 * @author Firoz Ansari
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var imageLoader: ImageLoader
    lateinit var imageRequestBuilder: ImageRequest.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.bottomNavView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { navContrl, destination, _ ->

            if (navContrl.previousBackStackEntry?.destination?.id == R.id.navigation_movies ||
                navContrl.previousBackStackEntry?.destination?.id == R.id.navigation_search ||
                navContrl.previousBackStackEntry?.destination?.id == R.id.navigation_account
            ) {
                binding.bottomNavView.isVisible = destination.id != R.id.playerFragment &&
                    destination.id != R.id.movieListFragment
                    //&& destination.id != R.id.castDetailsFragment
            } else {
                binding.bottomNavView.isVisible = destination.id != R.id.playerFragment &&
                    destination.id != R.id.movieListFragment &&
                    destination.id != R.id.detailFragment
                    //&& destination.id != R.id.castDetailsFragment
            }
        }
        imageLoader = ImageLoader(this)
        imageRequestBuilder = ImageRequest.Builder(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
