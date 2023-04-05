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
import com.example.taipeiparkinglots.databinding.FragmentLoginBinding
import com.example.taipeiparkinglots.ext.UnderlineSpannable

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.passwordEditText.setOnEditorActionListener { _, action, _ ->
            handlePasswordEnter(action)
        }
        binding.loginButton.setOnClickListener { submit() }
        binding.loginConstraintLayout.setOnClickListener { hideKeyboard(view) }
        binding.signupTextView.text = UnderlineSpannable.getUnderlineSpannable(getString(R.string.goto_sign_up))
        binding.signupTextView.setOnClickListener { gotoSignupPage() }
        binding.guestTextView.text = UnderlineSpannable.getUnderlineSpannable(getString(R.string.use_anonymous_identity))
        binding.guestTextView.setOnClickListener { guestLogin() }

        viewModel.isAnonymousLoginSuccessful.observe(viewLifecycleOwner) { isSuccessfulEvent ->
            val isSuccessful = isSuccessfulEvent.getContentIfNotHandled() ?: return@observe
            if (isSuccessful) {
                Toast.makeText(requireContext(), getString(R.string.guest_login_success_msg), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.mainPageFragment)
            } else {
                Toast.makeText(requireContext(), getString(R.string.guest_login_failure_msg), Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.isLoginSuccessful.observe(viewLifecycleOwner) { isSuccessfulEvent ->
            val isSuccessful = isSuccessfulEvent.getContentIfNotHandled() ?: return@observe
            if (isSuccessful) {
                Toast.makeText(requireContext(), getString(R.string.login_success_msg), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.mainPageFragment)
            } else {
                Toast.makeText(requireContext(), getString(R.string.login_failure_msg), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handlePasswordEnter(action: Int): Boolean {
        if (action == EditorInfo.IME_ACTION_DONE) {
            submit()
            return true
        }
        return false
    }

    private fun submit() {
        val myAccount = binding.accountEditText.text.toString()
        val myPassword = binding.passwordEditText.text.toString()
        if (myAccount.isEmpty() || myPassword.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.plz_enter_email_msg), Toast.LENGTH_SHORT).show()
        } else {
            viewModel.signInWithEmail(myAccount, myPassword)
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun gotoSignupPage() {
        findNavController().navigate(R.id.signupFragment)
    }

    private fun guestLogin() {
        viewModel.signInAnonymously()
    }
}