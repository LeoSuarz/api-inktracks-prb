package com.inktracks.api.model

import jakarta.persistence.*

@Entity
@Table(
    name = "list_items",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["list_id","book_id"])
    ]
)
class ListItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_items_id")      // <- aquí
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    val book: Book,

    @Column(name = "list_id")
    val listId: Long
)
