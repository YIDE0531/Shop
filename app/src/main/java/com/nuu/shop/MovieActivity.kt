package com.nuu.shop

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row_movie.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.net.URL

class MovieActivity : AppCompatActivity(), AnkoLogger {
    private lateinit var movies: List<Movie>
    private lateinit var context: Context
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://pastebin.com/raw/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        context = this
        doAsync {
//            val json = URL("https://pastebin.com/raw/Vj2BRycF").readText()
//            movies = Gson().fromJson<List<Movie>>(json, object: TypeToken<List<Movie>>(){}.type)
            val movieService = retrofit.create(MovieService::class.java)
            movies = movieService.listMovies()
                .execute()
                .body()!!
            movies.forEach{
                info { "${it.Title} ${it.imdbRating}" }
            }
            uiThread {
                recycler.layoutManager = LinearLayoutManager(it)
                recycler.setHasFixedSize(true)//是否固定大小
                recycler.adapter = MovieAdapter()
            }
        }
    }

    inner class MovieAdapter(): RecyclerView.Adapter<MovieHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_movie, parent, false)
            return MovieHolder(view)
        }

        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
            holder.bindMovie(movies[position])
        }

        override fun getItemCount(): Int {
            return movies?.size?:0
        }
    }

    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view){
        var tvTitle: TextView = view.tv_movie_title
        var tvImdb: TextView = view.tv_movie_imdb
        var tvDirector: TextView = view.tv_movie_director
        var imvPoster: ImageView = view.imv_movie_poster

        fun bindMovie(movie: Movie){
            tvTitle.text = movie.Title
            tvImdb.text = movie.imdbRating
            tvDirector.text = movie.Director
            Glide.with(this@MovieActivity)
                .load(movie.Poster)
                .override(300)
                .into(imvPoster)
        }
    }
}

data class Movie(
    val Actors: String,
    val Awards: String,
    val ComingSoon: Boolean,
    val Country: String,
    val Director: String,
    val Genre: String,
    val Images: List<String>,
    val Language: String,
    val Metascore: String,
    val Plot: String,
    val Poster: String,
    val Rated: String,
    val Released: String,
    val Response: String,
    val Runtime: String,
    val Title: String,
    val Type: String,
    val Writer: String,
    val Year: String,
    val imdbID: String,
    val imdbRating: String,
    val imdbVotes: String,
    val totalSeasons: String
)

interface MovieService{
    @GET("Vj2BRycF")
    fun listMovies(): Call<List<Movie>>
}