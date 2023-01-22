package com.example.myapplication.userlist.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentUserListDetailBinding
import com.example.myapplication.userlist.model.Data
import com.example.myapplication.userlist.viewmodel.UserListViewModel
import com.example.myapplication.userlist.viewmodel.UserListViewModelFactory


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * a fragment to show a user details
 */
class UserListDetailFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var viewModel: UserListViewModel

    lateinit var binding: FragmentUserListDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailProgressBar.visibility = View.VISIBLE
        initViewModel()
        binding.setVariable(BR.viewModel, viewModel)
        (activity as UserListActivity).setActionBarTitle(getString(R.string.contact_details))

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                (activity as UserListActivity).setActionBarTitle(getString(R.string.contact_list))
                return@OnKeyListener false
            }
            false
        })

    }

    private fun initViewModel() {
        val userListViewModelFactory = UserListViewModelFactory()
        viewModel =
            ViewModelProvider(this, userListViewModelFactory).get(UserListViewModel::class.java)
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        param1?.let {
            viewModel.fetchUserDetailInfo(it.toInt())
        }
    }

    private fun observeViewModel() {
        viewModel.selectedUserMutableLiveData.observe(viewLifecycleOwner) {
            it?.let {
                populateUserInterface(it)
            }
        }

        viewModel.fetchLoadStatus().observe(viewLifecycleOwner) {
            if (!it) {
                binding.detailProgressBar.visibility = View.GONE
            }
        }
    }

    private fun populateUserInterface(data: Data) {
        viewModel.currentUserData.set(data)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserListDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}