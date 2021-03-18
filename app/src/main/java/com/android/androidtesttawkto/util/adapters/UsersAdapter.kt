package com.android.androidtesttawkto.util.adapters

import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.androidtesttawkto.R
import com.android.androidtesttawkto.models.UserModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import kotlinx.android.synthetic.main.list_item_user.view.*
import timber.log.Timber

class UsersAdapter(
    private var users: MutableList<UserModel>
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        val VIEW_TYPE_NORMAL: Int = 0
        val VIEW_TYPE_LOADING: Int = 1

        val NEGATIVE_MATRIX = floatArrayOf(
            -1.0f, 0f, 0f, 0f, 255f,
            0f, -1.0f, 0f, 0f, 255f,
            0f, 0f, -1.0f, 0f, 255f,
            0f, 0f, 0f, 1.0f, 0f
        )
    }

    private var isLoaderVisible = false
    var onItemClick: ((UserModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                UserViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.list_item_user, parent, false
                    )
                )
            }

            VIEW_TYPE_LOADING -> {
                ProgressViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.list_item_loading, parent, false
                    )
                )
            }

            else -> {
                return ProgressViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.list_item_loading, parent, false
                    )
                )
            }
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        if (isLoaderVisible) {
            return if (position == users.size - 1) {
                VIEW_TYPE_LOADING
            } else {
                VIEW_TYPE_NORMAL
            }
        } else {
            return VIEW_TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int = users.size

    fun updateUsersList(usersList: List<UserModel>) {
        users = usersList.toMutableList()
        Timber.d("updateUsersList, ${users.size}")
        notifyDataSetChanged()
    }

    fun addToUsers(newUsersList: List<UserModel>) {
        users.addAll(newUsersList)
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        Timber.d("adding loading view...")
        isLoaderVisible = true
        users.add(UserModel())
        notifyItemInserted(users.size - 1)
    }

    fun getLastUserId() = users[(users.size - 1)].id

    fun removeLoadingView() {
        Timber.d("removing loading view...")
        isLoaderVisible = false
        val pos = users.size - 1
        val dummyItem = users[pos]
        if (dummyItem != null) {
            users.removeAt(pos)
            notifyItemRemoved(pos)
        }
    }

    inner class UserViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun onBind(position: Int) {
            itemView.userName.text = users[position].login
            itemView.userDetail.text = users[position].url
            itemView.userNotes.visibility = if (users[position].hasNotes) View.VISIBLE else View.GONE

            Glide.with(itemView.context)
                .load(users[position].avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(GlideRequestListener(position + 1))
                .circleCrop()
                .thumbnail(1.0f)
                .into(itemView.userGravatar)

            itemView.setOnClickListener {
                onItemClick?.invoke(users[position])
            }
        }

        override fun clear() {}
    }

    inner class ProgressViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun onBind(position: Int) {
        }

        override fun clear() {}
    }

    class GlideRequestListener(private val position: Int) : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            return if (resource != null) {
                if (position % 4 == 0) {
                    resource.colorFilter = ColorMatrixColorFilter(NEGATIVE_MATRIX)
                }
                target?.onResourceReady(resource, DrawableCrossFadeTransition(1000, isFirstResource))
                true
            } else {
                false
            }
        }
    }
}

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun clear()

    private var currentPosition: Int = 0

    open fun onBind(position: Int) {
        currentPosition = position
        clear()
    }

    fun getCurrentPosition(): Int = currentPosition
}