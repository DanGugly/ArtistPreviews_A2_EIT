package com.example.artistgenresapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.artistgenresapp.view.ClassicFragment
import com.example.artistgenresapp.view.PopFragment
import com.example.artistgenresapp.view.RockFragment

class FragmentAdapter(
        private val fragmentManager: FragmentManager,
        private val lifecycle: Lifecycle
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {
        //How many fragments we are using
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> RockFragment.newInstance()
                1 -> ClassicFragment.newInstance()
                2 -> PopFragment.newInstance()
                else -> RockFragment.newInstance()
            }
        }

}