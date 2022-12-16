package com.example.navcomponentpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.navcomponentpractice.R
import com.example.navcomponentpractice.databinding.FragmentConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmationFragment : Fragment() {

    private val viewModel: ConfirmationViewModel by viewModels()
    private lateinit var binding: FragmentConfirmationBinding
    private val args: ConfirmationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentConfirmationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lastCompletedTransaction.value = args.completedTransaction

        viewModel.lastCompletedTransaction.observe(viewLifecycleOwner) { transaction ->
            binding.confirmation.text = resources.getString(
                R.string.transaction_confirmation,
                transaction.amount.toString(),
                transaction.recipientName
            )
        }
    }
}