package game

import com.google.genai.kotlin.Client
import kotlinx.coroutines.runBlocking

class Api private constructor(
    private val client: Client,
    private val model: String
) {
    fun generate(prompt: String): String = runBlocking {
        client.use {
            val response = it.models.generateContent(
                model = model,
                text = prompt
            )
            response.text?.trim().orEmpty()
        }
    }

    companion object {
        fun fromEnvironment(): Api {
            val apiKey = System.getenv("GOOGLE_API_KEY")
                ?: error("GOOGLE_API_KEY environment variable is not set")
            val model = System.getenv("GEMINI_MODEL")
                ?.takeIf { it.isNotBlank() }
                ?: "gemini-flash-latest"

            return Api(
                client = Client(apiKey = apiKey),
                model = model
            )
        }
    }
}
