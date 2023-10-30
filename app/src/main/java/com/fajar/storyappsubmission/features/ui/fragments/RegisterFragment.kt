package com.fajar.storyappsubmission.features.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fajar.storyappsubmission.R
import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthBody
import com.fajar.storyappsubmission.databinding.FragmentRegisterBinding
import com.fajar.storyappsubmission.features.StoryActivity
import com.fajar.storyappsubmission.features.ui.viewmodel.RegisterViewModel
import com.fajar.storyappsubmission.features.utils.Status
import com.fajar.storyappsubmission.features.utils.hideKeyboard
import com.fajar.storyappsubmission.features.utils.isEmailValid
import com.fajar.storyappsubmission.features.utils.isPasswordValid
import com.fajar.storyappsubmission.features.utils.showPositiveAlert
import com.fajar.storyappsubmission.features.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var fullName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var rePassword: String

    private val registerVM: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as StoryActivity?)?.hideLoading()
        (activity as StoryActivity?)?.hideAppBar()

        binding.textSignIn.setOnClickListener {
            (activity as StoryActivity?)?.showLoading()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        checkData()

    }

    private fun checkData() {
        binding.apply {
            btnSignUp.setOnClickListener {
                fullName = inputEdtFullname.editText?.text.toString()
                email = inputEdtEmail.editText?.text.toString()
                password = inputEdtPassword.editText?.text.toString()
                rePassword = inputEdtRepassword.editText?.text.toString()

                setError()

                when {
                    fullName.isBlank() -> inputEdtFullname.error =
                        getString(R.string.error_empty_name)
                    email.isBlank() -> inputEdtEmail.error =
                        getString(R.string.error_empty_email)
                    !email.isEmailValid() -> inputEdtEmail.error =
                        getString(R.string.error_format_email)
                    password.isBlank() -> inputEdtPassword.error =
                        getString(R.string.error_empty_password)
                    password != rePassword -> inputEdtRepassword.error =
                        getString(R.string.error_re_password)
                    password.isPasswordValid() -> inputEdtRepassword.error =
                        getString(R.string.error_char_password)
                    rePassword.isBlank() -> inputEdtRepassword.error =
                        getString(R.string.error_empty_password)
                    else -> {
                        activity?.hideKeyboard()
                        clearError()
                        val request = AuthBody(fullName, email, password)
                        storeUser(request)
                    }
                }
            }
        }
    }

    private fun setError() {
        binding.apply {
            inputEdtEmail.isErrorEnabled = true
            inputEdtFullname.isErrorEnabled = true
            inputEdtPassword.isErrorEnabled = true
            inputEdtRepassword.isErrorEnabled = true
        }
    }

    private fun clearError() {
        binding.apply {
            inputEdtEmail.apply {
                error = null
                isErrorEnabled = false
            }

            inputEdtFullname.apply {
                error = null
                isErrorEnabled = false
            }

            inputEdtPassword.apply {
                error = null
                isErrorEnabled = false
            }

            inputEdtRepassword.apply {
                error = null
                isErrorEnabled = false
            }
        }
    }

    private fun storeUser(addUser: AuthBody) {
        registerVM.registerUser(addUser).observe(viewLifecycleOwner) { data ->
            when (data.status) {
                Status.LOADING -> {
                    (activity as StoryActivity?)?.showLoading()
                }
                Status.SUCCESS -> {
                    (activity as StoryActivity?)?.showLoading()
                    showToast(requireContext(), getString(R.string.success_register))
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                Status.ERROR -> {
                    (activity as StoryActivity?)?.hideLoading()
                    showPositiveAlert(
                        requireContext(),
                        getString(R.string.error_dialog_alert),
                        data.message.toString()
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}