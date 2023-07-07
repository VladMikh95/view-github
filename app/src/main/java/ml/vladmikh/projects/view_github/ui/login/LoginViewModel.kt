package ml.vladmikh.projects.view_github.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ml.vladmikh.projects.view_github.data.repository.UserInfoRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserInfoRepository): ViewModel() {

    //val token: MutableLiveData<String>
    //val state: LiveData<State>
    //val actions: Flow<Action>

    fun onSignButtonPressed() {
        // TODO:
    }

    private var _answer = MutableLiveData<String>()
    val answer: LiveData<String> get()= _answer

    fun getUserInfo (token: String, userName: String) {
        viewModelScope.launch {
            _answer.value = repository.getUserInfo(token, userName).toString()
        }
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToMain : Action
    }
}