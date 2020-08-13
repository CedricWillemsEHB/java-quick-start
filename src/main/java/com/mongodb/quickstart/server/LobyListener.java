package com.mongodb.quickstart.server;

import java.util.List;

public interface LobyListener {
    int makeLobyGetID();
    List<Object> getPlayersInLoby(int id);

}
