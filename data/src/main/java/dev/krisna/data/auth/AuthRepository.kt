package dev.krisna.data.auth

import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInEmail(email: String, password: String): Result<Unit>
    suspend fun signUpEmail(email: String, password: String): Result<Unit>
    fun observeSessionStatus(): Flow<SessionStatus>
}
