package ml.vladmikh.projects.view_github.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.view_github.databinding.FragmentLoginBinding

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

        viewModel.getUserInfo("Bearer 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", "VladMikh95")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.answer.observe(viewLifecycleOwner) {
            val result = it
            Log.i("abc", result)
        }

        binding.loginButton.setOnClickListener {
            val token = "Bearer " + binding.editText.text
            viewModel.getUserInfo(token, "VladMikh95")
        }
    }


}