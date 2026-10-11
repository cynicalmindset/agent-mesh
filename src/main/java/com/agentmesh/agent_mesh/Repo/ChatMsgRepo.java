package com.agentmesh.agent_mesh.Repo;

import com.agentmesh.agent_mesh.Model.ChatMsg;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ChatMsgRepo extends JpaRepository<ChatMsg, Long> {
}
