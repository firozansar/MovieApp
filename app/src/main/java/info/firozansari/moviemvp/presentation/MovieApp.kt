package info.firozansari.moviemvp.presentation

import android.app.Application
import info.firozansari.moviemvp.di.AppComponent
import info.firozansari.moviemvp.di.AppModules

/**
 * The main application
 * @author Firoz Ansari
 */
class MovieApp : Application() {

    val appComponent by lazy { createAppComponent() }

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        appComponent.inject(this)
    }

    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder().applicationModule(AppModules(this))

    }
}