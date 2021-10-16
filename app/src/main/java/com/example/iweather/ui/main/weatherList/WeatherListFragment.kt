package com.example.iweather.ui.main.weatherList

import android.app.SearchManager
import android.database.Cursor
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.useCase.citySearch.CitySearch
import com.example.domain.useCase.citySearch.CitySearchUseCase
import com.example.iweather.R
import com.example.iweather.databinding.FragmentWeatherListBinding
import com.example.iweather.helper.DebouncingQueryTextListener
import com.example.iweather.helper.getQueryTextChangeStateFlow
import com.example.iweather.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.provider.BaseColumns

import android.database.MatrixCursor
import android.widget.Toast
import com.blankj.utilcode.util.KeyboardUtils
import com.example.domain.model.CityNameDomainEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WeatherListFragment : Fragment() {

    private var fragmentWeatherListBinding: FragmentWeatherListBinding? = null
    private val viewBinding get() = fragmentWeatherListBinding!!
    private val viewModel: MainViewModel by activityViewModels()
    private var viewStubInflated: View? = null
    private lateinit var weatherDataAdapter: WeatherDataAdapter
    private var itemDecoration = SpacingDecoration()
    private lateinit var suggestAdapter: CursorAdapter
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
        setupListView(ListViewType.Grid)
        setupViewModel()

        viewBinding.buttonToggle.addOnButtonCheckedListener { group, checkedId, isChecked ->


            viewBinding.recycleView.run {

                if (checkedId == viewBinding.viewAsTiels.id && isChecked) {
                    handleListViewType(ListViewType.Grid)

                } else {
                    handleListViewType(ListViewType.List)

                }
            }
        }

    }

    private fun setupListView(listViewType: ListViewType) {
        viewBinding.recycleView.run {
            weatherDataAdapter = WeatherDataAdapter(ListViewType.Grid)
            adapter = weatherDataAdapter
            handleListViewType(listViewType)

        }

        viewBinding.swipeToRefreshLayout.setOnRefreshListener {
            viewModel.updateAllCity()
            Toast.makeText(requireContext(), "Refresh weather...", Toast.LENGTH_LONG)
                .show()
            viewLifecycleOwner.lifecycleScope.launch {
                delay(2000)
                viewBinding.swipeToRefreshLayout.isRefreshing = false

            }
        }


    }

    private fun handleListViewType(listViewType: ListViewType) {
        viewBinding.recycleView.run {
            weatherDataAdapter.listViewType = listViewType

            when (listViewType) {
                ListViewType.Grid -> {
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    itemDecoration = SpacingDecoration()
                    addItemDecoration(itemDecoration)
                }
                ListViewType.List -> {
                    removeItemDecoration(itemDecoration)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }

        }
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.uiState.collectLatest {
                when (it) {
                    MainViewModel.MainUiState.FailFetchingDefaultCity -> handleViewStub(ViewStubSate.FailFetchingDefaultCity)
                    MainViewModel.MainUiState.FetchingDefaultCity -> handleViewStub(ViewStubSate.FetchingDefaultCity)
                    MainViewModel.MainUiState.SuccessFetch -> handleViewStub(ViewStubSate.SuccessFetch)
                    is MainViewModel.MainUiState.NewSearchResult -> {
                        viewBinding.progress.hide()
                        populateAdapter(it.list)
                    }
                    is MainViewModel.MainUiState.SearchLoading -> if (it.isLoading) viewBinding.progress.show() else viewBinding.progress.hide()
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

        val from = arrayOf("cityName")
        val to = intArrayOf(android.R.id.text1)
        suggestAdapter = SimpleCursorAdapter(
            requireContext(),
            R.layout.search_item,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        viewBinding.searchView.suggestionsAdapter = suggestAdapter
        viewBinding.searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener,
            androidx.appcompat.widget.SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {

                return true
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor: Cursor = suggestAdapter.getItem(position) as Cursor
                val txt: String = cursor.getString(cursor.getColumnIndex("cityName"))
                viewBinding.searchView.setQuery("", true)

                viewModel.fetchCityWeather(txt)
                KeyboardUtils.hideSoftInput(viewBinding.root)
                return true
            }

        })
        viewBinding.searchView.setOnClickListener {
            viewBinding.searchView.isIconified = false
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewBinding.searchView.setOnQueryTextListener(
                DebouncingQueryTextListener(
                    viewLifecycleOwner.lifecycle
                ) { newText ->
                    newText?.let {
                        if (it.isEmpty()) {
//                            viewModel.resetSearch()
                        } else {
                            viewModel.citySearch(it)
                        }
                    }
                }
            )
        }


    }

    // You must implements your logic to get data using OrmLite
    private fun populateAdapter(list: List<CityNameDomainEntity>) {
        val c = MatrixCursor(arrayOf(BaseColumns._ID, "cityName"))
        list.forEachIndexed { index, cityNameDomainEntity ->
            c.addRow(arrayOf<Any>(index, cityNameDomainEntity.cityName))
        }
        suggestAdapter.changeCursor(c)
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

    sealed class ListViewType {
        object List : ListViewType()
        object Grid : ListViewType()
    }
}