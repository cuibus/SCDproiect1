package packagetracking.server.chatbot;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class ChatbotService {

    private final WebClient client;
    private final String ollamaModel = "gemma3:1b";

    public ChatbotService() {
        this.client = WebClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
    }

    public String askOllama(String prompt) {
        Map<String, Object> request = Map.of(
                "model", ollamaModel, // sau alt model descărcat (ex. mistral)
                "prompt", prompt,
                "stream", false
        );

        try {
            OllamaResponse result = client.post()
                    .uri("/api/generate")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OllamaResponse.class)
                    .block();
            return result.response;
        } catch (Exception e) {
            return "Some problem occurred while generating ollama";
        }
    }
}

class OllamaResponse {
    String model;
    String created_at;
    String response;
    boolean done;
}