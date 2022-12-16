package com.example.navcomponentpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.navcomponentpractice.data.ui.ValidationResult
import com.example.navcomponentpractice.databinding.FragmentChooseRecipientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseRecipientFragment: Fragment() {

    private lateinit var binding: FragmentChooseRecipientBinding
    private val viewModel: ChooseRecipientViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentChooseRecipientBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            viewModel.submitRecipient(binding.recipient.text.toString())
        }

        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.recipientValidationResult.observe(viewLifecycleOwner) { result ->
            when(result) {
                is ValidationResult.Success -> {
                    //Hide validation errors
                    binding.validationError.apply {
                        text = ""
                        visibility = View.GONE
                    }

                    //Navigate to specify amount screen & pass recipient name
                    ChooseRecipientFragmentDirections.specifyAmount(
                        viewModel.recipient.value!!
                    ).let { specifyAmount ->
                        findNavController().navigate(specifyAmount)
                    }
                }
                is ValidationResult.Error -> {
                    //Show validation errors
                    binding.validationError.apply {
                        text = resources.getString(result.errorMessageResId, *result.messageArgs)
                        visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}