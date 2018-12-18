package com.example.android.pocketgymbro.ui.search

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.pocketgymbro.AppExecutors

import com.example.android.pocketgymbro.R
import com.example.android.pocketgymbro.binding.FragmentBindingComponent
import com.example.android.pocketgymbro.databinding.SearchFragmentBinding
import com.example.android.pocketgymbro.di.Injectable
import com.example.android.pocketgymbro.testing.OpenForTesting
import com.example.android.pocketgymbro.ui.exercise_search_suggestion.ExerciseSearchSuggestionAdapter
import com.example.android.pocketgymbro.util.autoCleared
import com.example.android.pocketgymbro.vo.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

@OpenForTesting
final class SearchFragment @Inject constructor() : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var dataBindingComponent: DataBindingComponent = FragmentBindingComponent(this)

    var binding by autoCleared<SearchFragmentBinding>()

    private var adapter by autoCleared<ExerciseSearchSuggestionAdapter>()

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.search_fragment,
            container,
            false,
            dataBindingComponent
        )
        binding.emptyResults = false
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SearchViewModel::class.java)

        val exerciseSearchSuggestionAdapter = ExerciseSearchSuggestionAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        )

        initializeRecyclerView()
        binding.resultRecyclerview.adapter = exerciseSearchSuggestionAdapter
        adapter = exerciseSearchSuggestionAdapter

        initializeSearchInputListeners()
    }

    private fun search(v: View) {
        binding.loading = true
        val query = search_edit_text.text.toString()
        dismissKeyboard(v.windowToken)
        searchViewModel.setQuery(query)
    }

    private fun initializeRecyclerView() {
        result_recyclerview.layoutManager = LinearLayoutManager(context)
        searchViewModel.results.observe(this, Observer { result ->
            when (result?.status) {

                Status.SUCCESS -> {
                }
                Status.ERROR -> {
                    Snackbar.make(
                        constraint_layout,
                        result.message as CharSequence,
                        Snackbar.LENGTH_SHORT
                    ).setAction(R.string.retry) {
                        val query = search_edit_text.text.toString()
                        searchViewModel.setQuery(query)
                    }
                        .show()
                }
                Status.LOADING -> {
                }
            }

            binding.emptyResults = result?.data?.size == 0
            binding.searchResource = result
            adapter.submitList(result?.data)
            binding.loading = false
        })

    }

    private fun initializeSearchInputListeners() {
        initializeOnEditorActionListener()
        initializeOnKeyListener()
    }

    private fun initializeOnKeyListener() {
        search_edit_text.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                search(view)
                true
            } else {
                false
            }

        }
    }

    private fun initializeOnEditorActionListener() {
        search_edit_text.setOnEditorActionListener { view, id, _ ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                search(view)
                true
            } else {
                false
            }
        }
    }

    private fun dismissKeyboard(windowToken: IBinder?) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

}
