package com.example.taipeiparkinglots

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.taipeiparkinglots.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.passwordAgainEditText.setOnEditorActionListener { _, action, _ ->
            handlePasswordAgainEnter(action)
        }
        binding.signupButton.setOnClickListener { submit() }
        binding.signupConstraintLayout.setOnClickListener { hideKeyboard(view) }

        viewModel.isSignupSuccessful.observe(viewLifecycleOwner) { isSuccessfulEvent ->
            val isSuccessful = isSuccessfulEvent.getContentIfNotHandled() ?: return@observe
            if (isSuccessful) {
                Toast.makeText(requireContext(), getString(R.string.sign_up_success_msg), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.mainPageFragment)
            } else {
                Toast.makeText(requireContext(), getString(R.string.sign_up_failure_msg), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handlePasswordAgainEnter(action: Int): Boolean {
        if (action == EditorInfo.IME_ACTION_DONE) {
            submit()
            return true
        }
        return false
    }

    private fun submit() {
        val myAccount = binding.accountEditText.text.toString()
        val myPassword = binding.passwordEditText.text.toString()
        val secondPassword = binding.passwordAgainEditText.text.toString()
        if (myAccount.isEmpty() || myPassword.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.plz_enter_email_msg), Toast.LENGTH_SHORT).show()
        } else if (secondPassword != myPassword) {
            Toast.makeText(requireContext(), getString(R.string.password_different_msg), Toast.LENGTH_SHORT).show()
        } else {
            viewModel.createAccount(myAccount, myPassword)
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}