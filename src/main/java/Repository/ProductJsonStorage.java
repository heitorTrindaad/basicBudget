package Repository;

import Model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductJsonStorage {
    private static final String FILE_PATH = "Products.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveToFile(List<Product> products) {
        try (Writer writer = new FileWriter(FILE_PATH)){
            gson.toJson(products, writer);
        } catch(IOException e){
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static List<Product> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)){
            Type listType = new TypeToken<ArrayList<Product>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch(IOException e){
            return new ArrayList<>();
        }
    }
}
