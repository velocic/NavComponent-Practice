package com.example.navcomponentpractice.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navcomponentpractice.R
import com.example.navcomponentpractice.data.Transaction
import com.example.navcomponentpractice.databinding.FragmentViewTransactionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewTransactionsFragment: Fragment() {

    private lateinit var binding: FragmentViewTransactionsBinding
    private val viewModel: ViewTransactionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentViewTransactionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.transactionList.apply {
            adapter = TransactionAdapter(
                requireContext(),
                viewModel.transactions
            )
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.next.setOnClickListener {
            ViewTransactionsFragmentDirections
                .moveToViewTransactionsDummyScreen().let { dummyScreen ->
                    findNavController().navigate(dummyScreen)
            }
        }

        Toast.makeText(
            requireContext(),
            "Touched the DummyDataRepository ${viewModel.transactionRepository.timesAccessed} times " +
            "and reused the ViewTransactionsViewModel ${viewModel.timesReused++} times.",
            Toast.LENGTH_LONG
        ).show()
    }
}

private class TransactionAdapter(
    private val context: Context,
    private val transactions: List<Transaction>
)
    : RecyclerView.Adapter<TransactionViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_transaction, parent, false
        )

        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        transactions[position].let { transaction ->
            holder.amount.text = transaction.amount.toString()
            holder.name.text = transaction.recipientName
        }
    }

    override fun getItemCount(): Int = transactions.size
}

private class TransactionViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.transaction_name)
    val amount: TextView = view.findViewById(R.id.transaction_amount)
}

