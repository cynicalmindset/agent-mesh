package com.agentmesh.agent_mesh.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id", nullable = false)
    @JsonIgnore
    private ChatMemory chatMemory;

    private String sender;
    private String agentId;
    private String agentName;
    private String agentRole;
    private String agentColor;
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String content;

    public ChatMsg() {}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setConversation(ChatMemory chatMemory) { this.chatMemory = chatMemory;

    }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }
    public String getAgentName() { return agentName; }
    public void setAgentName(String agentName) { this.agentName = agentName; }
    public String getAgentRole() { return agentRole; }
    public void setAgentRole(String agentRole) { this.agentRole = agentRole; }
    public String getAgentColor() { return agentColor; }
    public void setAgentColor(String agentColor) { this.agentColor = agentColor; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}