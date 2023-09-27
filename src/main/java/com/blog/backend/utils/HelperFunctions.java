package com.blog.backend.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

public class HelperFunctions {

    public static String getBase64(int id, String type){
        String Base64Image="";
        try{

            Path path = Path.of("src/main/resources/uploads/"+type+"/"+Integer.toString(id) + ".txt");


            if(Files.exists(path)){
                String content = Files.readString(path);
                Base64Image = content;
            }

        }catch (FileNotFoundException e){
            return "";

        }catch (IOException e){
            throw new RuntimeException(e); //this user has no image
        }
        return Base64Image;
    }


    public static String setBase64(int id , String pic , String type){
        try{
            Path path = Path.of("src/main/resources/uploads/"+type+"/");
            if(!Files.exists(path)){
                Files.createDirectories(path);
            }
            path = Paths.get(path + "/"+Integer.toString(id)+ ".txt");

            if(!Files.exists(path)){
                Files.createFile(path);
            }
            Files.writeString(path, pic);
            System.out.println("Successfully wrote to the file.");
            System.out.println(path.toString());
            return path.toString();
        }catch(FileAlreadyExistsException e){
            System.out.println("File exists");
            return "";
        }catch (IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return "Something went wrong";

    }

}
