package info.firozansari.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.firozansari.movieapp.data.repository.BaseRepository
import kotlinx.coroutines.launch

open class BaseViewModel(private val baseRepo: BaseRepository) : ViewModel() {

    suspend fun clearSessionPrefs() {
        baseRepo.clearSessionPrefs()
    }

    fun saveSessionId(sessionId: String) = viewModelScope.launch {
        baseRepo.saveSessionId(sessionId = sessionId)
    }

    fun getSessionId() = baseRepo.getSessionId()

    fun saveAccessToken(accessToken: String) = viewModelScope.launch {
        baseRepo.saveAccessToken(accessToken)
    }

    fun getAccessToken() = baseRepo.getAccessToken()

    fun saveAccountId(accountId: Int) = viewModelScope.launch {
        baseRepo.saveAccountId(accountId)
    }

    fun getAccountId() = baseRepo.getAccountId()

    fun saveUserName(username: String) = viewModelScope.launch {
        baseRepo.saveUserName(username)
    }

    fun getUserName() = baseRepo.getUserName()
}
