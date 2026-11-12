package packagetracking.server.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import packagetracking.server.pkg.Package;
import packagetracking.server.pkg.PackageService;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    private final WebClient client;
    private final String ollamaModel;
    private final int contextSize;
    private int lastUsed = 0;

    @Autowired
    private PackageService packageService;

    public ChatbotService(@Value("${ollama.url}") String url,
                          @Value("${ollama.model}") String model,
                          @Value("${ollama.context-size}") int contextSize) {
        this.ollamaModel = model;
        this.contextSize = contextSize;
        this.client = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.create()
                                        .responseTimeout(Duration.ofMinutes(3))
                        )
                )
                .build();
    }

    public String askOllama(String prompt, List<Map<String, String>> history) {
        prompt = prompt.trim();

        if (prompt.equals("/usage")) {
            return "Context used: " + lastUsed + " / " + contextSize
                    + " tokens (" + (lastUsed * 100 / contextSize) + "%)";
        }

        List<Map<String, String>> messages = new ArrayList<>();
        if (prompt.equals("/summary")) {
            messages.addAll(history);
            messages.add(Map.of("role", "user", "content", "Summarize the conversation above in 3-5 sentences."));
        } else {
            messages.add(Map.of("role", "system", "content",
                    "You are a chatbot. Database contains: ["
                            + packageService.findAllPackages().stream().map(Package::toString).collect(Collectors.joining())
                            + "] If the request does not refer to the database, reply with <<I cannot answer>>"));
            messages.addAll(history);
            messages.add(Map.of("role", "user", "content", prompt));
        }

        Map<String, Object> request = Map.of(
                "model", ollamaModel,
                "messages", messages,
                "stream", false,
                "options", Map.of("num_ctx", contextSize)
        );

        try {
            OllamaChatResponse result = client.post()
                    .uri("/api/chat")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OllamaChatResponse.class)
                    .block();

            lastUsed = result.prompt_eval_count + result.eval_count;
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
    public int prompt_eval_count;
    public int eval_count;

    public static class Message {
        public String role;
        public String content;
    }
}