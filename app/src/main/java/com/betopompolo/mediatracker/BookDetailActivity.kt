package com.betopompolo.mediatracker

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.betopompolo.mediatracker.databinding.BadgeLayoutBinding
import com.betopompolo.mediatracker.databinding.BookDetailLayoutBinding

val mockBooks = mapOf(
    "1" to BookDetail(
        title = "O Hobbit",
        synopsis = "Bilbo Bolseiro leva uma vida tranquila até ser convidado para uma aventura com um grupo de anões.",
        author = "J.R.R. Tolkien",
        publisher = "HarperCollins",
        firstPublished = "1937",
        genres = listOf("Fantasia", "Aventura"),
        coverRes = R.drawable.lotr_book_cover,
        totalPages = 310
    ),

    "2" to BookDetail(
        title = "1984",
        synopsis = "Winston Smith vive em uma sociedade controlada por um governo que observa e manipula todos os aspectos da vida.",
        author = "George Orwell",
        publisher = "Secker & Warburg",
        firstPublished = "1949",
        genres = listOf("Distopia", "Ficção"),
        coverRes = R.drawable.book_1984,
        totalPages = 328
    ),

    "3" to BookDetail(
        title = "Duna",
        synopsis = "Paul Atreides se muda com sua família para Arrakis, um planeta desértico fundamental para o futuro do império.",
        author = "Frank Herbert",
        publisher = null,
        firstPublished = "1965",
        genres = listOf("Ficção científica", "Aventura"),
        coverRes = R.drawable.dune_cover,
        totalPages = 680
    )
)

class BookDetailActivity : AppCompatActivity() {

    companion object {
        const val BOOK_ID_KEY = "book_id"
    }

    private lateinit var binding: BookDetailLayoutBinding
    private lateinit var detail: BookDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BookDetailLayoutBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val bookId = intent.getStringExtra(BOOK_ID_KEY)

        detail = mockBooks[bookId] ?: mockBooks["1"]!!

        binding.title.text = detail.title
        binding.synopsis.text = detail.synopsis
        binding.author.text = detail.author
        binding.publisher.text =
            detail.publisher ?: "Editora não informada"

        binding.firstPublished.text = detail.firstPublished
        binding.genres.text = detail.genres.joinToString(", ")

        binding.bookCover.setImageResource(detail.coverRes)

        createAndLayoutBadges(
            listOf(
                "Livro",
                detail.genres.first(),
                "${detail.totalPages} páginas"
            )
        )

        binding.fabPreviousPage.setOnClickListener {

            detail.getPreviousPage()?.let {
                detail = it
            }

            updateReadingProgressViews()
        }

        binding.fabNextPage.setOnClickListener {

            detail.getNextPage()?.let {
                detail = it
            }

            updateReadingProgressViews()
        }

        updateReadingProgressViews()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun updateReadingProgressViews() {

        binding.readingProgressPage.text =
            getString(
                R.string.of,
                detail.currentPage,
                detail.totalPages
            )

        binding.readingProgressPercent.text =
            "${detail.progressPercentage}%"

        binding.readingProgressIndicator.progress =
            detail.progressPercentage

        binding.fabPreviousPage.isEnabled =
            detail.currentPage > 0

        binding.fabNextPage.isEnabled =
            detail.currentPage < detail.totalPages
    }

    private fun createAndLayoutBadges(
        badgesTexts: List<String>
    ) {

        val maxBadgesPerRow = 3

        badgesTexts
            .take(maxBadgesPerRow)
            .forEach {

                createBadgeView(
                    binding.badgeRow1,
                    it
                )
            }

        badgesTexts
            .drop(maxBadgesPerRow)
            .take(maxBadgesPerRow)
            .forEach {

                createBadgeView(
                    binding.badgeRow2,
                    it
                )
            }
    }

    private fun createBadgeView(
        row: LinearLayout,
        text: String
    ) {

        val badgeBinding =
            BadgeLayoutBinding.inflate(
                layoutInflater,
                row,
                false
            )

        badgeBinding.badgeText.text = text

        row.addView(badgeBinding.root)
    }
}

data class BookDetail(
    val title: String,
    val synopsis: String,
    val author: String,
    val publisher: String?,
    val firstPublished: String,
    val genres: List<String>,
    val coverRes: Int,
    val currentPage: Int = 0,
    val totalPages: Int
) {

    init {
        require(totalPages > 0)
        require(currentPage in 0..totalPages)
    }

    val progressPercentage: Int
        get() =
            ((currentPage.toFloat() / totalPages) * 100).toInt()

    fun getNextPage(): BookDetail? {

        return if (currentPage < totalPages) {
            copy(currentPage = currentPage + 1)
        } else {
            null
        }
    }

    fun getPreviousPage(): BookDetail? {

        return if (currentPage > 0) {
            copy(currentPage = currentPage - 1)
        } else {
            null
        }
    }
}