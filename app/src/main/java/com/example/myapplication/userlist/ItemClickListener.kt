package com.example.myapplication.userlist

import com.example.myapplication.userlist.model.Data

/**
 * an interface to handle click events from recycler view
 */
interface ItemClickListener {
     fun viewProfile(data: Data)
}