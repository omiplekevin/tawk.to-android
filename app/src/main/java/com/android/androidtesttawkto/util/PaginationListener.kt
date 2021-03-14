package com.android.androidtesttawkto.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        onScrolling(dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) == totalItemCount
                && firstVisibleItemPosition >= 0
                && visibleItemCount != totalItemCount
            ) {
                loadMoreItems()
            }
        } else {
            //ignored - you are on the last page
        }
    }

    protected abstract fun loadMoreItems()

    protected abstract fun onScrolling(dx: Int, dy: Int)

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}