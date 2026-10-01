package com.betopompolo.mediatracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.betopompolo.mediatracker.databinding.ActivityHomeBinding
import com.betopompolo.mediatracker.databinding.HomeListItemLayoutBinding

val mockList = listOf(
    HomeListItem("1", "O Hobbit", 310, R.drawable.lotr_book_cover),
    HomeListItem("2", "1984", 328, R.drawable.book_1984),
    HomeListItem("3", "Duna", 680, R.drawable.dune_cover)
)

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listView.adapter = HomeListAdapter(mockList)
        binding.listView.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
    }
}

class HomeListAdapter(
    private val list: List<HomeListItem>
) : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    class ViewHolder(
        val listItemBinding: HomeListItemLayoutBinding
    ) : RecyclerView.ViewHolder(listItemBinding.root) {

        val context: Context = listItemBinding.root.context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = HomeListItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = list[position]

        holder.listItemBinding.title.text = item.title

        holder.listItemBinding.pageCount.text =
            holder.context.getString(
                R.string.home_list_item_page_count,
                item.totalPages
            )

        holder.listItemBinding.coverImage.setImageResource(item.coverRes)

        holder.listItemBinding.badge.badgeText.text =
            holder.context.getString(R.string.book)

        holder.listItemBinding.root.setOnClickListener {

            val intent = Intent(
                holder.context,
                BookDetailActivity::class.java
            )

            intent.putExtra(
                BookDetailActivity.BOOK_ID_KEY,
                item.id
            )

            holder.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

data class HomeListItem(
    val id: String,
    val title: String,
    val totalPages: Int,
    val coverRes: Int
)