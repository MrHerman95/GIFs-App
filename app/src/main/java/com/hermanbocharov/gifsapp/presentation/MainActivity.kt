package com.hermanbocharov.gifsapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hermanbocharov.gifsapp.databinding.ActivityMainBinding
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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.DefaultLoadStateViewHolder

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as GifsApp).component
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    companion object {
        private const val MAX_QUERY_LENGTH = 50
    }
}
