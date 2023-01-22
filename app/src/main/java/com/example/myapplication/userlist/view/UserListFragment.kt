package com.example.myapplication.userlist.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentUserListBinding
import com.example.myapplication.replaceFragment
import com.example.myapplication.userlist.ItemClickListener
import com.example.myapplication.userlist.model.Data
import com.example.myapplication.userlist.viewmodel.UserListViewModel
import com.example.myapplication.userlist.viewmodel.UserListViewModelFactory
import com.example.myapplication.utils.CommonMethods


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * shows all user profile in recycler view
 */
class UserListFragment : Fragment(), ItemClickListener {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var userListViewModel: UserListViewModel
    private var userListAdapter: UserListAdapter? = null
    private var clickedId: Int? = -1
    var mContainerId: Int = -1
    lateinit var binding:FragmentUserListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        mContainerId = container?.id ?: -1
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        userListViewModel.setNavigator(this)
        binding.setVariable(BR.viewModel, userListViewModel)
        initAdapter()

        activity?.let {
            if(!CommonMethods().internetCheck(it)){
                CommonMethods().showToast(it, getString(R.string.no_internet))
                return
            }
        }

        observeViewModel()
    }

    private fun initViewModel() {
        val userListViewModelFactory = UserListViewModelFactory()
        userListViewModel = ViewModelProvider(this, userListViewModelFactory).get(UserListViewModel::class.java)
    }

    private fun initAdapter() {
        userListAdapter = UserListAdapter(arrayListOf(), this@UserListFragment.requireActivity(), userListViewModel)
        binding.userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }
        val dividerItemDecoration = DividerItemDecoration(
            binding.userListRecyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        binding.userListRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onResume() {
        super.onResume()
        userListViewModel.fetchUserListInfo(2)
    }

    private fun observeViewModel() {

        userListViewModel.fetchUsersLiveData().observe(viewLifecycleOwner) {
            it?.let {
                userListAdapter?.refreshAdapter(it)
            }
        }

        userListViewModel.fetchLoadStatus().observe(viewLifecycleOwner) {
            if (!it) {
                println(it)
                binding.progress.visibility = View.GONE
            }
        }

        userListViewModel.fetchError().observe(viewLifecycleOwner) {
            it?.let {
                if (!TextUtils.isEmpty(it)) {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun viewProfile(data: Data) {
        clickedId = data.id
        launchDetailFragment()
    }

    private fun launchDetailFragment() {
        val userListDetailFragment = UserListDetailFragment.newInstance(clickedId.toString(), "")
        activity?.replaceFragment(userListDetailFragment, mContainerId)
    }
}