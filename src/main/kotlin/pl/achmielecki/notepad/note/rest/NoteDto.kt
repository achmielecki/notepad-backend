package pl.achmielecki.notepad.note.rest

import pl.achmielecki.notepad.note.Note
import java.time.LocalDateTime

data class NoteDto(
    val uid: String?,
    val code: String,
    val lastUpdate: LocalDateTime?,
    val content: String
) {
    constructor(note: Note)
            : this(null, note.code, note.lastUpdate, note.content)

    constructor(note: Note, uid: String?)
            : this(uid, note.code, note.lastUpdate, note.content)
}