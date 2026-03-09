package Repository;

import Model.Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClientJsonStorage {
    private static final String FILE_PATH = "Clients.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveToFile(List<Client> clients) {
        try (Writer writer = new FileWriter(FILE_PATH)){
            gson.toJson(clients, writer);
        } catch(IOException e){
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static List<Client> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)){
            Type listType = new TypeToken<ArrayList<Client>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch(IOException e){
            return new ArrayList<>();
        }
    }
}
