package com.inktracks.api.model

import jakarta.persistence.*
@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var username: String,
    val email: String,
    var password: String,

    @Column(name = "profile_pic")
    var profilePic : String? = null,


    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val lists: List<ReadingList> = emptyList(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val readingStatuses: List<ReadingStatus> = emptyList()
)