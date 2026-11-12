package packagetracking.server.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatbot")
@CrossOrigin
public class ChatbotController {
    @Autowired
    private ChatbotService chatbotService;

    public static class ChatRequest {
        public String message;
        public List<Map<String, String>> history;
    }

    @PostMapping
    public String chat(@RequestBody ChatRequest request) {
        return chatbotService.askOllama(request.message, request.history);
    }
}