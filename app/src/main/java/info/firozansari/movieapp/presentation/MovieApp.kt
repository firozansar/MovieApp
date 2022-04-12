package info.firozansari.movieapp.presentation

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import info.firozansari.movieapp.BuildConfig
import timber.log.Timber

/**
 * The main application
 * @author Firoz Ansari
 */
@HiltAndroidApp
open class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Stetho.initializeWithDefaults(this)
    }
}
