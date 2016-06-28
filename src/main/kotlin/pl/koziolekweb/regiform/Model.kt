package pl.koziolekweb.regiform

import java.time.LocalDateTime
import java.util.*

/**
 * Created by BKuczynski on 2016-06-28.
 */

data class Email(val address: String)

data class User(val firstName: String, val lastName: String, val email: Email, val specialization: String)

data class Registration(val uuid: UUID, val date: LocalDateTime, val user: User)

