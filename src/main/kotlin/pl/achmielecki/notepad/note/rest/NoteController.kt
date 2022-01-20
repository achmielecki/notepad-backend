package pl.achmielecki.notepad.note.rest

import org.springframework.web.bind.annotation.*
import pl.achmielecki.notepad.note.NoteService


@RestController
@CrossOrigin(origins = ["\${cors.origin}"])
@RequestMapping("api/v1")
class NoteController(private val service: NoteService) {

    @GetMapping("{code}")
    fun getNote(@PathVariable code: String) =
        NoteDto(service.getNote(code))

    @PostMapping
    fun newNote() =
        NoteDto(service.createNote())

    @PutMapping
    fun updateNote(@RequestBody noteDto: NoteDto) =
        NoteDto(service.updateNote(noteDto))
}