package com.inktracks.api.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "friends")
@IdClass(FriendId::class) // Esta línea es necesaria
data class Friend(
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Id
    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    val friend: User,

    @Convert(converter = FriendStatusConverter::class)
    @Column(nullable = false)
    val status: FriendStatus = FriendStatus.PENDING



)


enum class FriendStatus {
    PENDING,
    ACCEPTED,
    REJECTED;

    companion object {
        fun fromString(value: String): FriendStatus {
            return valueOf(value.uppercase(Locale.getDefault()))
        }
    }
}
@Converter(autoApply = true)
class FriendStatusConverter : AttributeConverter<FriendStatus, String> {

    override fun convertToDatabaseColumn(attribute: FriendStatus?): String? {
        return attribute?.name
    }

    override fun convertToEntityAttribute(dbData: String?): FriendStatus? {
        return dbData?.let { FriendStatus.fromString(it) }
    }
}
