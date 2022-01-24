package pl.achmielecki.notepad.note.sock

import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.stereotype.Component
import org.springframework.util.MimeTypeUtils
import pl.achmielecki.notepad.note.NoteService
import pl.achmielecki.notepad.note.rest.NoteDto

@Component
class SocketsHandler(
    private val noteService: NoteService,
    private val messageTemplate: SimpMessageSendingOperations
) {
    private val sessions = HashMap<String, MutableSet<String>>()

    @Synchronized
    fun saveNoteAndSendOut(noteDto: NoteDto, headers: SimpMessageHeaderAccessor) =
        sendOut(NoteDto(noteService.updateNote(noteDto), noteDto.uid))

    private fun sendOut(noteDto: NoteDto) {
        sessions[noteDto.code]?.forEach { id ->
            messageTemplate.convertAndSendToUser(
                id,
                "/notes",
                noteDto,
                headersForSessionId(id)
            )
        }
    }

    private fun headersForSessionId(id: String): MessageHeaders {
        val headerAccessor = SimpMessageHeaderAccessor.create()
        headerAccessor.setContentType(MimeTypeUtils.APPLICATION_JSON)
        headerAccessor.sessionId = id
        headerAccessor.destination = "/notes"
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.messageHeaders
    }

    fun addUserToSession(headers: MessageHeaders) {
        val sessionId = getSessionId(headers)
        val code = getCode(headers)
        if (sessions[code] == null) {
            sessions[code] = HashSet()
        }
        sessions[code]?.add(sessionId)
    }

    fun removeUserFromSubscription(headers: MessageHeaders) {
        val sessionId = getSessionId(headers)
        val code = getCode(headers)
        sessions[code]?.remove(sessionId)
    }

    private fun getCode(headers: MessageHeaders) =
        (headers["nativeHeaders"] as Map<String, List<String>>)["code"]?.get(0) as String

    private fun getSessionId(headers: MessageHeaders) = headers["simpSessionId"] as String
}