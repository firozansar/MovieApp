package info.firozansari.movieapp.presentation.util

sealed class ViewModelResult {
    object Loading : ViewModelResult()
    object Success : ViewModelResult()
    object Updated : ViewModelResult()
    object Error : ViewModelResult()
}
