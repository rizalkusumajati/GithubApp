package com.riztech.githubapp.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riztech.githubapp.data.model.mapper.EntityUserMapper
import com.riztech.githubapp.data.source.local.UserDao
import com.riztech.githubapp.domain.model.User
import com.riztech.githubapp.presentation.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    val userDao: UserDao,
    val dispatcher: CoroutineDispatcher,
    val mapper: EntityUserMapper
): ViewModel() {

    private val _localUser = MutableLiveData<Result<List<User>>>()
    val localUser: LiveData<Result<List<User>>> = _localUser


    fun getAllLocalData() {
        viewModelScope.launch(dispatcher) {
            val result = userDao.getAll()
            if(!result.isEmpty()){
                val data = result.map { mapper.mapToDomain(it) }
                _localUser.postValue(Result.Success(data))
            }else{
                _localUser.postValue(Result.Failure("No Data Found", Throwable("No Data Found")))
            }
        }
    }
    // TODO: Implement the ViewModel
}