package info.firozansari.moviemvp.di

import dagger.Component
import info.firozansari.moviemvp.presentation.MovieApp
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface AppComponent {
    fun inject(application: MovieApp)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
}