package com.agentmesh.agent_mesh.Service;

import com.agentmesh.agent_mesh.Model.Agent;
import com.agentmesh.agent_mesh.Tools.AssignTask;
import org.springframework.stereotype.Service;

@Service
public class AiAgentService {

    private final AgentManager agentManager;
    private final AgentExe agentExe;
    private final AgentIntercomm agentIntercomm;
    private final AssignTask assignTask;
    private final AgentMap agentMap;

    public AiAgentService(
            AgentManager agentManager,
            AgentExe agentExe,
            AgentIntercomm agentIntercomm,
            AssignTask assignTask,
            AgentMap agentMap) {

        this.agentManager = agentManager;
        this.agentExe = agentExe;
        this.agentIntercomm = agentIntercomm;
        this.assignTask = assignTask;
        this.agentMap = agentMap;
    }

    public String ask(String question) {
        return ask(question, "auto");
    }

    public String ask(String question, String agentId) {

        Agent agent;

        if (agentId != null
                && !agentId.isBlank()
                && !agentId.equals("auto")) {

            agent = agentMap.getAgent(agentId);

            if (agent == null) {
                return "Agent was not found: " + agentId;
            }

        } else {
            agent = agentManager.routeByTask(question);
        }

        if (agent == null) {
            return "Agent was not found";
        }

        if (agent.getId().equals("software-agent")) {
            return agentExe.execute(
                    agent,
                    question,
                    assignTask
            );
        }

        return agentExe.execute(agent, question);
    }
    public String delegate(String toAgent, String task) {
        return agentIntercomm.assignTask(toAgent, task);
    }
}