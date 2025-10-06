package dev.krisna.data

import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(email: String, password: String)
    fun observeSessionStatus(): Flow<SessionStatus>
}
