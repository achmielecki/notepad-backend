package pl.achmielecki.notepad.note.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NoteDoesntExistError: Error("Note doesn't exist.")