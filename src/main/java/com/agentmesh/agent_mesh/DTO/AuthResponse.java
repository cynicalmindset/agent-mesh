package com.agentmesh.agent_mesh.DTO;

public record AuthResponse(
        String token,
        String email
) {
}
