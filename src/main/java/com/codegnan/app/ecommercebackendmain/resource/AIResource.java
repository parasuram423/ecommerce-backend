 
package com.codegnan.app.ecommercebackendmain.resource;

import com.codegnan.app.ecommercebackendmain.service.AIService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
@CrossOrigin(origins = "http://localhost:5173")
public class AIResource {

    private final AIService aiService;

    public AIResource(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {

        String prompt = request.get("prompt");

        if (prompt == null || prompt.isBlank()) {
            return Map.of("answer", "Please enter your question.");
        }

        String answer = aiService.getAnswer(prompt);

        return Map.of("answer", answer);
    }
}
