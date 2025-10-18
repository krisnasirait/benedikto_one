package dev.krisna.data.auth

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient
) : AuthRepository {

    override suspend fun signInEmail(email: String, password: String): Result<Unit> {
        return runCatching {
            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    override suspend fun signUpEmail(
        email: String,
        password: String
    ): Result<Unit> {
        return runCatching {
            supabase.auth.signUpWith(Email) {
                this.password = password
                this.email = email
            }
        }
    }

    override fun observeSessionStatus(): Flow<SessionStatus> {
        return supabase.auth.sessionStatus
    }
}
