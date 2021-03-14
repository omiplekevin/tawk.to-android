package com.android.androidtesttawkto.activities

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.*
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.androidtesttawkto.R
import com.android.androidtesttawkto.models.UserModel
import com.android.androidtesttawkto.util.DataState
import com.android.androidtesttawkto.util.PaginationListener
import com.android.androidtesttawkto.util.adapters.UsersAdapter
import com.android.androidtesttawkto.viewmodels.MainStateEvent
import com.android.androidtesttawkto.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()
    var adapter = UsersAdapter(arrayListOf())

    companion object {
        const val REQUEST_CODE = 1 //view
        const val RESULT_UPDATED_CODE = 9 //updated
        const val RESULT_DEFAULT = 0 //nothing happened
    }

    private var isLoading: Boolean = false
    private var isSearching: Boolean = false
    private var startId: Int = 0
    private var latestId: Int = startId
    private lateinit var searchView: SearchView

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            refreshList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupConnectionListener()
        setupUI()
        subscribeObservers()

        refreshList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        if (menu != null) {
            val searchItem: MenuItem = menu.findItem(R.id.action_search)
            searchView = searchItem.actionView as SearchView
            searchView.setOnCloseListener(object : SearchView.OnCloseListener {
                override fun onClose(): Boolean {
                    isSearching = false
                    return true
                }
            })

            val searchPlate =
                searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
            searchPlate.hint = "Search"
            val searchPlateView: View =
                searchView.findViewById(androidx.appcompat.R.id.search_plate)
            searchPlateView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    isSearching = true
                    viewModel.searchStateEvent(MainStateEvent.SearchUsersEvent, query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    isSearching = true
                    return false
                }
            })

            val searchManager =
                getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.onActionViewCollapsed()
            isSearching = false
            //refresh list
            refreshList()
        } else {
            super.onBackPressed()
        }
    }

    private fun refreshList() {
        viewModel.setStateEvent(MainStateEvent.GetUsersEvent, startId, 0,false)
    }

    private fun setupUI() {
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter.onItemClick = {
            var intent = Intent(this, UserProfileActivity::class.java)
            intent.putExtra("username", it.login)
            intent.putExtra("id", it.id)
            resultLauncher.launch(intent)
        }
        usersRecyclerView.addOnScrollListener(object :
            PaginationListener(usersRecyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                if (!isSearching) {
                    isLoading = true
                    latestId = adapter.getLastUserId()
                    adapter.addLoadingView()
                    Timber.d("Should load more items here... starting at $latestId")
                    viewModel.setStateEvent(MainStateEvent.GetUsersEvent, latestId, adapter.itemCount, true)
                } else {
                    Timber.d("pagination is disabled during search! items are locally fetched")
                }
            }

            override fun onScrolling(dx: Int, dy: Int) {
            }

            override fun isLastPage(): Boolean {
                Timber.d("on last page")
                //last page is set to always false due to github's unspecified page(since parameter) total
                return false
            }

            override fun isLoading(): Boolean {
                Timber.d("is loading")
                return isLoading
            }
        })
        usersRecyclerView.adapter = adapter

        if (isOnline(this)) {
            connectivityStatus.visibility = View.GONE
        } else {
            connectivityStatus.visibility = View.VISIBLE
        }
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
                        refreshList()
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Timber.d("Connectivity: onLost, $network")
                    runOnUiThread { connectivityStatus.visibility = View.VISIBLE }
                }
            })
        }
    }

    private fun subscribeObservers() {
        viewModel.state.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<List<UserModel>> -> {
                    Timber.d("Updating list...")
                    updateList(dataState.data)

                    listLoadingProgress.visibility = View.GONE
                    usersRecyclerView.visibility = View.VISIBLE

                    if (latestId != startId) {
                        adapter.removeLoadingView()
                    }
                }

                is DataState.Error -> {
                    Timber.e(dataState.exception)
                    if (latestId != startId) {
                        adapter.removeLoadingView()
                    }
                }

                is DataState.LoadMore<List<UserModel>> -> {
                    Timber.d("Loading more data: ${dataState.data}")
                    if (latestId != startId) {
                        adapter.removeLoadingView()
                    }

                    adapter.addToUsers(dataState.data)
                    isLoading = false
                }

                is DataState.Loading -> {
                    Timber.d("Loading users...")
                    if (!isLoading) {
                        listLoadingProgress.visibility = View.VISIBLE
                        usersRecyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun updateList(users: List<UserModel>) {
        users.let { listOfUsers -> listOfUsers.let { adapter.updateUsersList(it) } }
        adapter.notifyDataSetChanged()
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
}