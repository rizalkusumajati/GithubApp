package com.riztech.githubapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riztech.githubapp.data.model.mapper.DataUserMapper
import com.riztech.githubapp.domain.model.User
import com.riztech.githubapp.domain.repository.UserRepository
import com.riztech.githubapp.presentation.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    val userRepository: UserRepository,
    val dispatcher: CoroutineDispatcher,
    val mapper: DataUserMapper,
) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _detail = MutableLiveData<Result<User>>()
    val detail : LiveData<Result<User>> get() = _detail

    fun getUserDetail(login: String){
        viewModelScope.launch(dispatcher) {
                _detail.postValue(Result.Loading)
                try{
                   val result = userRepository.getUserDetail(login)
                    _detail.postValue(Result.Success(mapper.mapToDomain(result)))
                }catch (e: Exception){
                    _detail.postValue(Result.Failure("${e.message}", e))
                }
        }
    }

    fun saveLocalUser(user: User){
        viewModelScope.launch(dispatcher) {
            userRepository.saveFavoriteUser(user)
        }
    }
}