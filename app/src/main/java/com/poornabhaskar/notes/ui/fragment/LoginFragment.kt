package com.poornabhaskar.notes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.poornabhaskar.notes.R
import com.poornabhaskar.notes.databinding.FragmentLoginBinding
import com.poornabhaskar.notes.model.UserRequest
import com.poornabhaskar.notes.util.NetworkResult
import com.poornabhaskar.notes.validation.UserValidation
import com.poornabhaskar.notes.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding?=null
    private val binding get() = _binding!!
    private val userViewModel by viewModels<UserViewModel>()
    @Inject lateinit var userValidation: UserValidation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val userRequest: UserRequest =getUserRequest()
            val validationResult = userValidation.validateOnLoginUser(userRequest)
            if (validationResult.first){
                userViewModel.loginUser(userRequest)
            }else{
                binding.txtError.text=validationResult.second
            }
        }
        binding.btnSignUp.setOnClickListener {

        }
        bindUserResponseLiveDataObservers()
    }

    private fun getUserRequest(): UserRequest {
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest( email, password,null)
    }

    private fun bindUserResponseLiveDataObservers() {
        userViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    //Token
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}