package com.example.iweather.ui.main.weatherList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.iweather.R
import com.example.iweather.databinding.FragmentWeatherListBinding
import com.example.iweather.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class WeatherListFragment : Fragment() {

    private var fragmentWeatherListBinding: FragmentWeatherListBinding? = null
    private val viewBinding get() = fragmentWeatherListBinding!!
    private val viewModel: MainViewModel by activityViewModels()
    private var viewStubInflated: View? = null
    private lateinit var weatherDataAdapter: WeatherDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentWeatherListBinding = FragmentWeatherListBinding.inflate(
            layoutInflater,
            container,
            false

        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupSearchView()
        setupListView()
        setupViewModel()

    }

    private fun setupListView() {
        viewBinding.recycleView.run {
            weatherDataAdapter = WeatherDataAdapter()
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = weatherDataAdapter
            addItemDecoration(SpacingDecoration())
        }
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.uiState.collectLatest {
                when (it) {
                    MainViewModel.MainUiState.FailFetchingDefaultCity -> handleViewStub(ViewStubSate.FailFetchingDefaultCity)
                    MainViewModel.MainUiState.FetchingDefaultCity -> handleViewStub(ViewStubSate.FetchingDefaultCity)
                    MainViewModel.MainUiState.SuccessFetch -> handleViewStub(ViewStubSate.SuccessFetch)
                    else -> {
                    }
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.getWeatherData().collectLatest {
                weatherDataAdapter.submitData(it)
            }
        }
    }


    private fun setupSearchView() {
        viewBinding.searchView.setOnClickListener {
            viewBinding.searchView.isIconified = false
        }

    }

    private fun handleViewStub(viewStubState: ViewStubSate) {
        viewStubInflated?.visibility = View.GONE
        val viewStub = when (viewStubState) {
            ViewStubSate.FetchingDefaultCity -> {
                viewBinding.viewStubFetching
            }
            ViewStubSate.FailFetchingDefaultCity -> {
                viewBinding.viewStubFail

            }
            ViewStubSate.SuccessFetch -> null
        }

        viewStubInflated = viewStub?.inflate()
    }


    sealed class ViewStubSate {
        object FetchingDefaultCity : ViewStubSate()
        object SuccessFetch : ViewStubSate()
        object FailFetchingDefaultCity : ViewStubSate()
    }
}