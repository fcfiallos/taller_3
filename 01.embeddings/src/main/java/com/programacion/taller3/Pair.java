package com.programacion.taller3;

public record Pair(int tokenId, String token) {
    @Override
    public String toString() {
        return "Pair["+ token + ": " + tokenId + "]";
    }
}
