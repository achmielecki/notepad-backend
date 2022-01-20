package pl.achmielecki.notepad.note

import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class CodeGeneratorImpl: CodeGenerator {
    private val characters = "0123456789abcdefghijklmnopqrstuvwxyz"
    private val rand: SecureRandom = SecureRandom()

    override fun generateCode(): String {
        val code = StringBuilder()
        for (i in 0..4) {
            code.append(randomChar())
        }
        return code.toString()
    }

    private fun randomChar() =
        characters[rand.nextInt(characters.length)]
}