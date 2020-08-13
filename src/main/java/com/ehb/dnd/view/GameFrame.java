package com.ehb.dnd.view;

import com.ehb.dnd.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    /**
     * GameFrame is the JFrame where to whole game play itself
     * It also has a innerclass of a client side Socket
     */
    private static final long serialVersionUID = 1L;
    //Initialize datamembers
    private Toolbar toolbar;
    private ImagePanel imagePanel;
    private Map map;
    private int currentX;
    private int currentY;
    private Room currentRoom;
    private Player player;
    private User user;
    private String imageUrl = "image\\default\\";
    //TODO test class ClientSideConnectionTest
    private ClientSideConnectionTest csc;

    public GameFrame() {
        super("Hello world");
        setLayout(new BorderLayout());
        imagePanel = new ImagePanel();
        toolbar = new Toolbar(false);
        add(imagePanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.SOUTH);
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }
    public GameFrame(Player player) {
        super("Dungeons & Dragons | Adventurer: "+player.getName());
        //Make a new client side Socket and connect to the server side
        connectToServer();
        setLayout(new BorderLayout());

        imagePanel = new ImagePanel();
        toolbar = new Toolbar(player.isDM());
        this.player = player;
        //Setup actions for the buttons
        toolbar.setStringListener(new StringListener() {
            @Override
            public void makeMap() throws IOException {
                if (map == null) {
                    generatePath();
                    //TODO test class ClientSideConnectionTest
                    csc.sendMakeMap();
                }
                generateMapImage();
                showMapImage();

            }

            @Override
            public void clickNorth() throws IOException {
                // Go up
                if(currentRoom.isGoUp()) {
                    csc.sendNorth();
                    goNorth();
                    generateMapImage();
                    showMapImage();
                }
            }

            @Override
            public void clickSouth() throws IOException {
                // Go down
                if(currentRoom.isGoDown()) {
                    csc.sendSouth();
                    goSouth();
                    generateMapImage();
                    showMapImage();
                }
            }

            @Override
            public void clickEast() throws IOException {
                // Go right
                if(currentRoom.isGoRight()) {
                    csc.sendEast();
                    goEast();
                    generateMapImage();
                    showMapImage();
                }
            }

            @Override
            public void clickWest() throws IOException {
                // Go left
                if(currentRoom.isGoLeft()) {
                    csc.sendWest();
                    goWest();
                    generateMapImage();
                    showMapImage();
                }

            }

            @Override
            public void clickFight() throws IOException {
                // The player attack the monster
                System.out.println(player.getName() + " fight the monsters!");
                attackEnemy();
            }

            @Override
            public void clickRun() throws IOException {
                // The player run away from the monster
                System.out.println(player.getName() + " run away from the monsters!");
                csc.sendButtonNum(9);
            }
        });
        add(imagePanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.SOUTH);

        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //If player isn't a dungeon master, wait for a input of the dungeon master
        if (!player.isDM()) {
            try {
                //Show start page for players
                BufferedImage image = ImageIO.read(new File(imageUrl + "startPlayer.png"));
                imagePanel.setImage(image);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            //Disable the buttons
            toolbar.toggleButtons(player.isDM());
            //wait for a input of the dungeon master
            nextAction();
        } else {
            try {
                //Show start page for dungeon master
                BufferedImage image = ImageIO.read(new File(imageUrl + "startDM.png"));
                imagePanel.setImage(image);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public GameFrame(User user,Player player) {
        super("Dungeons & Dragons | Adventurer: "+player.getName());
        //Make a new client side Socket and connect to the server side
        connectToServer();
        setLayout(new BorderLayout());

        imagePanel = new ImagePanel();
        toolbar = new Toolbar(player.isDM());
        this.user = user;
        this.player = player;
        //Setup actions for the buttons
        toolbar.setStringListener(new StringListener() {
            @Override
            public void makeMap() throws IOException {
                if (map == null) {
                    generatePath();
                    //TODO test class ClientSideConnectionTest
                    csc.setMap(map);
                    csc.setCurrentRoom(currentRoom);
                    csc.sendMakeMap();
                }
                generateMapImage();
                showMapImage();

            }

            @Override
            public void clickNorth() throws IOException {
                // Go up
                if(currentRoom.isGoUp()) {
                    csc.sendNorth();
                    goNorth();
                    generateMapImage();
                    showMapImage();
                }
            }

            @Override
            public void clickSouth() throws IOException {
                // Go down
                if(currentRoom.isGoDown()) {
                    csc.sendSouth();
                    goSouth();
                    generateMapImage();
                    showMapImage();
                }
            }

            @Override
            public void clickEast() throws IOException {
                // Go right
                if(currentRoom.isGoRight()) {
                    csc.sendEast();
                    goEast();
                    generateMapImage();
                    showMapImage();
                }
            }

            @Override
            public void clickWest() throws IOException {
                // Go left
                if(currentRoom.isGoLeft()) {
                    csc.sendWest();
                    goWest();
                    generateMapImage();
                    showMapImage();
                }

            }

            @Override
            public void clickFight() throws IOException {
                // The player attack the monster
                System.out.println(player.getName() + " fight the monsters!");
                attackEnemy();
            }

            @Override
            public void clickRun() throws IOException {
                // The player run away from the monster
                System.out.println(player.getName() + " run away from the monsters!");
                csc.sendButtonNum(9);
            }
        });
        add(imagePanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.SOUTH);

        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //If player isn't a dungeon master, wait for a input of the dungeon master
        if (!player.isDM()) {
            try {
                //Show start page for players
                BufferedImage image = ImageIO.read(new File(imageUrl + "startPlayer.png"));
                imagePanel.setImage(image);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            //Disable the buttons
            toolbar.toggleButtons(player.isDM());
            //wait for a input of the dungeon master
            nextAction();
        } else {
            try {
                //Show start page for dungeon master
                BufferedImage image = ImageIO.read(new File(imageUrl + "startDM.png"));
                imagePanel.setImage(image);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public GameFrame(User user,Player player, ClientSideConnectionTest csc) {
        super("Dungeons & Dragons | Adventurer: "+player.getName());
        //Make a new client side Socket and connect to the server side
        //TODO test class ClientSideConnectionTest
        this.csc = csc;
        setLayout(new BorderLayout());

        imagePanel = new ImagePanel();
        toolbar = new Toolbar(player.isDM());
        this.user = user;
        this.player = player;
        //Setup actions for the buttons
        toolbar.setStringListener(new StringListener() {
            @Override
            public void makeMap() throws IOException {
                if (map == null) {
                    generatePath();
                    //TODO test class ClientSideConnectionTest
                    csc.setMap(map);
                    csc.setCurrentRoom(currentRoom);
                    csc.sendMakeMap();
                }
                generateMapImage();
                showMapImage();

            }

            @Override
            public void clickNorth() throws IOException {
                // Go up
                if(currentRoom.isGoUp()) {
                    csc.sendNorth();
                    goNorth();
                    generateMapImage();
                    showMapImage();
                }
            }

            @Override
            public void clickSouth() throws IOException {
                // Go down
                if(currentRoom.isGoDown()) {
                    csc.sendSouth();
                    goSouth();
                    generateMapImage();
                    showMapImage();
                }
            }

            @Override
            public void clickEast() throws IOException {
                // Go right
                if(currentRoom.isGoRight()) {
                    csc.sendEast();
                    goEast();
                    generateMapImage();
                    showMapImage();
                }
            }

            @Override
            public void clickWest() throws IOException {
                // Go left
                if(currentRoom.isGoLeft()) {
                    csc.sendWest();
                    goWest();
                    generateMapImage();
                    showMapImage();
                }

            }

            @Override
            public void clickFight() throws IOException {
                // The player attack the monster
                System.out.println(player.getName() + " fight the monsters!");
                attackEnemy();
            }

            @Override
            public void clickRun() throws IOException {
                // The player run away from the monster
                System.out.println(player.getName() + " run away from the monsters!");
                csc.sendButtonNum(9);
            }
        });
        add(imagePanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.SOUTH);

        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //If player isn't a dungeon master, wait for a input of the dungeon master
        if (!player.isDM()) {
            try {
                //Show start page for players
                BufferedImage image = ImageIO.read(new File(imageUrl + "startPlayer.png"));
                imagePanel.setImage(image);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            //Disable the buttons
            toolbar.toggleButtons(player.isDM());
            //wait for a input of the dungeon master
            nextAction();
        } else {
            try {
                //Show start page for dungeon master
                BufferedImage image = ImageIO.read(new File(imageUrl + "startDM.png"));
                imagePanel.setImage(image);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    //Make thread to wait for a input of the server
    public void nextAction() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("----Turn----");
                updateTurn();
            }
        });
        t.start();
    }
    //Get the input of the server and do something
    public void updateTurn() {
        int n = csc.receiveButtonNum();
        System.out.println("Command :" + n);
        switch(n) {
            //Show map if you're a player
            case ServerContract.SHOW_MAP:
                if(!player.isDM()) {
                    System.out.println("case 1");
                    initializeMap();
                    System.out.println("initializeMap");
                    map = csc.receiveRooms();
                    //TODO test class ClientSideConnectionTest
                    csc.setMap(map);
                    System.out.println("csc.receiveRooms() : " + map.toString());
                    startMapPlayer();
                    System.out.println("startMapPlayer");
                    try {
                        generateMapImage();
                        System.out.println("generateMapImage");
                        showRoomImage();
                        System.out.println("showRoomImage");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                //Wait for a new inupt
                nextAction();
                break;
            //Go North
            case ServerContract.GO_NORTH:
                goNorth();
                try {
                    generateMapImage();
                    showRoomImage();
                } catch(Exception e) {
                    System.out.println(e);
                }
                //Wait for a new inupt
                nextAction();
                break;
            //Go South
            case ServerContract.GO_SOUTH:
                goSouth();
                try {
                    generateMapImage();
                    showRoomImage();
                } catch(Exception e) {
                    System.out.println(e);
                }
                //Wait for a new inupt
                nextAction();
                break;
            //Go East
            case ServerContract.GO_EAST:
                goEast();
                try {
                    generateMapImage();
                    showRoomImage();
                } catch(Exception e) {
                    System.out.println(e);
                }
                //Wait for a new inupt
                nextAction();
                break;
            //Go West
            case ServerContract.GO_WEST:
                goWest();
                try {
                    generateMapImage();
                    showRoomImage();
                } catch(Exception e) {
                    System.out.println(e);
                }
                //Wait for a new inupt
                nextAction();
                break;
            //TODO Set player in the fight system if you're a player
            case ServerContract.SET_UP_FIGHT:
                //Enable the buttons for the player to fight
                if(!player.isDM()) {
                    toolbar.untoggleButtons(player.isDM());
                    currentRoom.getEvent().changeCharacters(csc.recieveCharacters());
                    //TODO: show enemies on screen
                    System.out.println(currentRoom.getEvent().getCharacters());
                }
                break;
            //TODO Get result of the fight and continue exploring if you're dungeon master
            case ServerContract.GET_FROM_FIGHT:
                try {
                    if(player.isDM()) {
                        if(currentRoom.checkEventAvailable()) {
                            int deadCharacters = 0;
                            int maxCharacters = 0;

                            List<ICharacters> characters = new ArrayList<ICharacters>();
                            characters = csc.recieveCharacters();
                            currentRoom.getEvent().changeCharacters(characters);
                            maxCharacters = characters.size();
                            //See if all monster are dead
                            for(ICharacters character : characters) {
                                //Get the amount of dead enemies
                                if(character.getHP() == 0) {
                                    deadCharacters++;
                                }
                            }
                            if(deadCharacters==maxCharacters) {
                                //TODO: Disable event
                            }
                            //TODO: send event back to the players
                            //Enable buttons for dungeon master
                            toolbar.untoggleButtons(player.isDM());
                        }
                    }
                } catch(Exception e) {
                    System.out.println(e);
                }
                break;
            default:
                //Wait for a new inupt
                nextAction();
        }

    }
    //Connect to the client side
    public void connectToServer() {
        //TODO test class ClientSideConnectionTest
        csc = new ClientSideConnectionTest();
    }
    //innerClass client side
    public class ClientSideConnection{

        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public ClientSideConnection() {
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
        public void sendNorth() {
            try {
                //Send condition
                dataOut.writeInt(2);
                dataOut.flush();
            } catch (IOException ex) {
                System.out.println("IO Exception from sendButtonNum() CSC");
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
                //byte[] data=str.getBytes("UTF-8");
                //dataOut.writeInt(data.length);
                //dataOut.write(data);
                dataOut.writeInt(1);
                str = Serializator.convertToByteString(map);
                byte[] data = str.getBytes("UTF-8");
                dataOut.writeInt(data.length);
                System.out.println(data.length);
                dataOut.write(data);
                //TODO: if it work erase the comment block
                /*dataOut.writeInt(map.getMainPath().size() + map.getSubPaths().size());
                int index;
                for (Room r : map.getMainPath()) {
                    index = map.getMap().indexOf(new Room(r.getY(), r.getX()));
                    r = map.getMap().get(index);
                    str = Serializator.serializeRoom(r);
                    byte[] data = str.getBytes("UTF-8");
                    dataOut.writeInt(data.length);
                    dataOut.write(data);
                }
                for (Room r : map.getSubPaths()) {
                    index = map.getMap().indexOf(new Room(r.getY(), r.getX()));
                    r = map.getMap().get(index);
                    str = Serializator.serializeRoom(r);
                    byte[] data = str.getBytes("UTF-8");
                    dataOut.writeInt(data.length);
                    dataOut.write(data);
                }*/
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

    public void initializeMap() {
        //Make a map
        map = new Map(20,20);
    }
    //Generate path in map
    public void generatePath() {
        initializeMap();
        //Find A random path
        while(!map.findPath(10, 10, 10, 10, 20,3,5));
        //Connect the rooms of the map
        startMap();
        //Setuo the events
        setEventsInMap();
    }
    //Start the map
    public void startMap() {
        map.makeRoomConnected();
    }
    //Set events in the map
    //TODO: it is hard coded for now, make the DM choose the events
    public void setEventsInMap() {
        List<IEvent> events = new ArrayList<IEvent>();
        IEvent combat1 = new Combat();
        List<ICharacters> characters = new ArrayList<ICharacters>();
        characters.add(new Monster("Wolf1",50,2));
        combat1.setupEvent("Wolves", characters);
        events.add(combat1);
        IEvent combat2 = new Combat();
        characters = new ArrayList<ICharacters>();
        characters.add(new Monster("Heks1",25,8));
        combat2.setupEvent("Witches", characters);
        events.add(combat2);
        map.setEvent(events);
        currentX = map.getMainPath().get(0).getX();
        currentY = map.getMainPath().get(0).getY();
        currentRoom = map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY)));
        //TODO test class ClientSideConnectionTest
        csc.setMap(map);
        csc.setCurrentRoom(currentRoom);
    }
    //Start the map for the players
    public void startMapPlayer() {
        map.setMapWithPaths();
        startMap();
        currentX = map.getMainPath().get(0).getX();
        currentY = map.getMainPath().get(0).getY();
        currentRoom = map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY)));
        //TODO test class ClientSideConnectionTest
        csc.setMap(map);
        csc.setCurrentRoom(currentRoom);
        System.out.println(map.showMapWithEvent());
    }
    //Show the map on GUI
    public void showMapImage() throws IOException{
        String imageMapUrl = imageUrl + "map\\";
        BufferedImage image = ImageIO.read(new File(imageMapUrl + "finalImg.png"));
        imagePanel.setImage(image);
    }
    //Show the room where the player is at
    public void showRoomImage() throws IOException {
        //Set path for room image
        String imageRoomUrl = imageUrl + "room\\";
        int i = 0;
        if(map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).checkEventAvailable()) {
            i = 17;
        } else {
            if(!map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 0;
            } else if(!map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 1;
            } else if(!map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 2;
            } else if(!map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 3;
            } else if(!map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 4;
            } else if(!map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 5;
            } else if(!map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 6;
            } else if(!map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 7;
            } else if(map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 8;
            } else if(map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 9;
            } else if(map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 10;
            } else if(map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 11;
            } else if(map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 12;
            } else if(map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 13;
            } else if(map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 14;
            } else if(map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoRight()
                    && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).isGoLeft()) {
                i = 15;
            }
        }

        //Set room as buffered image
        BufferedImage image = ImageIO.read(new File(imageRoomUrl + i + ".png"));
        //Set image in panel
        imagePanel.setImage(image);
    }

    //Made an image of the map
    public void generateMapImage() throws IOException {
        String imageMapUrl = imageUrl + "map\\";
        // Setup members to make a image of the map
        int rows = map.getY();   //we assume the no. of rows and cols are known and each chunk has equal width and height
        int cols = map.getX();
        int chunks = 18;

        int chunkWidth, chunkHeight;
        int type;
        //fetching image files
        File[] imgFiles = new File[chunks];
        for (int i = 0; i < chunks; i++) {
            imgFiles[i] = new File(imageMapUrl + i + ".png");
        }

        //creating a buffered image array from image files
        BufferedImage[] buffImages = new BufferedImage[chunks];
        for (int i = 0; i < chunks; i++) {
            buffImages[i] = ImageIO.read(imgFiles[i]);

        }
        type = buffImages[0].getType();
        chunkWidth = buffImages[0].getWidth();
        chunkHeight = buffImages[0].getHeight();

        //Initializing the final image
        BufferedImage finalImg = new BufferedImage(chunkWidth*cols, chunkHeight*rows, type);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(i == currentY && j == currentX) {
                    //Image for current room
                    finalImg.createGraphics().drawImage(buffImages[16], chunkWidth * j, chunkHeight * i, null);
                }
                else if(map.getMap().get(map.getMap().indexOf(new Room(j, i))).checkEventAvailable()) {
                    //Image for room with event
                    finalImg.createGraphics().drawImage(buffImages[17], chunkWidth * j, chunkHeight * i, null);
                }
                else {
                    if(!map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[0], chunkWidth * j, chunkHeight * i, null);
                    } else if(!map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[1], chunkWidth * j, chunkHeight * i, null);
                    } else if(!map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[2], chunkWidth * j, chunkHeight * i, null);
                    } else if(!map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[3], chunkWidth * j, chunkHeight * i, null);
                    } else if(!map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[4], chunkWidth * j, chunkHeight * i, null);
                    } else if(!map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[5], chunkWidth * j, chunkHeight * i, null);
                    } else if(!map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[6], chunkWidth * j, chunkHeight * i, null);
                    } else if(!map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[7], chunkWidth * j, chunkHeight * i, null);
                    } else if(map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[8], chunkWidth * j, chunkHeight * i, null);
                    } else if(map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[9], chunkWidth * j, chunkHeight * i, null);
                    } else if(map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[10], chunkWidth * j, chunkHeight * i, null);
                    } else if(map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[11], chunkWidth * j, chunkHeight * i, null);
                    } else if(map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[12], chunkWidth * j, chunkHeight * i, null);
                    } else if(map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[13], chunkWidth * j, chunkHeight * i, null);
                    } else if(map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && !map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[14], chunkWidth * j, chunkHeight * i, null);
                    } else if(map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoUp() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoRight()
                            && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoDown() && map.getMap().get(map.getMap().indexOf(new Room(j, i))).isGoLeft()) {
                        finalImg.createGraphics().drawImage(buffImages[15], chunkWidth * j, chunkHeight * i, null);
                    }
                }

            }
        }
        ImageIO.write(finalImg, "png", new File( imageMapUrl +"finalImg.png"));

    }
    //Attack the first enemy encountered
    public void attackEnemy() {
        //Get the enemies
        List<ICharacters> enemies = new ArrayList<ICharacters>();
        enemies = csc.recieveCharacters();
        for(ICharacters enemy : enemies) {
            //if the enemy is alive the player attack him and the enemy attack the player
            if(enemy.getHP()>0) {
                enemy.getAttacked(player.getAttack());
                player.setHp(player.getHp()-enemy.attack());
                //TODO:Show the fight on the interface
                System.out.println(enemy.toString());
                System.out.println(player.toString());
                break;
            }
        }
        //Disable the button
        toolbar.toggleButtons(player.isDM());
        //Send the enemies back to the server
        csc.sendCombat(enemies);
    }
    //Start the fight
    //TODO: The fighting system doesn't work right now
    public void startFight() {
        if(currentRoom.getEvent().getCharacters()!=null) {
            //csc.sendFight();
            //toolbar.toggleButtons(player.isDM());
            //nextAction();
        }

    }
    //player go up in map
    public void goNorth() {
        if(currentRoom.isGoUp()) {
            currentY -= 1;
            currentRoom = map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY)));
            //TODO test class ClientSideConnectionTest
            csc.setCurrentRoom(currentRoom);
            System.out.println("Go: " +map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).toString());
            if(currentRoom.checkEventAvailable() && player.isDM()) {
                startFight();
            }
        }
    }
    //player go down in map
    public void goSouth() {
        if(currentRoom.isGoDown()) {
            currentY += 1;
            currentRoom = map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY)));
            //TODO test class ClientSideConnectionTest
            csc.setCurrentRoom(currentRoom);
            System.out.println("Go: " +map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).toString());
            if(currentRoom.checkEventAvailable() && player.isDM()) {
                startFight();
            }
        }
    }
    //player go right in map
    public void goEast() {
        if(currentRoom.isGoRight()) {
            currentX += 1;
            currentRoom = map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY)));
            //TODO test class ClientSideConnectionTest
            csc.setCurrentRoom(currentRoom);
            System.out.println("Go: " +map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).toString());
            if(currentRoom.checkEventAvailable() && player.isDM()) {
                startFight();
            }
        }
    }
    //player go left in map
    public void goWest() {
        if(currentRoom.isGoLeft()) {
            currentX -= 1;
            currentRoom = map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY)));
            //TODO test class ClientSideConnectionTest
            csc.setCurrentRoom(currentRoom);
            System.out.println("Go: " +map.getMap().get(map.getMap().indexOf(new Room(currentX, currentY))).toString());
            if(currentRoom.checkEventAvailable() && player.isDM()) {
                startFight();
            }
        }
    }
}
