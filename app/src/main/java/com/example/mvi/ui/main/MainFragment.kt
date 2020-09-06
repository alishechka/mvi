package com.example.mvi.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvi.R
import com.example.mvi.model.BlogPost
import com.example.mvi.ui.DataStateListener
import com.example.mvi.ui.main.state.MainStateEvent.GetBlogPostsEvent
import com.example.mvi.ui.main.state.MainStateEvent.GetUserEvent
import com.example.mvi.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.ClassCastException

class MainFragment : Fragment(), BlogListAdapter.Interaction {
    lateinit var viewModel: MainViewModel
    lateinit var dataStateHandler: DataStateListener
    lateinit var blogListAdapter: BlogListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("invalid Activity")

        subscribeObservers()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            blogListAdapter = BlogListAdapter(this@MainFragment)
            adapter = blogListAdapter
        }
    }

    fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("DEBUG: DataState: ${dataState}")
            //handle loading and message
            dataStateHandler.onDataStateChanged(dataState)
            //handle Data<T>
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let {
                    it.blogPosts?.let { blogPost ->
                        //set BlogState data
                        viewModel.setBlogListData(blogPost)
                    }
                    it.user?.let { user ->
                        //set User data
                        viewModel.setUser(user)
                    }
                }

            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.blogPosts?.let {
                println("DEBUG: DataState: ${it}")
                blogListAdapter.submitList(it)
            }
            viewState.user?.let {
                println("DEBUG: DataState: ${it}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_user -> triggerGetUserEvent()
            R.id.action_get_blogs -> triggerGetBlogsEvent()
        }
        return super.onOptionsItemSelected(item)

    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(GetBlogPostsEvent())
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        println("DEBUG: CLICKED $position")
        println("DEBUG: CLICKED $item")
    }
}