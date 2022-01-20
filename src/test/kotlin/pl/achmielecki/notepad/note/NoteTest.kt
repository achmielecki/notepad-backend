package pl.achmielecki.notepad.note

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import pl.achmielecki.notepad.note.rest.NoteDto
import java.time.LocalDateTime.now

class NoteTest {

    lateinit var service: NoteService
    lateinit var repository: NoteRepository

    @BeforeEach
    fun init() {
        repository = FakeNoteRepository()
        service = NoteService(repository, CodeGeneratorImpl())
    }

    @Test
    fun createNote() {

        val note = service.createNote()

        assertThat(note._id).isNotBlank
        assertThat(note.code).hasSize(5)
        assertThat(note.lastUpdate).isAfter(now().minusMinutes(1))
        assertThat(note.content).isEqualTo("")
    }

    @Test
    fun updateNote() {
        val note = service.createNote()
        val newNote = NoteDto(null, note.code, now(), "123", )

        service.updateNote(newNote)

        assertThat(repository.count()).isEqualTo(1)
        val testedNote = repository.findAll().first()
        assertThat(testedNote.code).hasSize(5)
        assertThat(testedNote.content).isEqualTo("123")
    }

}