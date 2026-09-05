package game

import com.google.genai.Client

class Api private constructor(
    private val client: Client,
    private val model: String
) {
    fun generate(prompt: String): String {
        val response = client.models.generateContent(model, prompt, null)
        return response.text()?.trim().orEmpty()
    }

    companion object {
        fun fromEnvironment(): Api {
            val apiKey = System.getenv("GEMINI_API_KEY")
                ?: error("GEMINI_API_KEY environment variable is not set")
            val model = System.getenv("GEMINI_MODEL")
                ?.takeIf { it.isNotBlank() }
                ?: "gemini-3.7-flash"

            return Api(
                client = Client.builder().apiKey(apiKey).build(),
                model = model
            )
        }
    }
}
