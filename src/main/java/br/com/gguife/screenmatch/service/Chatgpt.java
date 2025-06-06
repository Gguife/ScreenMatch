package br.com.gguife.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class Chatgpt {
    public static String chatgptTraduction(String text) {
        OpenAiService service = new OpenAiService("Paste token");

        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduza para o português o texto: " + text)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var response = service.createCompletion(request);
        return response.getChoices().get(0).getText();
    }
}
