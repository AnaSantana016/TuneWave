package com.example.tunewave.di

import android.content.SharedPreferences
import com.example.tunewave.data.repositories.authentication.AuthRepository
import com.example.tunewave.data.repositories.authentication.AuthRepositoryImp
import com.example.tunewave.data.repositories.userrepository.userRepository
import com.example.tunewave.data.repositories.userrepository.userRepositoryImp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository {
        return AuthRepositoryImp(auth,database,appPreferences,gson)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): userRepository {
        return userRepositoryImp(database,storageReference)
    }

}