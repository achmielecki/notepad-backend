package pl.achmielecki.notepad.note

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Note(
    @Id
    val _id: String?,
    @Indexed(unique=true)
    val code: String,
    val lastUpdate: LocalDateTime,
    val content: String
) {
    constructor(code: String, lastUpdate: LocalDateTime)
            : this(null, code, lastUpdate, "")
}