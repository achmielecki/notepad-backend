package pl.achmielecki.notepad.note

import pl.achmielecki.notepad.fixtures.FakeMongoImpl

class FakeNoteRepository: FakeMongoImpl<Note>(), NoteRepository {
    override fun existsByCode(code: String): Boolean =
        findByCode(code) != null

    override fun findByCode(code: String): Note? =
        findAll().firstOrNull { it.code == code }
}