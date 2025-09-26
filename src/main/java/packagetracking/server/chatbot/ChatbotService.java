package packagetracking.server.chatbot;


import org.springframework.stereotype.Service;

@Service
public class ChatbotService {

    public String getChatResponse(String userMessage) {
        return "chatbot answer: " + userMessage;
    }
}