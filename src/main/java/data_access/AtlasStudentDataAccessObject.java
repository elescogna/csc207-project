package data_access;

import entities.Item;
import entities.SchoolItem;
import entities.Student;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.create_order.CreateOrderDataAccessInterface;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class AtlasStudentDataAccessObject extends AtlasDataAccessObject implements CreateOrderDataAccessInterface {
    private static final String atlasCollectionName = "students";

    public boolean existsByEmail(String email) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        boolean exists = true;

        HashMap<String, Object> requestBodyMap = new HashMap<String, Object>();
        HashMap<String, String> filterValue = new HashMap<String, String>();
        filterValue.put("uoftEmail", email);

        requestBodyMap.put("dataSource", atlasDataSourceName);
        requestBodyMap.put("database", atlasDatabaseName);
        requestBodyMap.put("collection", atlasCollectionName);
        requestBodyMap.put("filter", filterValue);

        Request request =
                preparePostRequest(atlasCollectionName, "/action/find", requestBodyMap);

        try (Response response = client.newCall(request).execute()) {
            JSONObject responseBodyJson = new JSONObject(response.body().string());
            try {
                JSONArray allItemDocuments = responseBodyJson.getJSONArray("documents");
            } catch (JSONException j) {
                exists = false;
            }
        } catch (IOException e) {}
        return exists;
    }

    public void create(String orderId, String buyerEmail, String sellerEmail, Item item,
                       String address) {}

    public void update(String itemId) {}
}
