package com.example.joc.Classes;

import android.os.Parcelable;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class MySocket implements Serializable {

    private Socket socket;
    public static final int PORT = 5432;

    public MySocket(Socket socket){
        this.socket = socket;
    }

    public void sendInt(int num) throws Exception {
        try{
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(num);
        }catch(Exception e){
            throw e;
        }
    }
    public int recieveInt() throws Exception {
        int num;
        try{
            DataInputStream is = new DataInputStream(socket.getInputStream());
            num = is.readInt();
        }catch(Exception e){
            throw e;
        }
        return num;
    }

    public void sendDouble(double num) throws Exception {
        try{
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeDouble(num);
        }catch(Exception e){
            throw e;
        }
    }
    public double recieveDouble() throws Exception {
        double num;
        try{
            DataInputStream is = new DataInputStream(socket.getInputStream());
            num = is.readDouble();
        }catch(Exception e){
            throw e;
        }
        return num;
    }

    public void sendString(String msg) throws Exception {
        try{
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            oos.writeUTF(msg);
        }catch(Exception e){
            throw e;
        }
    }

    public String recieveString() throws Exception {
        String msg;
        try{
            DataInputStream is = new DataInputStream(socket.getInputStream());
            msg = is.readUTF();
        }catch(Exception e){
            throw e;
        }
        return msg;
    }

    public void sendStringArray(ArrayList<String> strings) throws Exception {

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(strings);
        oos.flush();

    }

    public ArrayList<String> recieveStringArray() throws Exception {

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ArrayList<String> strings = (ArrayList<String>) ois.readObject();

        return strings;
    }

    public void close() throws Exception {
        try{
            socket.close();
        }catch(Exception e){
            throw e;
        }

    }

}
