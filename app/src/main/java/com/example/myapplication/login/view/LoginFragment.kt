package com.example.myapplication.login.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.hideKeyboard
import com.example.myapplication.login.model.LoginModel
import com.example.myapplication.login.viewmodel.LoginViewModel
import com.example.myapplication.login.viewmodel.LoginViewModelFactory
import com.example.myapplication.openActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        binding.setVariable(BR.viewmodel, viewModel)
        viewModel.userName.set(getString(R.string.user_name))
        viewModel.password.set(getString(R.string.user_password))

        binding.buttonLogin.setOnClickListener {
            binding.buttonLogin.hideKeyboard()
            binding.loginProgress.visibility = View.VISIBLE
            viewModel.validateLogin(
                LoginModel(
                    binding.loginEmailAddress.text.toString(),
                    binding.loginPassword.text.toString()
                )
            )
        }

        observeViewModel()
    }

    private fun initViewModel() {
        val loginViewModelFactory = LoginViewModelFactory()
        viewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
    }

    private fun observeViewModel() {

        viewModel.fetchTokenStatus().observe(viewLifecycleOwner) {
            it?.let {
                context?.openActivity(Class.forName("com.example.myapplication.userlist.view.UserListActivity"))
            }
        }

        viewModel.fetchLoadStatus().observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    binding.loginProgress.visibility = View.VISIBLE
                } else {
                    binding.loginProgress.visibility = View.GONE
                }
            }
        }

        viewModel.fetchError().observe(viewLifecycleOwner) {
            it?.let {
                if (!TextUtils.isEmpty(it)) {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}