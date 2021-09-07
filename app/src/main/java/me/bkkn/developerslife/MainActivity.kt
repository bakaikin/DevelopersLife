package me.bkkn.developerslife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import me.bkkn.developerslife.`interface`.RetrofitServices
import me.bkkn.developerslife.common.Common
import me.bkkn.developerslife.common.Common.toHttps
import me.bkkn.developerslife.model.Data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var mService: RetrofitServices
lateinit var mImageView: ImageView
lateinit var mTextView: TextView
lateinit var mBtnPrev: Button
lateinit var mBtnNext: Button

var mList: MutableList<Data> = mutableListOf()
var state: Int = -1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mImageView = findViewById(R.id.imageView)
        mTextView = findViewById(R.id.textView)
        mBtnPrev = findViewById(R.id.btn_prev)
        mBtnNext = findViewById(R.id.btn_next)

        mBtnPrev.isEnabled = false

        mBtnPrev.setOnClickListener {
            publishData( getCachedData())
        }

        mBtnNext.setOnClickListener {
            getData()
        }

        mService = Common.retrofitService

        getData()
    }

    private fun publishData(data: Data) {
        val url: String? = toHttps(data?.gifURL)
        Glide.with(applicationContext)
            .asGif()
            .load(url)
            .override(1000, 1000)
            .error("no_connection")
            .into(mImageView);
        if (data != null) {
            mList.add(data)
            state++
            mBtnPrev.isEnabled = state >= 0
        }
    }

    private fun getCachedData() : Data {
        return mList.get(state)
    }

    private fun getData() {
        mService.getData().enqueue(object : Callback<Data> {
            override fun onFailure(call: Call<Data>, t: Throwable) {
                Glide.with(applicationContext)
                    .asGif()
                    .load("no_connection.gif")
                    .into(mImageView);
                mTextView.text = "No connection"
            }

            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                response.body()?.let { publishData(it) }
                mTextView.text = response.body()?.description
            }
        })
    }
}