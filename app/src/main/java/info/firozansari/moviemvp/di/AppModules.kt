package info.firozansari.moviemvp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import info.firozansari.moviemvp.data.db.AppDatabase
import info.firozansari.moviemvp.presentation.Config
import javax.inject.Singleton

@Module
class AppModules {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Config.DATABASE_NAME).build()

//    @Provides
//    @ApiKeyInfo
//    fun provideApiKey(): String = BuildConfig.API_KEY
//
//    @Provides
//    @PreferenceInfo
//    fun provideprefFileName(): String = AppConstants.PREF_NAME
//
//    @Provides
//    @Singleton
//    fun providePrefHelper(appPreferenceHelper: AppPreferenceHelper): PreferenceHelper = appPreferenceHelper
//
//    @Provides
//    @Singleton
//    fun provideProtectedApiHeader(@ApiKeyInfo apiKey: String, preferenceHelper: PreferenceHelper)
//        : ApiHeader.ProtectedApiHeader = ApiHeader.ProtectedApiHeader(apiKey = apiKey,
//        userId = preferenceHelper.getCurrentUserId(),
//        accessToken = preferenceHelper.getAccessToken())
//
//    @Provides
//    @Singleton
//    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper = appApiHelper
//
//    @Provides
//    @Singleton
//    fun provideQuestionRepoHelper(appDatabase: AppDatabase): QuestionRepo = QuestionRepository(appDatabase.questionsDao())
//
//    @Provides
//    @Singleton
//    fun provideOptionsRepoHelper(appDatabase: AppDatabase): OptionsRepo = OptionsRepository(appDatabase.optionsDao())
//
//    @Provides
//    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

}