package com.example.android.pocketgymbro.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.android.pocketgymbro.testing.OpenForTesting
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance
 */
@OpenForTesting
class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(fragment)
            .load(url)
            .into(imageView)
    }
}