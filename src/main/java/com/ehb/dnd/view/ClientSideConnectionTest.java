package com.ehb.dnd.view;

import com.ehb.dnd.model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSideConnectionTest {

    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private Room currentRoom;
    private Map map;

    public ClientSideConnectionTest() {
        System.out.println("----Client----");
        try {
            //Connect to the server
            socket = new Socket("localhost", 51734);
            //Setup streams for the input and output
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("IO Exception from CSC constructor");
        }
    }

    public int makeLobby(){
        try {
            //Send condition
            dataOut.writeInt(ServerContract.MAKE_LOBBY);
            System.out.println("ServerContract.MAKE_LOBBY : ");
            dataOut.flush();
            System.out.println("dataOut.flush()");
            int i = dataIn.readInt();
            System.out.println("ServerContract.MAKE_LOBBY : "+i);
            if (i == ServerContract.MAKE_LOBBY){
                int y = dataIn.readInt();
                System.out.println("Lobby : "+y);
                return y;
            }
        } catch (IOException ex) {
            System.out.println("IO Exception from makeLobby() CSC");
        }
        return -1;
    }

    public boolean getInLobby(int lobbbyId){
        try {
            System.out.println("getInLobby");
            //Send condition
            dataOut.writeInt(ServerContract.GET_IN_LOBBY);
            dataOut.writeInt(lobbbyId);
            dataOut.flush();
            System.out.println("dataOut.flush()");
            int i = dataIn.readInt();
            System.out.println("ServerContract.GET_IN_LOBBY : "+i);
            if (i == ServerContract.GET_IN_LOBBY){
                boolean isInLobby = dataIn.readBoolean();
                System.out.println("Lobby : "+isInLobby);
                return isInLobby;
            }
        } catch (IOException ex) {
            System.out.println("IO Exception from makeLobby() CSC");
        }
        System.out.println("getInLobby error");
        return false;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void sendNorth() {
        try {
            //Send condition
            dataOut.writeInt(2);
            dataOut.flush();
        } catch (IOException ex) {
            System.out.println("IO Exception from sendNorth() CSC");
        }
    }

    public void sendSouth() {
        try {
            //Send condition
            dataOut.writeInt(3);
            dataOut.flush();
        } catch (IOException ex) {
            System.out.println("IO Exception from sendButtonNum() CSC");
        }
    }

    public void sendEast() {
        try {
            //Send condition
            dataOut.writeInt(4);
            dataOut.flush();
        } catch (IOException ex) {
            System.out.println("IO Exception from sendButtonNum() CSC");
        }
    }

    public void sendWest() {
        try {
            //Send condition
            dataOut.writeInt(5);
            dataOut.flush();
        } catch (IOException ex) {
            System.out.println("IO Exception from sendButtonNum() CSC");
        }
    }

    //Send other players action to make map
    public void sendMakeMap() {
        try {
            String str = "";
            dataOut.writeInt(1);
            str = Serializator.convertToByteString(map);
            byte[] data = str.getBytes("UTF-8");
            dataOut.writeInt(data.length);
            System.out.println(data.length);
            dataOut.write(data);
            dataOut.flush();
        } catch (IOException ex) {
            System.out.println("IO Exception from sendButtonNum() CSC");
        }
    }

    //TODO Send to the server the starting of a fight
    public void sendFight() {
        try {
            //Send condition
            dataOut.writeInt(6);
            //If available send character to server
            if(currentRoom.checkEventAvailable() && currentRoom.getEvent().getCharacters() != null) {
                //You can't send object direct to server so you need to convert
                //the object in a string with serialization then convert the string in an array of bytes
                //to send it
                String s;
                byte[] data;
                List<ICharacters> characters = new ArrayList<ICharacters>();
                characters = currentRoom.getEvent().getCharacters();
                dataOut.writeInt(characters.size());
                for( ICharacters iCharacter :characters) {
                    s = Serializator.convertToByteString(iCharacter);
                    data = s.getBytes("UTF-8");
                    dataOut.writeInt(data.length);
                    dataOut.write(data);
                }
            }
            dataOut.flush();
        } catch (IOException ex) {
            System.out.println("IO Exception from sendFight() CSC");
        }
    }

    //TODO Send to the server the following of the fight
    public void sendCombat(List<ICharacters> enemies) {
        try {
            //Send condition
            dataOut.writeInt(8);
            if(currentRoom.checkEventAvailable()) {
                String s;
                byte[] data;
                dataOut.writeInt(enemies.size());
                for( ICharacters iCharacter :enemies) {
                    s = Serializator.convertToByteString(iCharacter);
                    data = s.getBytes("UTF-8");
                    dataOut.writeInt(data.length);
                    dataOut.write(data);
                }
            }
            dataOut.flush();
        } catch (IOException ex) {
            System.out.println("IO Exception from sendCombat() CSC");
        }
    }

    public void sendButtonNum(int n) {
        try {
            dataOut.writeInt(n);
            dataOut.flush();
        } catch (IOException ex) {
            System.out.println("IO Exception from sendButtonNum() CSC");
        }
    }

    public int receiveButtonNum() {
        int n = -1;
        try {
            n = dataIn.readInt();
        } catch(IOException ex) {
            System.out.println("IO Exception from receiveButtonNum() CSC");
        }
        return n;
    }

    //Get list of rooms
    public Map receiveRooms() {
        Map m = null;
        //List<Room> listRooms = new ArrayList<Room>();
        try {
            System.out.println("receiveRooms");
            int length = dataIn.readInt();
            System.out.println("lenght: " + length);
            byte[] data = new byte[length];
            dataIn.readFully(data);
            String str = new String(data, "UTF-8");
            System.out.println(str);
            m = (Map) Serializator.convertFromByteString(str);
            System.out.println(m.toString());

                /*int maxRoom = dataIn.readInt();
                System.out.println("max Room: " + maxRoom);
                for (int i = 0; i < maxRoom; i++) {
                    int length = dataIn.readInt();
                    //System.out.println("lenght: " + length);
                    byte[] data = new byte[length];
                    dataIn.readFully(data);
                    String str = new String(data, "UTF-8");
                    //System.out.println(str);
                    Room r = Serializator.deserializeRoom(str);
                    listRooms.add(r);
                    System.out.println(r.toString());
                    //System.out.println(i);
                }*/
        } catch (IOException ex) {
            System.out.println("IO Exception from receiveButtonNum() CSC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return m;
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
                characters.add(iCharacter);
            }
        }catch (IOException ex) {
            System.out.println("IO Exception from recieveCharcter() SSC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return characters;
    }

    //Close connection with the server
    public void closeConnection() {
        try {
            socket.close();
            System.out.println("---CONNECTION CLOSED---");
        } catch(IOException ex) {
            System.out.println("IO Exception from closeConnestion() CSC");
        }
    }
}
