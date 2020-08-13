package com.ehb.dnd.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map implements Serializable {
    ObjectId id;
    List<Room> map;
    List<Room> mainPath = new ArrayList<Room>();
    List<Room> subPaths = new ArrayList<Room>();
    int x;
    int y;
    public Map() {
    }

    public Map(int x, int y) {

        super();
        this.map = new ArrayList<>();
        this.x = x;
        this.y = y;
        for (int h = 0; h < y; h++){
            for(int w = 0; w < x; w++){
                Room room = new Room(w,h);
                map.add(room);
            }
        }
        // TODO Auto-generated constructor stub
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }


    public List<Room> getMap() {
        return map;
    }


    public void setMap(List<Room> map) {
        this.map = map;
    }

    public void setEvent(List<IEvent> events) {
        int randomNum;
        int x;
        int y;
        int index;
        Random rand;
        for(int i = 0; i < events.size(); i++) {
            rand = new Random();
            do {
                randomNum =  rand.nextInt((getMainPath().size() -1) ) + 1;
                x = getMainPath().get(randomNum).getX();
                y = getMainPath().get(randomNum).getY();
                index = getMap().indexOf(new Room(x,y));
            }while(!getMap().get(index).isEventNull(events.get(i)));
        }
    }


    public List<Room> getMainPath() {
        return mainPath;
    }


    public void setMainPath(List<Room> path) {
        this.mainPath = path;
    }

    public List<Room> getSubPaths() {
        return subPaths;
    }


    public void setSubPaths(List<Room> subPaths) {
        this.subPaths = subPaths;
    }
    public void setMapWithPaths() {
        int index;
        for(Room r : mainPath ) {
            index = getMap().indexOf(r);
            this.map.get(index).setAccessible(true);
        }
    }
    //Connect every room to make the map playable
    public void makeRoomConnected() {
        //Initialize the dimensions of the map
        int minx = x;
        int miny = y;
        int maxx = 0;
        int maxy = 0;
        int index;
        int index2;
        for (int y2 = 0; y2 < y; y2++){
            for(int x2 = 0; x2 < x; x2++) {
                index = getMap().indexOf(new Room(x2,y2));
                //Look for each room if it is accessible
                if(this.map.get(index).getAccessible()) {
                    //Set direction true if the room in the direction is accessible
                    if(y2>0){
                        index2 = getMap().indexOf(new Room(x2,y2-1));
                        if(this.map.get(index2).getAccessible())
                            this.map.get(index).setGoUp(true);
                    }
                    if(y2<y){
                        index2 = getMap().indexOf(new Room(x2,y2+1));
                        if(this.map.get(index2).getAccessible())
                            this.map.get(index).setGoDown(true);
                    }
                    if(x2<x){
                        index2 = getMap().indexOf(new Room(x2+1,y2));
                        if(this.map.get(index2).getAccessible())
                            this.map.get(index).setGoRight(true);
                    }
                    if(x2>0){
                        index2 = getMap().indexOf(new Room(x2-1,y2));
                        if(this.map.get(index2).getAccessible())
                            this.map.get(index).setGoLeft(true);
                    }
                    //Set the dimensions
                    if(minx>x2)
                        minx = x2;
                    if(miny>y2)
                        miny = y2;
                    if(maxx<x2)
                        maxx = x2;
                    if(miny<y2)
                        maxy = y2;
                }
            }
        }
        //TODO: Remade the dimension of the map
        this.x = maxx +1;
        this.y = maxy +1;
    }

    public void makeRoomConnectedWithPath() {
        int maxx = 0;
        int maxy = 0;
        int index;
        for(int i = 0; i < this.mainPath.size(); i++) {
            //System.out.println(path.get(i).toString());
            int x = this.mainPath.get(i).getX();
            int y = this.mainPath.get(i).getY();
            index = getMap().indexOf(new Room(x,y));
            if(i > 0) {
                if(x < this.mainPath.get(i-1).getX())
                    this.map.get(index).setGoRight(true);

                if(x > this.mainPath.get(i-1).getX())
                    this.map.get(index).setGoLeft(true);

                if(y > this.mainPath.get(i-1).getY())
                    this.map.get(index).setGoUp(true);

                if(y < this.mainPath.get(i-1).getY())
                    this.map.get(index).setGoDown(true);
            }
            if(i < mainPath.size()-1) {
                if(x < mainPath.get(i+1).getX())
                    map.get(index).setGoRight(true);

                if(x > mainPath.get(i+1).getX())
                    map.get(index).setGoLeft(true);

                if(y > mainPath.get(i+1).getY())
                    map.get(index).setGoUp(true);

                if(y < mainPath.get(i+1).getY())
                    map.get(index).setGoDown(true);
            }
            if(maxx < this.mainPath.get(i).getX()) {
                maxx = this.mainPath.get(i).getX();
            }
            if(maxy < mainPath.get(i).getY()) {
                maxy = mainPath.get(i).getY();
            }
        }
        for(int i = 0; i < this.subPaths.size(); i++) {
            //System.out.println(path.get(i).toString());
            int x = this.subPaths.get(i).getX();
            int y = this.subPaths.get(i).getY();
            index = getMap().indexOf(new Room(x,y));
            if(i > 0) {
                if(x < this.subPaths.get(i-1).getX())
                    this.map.get(index).setGoRight(true);

                if(x > this.subPaths.get(i-1).getX())
                    this.map.get(index).setGoLeft(true);

                if(y > this.subPaths.get(i-1).getY())
                    this.map.get(index).setGoUp(true);

                if(y < this.subPaths.get(i-1).getY())
                    this.map.get(index).setGoDown(true);
            }
            if(i < subPaths.size()-1) {
                if(x < subPaths.get(i+1).getX())
                    map.get(index).setGoRight(true);

                if(x > subPaths.get(i+1).getX())
                    map.get(index).setGoLeft(true);

                if(y > subPaths.get(i+1).getY())
                    map.get(index).setGoUp(true);

                if(y < subPaths.get(i+1).getY())
                    map.get(index).setGoDown(true);
            }
            if(maxx < this.subPaths.get(i).getX()) {
                maxx = this.subPaths.get(i).getX();
            }
            if(maxy < subPaths.get(i).getY()) {
                maxy = subPaths.get(i).getY();
            }
        }
        this.x = maxx +1;
        this.y = maxy +1;

    }
    public String showMapWithEvent() {
        String str = "";
        for (int i = 0; i < this.x; i++) {
            str += "___";
        }
        str += "\n";
        int index;
        for (int h = 0; h < y; h++){
            for(int w = 0; w < x; w++){
                index = getMap().indexOf(new Room(w,h));
                if(map.get(index).isGoLeft()) {
                    str += " ";
                } else {
                    str += "|";
                }
                if(map.get(index).isEventNull(null)) {
                    if(map.get(index).isGoDown()) {
                        str += " ";
                    } else {
                        str += "_";
                    }
                } else {
                    str += "*";
                }

                if(map.get(index).isGoRight()) {
                    str += " ";
                } else {
                    str += "|";
                }
            }
            str += "\n";
        }

        return str;
    }

    public String showMap() {
        String str = "";
        for (int i = 0; i < this.x; i++) {
            str += " _ ";
        }
        str += "\n";
        int index;
        for (int h = 0; h < y; h++){
            for(int w = 0; w < x; w++){
                index = getMap().indexOf(new Room(w,h));
                if(map.get(index).isGoLeft()) {
                    str += " ";
                } else {
                    str += "|";
                }
                if(map.get(index).isGoDown()) {
                    str += " ";
                } else {
                    str += "_";
                }
                if(map.get(index).isGoRight()) {
                    str += " ";
                } else {
                    str += "|";
                }
            }
            str += "\n";
        }

        return str;
    }
    public String showMap(int findx, int findy) {
        String str = "";
        int index;
        int index2 = getMap().indexOf(new Room(findx,findy));
        for (int h = 0; h < y; h++){
            for(int t = 0; t <3; t++) {
                for(int w = 0; w < x; w++){
                    index = getMap().indexOf(new Room(w,h));
                    if(t==0) {
                        str += "|";
                        if(map.get(index).isGoUp()) {
                            str += " ";
                        } else {
                            str += "_";
                        }
                        str += "|";
                    }
                    if(t==1) {
                        if(map.get(index).isGoLeft()) {
                            str += " ";
                        } else {
                            str += "|";
                        }
                        if(map.get(index).equals(map.get(index2))) {
                            str += "*";
                        } else {
                            str += " ";
                        }
                        if(map.get(index).isGoRight()) {
                            str += " ";
                        } else {
                            str += "|";
                        }
                    }
                    if(t==2) {
                        str += "|";
                        if(map.get(index).isGoDown()) {
                            str += " ";
                        } else {
                            str += "_";
                        }
                        str += "|";
                    }
                }
                str += "\n";
            }
        }
        return str;
    }

    public boolean findPath(int x, int y, int xmax, int ymax, int minDistance, int maxBranch, int lengthBranch) {
        while(!findMainPath(x, y, xmax, ymax, minDistance));
        int i = 0;
        while( i <= maxBranch) {
            Random rand = new Random();
            int randomNum =  rand.nextInt((mainPath.size())-3);
            Room r = mainPath.get(randomNum);
            if(findSubPath(r.getX(), r.getY(), xmax, ymax, lengthBranch, true)) {
                i++;
            }
        }

        return true;
    }

    public boolean findSubPath(int x, int y, int xmax, int ymax, int minDistance, boolean fristLoop) {
        if(fristLoop)
            minDistance++;
        //Room goal = new Room(3,3);
        Room r =new Room(x,y);
        int index = getMap().indexOf(r);
        if(minDistance == 0) return true;
        if(!fristLoop)
            if(map.get(index).getAccessible()) return false;
        map.get(index).setAccessible(true);
        Random rand = new Random();
        int randomNum =  rand.nextInt((4) + 1) + 1;
        switch(randomNum) {
            case 1:
                if(y>0) {
                    if(findSubPath(x, y - 1, xmax, ymax, minDistance -1, false)) {
                        if(!fristLoop)
                            subPaths.add(r);
                        return true;
                    }

                    break;
                }
            case 2:
                if(x>0) {
                    if(findSubPath(x - 1, y, xmax, ymax, minDistance -1, false)){
                        if(!fristLoop)
                            subPaths.add(r);
                        return true;
                    }
                    break;
                }
            case 3:
                if(y<ymax-1) {
                    if(findSubPath(x, y + 1, xmax, ymax, minDistance -1, false)) {
                        if(!fristLoop)
                            subPaths.add(r);
                        return true;
                    }
                    break;
                }
            case 4:
                if(x<xmax-1) {
                    if(findSubPath(x + 1, y, xmax, ymax, minDistance -1, false)){
                        if(!fristLoop)
                            subPaths.add(r);
                        return true;
                    }
                    break;
                }
        }
        if(!fristLoop)
            subPaths.remove(r);
        if(!fristLoop)
            map.get(index).setAccessible(false);
        return false;
    }

    public boolean findMainPath(int x, int y, int xmax, int ymax, int minDistance) {
        //Room goal = new Room(3,3);
        Room r =new Room(x,y);
        int index = getMap().indexOf(r);
        if(minDistance == 0) return true;
        if(map.get(index).getAccessible()) return false;
        map.get(index).setAccessible(true);
        Random rand = new Random();
        int randomNum =  rand.nextInt((4) + 1) + 1;
        switch(randomNum) {
            case 1:
                if(y>0) {
                    if(findMainPath(x, y - 1, xmax, ymax, minDistance -1)) {
                        mainPath.add(r);
                        return true;
                    }

                    break;
                }
            case 2:
                if(x>0) {
                    if(findMainPath(x - 1, y, xmax, ymax, minDistance -1)){
                        mainPath.add(r);
                        return true;
                    }
                    break;
                }
            case 3:
                if(y<ymax-1) {
                    if(findMainPath(x, y + 1, xmax, ymax, minDistance -1)) {
                        mainPath.add(r);
                        return true;
                    }
                    break;
                }
            case 4:
                if(x<xmax-1) {
                    if(findMainPath(x + 1, y, xmax, ymax, minDistance -1)){
                        mainPath.add(r);
                        return true;
                    }
                    break;
                }
        }
        mainPath.remove(r);
        map.get(index).setAccessible(false);
        return false;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Map{");
        sb.append("id=").append(id);
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }
}
