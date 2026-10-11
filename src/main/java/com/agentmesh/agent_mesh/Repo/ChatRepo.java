package com.agentmesh.agent_mesh.Repo;

import com.agentmesh.agent_mesh.Model.ChatMemory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRepo extends JpaRepository<ChatMemory, Long> {
    List<ChatMemory> findAllByOrderByUpdatedAtDesc();
}
