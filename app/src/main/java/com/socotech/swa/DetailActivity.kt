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
        // set name
        val lastName = result.name.last
        val firstName = result.name.first
        name?.text = "$firstName $lastName"
        // set email
        email?.text = result.email
        // set phone
        phone?.text = result.phone
        // set location
        street?.text = result.location.street
        val city = result.location.city
        val state = result.location.state
        val postcode = result.location.postcode
        city_state_zip?.text = "$city, $state $postcode"
        // set avatar
        Picasso.get().load(result.picture.large).transform(CircleTransformation()).into(avatar)
    }

}
