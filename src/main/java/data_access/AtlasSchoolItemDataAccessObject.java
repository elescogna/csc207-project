package data_access;

import entities.Furniture;
import entities.Item;
import entities.SchoolItem;
import entities.Student;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.home.HomeDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

public class AtlasSchoolItemDataAccessObject extends AtlasDataAccessObject
        implements HomeDataAccessInterface, SearchDataAccessInterface {
    private static final String atlasCollectionName = "school-items";

    @Override
    public ArrayList<Item> getAllItems() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        HashMap<String, Object> requestBodyMap = new HashMap<String, Object>();

        requestBodyMap.put("dataSource", atlasDataSourceName);
        requestBodyMap.put("database", atlasDatabaseName);
        requestBodyMap.put("collection", atlasCollectionName);
        requestBodyMap.put("filter", new HashMap<String, String>());

        Request request =
            preparePostRequest(atlasCollectionName, "/action/find", requestBodyMap);

        try (Response response = client.newCall(request).execute()) {
            JSONObject responseBodyJson = new JSONObject(response.body().string());
            JSONArray allItemDocuments = responseBodyJson.getJSONArray("documents");

            ArrayList<Item> result = new ArrayList<Item>();

            for (Object document : allItemDocuments) {
                JSONObject itemDocument = (JSONObject)document;

                // General item attributes

                String id = itemDocument.getString("_id");
                String name = itemDocument.getString("name");
                String description = itemDocument.getString("description");
                String condition = itemDocument.getString("condition");
                double price = itemDocument.getDouble("price");
                int age = itemDocument.getInt("age");
                boolean soldYet = itemDocument.getBoolean("soldYet");
                String pickupAddress = itemDocument.getString("pickupAddress");
                // TODO: when we get around to this, we have to get a student based on
                // the owner ID that is provided here like:
                // studentDataAccessObject.get(jsonDocument.getString("ownerId"));
                Student owner = new Student("id", "test", "test", "test", "test", false,
                        new ArrayList<>());
                String type = itemDocument.getString("type");
                String picture = itemDocument.getString("picture");
                LocalDateTime creationTime =
                    LocalDateTime.parse(itemDocument.getString("creationTime"));

                // Item-specific attributes
                String brand = itemDocument.getString("brand");
                String colour = itemDocument.getString("colour");

                SchoolItem newItem =
                    new SchoolItem(id, name, description, condition, price, age,
                            soldYet, pickupAddress, owner, type, picture,
                            creationTime, brand, colour);

                result.add(newItem);
            }

            return result;
        }
    }

    @Override
    public ArrayList<Item> getItemsByFilters(HashMap<String, String> filteredAttributes, Student currentStudent)
            throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        HashMap<String, Object> requestBodyMap = new HashMap<String, Object>();

        requestBodyMap.put("dataSource", atlasDataSourceName);
        requestBodyMap.put("database", atlasDatabaseName);
        requestBodyMap.put("collection", atlasCollectionName);

        // create a deep copy so that you don't mutate the parameter
        HashMap<String, Object> newFilteredAttributes = new HashMap<>(filteredAttributes.size());
        for (HashMap.Entry<String, String> entry : filteredAttributes.entrySet()) {
            newFilteredAttributes.put(new String(entry.getKey()), new String(entry.getValue()));
        }

        // Now modify all the attributes that need a range to account for a range instead of a single exact value
        HashMap<String, Object> priceRangeMap = new HashMap<>();
        priceRangeMap.put("$lte", newFilteredAttributes.get("price"));
        newFilteredAttributes.put("price", priceRangeMap);

        HashMap<String, Object> ageMap = new HashMap<>();
        ageMap.put("$lte", newFilteredAttributes.get("age"));
        newFilteredAttributes.put("age", ageMap);

        HashMap<String, Object> conditionScoreMap = new HashMap<>();
        conditionScoreMap.put("$gte", newFilteredAttributes.get("conditionScore"));
        newFilteredAttributes.put("conditionScore", conditionScoreMap);

        // Filter for soldYet
        newFilteredAttributes.put("soldYet", false);

        requestBodyMap.put("filter", newFilteredAttributes);

        // sort by creation time
        requestBodyMap.put("sort", new HashMap<String, Object>() {{
            put("creationTime", 1); // 1 for ascending, -1 for descending
        }});

        Request request = preparePostRequest(atlasCollectionName, "/action/find", requestBodyMap);

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) {
                throw new IOException("Bad request made to Atlas Data API");
            }

            JSONObject responseBodyJson = new JSONObject(response.body().string());
            if (responseBodyJson.isNull("document")) {
                return null;
            }
            JSONArray allItemDocuments = responseBodyJson.getJSONArray("documents");

            ArrayList<Item> result = new ArrayList<Item>();

            for (Object document : allItemDocuments) {
                JSONObject itemDocument = (JSONObject)document;

                // General item attributes

                String id = itemDocument.getString("_id");
                String name = itemDocument.getString("name");
                String description = itemDocument.getString("description");
                String condition = itemDocument.getString("condition");
                double price = itemDocument.getDouble("price");
                int age = itemDocument.getInt("age");
                boolean soldYet = itemDocument.getBoolean("soldYet");
                String pickupAddress = itemDocument.getString("pickupAddress");
                // TODO: when we get around to this, we have to get a student based on
                // the owner ID that is provided here like:
                // Student.get(jsonDocument.getString("ownerId"));
                Student owner = new Student("id", "test", "test", "test", "test", false,
                        new ArrayList<>());
                String type = itemDocument.getString("type");
                String picture = itemDocument.getString("picture");
                LocalDateTime creationTime =
                        LocalDateTime.parse(itemDocument.getString("creationTime"));

                // Item-specific attributes
                String brand = itemDocument.getString("brand");
                String colour = itemDocument.getString("colour");

                // This line assumes that calculateDistance is implemented
                // and that we have access to the current user infomation
                double distance = calculateDistance(currentStudent.getHomeAddress(), pickupAddress);
                double maxDistance = Double.parseDouble(filteredAttributes.get("distanceRange"));

                if (distance < maxDistance) {
                    SchoolItem newItem =
                            new SchoolItem(id, name, description, condition, price, age,
                                    soldYet, pickupAddress, owner, type, picture,
                                    creationTime, brand, colour);

                    result.add(newItem);
                }
            }

            return result;
        }
    }
}
