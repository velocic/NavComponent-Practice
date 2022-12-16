package com.example.navcomponentpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navcomponentpractice.R
import com.example.navcomponentpractice.data.ui.ValidationResult
import com.example.navcomponentpractice.databinding.FragmentSpecifyAmountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SpecifyAmountFragment: Fragment() {

    private lateinit var binding: FragmentSpecifyAmountBinding
    private val viewModel: SpecifyAmountViewModel by viewModels()
    private val args: SpecifyAmountFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentSpecifyAmountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recipientName.value = args.recipientName

        viewModel.recipientName.observe(viewLifecycleOwner) { recipientName ->
            binding.amountLabel.text = getString(R.string.amount_label, recipientName)
        }

        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.send.setOnClickListener {
            binding.amount.text.toString().let { amount ->
                if (amount.isEmpty()) {
                    viewModel.submitAmount(0.0)
                } else {
                    viewModel.submitAmount(amount.toDouble())
                }
            }
        }

        lifecycleScope.launch {
            viewModel.amountValidationResult.collect { validationResult ->
                when (validationResult) {
                    is ValidationResult.Success -> {
                        binding.amountValidationError.visibility = View.GONE
                    }
                    is ValidationResult.Error -> {
                        binding.amountValidationError.apply {
                            visibility = View.VISIBLE
                            text = resources.getString(
                                validationResult.errorMessageResId,
                                *validationResult.messageArgs
                            )
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.completedTransaction.collect { completedTransaction ->
                SpecifyAmountFragmentDirections.viewConfirmation(
                    completedTransaction
                ).let { viewConfirmation ->
                    findNavController().navigate(viewConfirmation)
                }
            }
        }
    }
}