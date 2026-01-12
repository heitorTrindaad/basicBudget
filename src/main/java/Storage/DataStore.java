package Storage;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataStore {

    private static final String PATH = "data.json";
    private static final Gson gson =
            new GsonBuilder().setPrettyPrinting().create();

    public static void save(Object data){
        try(FileWriter w = new FileWriter(PATH)){
            gson.toJson(data,w);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static <T> T load(Class<T> clazz){
        try{
            if(!Files.exists(Path.of(PATH))) return null;
            FileReader r = new FileReader(PATH);
            return gson.fromJson(r,clazz);
        }catch(Exception e){
            return null;
        }
    }
}
