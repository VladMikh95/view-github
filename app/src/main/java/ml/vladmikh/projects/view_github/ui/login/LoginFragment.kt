package ml.vladmikh.projects.view_github.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ml.vladmikh.projects.view_github.R
import ml.vladmikh.projects.view_github.databinding.FragmentLoginBinding
import ml.vladmikh.projects.view_github.utils.bindTextTwoWay
import ml.vladmikh.projects.view_github.ui.login.LoginViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.answer.observe(viewLifecycleOwner) {
            val result = it
            Log.i("abc", result.toString())
        }

        binding.loginButton.setOnClickListener {
            val token = binding.tokenEditText.text.toString()
            viewModel.onSignButtonPressed(token, "VladMikh95")
            Log.i("abc", token)

        }

        lifecycleScope.launch {
            viewModel.actions.collect { handleAction(it) }
        }

        //Двусторонняя привязка данных для связи editText и свойства viewModel.token
        binding.tokenEditText.bindTextTwoWay(liveData = viewModel.token, lifecycleOwner = viewLifecycleOwner)

        viewModel.state.observe(viewLifecycleOwner) { state ->

            binding.textInputLayout.error = if (state is LoginViewModel.State.InvalidInput) getString(R.string.error_auth) else ""

        }
    }

    private fun handleAction(action: LoginViewModel.Action) {
        when (action) {
            LoginViewModel.Action.RouteToRepositoriesList -> routeToRepositoriesList()
            is LoginViewModel.Action.ShowError -> showError(action.message)
        }
    }

    private fun showError(message: String) {

    }

    private fun routeToRepositoriesList() {
        findNavController().navigate(R.id.action_loginFragment_to_repositoriesListFragment)
    }


}