package pl.achmielecki.notepad.note

import org.springframework.data.mongodb.repository.MongoRepository

interface NoteRepository: MongoRepository<Note, String> {
    fun existsByCode(code: String): Boolean
    fun findByCode(code: String): Note?
}