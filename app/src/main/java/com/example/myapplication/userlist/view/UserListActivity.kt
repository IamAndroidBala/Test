package com.example.myapplication.userlist.view

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityUserListBinding
import com.example.myapplication.replaceFragmentWithNoHistory

/**
 * an activity with a fragment which contains list of users retrived from api
 */
class UserListActivity : AppCompatActivity() {

    @VisibleForTesting
    var binding : ActivityUserListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setActionBarTitle(getString(R.string.contact_list))
        replaceFragmentWithNoHistory(UserListFragment(), R.id.container_fragment)
    }

    fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }
}