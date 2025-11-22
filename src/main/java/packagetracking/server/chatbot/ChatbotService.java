package packagetracking.server.chatbot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import packagetracking.server.pkg.Package;
import packagetracking.server.pkg.PackageService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    private final WebClient client;
    private final String ollamaModel = "gemma3:1b";

    @Autowired
    private PackageService packageService;

    public ChatbotService() {
        this.client = WebClient.builder()
                .baseUrl("http://localhost:11434")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String askOllama(String prompt) {

        Map<String, Object> request = Map.of(
                "model", ollamaModel,
                "messages", List.of(
                        Map.of("role", "user", "content",
                        "You are a chatbot. Database contains: [" +
                                packageService.findAllPackages().stream().map(Package::toString).collect(Collectors.joining())
                                + "] Give a response to this request: " +
                                prompt +". If request does not refer to the database, reply with <<I cannot answer>> "
                        )
                ),
                "stream", false
        );

        try {
            OllamaChatResponse result = client.post()
                    .uri("/api/chat")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OllamaChatResponse.class)
                    .block();

            return result.message.content;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error while contacting Ollama: " + e.getMessage();
        }
    }
}

class OllamaChatResponse {
    public String model;
    public String created_at;
    public Message message;

    public static class Message {
        public String role;
        public String content;
    }
}