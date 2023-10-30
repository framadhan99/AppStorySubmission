package com.fajar.storyappsubmission.features.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.fajar.storyappsubmission.R
import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthBody
import com.fajar.storyappsubmission.databinding.FragmentLoginBinding
import com.fajar.storyappsubmission.features.ui.activity.HomeActivity
import com.fajar.storyappsubmission.features.StoryActivity
import com.fajar.storyappsubmission.features.ui.viewmodel.LoginViewModel
import com.fajar.storyappsubmission.features.utils.hideKeyboard
import com.fajar.storyappsubmission.features.utils.showToast
import com.fajar.storyappsubmission.features.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var email: String
    private lateinit var password: String

    private val loginVM: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as StoryActivity?)?.hideLoading()
        (activity as StoryActivity?)?.hideAppBar()


        binding.textRegister.setOnClickListener {
            (activity as StoryActivity?)?.showLoading()
            NavHostFragment
                .findNavController(this)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        checkLogin()
        checkData()

    }


    private fun checkLogin(){
        loginVM.tokenUser.observe(viewLifecycleOwner){ checkLogin ->
            if (checkLogin.isNotEmpty()){
                showToast(requireContext(), getString(R.string.notice_checking_session))
                val mIntent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(mIntent)
                (activity as StoryActivity?)?.finish()
//                val moveToHome = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
//                NavHostFragment.findNavController(this).navigate(moveToHome)
            }
        }
    }


    private fun checkData() {
        binding.apply {
            btnLogin.setOnClickListener {
                email = edtLoginEmail.text.toString()
                password = edtLoginPassword.text.toString()

                if(edtLoginEmail.error == null && edtLoginEmail.error == null){
                    activity?.hideKeyboard()
                    val request = AuthBody(null, email, password)
                    validateUser(request)
                } else showToast(requireContext(), getString(R.string.error_check_not_valid))
            }
        }
    }

    private fun validateUser(addUser: AuthBody) {
        loginVM.loginUser(addUser).observe(viewLifecycleOwner) { data ->
            when (data.status) {
                Status.LOADING -> {
                    (activity as StoryActivity?)?.showLoading()

                }
                Status.SUCCESS -> {
                    (activity as StoryActivity?)?.showLoading()
                    val mIntent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(mIntent)
                    (activity as StoryActivity?)?.finish()
                }
                Status.ERROR -> {
                    (activity as StoryActivity?)?.hideLoading()
                    binding.textWelcome.apply {
                        setTextColor(ResourcesCompat.getColor(resources, R.color.merah, null))
                        text = getString(R.string.error_invalid_login)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}