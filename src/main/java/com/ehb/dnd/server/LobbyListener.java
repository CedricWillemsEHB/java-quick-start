package com.ehb.dnd.server;

import java.util.List;

public interface LobbyListener {
    int makeLobyGetID();
    List<Object> getPlayersInLoby(int id);
    boolean getInLobby(int id);
}
