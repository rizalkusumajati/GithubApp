package com.riztech.githubapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riztech.githubapp.data.model.mapper.DataUserMapper
import com.riztech.githubapp.domain.model.Games.Games
import com.riztech.githubapp.domain.repository.UserRepository
import com.riztech.githubapp.presentation.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    val userRepository: UserRepository,
    val dispatcher: CoroutineDispatcher,
    val mapper: DataUserMapper,
) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _detail = MutableLiveData<Result<Games>>()
    val detail : LiveData<Result<Games>> get() = _detail

    fun getUserDetail(id: Int){
        viewModelScope.launch(dispatcher) {
                _detail.postValue(Result.Loading)
                try{
                   val result = userRepository.getUserDetail(id)
                   val domain = mapper.mapToDomain(result)
                   val localFav = userRepository.getFavoriteGame(domain)

                    if (localFav != null) {
                        val newDomain = domain.copy(isFavorite = true)
                        _detail.postValue(Result.Success(newDomain))
                    }else{
                        _detail.postValue(Result.Success(domain))
                    }

                }catch (e: Exception){
                    _detail.postValue(Result.Failure("${e.message}", e))
                }
        }
    }

    fun saveLocalUser(user: Games){
        viewModelScope.launch(dispatcher) {
            userRepository.saveFavoriteUser(user)
        }
    }

    fun removeLocalUser(user: Games){
        viewModelScope.launch(dispatcher) {
            userRepository.deleteFavorite(user)
        }
    }
}