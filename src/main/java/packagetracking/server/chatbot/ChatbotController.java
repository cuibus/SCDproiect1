package packagetracking.server.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import packagetracking.server.pkg.Package;
import packagetracking.server.pkg.PackageService;

import java.util.List;

@RestController
@RequestMapping("/chatbot")
@CrossOrigin
public class ChatbotController {
    @Autowired
    private ChatbotService chatbotService;

    @PostMapping
    public String chat(@RequestBody String request)
    {
        return chatbotService.askOllama(request);
    }
}
