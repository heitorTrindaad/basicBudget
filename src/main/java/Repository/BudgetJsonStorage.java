package Repository;

import Model.Budget;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BudgetJsonStorage {
    private static final String FILE_PATH = "Budget.json";
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                    new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                    LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
            .create();

    public static void saveToFile(List<Budget> budgets) {
        try (Writer writer = new FileWriter(FILE_PATH)){
            gson.toJson(budgets, writer);
        } catch(IOException e){
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static List<Budget> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)){
            Type listType = new TypeToken<ArrayList<Budget>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch(IOException e){
            return new ArrayList<>();
        }
    }
}
