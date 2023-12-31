package data_access;

import entities.Furniture;
import entities.Item;
import entities.Order;
import entities.Student;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.ImageIcon;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.create_order.CreateOrderDataAccessInterfaceItem;
import use_case.home.HomeDataAccessInterface;
import use_case.post.FurniturePostDataAccessInterface;
import use_case.profile.ProfileDataAccessInterface;
import use_case.search.SearchDataAccessInterface;
import use_case.view_item.ViewItemDataAccessInterface;

public class AtlasFurnitureDataAccessObject extends AtlasDataAccessObject
    implements HomeDataAccessInterface, FurniturePostDataAccessInterface,
               CreateOrderDataAccessInterfaceItem, ViewItemDataAccessInterface,
               SearchDataAccessInterface, ProfileDataAccessInterface {

    private static final String atlasCollectionName = "furniture";

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
            if (response.code() != 200) {
                throw new IOException("Bad request made to Atlas Data API");
            }

            JSONObject responseBodyJson = new JSONObject(response.body().string());
            JSONArray allItemDocuments = responseBodyJson.getJSONArray("documents");

            ArrayList<Item> result = new ArrayList<Item>();

            for (Object document : allItemDocuments) {
                JSONObject itemDocument = (JSONObject)document;

                // General item attributes

                String id = itemDocument.getString("_id");
                String name = itemDocument.getString("name");
                String description = itemDocument.getString("description");
                int condition = itemDocument.getInt("condition");
                int price = itemDocument.getInt("price");
                int age = itemDocument.getInt("age");
                boolean soldYet = itemDocument.getBoolean("soldYet");
                String pickupAddress = itemDocument.getString("pickupAddress");
                String ownerId = itemDocument.getString("ownerId");
                String type = itemDocument.getString("type");
                String picture = itemDocument.getString("picture");
                LocalDateTime creationTime =
                    LocalDateTime.parse(itemDocument.getString("creationTime"));

                // Item-specific attributes

                double length = itemDocument.getDouble("length");
                double width = itemDocument.getDouble("width");
                double height = itemDocument.getDouble("height");

                Furniture newItem =
                    new Furniture(id, name, description, condition, price, age, soldYet,
                            pickupAddress, ownerId, type, picture, creationTime,
                            length, width, height);
                result.add(newItem);
            }

            return result;
        }
    }

    private HashMap<String, Object> itemToDocument(Furniture item) {
        HashMap<String, Object> document = new HashMap<>();
        document.put("name", item.getName());
        document.put("description", item.getDescription());
        document.put("condition", item.getCondition());
        document.put("price", item.getPrice());
        document.put("age", item.getAge());
        document.put("soldYet", item.isSoldYet());
        document.put("pickupAddress", item.getPickupAddress());
        document.put("ownerId", item.getOwnerId());
        document.put("type", item.getType());
        document.put("picture", item.getPicture());
        document.put("creationTime", item.getCreationTime().toString());
        document.put("length", item.getLength());
        document.put("width", item.getWidth());
        document.put("height", item.getHeight());

        return document;
    }

    public void addItemToFurnitureCollection(Furniture newItem)
            throws IOException {
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            HashMap<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("dataSource", atlasDataSourceName);
            requestBodyMap.put("database", atlasDatabaseName);
            requestBodyMap.put("collection", atlasCollectionName);
            requestBodyMap.put("document", itemToDocument(newItem));

            Request request = preparePostRequest(atlasCollectionName,
                    "/action/insertOne", requestBodyMap);

            try (okhttp3.Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    // Handle a successful response
                    System.out.println("Item added successfully to the collection!");
                } else {
                    // Handle an unsuccessful response
                    System.out.println(
                            "Failed to add item to the collection. HTTP status code: " +
                            response.code());
                }
            }
    }

    /**
     * Updates the sold status of the item with the given itemId to true.
     *
     * @param itemId the itemId to update the soldYet attribute for
     */
    public void updateSoldYet(String itemId) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        HashMap<String, Object> requestBodyMap = new HashMap<String, Object>();
        HashMap<Object, Object> filterValue = new HashMap<>();
        HashMap<String, String> idMap = new HashMap<String, String>();
        idMap.put("$oid", itemId);
        filterValue.put("_id", idMap);
        HashMap<String, Boolean> newValue = new HashMap<String, Boolean>();
        newValue.put("soldYet", true);
        HashMap<String, HashMap<String, Boolean>> updateValue =
            new HashMap<String, HashMap<String, Boolean>>();
        updateValue.put("$set", newValue);

        requestBodyMap.put("dataSource", atlasDataSourceName);
        requestBodyMap.put("database", atlasDatabaseName);
        requestBodyMap.put("collection", atlasCollectionName);
        requestBodyMap.put("filter", filterValue);
        requestBodyMap.put("update", updateValue);

        Request request = preparePostRequest(atlasCollectionName,
                "/action/updateOne", requestBodyMap);

        try (Response response = client.newCall(request).execute()) {
        } catch (IOException e) {
        }
    }

    @Override
    public Item getItem(String idToGet) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        HashMap<String, Object> requestBodyMap = new HashMap<String, Object>();

        requestBodyMap.put("dataSource", atlasDataSourceName);
        requestBodyMap.put("database", atlasDatabaseName);
        requestBodyMap.put("collection", atlasCollectionName);

        HashMap<String, Object> filter = new HashMap<String, Object>();

        // getting something by ID takes a bit more work
        HashMap<String, String> idMap = new HashMap<String, String>();
        idMap.put("$oid", idToGet);

        filter.put("_id", idMap);
        requestBodyMap.put("filter", filter);

        Request request = preparePostRequest(atlasCollectionName, "/action/findOne",
                requestBodyMap);

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) {
                throw new IOException("Bad request made to Atlas Data API");
            }

            JSONObject responseJson = new JSONObject(response.body().string());

            if (responseJson.isNull("document")) {
                return null;
            }

            JSONObject itemDocument = responseJson.getJSONObject("document");
            // General item attributes

            String id = itemDocument.getString("_id");
            String name = itemDocument.getString("name");
            String description = itemDocument.getString("description");
            int condition = itemDocument.getInt("condition");
            int price = itemDocument.getInt("price");
            int age = itemDocument.getInt("age");
            boolean soldYet = itemDocument.getBoolean("soldYet");
            String pickupAddress = itemDocument.getString("pickupAddress");
            String ownerId = itemDocument.getString("ownerId");
            String type = itemDocument.getString("type");
            String picture = itemDocument.getString("picture");
            LocalDateTime creationTime =
                LocalDateTime.parse(itemDocument.getString("creationTime"));

            // Item-specific attributes

            double length = itemDocument.getDouble("length");
            double width = itemDocument.getDouble("width");
            double height = itemDocument.getDouble("height");

            Furniture newItem = new Furniture(
                    id, name, description, condition, price, age, soldYet, pickupAddress,
                    ownerId, type, picture, creationTime, length, width, height);

            return newItem;
        }
    }

    @Override
    public ArrayList<Item>
    getItemsByFilters(HashMap<String, Object> filteredAttributes,
            Student currentStudent) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        HashMap<String, Object> requestBodyMap = new HashMap<String, Object>();

        requestBodyMap.put("dataSource", atlasDataSourceName);
        requestBodyMap.put("database", atlasDatabaseName);
        requestBodyMap.put("collection", atlasCollectionName);

        HashMap<String, Object> newFilteredAttributes = new HashMap<>();

        // List of keys to include
        ArrayList<String> keysToInclude =
            new ArrayList<>(Arrays.asList("soldYet", "type"));

        for (String key : keysToInclude) {
            if (filteredAttributes.containsKey(key)) {
                newFilteredAttributes.put(key, filteredAttributes.get(key));
            }
        }

        // Now modify all the attributes that need a range to account for a range
        // instead of a single exact value
        HashMap<String, Object> priceRangeMap = new HashMap<>();
        priceRangeMap.put("$lte", filteredAttributes.get("price"));
        newFilteredAttributes.put("price", priceRangeMap);

        HashMap<String, Object> ageMap = new HashMap<>();
        ageMap.put("$lte", filteredAttributes.get("age"));
        newFilteredAttributes.put("age", ageMap);

        HashMap<String, Object> conditionScoreMap = new HashMap<>();
        conditionScoreMap.put("$gte", filteredAttributes.get("conditionScore"));
        newFilteredAttributes.put("condition", conditionScoreMap);

        // Furniture-specific filters
        HashMap<String, Object> lengthRangeMap = new HashMap<>();
        lengthRangeMap.put("$lte", filteredAttributes.get("maxLength"));
        lengthRangeMap.put("$gte", filteredAttributes.get("minLength"));
        newFilteredAttributes.put("length", lengthRangeMap);

        HashMap<String, Object> widthRangeMap = new HashMap<>();
        widthRangeMap.put("$lte", filteredAttributes.get("maxWidth"));
        widthRangeMap.put("$gte", filteredAttributes.get("minWidth"));
        newFilteredAttributes.put("width", widthRangeMap);

        HashMap<String, Object> heightRangeMap = new HashMap<>();
        heightRangeMap.put("$lte", filteredAttributes.get("maxHeight"));
        heightRangeMap.put("$gte", filteredAttributes.get("minHeight"));
        newFilteredAttributes.put("height", heightRangeMap);

        requestBodyMap.put("filter", newFilteredAttributes);

        // sort by creation time
        requestBodyMap.put("sort", new HashMap<String, Object>() {
            {
                put("creationTime", -1); // 1 for ascending, -1 for descending
            }
        });

        Request request =
            preparePostRequest(atlasCollectionName, "/action/find", requestBodyMap);

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) {
                throw new IOException("Bad request made to Atlas Data API");
            }
            JSONObject responseBodyJson = new JSONObject(response.body().string());
            if (responseBodyJson.isNull("documents")) {
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
                int condition = itemDocument.getInt("condition");
                int price = itemDocument.getInt("price");
                int age = itemDocument.getInt("age");
                boolean soldYet = itemDocument.getBoolean("soldYet");
                String pickupAddress = itemDocument.getString("pickupAddress");
                String ownerId = itemDocument.getString("ownerId");
                String type = itemDocument.getString("type");
                String picture = itemDocument.getString("picture");
                LocalDateTime creationTime =
                    LocalDateTime.parse(itemDocument.getString("creationTime"));

                // Item-specific attributes

                double length = itemDocument.getDouble("length");
                double width = itemDocument.getDouble("width");
                double height = itemDocument.getDouble("height");

                // This line assumes that calculateDistance is implemented
                // and that we have access to the current user information
                double distance =
                    calculateDistance(currentStudent.getHomeAddress(), pickupAddress);
                double maxDistance = (double)filteredAttributes.get("distanceRange");

                if (distance <= maxDistance) {
                    Furniture newItem =
                        new Furniture(id, name, description, condition, price, age,
                                soldYet, pickupAddress, ownerId, type, picture,
                                creationTime, length, width, height);

                    result.add(newItem);
                }
            }
            return result;
        }
    }

    public ArrayList<Item> getAllItemsByOwnerID(String ownerID)
            throws IOException {
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            HashMap<String, Object> requestBodyMap = new HashMap<String, Object>();

            HashMap<String, String> filter = new HashMap<>();
            filter.put("ownerId", ownerID);

            requestBodyMap.put("dataSource", atlasDataSourceName);
            requestBodyMap.put("database", atlasDatabaseName);
            requestBodyMap.put("collection", atlasCollectionName);
            requestBodyMap.put("filter", filter);

            Request request =
                preparePostRequest(atlasCollectionName, "/action/find", requestBodyMap);

            try (Response response = client.newCall(request).execute()) {
                if (response.code() != 200) {
                    throw new IOException("Bad request made to Atlas Data API");
                }

                JSONObject responseBodyJson = new JSONObject(response.body().string());
                JSONArray allItemDocuments = responseBodyJson.getJSONArray("documents");

                ArrayList<Item> result = new ArrayList<Item>();

                for (Object document : allItemDocuments) {
                    JSONObject itemDocument = (JSONObject)document;

                    // General item attributes

                    String id = itemDocument.getString("_id");
                    String name = itemDocument.getString("name");
                    String description = itemDocument.getString("description");
                    int condition = itemDocument.getInt("condition");
                    int price = itemDocument.getInt("price");
                    int age = itemDocument.getInt("age");
                    boolean soldYet = itemDocument.getBoolean("soldYet");
                    String pickupAddress = itemDocument.getString("pickupAddress");
                    String ownerId = itemDocument.getString("ownerId");
                    String type = itemDocument.getString("type");
                    String picture = itemDocument.getString("picture");
                    LocalDateTime creationTime =
                        LocalDateTime.parse(itemDocument.getString("creationTime"));

                    // Item-specific attributes

                    double length = itemDocument.getDouble("length");
                    double width = itemDocument.getDouble("width");
                    double height = itemDocument.getDouble("height");

                    Furniture newItem =
                        new Furniture(id, name, description, condition, price, age, soldYet,
                                pickupAddress, ownerId, type, picture, creationTime,
                                length, width, height);
                    result.add(newItem);
                }

                return result;
            }
    }

    public Student getStudentByEmail(String id) throws IOException {
        return null;
    }

    @Override
    public int getLastImageIndex() throws IOException {
        ArrayList<Item> items = this.getAllItems();

        int maxIndexSoFar = 0;

        for (Item item : items) {
            int id = Integer.valueOf(item.getPicture());

            if (id > maxIndexSoFar) {
                maxIndexSoFar = id;
            }
        }

        return (int)maxIndexSoFar;
    }

    @Override
    public ArrayList<Order> getAllOrdersBySellerEmail(String sellerEmail)
        throws IOException {
        return null;
    }
}
