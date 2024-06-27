package com.sksingh.evencargo.repo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.sksingh.evencargo.data.Resource
import com.sksingh.evencargo.util.await
import javax.inject.Inject
import kotlin.Exception

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {

   return try {
        val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
        Resource.Success(result.user!!)
    }catch (e:Exception){
        e.printStackTrace()
        Resource.Failure(e)
    }
    }

    override suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Resource<FirebaseUser> {

            return try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
                result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
                Resource.Success(result.user!!)
            }catch (e:Exception){
                e.printStackTrace()
                Resource.Failure(e)
            }
        }

    override fun logout() {
        firebaseAuth.signOut()
    }
    }
