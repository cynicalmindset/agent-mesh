package com.agentmesh.agent_mesh.DTO;

public record ChatMsgReq(
 String sender,
         String agentId,
         String agentName,
         String agentRole,
         String agentColor,
         String content
){
}
