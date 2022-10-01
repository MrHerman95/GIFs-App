package com.hermanbocharov.gifsapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hermanbocharov.gifsapp.databinding.FragmentGifFullscreenBinding
import com.hermanbocharov.gifsapp.presentation.adapters.GifOriginalAdapter
import com.hermanbocharov.gifsapp.presentation.viewmodel.MainViewModel
import com.hermanbocharov.gifsapp.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class GifFullscreenFragment : Fragment() {

    private var _binding: FragmentGifFullscreenBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentGifFullscreenBinding is null")

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
        _binding = FragmentGifFullscreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val gifPosition = getGifPosition()
        val adapter = GifOriginalAdapter()
        binding.viewPagerGif.adapter = adapter
        observeGifOriginals(adapter)
        binding.viewPagerGif.setCurrentItem(gifPosition, false)
    }

    private fun observeGifOriginals(adapter: GifOriginalAdapter) {
        lifecycleScope.launch {
            viewModel.gifsFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun getGifPosition(): Int {
        return requireArguments().getInt(EXTRA_GIF_POSITION)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val EXTRA_GIF_POSITION = "gif_position"

        fun newInstance(position: Int): Fragment {
            return GifFullscreenFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_GIF_POSITION, position)
                }
            }
        }
    }
}
