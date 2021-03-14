package com.android.androidtesttawkto.activities

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.android.androidtesttawkto.R
import com.android.androidtesttawkto.models.UserProfileModel
import com.android.androidtesttawkto.util.DataState
import com.android.androidtesttawkto.util.adapters.UsersAdapter
import com.android.androidtesttawkto.viewmodels.ProfileStateEvent
import com.android.androidtesttawkto.viewmodels.UserProfileViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.connectivityStatus
import timber.log.Timber

@AndroidEntryPoint
class UserProfileActivity : AppCompatActivity() {

    val viewModel: UserProfileViewModel by viewModels()
    private lateinit var userProfile: UserProfileModel
    private var username: String? = ""
    private var id: Int? = 0
    private var hasLoadedContent: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        username = intent.extras?.getString("username")
        id = intent.extras?.getInt("id")

        Timber.d("username: $username, id: $id")

        setupUI()
        setupConnectionListener()
        subscribeObservers()

        refreshProfile()
    }

    private fun refreshProfile() {
        viewModel.setProfileStateEvent(ProfileStateEvent.GetUserProfile, username, id)
    }

    private fun subscribeObservers() {
        viewModel.state.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    Timber.d("Success data!")
                    userProfile = dataState.data
                    userProfileName.text = userProfile.name
                    userProfileUsername.text = userProfile.login
                    userProfileFollowers.text =
                        getString(R.string.template_followers, userProfile.followers)
                    userProfileFollowing.text =
                        getString(R.string.template_following, userProfile.following)
                    userProfileLocation.text = userProfile.location
                    userProfileCompany.text = userProfile.company
                    userProfileBlog.text = userProfile.blog
                    if (userProfile.hasNotes) {
                        userProfileNotesEditText.setText(userProfile.notes)
                    }

                    Glide.with(this)
                        .load(userProfile.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .circleCrop()
                        .thumbnail(1.0f)
                        .into(userProfileAvatar)

                    hasLoadedContent = true
                    setupUI()
                }

                is DataState.Error -> {
                    Timber.e(dataState.exception)
                }

                is DataState.Loading -> {
                    Timber.d("Loading...")
                }
            }
        })
    }

    private fun setupConnectionListener() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            it.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Timber.d("Connectivity: onAvailable, $network")
                    runOnUiThread {
                        connectivityStatus.visibility = View.GONE
                        mainContent.visibility = View.VISIBLE
                        refreshProfile()
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Timber.d("Connectivity: onLost, $network")
                    runOnUiThread {
                        connectivityStatus.visibility = View.VISIBLE
                        if (!hasLoadedContent) {
                            mainContent.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }

    private fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                //It will check for both wifi and cellular network
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }

    private fun setupUI() {
        if (::userProfile.isInitialized) {
            userProfileSaveBtn.isEnabled = true
            userProfileSaveBtn.setOnClickListener {
                userProfile.hasNotes = userProfileNotesEditText.text.isNotEmpty()
                userProfile.notes = userProfileNotesEditText.text.toString()
                viewModel.updateProfileStateEvent(ProfileStateEvent.UpdateUserProfile, userProfile)
                setResult(Activity.RESULT_OK)
            }
        } else {
            userProfileSaveBtn.isEnabled = false
        }

        if (isOnline(this)) {
            connectivityStatus.visibility = View.GONE
            mainContent.visibility = View.VISIBLE
        } else {
            connectivityStatus.visibility = View.VISIBLE
            if (!hasLoadedContent) {
                mainContent.visibility = View.GONE
            } else {
                mainContent.visibility = View.VISIBLE
            }
        }
    }
}