package com.jaquelinebruzasco.vntschool_exercise_screen.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaquelinebruzasco.vntschool_exercise_screen.R
import com.jaquelinebruzasco.vntschool_exercise_screen.databinding.FragmentProductsBinding
import com.jaquelinebruzasco.vntschool_exercise_screen.model.ProductModel
import com.jaquelinebruzasco.vntschool_exercise_screen.ui.fragments.adapters.ProductsListAdapter
import com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel.DeleteDocumentState
import com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel.GetCollectionState
import com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel.ProductsFragmentViewModel

class ProductsFragment : Fragment() {

    private val viewModel by viewModels<ProductsFragmentViewModel>()
    private lateinit var _binding: FragmentProductsBinding
    private val productsListAdapter by lazy { ProductsListAdapter(::productClicked) }
    private var originalList: MutableList<ProductModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservables()
        viewModel.getCollection()
        setupRecyclerView()
    }

    private fun initObservables() {
        viewModel.collectionObs.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GetCollectionState.Idle -> hideProgressBar()
                is GetCollectionState.Loading -> showProgressBar()
                is GetCollectionState.Success -> {
                    hideProgressBar()
                    loadProductsView(state.data)
                }
                is GetCollectionState.Failure -> {
                    hideProgressBar()
                    Toast.makeText(
                        requireContext(), resources.getString(
                            R.string.message_collect_failure
                        ), Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        viewModel.deleteDocumentObs.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DeleteDocumentState.Idle -> hideProgressBar()
                is DeleteDocumentState.Loading -> showProgressBar()
                is DeleteDocumentState.Success -> {
                    hideProgressBar()
                    productsListAdapter.list.removeAt(state.currentPosition)
                    productsListAdapter.notifyItemRemoved(state.currentPosition)
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.message_delete_success),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is DeleteDocumentState.Failure -> {
                    hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.message_delete_failure),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun loadProductsView(data: MutableList<ProductModel>) {
        _binding.apply {
            if (data.isEmpty()) {
                svSearch.visibility = View.GONE
                rvProductsList.visibility = View.GONE
                tvNoProducts.visibility = View.VISIBLE
            } else {
                originalList = data
                svSearch.visibility = View.VISIBLE
                rvProductsList.visibility = View.VISIBLE
                tvNoProducts.visibility = View.GONE
                productsListAdapter.list = data
            }
        }
    }

    private fun setupRecyclerView() = with(_binding) {
        rvProductsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsListAdapter
        }
        ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(rvProductsList)
    }

    private fun initView() {
        _binding.btnFloating.setOnClickListener {
            val dialog = ProductRegistrationBSDFragment(::refreshProductList)
            dialog.show(requireActivity().supportFragmentManager, "")
        }
        performSearch()
    }

    private fun refreshProductList() {
        viewModel.getCollection()
    }

    private fun productClicked(data: ProductModel) {
        Toast.makeText(requireContext(), "'${data.name}' clicked", Toast.LENGTH_LONG).show()
    }

    private fun showProgressBar() {
        _binding.apply {
            progressCircular.visibility = View.VISIBLE
            rvProductsList.visibility = View.GONE
            svSearch.visibility = View.GONE
        }
    }

    private fun hideProgressBar() {
        _binding.apply {
            progressCircular.visibility = View.GONE
            rvProductsList.visibility = View.VISIBLE
            svSearch.visibility = View.VISIBLE
        }
    }

    private fun performSearch() {
        _binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let { text ->
                    if (text.length >= 3) {
                        val newList = originalList.filter { product ->
                            product.name.lowercase().contains(text.lowercase())
                        }.toMutableList()
                        if (newList.isEmpty()) {
                            _binding.rvProductsList.visibility = View.GONE
                            _binding.tvNoProducts.visibility = View.VISIBLE
                        } else {
                            _binding.rvProductsList.visibility = View.VISIBLE
                            _binding.tvNoProducts.visibility = View.GONE
                            productsListAdapter.list = newList
                        }
                    } else {
                        productsListAdapter.list = originalList
                    }
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun itemTouchHelperCallback(): ItemTouchHelper.SimpleCallback {
        return object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentPosition = viewHolder.adapterPosition
                val product = productsListAdapter.list[currentPosition]
                viewModel.deleteDocument(product.id, currentPosition)
            }
        }
    }
}
