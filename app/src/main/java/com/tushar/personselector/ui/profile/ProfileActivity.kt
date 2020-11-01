package com.tushar.personselector.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tushar.personselector.base.BaseActivity
import com.tushar.personselector.databinding.ActivityProfileBinding
import com.tushar.personselector.repository.Resource
import com.tushar.personselector.utils.hide
import com.tushar.personselector.utils.show
import com.tushar.personselector.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * The Profiles Activity in which all the User profiles are shown.
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileActivity : BaseActivity<ProfileViewModel, ActivityProfileBinding>() {

    private val adapter : ProfileAdapter by lazy {
        ProfileAdapter(mutableListOf()){
            mViewModel.updatePerson(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        setViewModel()
        initRecyclerView()
        setObservers()
    }

    /**
     * Binding layout to ViewModel and setting up lifecycle owner
     * mViewModel.fetchProfiles() makes an API call/DB Call to fetch profiles
     */
    private fun setViewModel() {
        mViewBinding.viewModel = mViewModel
        mViewBinding.lifecycleOwner = this
        mViewModel.fetchProfiles()
    }

    /**
     * Set up recyclerview with adapter and layout manager
     */
    private fun initRecyclerView() {
        mViewBinding.rvProfiles.layoutManager = LinearLayoutManager(this)
        mViewBinding.rvProfiles.itemAnimator = null
        mViewBinding.rvProfiles.adapter = adapter
    }

    /**
     * The personsLiveData observes the profiles data in the model layer
     */
    private fun setObservers() {
        mViewModel.personsLiveData.observe(this, { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    mViewBinding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    mViewBinding.progressBar.hide()
                    if (!resource.data.isNullOrEmpty()) {
                        adapter.addPeople(resource.data)
                    }
                }
                Resource.Status.ERROR -> {
                    mViewBinding.progressBar.hide()
                    this.showToast(resource?.message!!, Toast.LENGTH_LONG)
                }
            }
        })
    }

    /**
     * Sets up ViewModel
     */
    override val mViewModel: ProfileViewModel
        get() = ViewModelProvider(this).get(ProfileViewModel::class.java)

    /**
     * Sets up Data Binding
     */
    override fun getViewBinding(): ActivityProfileBinding {
        return ActivityProfileBinding.inflate(layoutInflater)
    }
}