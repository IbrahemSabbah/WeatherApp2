package com.example.iweather.ui.main.weatherList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.iweather.R
import com.example.iweather.databinding.FragmentWeatherListBinding
import com.example.iweather.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WeatherListFragment : Fragment() {

    private var fragmentWeatherListBinding: FragmentWeatherListBinding? = null
    private val viewBinding get() = fragmentWeatherListBinding!!
    private val viewModel: MainViewModel by activityViewModels()

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

        initUiState()
        initSearchView()
    }

    private fun initUiState() {
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
    }

    private fun initSearchView() {
        viewBinding.searchView.setOnClickListener {
            viewBinding.searchView.isIconified = false
        }

    }

    private fun handleViewStub(viewStubState: ViewStubSate) {
        val layoutId = when (viewStubState) {
            ViewStubSate.FetchingDefaultCity -> {
                R.layout.fail_default_city_ui_state
            }
            ViewStubSate.FailFetchingDefaultCity -> {
                R.layout.fetching_default_city_ui_state

            }
            ViewStubSate.SuccessFetch -> 0
        }

        if (layoutId == 0) {
            viewBinding.viewStub.visibility = View.GONE
            return
        }
        viewBinding.viewStub.layoutResource = layoutId
        viewBinding.viewStub.inflate()
    }


    sealed class ViewStubSate {
        object FetchingDefaultCity : ViewStubSate()
        object SuccessFetch : ViewStubSate()
        object FailFetchingDefaultCity : ViewStubSate()
    }
}