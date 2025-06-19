package dev.daniza.portfoliowatcher.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.daniza.portfoliowatcher.interactor.get_news.GetNewsInteractor
import dev.daniza.portfoliowatcher.model.news.NewsHeadline
import dev.daniza.portfoliowatcher.parser.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsInteractor: GetNewsInteractor
) : ViewModel() {
    private val _newsHeadline: MutableStateFlow<PagingData<NewsHeadline>> = MutableStateFlow(PagingData.empty())
    val newsHeadline: StateFlow<PagingData<NewsHeadline>>
        get() = _newsHeadline.asStateFlow()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())

    fun getNewsHeadline() {
        viewModelScope.launch(Dispatchers.Main) {
            getNewsInteractor()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .map { data ->
                    data.map { it.toModel() }
                }
                .collect { data ->
                    _newsHeadline.value = data
                }
        }
    }
}