package com.agentmesh.agent_mesh.Controller;

import com.agentmesh.agent_mesh.Model.Agent;
import com.agentmesh.agent_mesh.Service.AgentMap;
import com.agentmesh.agent_mesh.Service.AgentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AgentController {
    private final AgentService agentService;
    private final AgentMap agentMap;

    public AgentController(AgentService agentService, AgentMap agentMap) {
        this.agentService = agentService;
        this.agentMap = agentMap;
    }
    @GetMapping("/agent")
    public Agent getAgent() {
        return agentService.createAgent();
    }
    @GetMapping("/agents")
    public Map<String, Agent> getAllAgents() {
        return agentMap.getAllAgents();
    }
}