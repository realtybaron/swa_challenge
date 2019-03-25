package com.socotech.swa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.socotech.swa.net.RandomUser
import com.socotech.swa.util.CircleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_user.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        // set content
        super.setContentView(R.layout.detail_user)
        // get user
        val result = intent?.extras?.getParcelable("user") as RandomUser
        // set views
        Picasso.get().load(result.picture.large).transform(CircleTransformation()).into(avatar)
    }

}
