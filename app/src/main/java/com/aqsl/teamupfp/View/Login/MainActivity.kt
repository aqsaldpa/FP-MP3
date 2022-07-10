package com.aqsl.teamupfp.View.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.aqsl.teamupfp.R
import com.aqsl.teamupfp.View.Beranda.BerandaActivity
import com.aqsl.teamupfp.View.History.HistoryActivity
import com.aqsl.teamupfp.View.InputAbssen.IsiAbsenActivity
import com.aqsl.teamupfp.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_beranda.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    private companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //firebase auth init
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //Google SignIn Button , Click to begin Google SignIn
        binding.btnsigningoogle.setOnClickListener {
            //begin signin
            Log.d(TAG, "onCreate : begin Google SignIn")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }


    private fun checkUser() {
        //check user login / logout
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user already login
            //start profile
            startActivity(Intent(this, BerandaActivity::class.java))
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google SignIn intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //auth firebase
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthwithGoogleAccount(account)
            } catch (e: Exception) {
                //failed google
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        }
    }

    private fun firebaseAuthwithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase Auth with google account")

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                //login Successs
                Log.d(TAG, "firebaseAuthWithGoogleAccount : LoggedIn")

                //get Loggedin user
                val firebaseUser = firebaseAuth.currentUser
                // get user info
                val uid = firebaseUser!!.uid
                val email = firebaseUser!!.email

                Log.d(TAG, "firebaseAuthWithGoogleAccount: Uid $uid")
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Uid $email")

                //check user new or existing
                if (authResult.additionalUserInfo!!.isNewUser) {
                    //user is new = Acc created
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Account Created... \n$email")
                    Toast.makeText(this, "Account Created... \n$email", Toast.LENGTH_SHORT).show()
                } else {
                    //existing user = logged in
                    Log.d(TAG, "firebaseAuthWithGoogleAccount : Existing user... \n$email")
                    Toast.makeText(this, "Loggedin... \n$email", Toast.LENGTH_SHORT).show()
                }
                //start profile act
                startActivity(Intent(this, BerandaActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount : Login Failed... ${e.message}")
                Toast.makeText(this, "Login failed ... ${e.message} ", Toast.LENGTH_SHORT).show()

            }
    }

}