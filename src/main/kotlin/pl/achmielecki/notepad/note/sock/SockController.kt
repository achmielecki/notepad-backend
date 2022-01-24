package pl.achmielecki.notepad.note.sock

import org.springframework.context.event.EventListener
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.socket.messaging.*
import pl.achmielecki.notepad.note.rest.NoteDto

@Controller
@RequestMapping("app")
class SockController(
    private val handler: SocketsHandler
) {
    @MessageMapping("note")
    fun send(noteDto: NoteDto, header: SimpMessageHeaderAccessor) =
        handler.saveNoteAndSendOut(noteDto, header)

    @EventListener
    fun handleSubscription(event: SessionSubscribeEvent) {
        handler.addUserToSession(event.message.headers)
    }

    @EventListener
    fun handleUnsubscription(event: SessionUnsubscribeEvent) {
        handler.removeUserFromSubscription(event.message.headers)
    }
}