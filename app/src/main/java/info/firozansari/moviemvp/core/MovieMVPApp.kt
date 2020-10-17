package info.firozansari.moviemvp.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * The base context.
 * @author Firoz Ansari
 */
class MovieMVPApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MovieMVPApp)
            modules(listOf(repositoryModule, usecaseModule, viewModelModules))
        }
    }

}