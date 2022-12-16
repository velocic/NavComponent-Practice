package com.example.navcomponentpractice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.navcomponentpractice.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewBalance.setOnClickListener {
            MainFragmentDirections.viewBalance().let { viewBalance ->
                findNavController().navigate(viewBalance)
            }
        }

        binding.viewTransactions.setOnClickListener {
            MainFragmentDirections.viewTransactions().let { viewTransactions ->
                findNavController().navigate(viewTransactions)
            }
        }

        binding.sendMoney.setOnClickListener {
            MainFragmentDirections.chooseRecipient().let { chooseRecipient ->
                findNavController().navigate(chooseRecipient)
            }
        }
    }
}