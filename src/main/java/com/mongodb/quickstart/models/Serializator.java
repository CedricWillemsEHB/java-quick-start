package com.mongodb.quickstart.models;

import java.io.*;
import java.util.Base64;
import java.util.List;

public class Serializator {
    public static String serializeMap(List<Room> map) {
        String serializedObject = "";

        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);

            so.writeObject(map);
            so.flush();
            serializedObject = bo.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return serializedObject;
    }
    public static String serializeRoom(Room room) {
        String serializedObject = "";

        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(room);
            so.flush();
            serializedObject = bo.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return serializedObject;
    }
    @SuppressWarnings("unchecked")
    public static List<Room> deserializeMap(String map) {
        List<Room> obj = null;
        try {
            byte b[] = map.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            obj = (List<Room>) si.readObject();
        } catch (Exception e) {
            System.out.println(e);
        }
        return obj;
    }
    public static Room deserializeRoom(String room) {
        Room obj = null;
        try {
            byte b[] = room.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            obj = (Room) si.readObject();
        } catch (Exception e) {
            System.out.println(e);
        }
        return obj;
    }
    public static String convertToByteString(Object object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            final byte[] byteArray = bos.toByteArray();
            return Base64.getEncoder().encodeToString(byteArray);
        }catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public static byte[] convertToByte(Object object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public static Object convertFromByteString(String byteString) throws IOException, ClassNotFoundException {
        final byte[] bytes = Base64.getDecoder().decode(byteString);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        }catch (IOException e){
            System.out.println(e);
        }catch (ClassNotFoundException e){
            System.out.println(e);
        }
        return null;
    }

    public static Object convertFromByteByte(byte[] byteString) throws IOException, ClassNotFoundException {
        //final byte[] bytes = Base64.getDecoder().decode(byteString);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(byteString); ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        }catch (IOException e){
            System.out.println(e);
        }catch (ClassNotFoundException e){
            System.out.println(e);
        }
        return null;
    }
}
