package com.mongodb.quickstart.server;

import com.mongodb.quickstart.models.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameServer implements LobyListener {
    private ServerSocket ss;
    private List<ServerSideConnection> players;
    private int numPlayers= 0;
    private int numDungeonmaster;
    private boolean partyReady;
    private List<Loby> lobies;

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
        Loby loby = null;
        do{
            Random rand = new Random();
            //generate random values from 0-1000000
            int int_random = rand.nextInt(1000000);
            loby = new Loby(int_random);
        } while (!lobies.contains(loby));

        return loby.getId();
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

    private class ServerSideConnection implements Runnable {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int playerID;
        private int lobyId = -1;
        private List<ServerSideConnection> playersInLoby = new ArrayList<>();
        private LobyListener lobyListener;


        public ServerSideConnection(Socket s, int playerID, LobyListener lobyListener) {
            socket = s;
            this.playerID = playerID;
            this.lobyListener = lobyListener;
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
            int receiverID= 0;
            boolean firstTime = true;
            try {
                //dataOut.writeInt(playerID);

                while(true) {
                    //TODO
                    int condition=dataIn.readInt();
                    switch(condition) {
                        case ServerContract.SHOW_MAP:
                            if(firstTime) {
                                numDungeonmaster = playerID;
                                firstTime = false;
                            }
                            //int maxRoom = dataIn.readInt();
                            int length = dataIn.readInt();
                            System.out.println("length : " + length);
                            byte[] data=new byte[length];
                            dataIn.readFully(data);
                            String str=new String(data,"UTF-8");
                            Map m = (Map) Serializator.convertFromByteString(str);
                            /*for(int i = 0 ; i < maxRoom; i++) {
                                int length = dataIn.readInt();
                                byte[] data=new byte[length];
                                dataIn.readFully(data);
                                String str=new String(data,"UTF-8");
                                listStrings.add(str);
                                System.out.println(str);
                                Room r = Serializator.deserializeRoom(str);
                                listRooms.add(r);

                                System.out.println(r.toString());
                            }*/
                            receiverID=0;
                            for(ServerSideConnection player : players) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(1);
                                    str = Serializator.convertToByteString(m);
                                    byte[] data2 = str.getBytes("UTF-8");
                                    player.dataOut.writeInt(data2.length);
                                    System.out.println("data2.length: " + data2.length);
                                    player.dataOut.write(data);
                                    dataOut.flush();
                                    /*player.dataOut.writeInt(maxRoom);
                                    for(Room str : listRooms) {
                                        player.sendRoom(str);
                                    }*/
                                }
                                receiverID++;
                            }

                            break;
                        case ServerContract.GO_NORTH:
                            receiverID=0;
                            for(ServerSideConnection player : players) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(2);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.GO_SOUTH:
                            receiverID=0;
                            for(ServerSideConnection player : players) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(3);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.GO_EAST:
                            receiverID=0;
                            for(ServerSideConnection player : players) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(4);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.GO_WEST:
                            receiverID=0;
                            for(ServerSideConnection player : players) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(5);
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
                            for(ServerSideConnection player : players) {
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
                str = Serializator.serializeRoom(r);
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
