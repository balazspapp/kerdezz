package hu.gde.kerdezz.answerservice.security

import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.server.ResponseStatusException

data class UserInfo(val user: String, val email: String?)

fun getCurrentUser(): String {
  return SecurityContextHolder.getContext().authentication?.name ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
}

fun getCurrentUserInfo(anonymous: Boolean): UserInfo {
  val authentication = SecurityContextHolder.getContext().authentication
  if (authentication?.name == null) {
    throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
  }
  val email: String? = authentication.takeIf { !anonymous }
    .let { if (it is JwtAuthenticationToken) it.tokenAttributes["email"].toString() else null }

  return UserInfo(authentication.name, email)
}
