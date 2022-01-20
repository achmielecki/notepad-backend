package pl.achmielecki.notepad.note

import org.springframework.stereotype.Service
import pl.achmielecki.notepad.note.rest.NoteDoesntExistError
import pl.achmielecki.notepad.note.rest.NoteDto
import java.time.LocalDateTime.now

@Service
class NoteService(
    val repository: NoteRepository,
    val codeGenerator: CodeGenerator
) {

    fun createNote() =
        repository.save(newNote())

    fun newNote() = Note(newCode(), now())

    private fun newCode(): String {
        var code: String
        do {
            code = codeGenerator.generateCode()
        } while (repository.existsByCode(code))
        return code
    }

    fun updateNote(newNote: NoteDto) =
        repository.save(supplyNote(newNote))

    private fun supplyNote(newNote: NoteDto): Note {
        val oldNote = repository.findByCode(newNote.code)
        if (oldNote != null) {
            return Note(oldNote._id, oldNote.code, now(), newNote.content)
        }
        throw NoteDoesntExistError()
    }

    fun getNote(code: String): Note =
        repository.findByCode(code) ?: throw NoteDoesntExistError()
}