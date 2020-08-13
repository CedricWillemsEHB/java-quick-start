package com.ehb.dnd.server;

import com.ehb.dnd.model.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameServer implements LobbyListener {
    private ServerSocket ss;
    private List<ServerSideConnection> players;
    private int numPlayers= 0;
    private List<Lobby> lobies;

    public GameServer() {
        players = new ArrayList<ServerSideConnection>();
        try {
            ss = new ServerSocket(51734);
            lobies= new ArrayList<>();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");
            while(true) {
                if(ss != null) {
                    Socket s = ss.accept();

                    System.out.println("Player #" + (numPlayers + 1) + " has connected.");
                    ServerSideConnection ssc = new ServerSideConnection(s, numPlayers,this);
                    players.add(ssc);
                    Thread t = new Thread(ssc);
                    t.start();
                    numPlayers++;
                }
            }
        }catch(IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    @Override
    public int makeLobyGetID() {
        Lobby lobby = null;
        do{
            Random rand = new Random();
            //generate random values from 0-1000000
            int int_random = rand.nextInt(1000000);
            lobby = new Lobby(int_random);
            if (!lobies.contains(lobby)){
                lobies.add(lobby);
                break;
            }
        } while (true);

        return lobby.getId();
    }

    @Override
    public List<Object> getPlayersInLoby(int id) {
        List<Object> playersInLoby = new ArrayList<>();
        for (ServerSideConnection p : players) {
            if (p.getLobyId() == id){
                playersInLoby.add(p);
            }
        }
        return playersInLoby;
    }

    @Override
    public boolean getInLobby(int id) {
        Lobby lobby = new Lobby(id);
        return lobies.contains(lobby);
    }

    private class ServerSideConnection implements Runnable {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int playerID;
        private int lobyId = -1;
        private List<ServerSideConnection> playersInLoby = new ArrayList<>();
        private LobbyListener lobbyListener;
        private int numDungeonmaster;


        public ServerSideConnection(Socket s, int playerID, LobbyListener lobbyListener) {
            socket = s;
            this.playerID = playerID;
            this.lobbyListener = lobbyListener;
            try {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                System.out.println("IOException from run() SSC");
            }
        }

        public int getPlayerID() {
            return playerID;
        }

        public int getLobyId() {
            return lobyId;
        }

        public void setLobyId(int lobyId) {
            this.lobyId = lobyId;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String command = "";
            String tmp = "";
            if(lobyId >= 0){
                System.out.println("playerID = " + playerID + ", lobyId = " + lobyId);
            } else {
                System.out.println("playerID = " + playerID + ", lobyId = null");
            }
            int receiverID= 0;
            boolean firstTime = true;
            try {
                while(true) {
                    if(lobyId >= 0){
                        System.out.println("playerID = " + playerID + ", lobyId = " + lobyId);
                    } else {
                        System.out.println("playerID = " + playerID + ", lobyId = null");
                    }
                    //TODO
                    int condition=dataIn.readInt();
                    switch(condition) {
                        case ServerContract.SHOW_MAP:
                            if(firstTime) {
                                //TODO make another function to setup numDungeonmaster
                                numDungeonmaster = playerID;
                                firstTime = false;
                            }
                            playersInLoby = new ArrayList<>();
                            for (Object o : lobbyListener.getPlayersInLoby(lobyId)) {
                                playersInLoby.add((ServerSideConnection) o);
                            }
                            //int maxRoom = dataIn.readInt();
                            int length = dataIn.readInt();
                            System.out.println("length : " + length);
                            byte[] data=new byte[length];
                            dataIn.readFully(data);
                            String str=new String(data,"UTF-8");
                            Map m = (Map) Serializator.convertFromByteString(str);
                            for(ServerSideConnection player : playersInLoby) {
                                if(playerID!=player.playerID) {
                                    player.dataOut.writeInt(ServerContract.SHOW_MAP);
                                    str = Serializator.convertToByteString(m);
                                    byte[] data2 = str.getBytes("UTF-8");
                                    player.dataOut.writeInt(data2.length);
                                    System.out.println("To player = "+ player.playerID+"data2.length: " + data2.length);
                                    player.dataOut.write(data);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.GO_NORTH:
                            receiverID=0;
                            for(ServerSideConnection player : playersInLoby) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(ServerContract.GO_NORTH);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.GO_SOUTH:
                            receiverID=0;
                            for(ServerSideConnection player : playersInLoby) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(ServerContract.GO_SOUTH);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.GO_EAST:
                            receiverID=0;
                            for(ServerSideConnection player : playersInLoby) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(ServerContract.GO_EAST);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.GO_WEST:
                            receiverID=0;
                            for(ServerSideConnection player : playersInLoby) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(ServerContract.GO_WEST);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.SET_UP_FIGHT:
                            List<ICharacters> enemies = new ArrayList<ICharacters>();
                            List<ServerSideConnection> playersInFight = new ArrayList<ServerSideConnection>();
                            str = "";
                            receiverID=0;
                            for(ServerSideConnection player : playersInLoby) {
                                if(receiverID!=playerID) {
                                    playersInFight.add(player);
                                }
                                receiverID++;
                            }
                            int maxCharacters = dataIn.readInt();
                            for(int y = 0; y < maxCharacters; y++) {
                                length = dataIn.readInt();
                                data=new byte[length];
                                dataIn.readFully(data);
                                str=new String(data,"UTF-8");
                                ICharacters iCharacter = (ICharacters) Serializator.convertFromByteString(str);
                                enemies.add(iCharacter);
                            }
                            startFight(playersInFight, enemies);
                            //TODO send the enemies back to the DM
                            break;
                        case ServerContract.GET_FROM_FIGHT:
                            break;
                        case ServerContract.MAKE_LOBBY:
                            System.out.println("MAKE_LOBBY");
                            lobyId = lobbyListener.makeLobyGetID();
                            System.out.println("MAKE_LOBBY : " + lobyId);
                            dataOut.writeInt(ServerContract.MAKE_LOBBY);
                            dataOut.writeInt(lobyId);
                            dataOut.flush();
                            break;
                        case ServerContract.GET_IN_LOBBY:
                            lobyId = dataIn.readInt();
                            dataOut.writeInt(ServerContract.GET_IN_LOBBY);
                            dataOut.writeBoolean(getInLobby(lobyId));
                            dataOut.flush();
                            System.out.println("GET_IN_LOBBY : " + lobyId);
                            break;
                    }
                }
            }catch(IOException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        //TODO start Fight
        public List<ICharacters> startFight(List<ServerSideConnection> playersInFight, List<ICharacters> enemies) throws IOException {
            //Setup the boolean to know if the players are still fighting
            boolean stillFighting = true;
            //Setup the condition give by the player to make an action
            int condition = 0;
            int deadEnemies = 0;
            int maxEnemies = 0;
            //Setup a list of players who run away
            List<ServerSideConnection> cowards = new ArrayList<ServerSideConnection>();
            while(stillFighting) {
                //Let every player play each on his turn
                for(ServerSideConnection player : playersInFight) {
                    //Send to the fighting player the choose to fight
                    player.dataOut.writeInt(6);
                    //Send to the fighting player the enemies to fight
                    sendCharacters(enemies);
                    //Wait for a response from the fighting player
                    condition=dataIn.readInt();
                    switch(condition) {
                        //The player fought a enemy
                        case 8:
                            enemies = recieveCharacters();
                            break;
                        //The player run from the enemies
                        case 9:
                            cowards.add(player);
                            break;
                    }
                }
                //Remove the players who run away from the players who are still fighting
                for(ServerSideConnection coward : cowards) {
                    playersInFight.remove(coward);
                }
                //Clear the cowards
                cowards.clear();
                //Get the amount of enemies
                maxEnemies = enemies.size();
                for(ICharacters enemy : enemies) {
                    //Get the amount of dead enemies
                    if(enemy.getHP() == 0) {
                        deadEnemies++;
                    }
                }
                //If all enemies are dead or if no players want to fight
                if(deadEnemies == maxEnemies || playersInFight.size() == 0) {
                    stillFighting = false;
                }
                deadEnemies = 0;
            }
            return enemies;
        }
        public List<ICharacters> recieveCharacters(){
            //Setup a list of characters
            List<ICharacters> characters = new ArrayList<ICharacters>();
            //Setup length of the byte array
            int length;
            //Setup the byte array
            byte[] data;
            //Setup string for the deserialized object
            String str;
            try {
                //Get the amount of characters
                int maxCharacters = dataIn.readInt();
                //loop to add every characters in a list
                for(int y = 0; y < maxCharacters; y++) {
                    length = dataIn.readInt();
                    data=new byte[length];
                    dataIn.readFully(data);
                    str=new String(data,"UTF-8");
                    ICharacters iCharacter = (ICharacters) Serializator.convertFromByteString(str);
                    System.out.println("recieveCharacters / iCharacter: " + iCharacter.toString());
                    characters.add(iCharacter);
                }
            }catch (IOException ex) {
                System.out.println("IO Exception from recieveCharcter() SSC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return characters;
        }
        public void sendCharacters(List<ICharacters> enemies)  {
            try {
                String s;
                byte[] data;
                dataOut.writeInt(enemies.size());
                for( ICharacters iCharacter :enemies) {
                    s = Serializator.convertToByteString(iCharacter);
                    data = s.getBytes("UTF-8");
                    dataOut.writeInt(data.length);
                    dataOut.write(data);
                }
            } catch (IOException ex) {
                System.out.println("IO Exception from sendCharacters() SSC");
            }

        }
        public void sendRoom(Room r) {
            String str;
            try {
                str = Serializator.convertToByteString(r);
                byte[] data=str.getBytes("UTF-8");
                dataOut.writeInt(data.length);
                dataOut.write(data);
                dataOut.flush();
            } catch (IOException ex) {
                System.out.println("IOException from sendButtonNum() SSC");
            }
        }
        //Send Int
        public void sendButtonNum(int n) {
            try {
                dataOut.writeInt(n);
                dataOut.flush();
            } catch (IOException ex) {
                System.out.println("IOException from sendButtonNum() SSC");
            }
        }

        //Close cooection
        public void closeConnection() {
            try {
                dataOut.close();
                System.out.println("Connection closed");
            } catch (IOException ex) {
                System.out.println("IOException from closeConnection() SSC");
            }
        }
    }



    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}
