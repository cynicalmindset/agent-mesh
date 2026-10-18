package com.agentmesh.agent_mesh.Controller;

import com.agentmesh.agent_mesh.DTO.ChatMsgReq;
import com.agentmesh.agent_mesh.Model.ChatMemory;
import com.agentmesh.agent_mesh.Model.ChatMsg;
import com.agentmesh.agent_mesh.Service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public List<ChatMemory> getAllChats() {
        return chatService.getAllChats();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatMemory createChat(@RequestBody CreateChatRequest request) {
        return chatService.createChat(request.title(), request.agentId());
    }

    @GetMapping("/{id}")
    public ChatMemory getChat(@PathVariable Long id) {
        return chatService.getChat(id);
    }

    @PostMapping("/{id}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatMsg addMessage(
            @PathVariable Long id,
            @RequestBody ChatMsgReq request) {
        return chatService.addMessage(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
    }

    public record CreateChatRequest(String title, String agentId) {}
}