package com.example.myapplication.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.myapplication.R

/**
 * user to bind image, name etc from xml using data binding
 */
object CustomViewBindings {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImageURL(imageView: ImageView, imageUrl: String?) {
        if (imageUrl != null) {
            if (imageView.getTag(R.id.image_url) == null || imageView.getTag(R.id.image_url) != imageUrl) {
                imageView.setImageBitmap(null)
                imageView.setTag(R.id.image_url, imageUrl)
                try {
                    Glide.with(imageView).asBitmap().placeholder(R.drawable.ic_person)
                        .load(imageUrl).apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(imageView)
                } catch (_: Exception) { }
            }
        } else {
            imageView.setTag(R.id.image_url, null)
            imageView.setImageBitmap(null)
        }
    }

    @JvmStatic
    @BindingAdapter("imageBackground")
    fun bindBackground(imageView: View, imageUrl: Drawable) {
        try {
            Glide.with(imageView.context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        imageView.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        } catch (e: Exception) {
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImageId(imageView: ImageView, imageUrl: Int?) {
        imageView.setImageResource(imageUrl!!)
    }

    @JvmStatic
    @BindingAdapter("name")
    fun bindName(textView: TextView, firstName: String?, lastName: String?) {
        val name = if(!firstName.isNullOrBlank() && !lastName.isNullOrBlank()) {
            "$firstName $lastName"
        } else if(!firstName.isNullOrBlank()){
            "$firstName"
        } else if(!lastName.isNullOrBlank()){
            "$lastName"
        } else {
            "User"
        }
        textView.text = name
    }

    @JvmStatic
    @BindingAdapter("email")
    fun bindEmail(textView: TextView, email: String?) {
       val userEmail = if(!email.isNullOrBlank()){
            "$email"
        } else {
            "No email address"
        }
        textView.text = userEmail
    }

}

