package com.agentmesh.agent_mesh.Service;

import com.agentmesh.agent_mesh.DTO.ChatMsgReq;
import com.agentmesh.agent_mesh.Model.ChatMemory;
import com.agentmesh.agent_mesh.Model.ChatMsg;
import com.agentmesh.agent_mesh.Repo.ChatMsgRepo;
import com.agentmesh.agent_mesh.Repo.ChatRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepo chatRepo;
    private final ChatMsgRepo chatMsgRepo;

    public ChatService(ChatRepo chatRepo, ChatMsgRepo chatMsgRepo) {
        this.chatRepo = chatRepo;
        this.chatMsgRepo = chatMsgRepo;
    }

    @Transactional(readOnly = true)
    public List<ChatMemory> getAllChats() {
        return chatRepo.findAllByOrderByUpdatedAtDesc();
    }

    @Transactional(readOnly = true)
    public ChatMemory getChat(Long id) {
        return chatRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Chat not found: " + id));
    }

    public ChatMemory createChat(String title, String agentId) {
        String safeTitle = (title == null || title.isBlank())
                ? "New Chat" : title.trim();

        return chatRepo.save(new ChatMemory(safeTitle, agentId));
    }

    @Transactional
    public ChatMsg addMessage(Long chatId, ChatMsgReq request) {
        if (request.content() == null || request.content().isBlank()) {
            throw new IllegalArgumentException("Message content cannot be empty");
        }

        ChatMemory chat = chatRepo.findById(chatId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Chat not found: " + chatId));

        ChatMsg message = new ChatMsg();
        message.setConversation(chat);
        message.setSender(request.sender());
        message.setAgentId(request.agentId());
        message.setAgentName(request.agentName());
        message.setAgentRole(request.agentRole());
        message.setAgentColor(request.agentColor());
        message.setContent(request.content());

        ChatMsg saved = chatMsgRepo.save(message);
        chat.setUpdatedAt(LocalDateTime.now());
        chatRepo.save(chat);

        return saved;
    }

    public void deleteChat(Long id) {
        ChatMemory chat = chatRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Chat not found: " + id));

        chatRepo.delete(chat);
    }
}