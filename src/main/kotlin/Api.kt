package game

import com.google.genai.kotlin.Client
import kotlinx.coroutines.runBlocking

class Api private constructor(
    private val apiKey: String,
    private val model: String
) {
    fun generate(prompt: String): String = runBlocking {
        Client(apiKey = apiKey).use { client ->
            client.models.generateContent(
                model = model,
                text = prompt
            ).text.orEmpty().trim()
        }
    }

    companion object {
        fun fromEnvironment(): Api {
            val apiKey = System.getenv("GOOGLE_API_KEY")
                ?.takeIf { it.isNotBlank() }
                ?: error("GOOGLE_API_KEY environment variable is not set")
            val model = System.getenv("GEMINI_MODEL")
                ?.takeIf { it.isNotBlank() }
                ?: "gemini-flash-latest"

            return Api(apiKey, model)
        }
    }
}
