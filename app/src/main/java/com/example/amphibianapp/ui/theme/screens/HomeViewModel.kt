package com.example.amphibianapp.ui.theme.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibianapp.AmphibianApplication
import com.example.amphibianapp.data.AmphibianData
import com.example.amphibianapp.data.AmphibianRepository
import com.example.amphibianapp.network.AmphibianApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.cache.CacheStrategy
import okio.IOException

sealed interface AmphibianApiState{
   data class Success(val amphibianData: List<AmphibianData>): AmphibianApiState
    object Loading: AmphibianApiState
    object Error: AmphibianApiState
}
class HomeViewModel(val amphibianRepository: AmphibianRepository): ViewModel() {
    private  val _uiState:MutableStateFlow<AmphibianApiState> = MutableStateFlow(AmphibianApiState.Loading)
    val uiState = _uiState.asStateFlow()
 init{
     getData()
 }
   private fun getData() {
        viewModelScope.launch{
       _uiState.value =     try {
                val amphibianData = amphibianRepository.getData()
                AmphibianApiState.Success(amphibianData)
            } catch (e:IOException){
                    AmphibianApiState.Error
            }

        }



    }
companion object{
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = (this[APPLICATION_KEY] as AmphibianApplication)
            val amphibianRepository = application.container.amphibianRepository
            HomeViewModel(amphibianRepository)
        }
    }
}
}