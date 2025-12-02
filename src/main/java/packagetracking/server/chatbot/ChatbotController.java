package packagetracking.server.pkg;

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

    @PostMapping
    public String chat(@RequestBody Map<String, String> body) {
        String request = body.get("question");
        System.out.println("Received: " + request);
        return chatbotService.askOllama(request);
    }

}

