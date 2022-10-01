package com.hermanbocharov.gifsapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hermanbocharov.gifsapp.R
import com.hermanbocharov.gifsapp.databinding.FragmentGifPreviewBinding
import com.hermanbocharov.gifsapp.presentation.adapters.DefaultLoadStateAdapter
import com.hermanbocharov.gifsapp.presentation.adapters.GifPreviewAdapter
import com.hermanbocharov.gifsapp.presentation.adapters.TryAgainAction
import com.hermanbocharov.gifsapp.presentation.viewmodel.MainViewModel
import com.hermanbocharov.gifsapp.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule


class GifPreviewFragment : Fragment() {

    private var _binding: FragmentGifPreviewBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentGifPreviewBinding is null")

    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.DefaultLoadStateViewHolder

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as GifsApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGifPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupGifPreviewList()
        setupSearchInput()
    }

    private fun setupGifPreviewList() {
        val adapter = GifPreviewAdapter()
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        binding.rvGifPreview.adapter = adapterWithLoadState

        mainLoadStateHolder = DefaultLoadStateAdapter.DefaultLoadStateViewHolder(
            binding.loadStateView,
            tryAgainAction
        )

        setupOnGifPreviewClickListener(adapter)
        observeGifPreviews(adapter)
        observeLoadState(adapter)
    }

    private fun observeGifPreviews(adapter: GifPreviewAdapter) {
        lifecycleScope.launch {
            viewModel.gifsFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun setupOnGifPreviewClickListener(adapter: GifPreviewAdapter) {
        adapter.onGifPreviewClickListener = {
            val fragment = GifFullscreenFragment.newInstance(it)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupSearchInput() {
        var timer = Timer()

        binding.searchEditText.addTextChangedListener {
            timer.cancel()
            timer = Timer()

            val searchKey = it.toString()

            if (searchKey.isEmpty()) return@addTextChangedListener

            if (searchKey.length > MAX_QUERY_LENGTH) {
                binding.searchTextInputLayout.error =
                    "Search query must be no longer than 50 characters"
                return@addTextChangedListener
            }

            binding.searchTextInputLayout.error = null

            timer.schedule(1000) {
                viewModel.setSearchKey(it.toString())
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeLoadState(adapter: GifPreviewAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance() = GifPreviewFragment()
        private const val MAX_QUERY_LENGTH = 50
    }
}
