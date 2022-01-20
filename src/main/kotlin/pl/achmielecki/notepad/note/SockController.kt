package pl.achmielecki.notepad.note

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import pl.achmielecki.notepad.note.rest.NoteDto

@Controller
@RequestMapping("app")
class SockController(private val service: NoteService) {

    @MessageMapping("note")
    @SendTo("/notes")
    fun send(noteDto: NoteDto): NoteDto =
        NoteDto(service.updateNote(noteDto), noteDto.uid)
}