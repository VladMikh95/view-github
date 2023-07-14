package ml.vladmikh.projects.view_github.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.ResponseBody
import retrofit2.Response
import kotlinx.coroutines.launch
import ml.vladmikh.projects.view_github.data.repository.UserInfoRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserInfoRepository): ViewModel() {

    val token: MutableLiveData<String> = MutableLiveData<String>("")

    private var _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    private val _actions: Channel<Action> = Channel(BUFFERED)
    val actions: Flow<Action> = _actions.receiveAsFlow()

    private var _answer = MutableLiveData<Response<ResponseBody>>()
    val answer: LiveData<Response<ResponseBody>> get()= _answer

    fun onSignButtonPressed (token: String, userName: String) {
        viewModelScope.launch {

            _state.value = State.Loading
            try {
                _answer.value = repository.getUserInfo(token, userName)
                Log.i("abc1", _answer.value.toString())

                if(_answer.value!!.code() == 200) {
                    _actions.send(Action.RouteToRepositoriesList)
                } else if(_answer.value!!.code() == 404) {
                    _state.value = State.InvalidInput("InvalidInput")
                } else {
                    _state.value = State.Idle

                }
            } catch (e: Exception) {
                _state.value = State.Idle
                _actions.send(Action.ShowError("error"))
            }

        }
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToRepositoriesList : Action
    }
}