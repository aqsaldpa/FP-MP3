package com.aqsl.teamupfp.View.Beranda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.aqsl.teamupfp.View.History.HistoryActivity
import com.aqsl.teamupfp.View.InputAbssen.IsiAbsenActivity
import com.aqsl.teamupfp.View.Login.MainActivity
import com.aqsl.teamupfp.databinding.ActivityBerandaBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_beranda.*

class BerandaActivity : AppCompatActivity() {

    private lateinit var binding :  ActivityBerandaBinding
    private lateinit var strTitle: String
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setinitLayout()
        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click , logout user
        binding.btnlogout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun setinitLayout() {
        cvAbsenMasuk.setOnClickListener {
            strTitle = "Absen Masuk"
            val intent = Intent(this@BerandaActivity, IsiAbsenActivity::class.java)
            intent.putExtra(IsiAbsenActivity.DATA_TITLE, strTitle)
            startActivity(intent)

            cvAbsenKeluar.setOnClickListener {
                strTitle = "Absen Keluar"
                val intent = Intent(this@BerandaActivity, IsiAbsenActivity::class.java)
                intent.putExtra(IsiAbsenActivity.DATA_TITLE, strTitle)
                startActivity(intent)
            }

            cvPerizinan.setOnClickListener {
                strTitle = "Izin"
                val intent = Intent(this@BerandaActivity, IsiAbsenActivity::class.java)
                intent.putExtra(IsiAbsenActivity.DATA_TITLE, strTitle)
                startActivity(intent)
            }

            cvHistory.setOnClickListener {
                val intent = Intent(this@BerandaActivity, HistoryActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun checkUser() {
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else{
            //user loggedin get user info
            val displaynme = firebaseUser.displayName
            //set email
            binding.tvemail.text = displaynme

        }
    }
}