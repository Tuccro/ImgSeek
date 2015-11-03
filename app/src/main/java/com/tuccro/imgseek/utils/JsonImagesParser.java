package com.tuccro.imgseek.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tuccro.imgseek.model.ImageDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuccro on 11/3/15.
 */
public class JsonImagesParser {

    String jsonString;

    public JsonImagesParser(String jsonString) {
        this.jsonString = jsonString;
    }

    List<ImageDescriptor> getImageDescriptors() {

        List<ImageDescriptor> list = new ArrayList();

        try {
            JsonParser parser = new JsonParser();
            JsonObject mainObject = parser.parse(jsonString).getAsJsonObject();
//            JsonObject internalObject = mainObject.getAsJsonObject("items");

            JsonArray results = mainObject.getAsJsonArray("items");

            String title;
            String urlIcon;
            String urlImage;

            for (JsonElement image : results) {

                JsonObject object = image.getAsJsonObject();

                title = object.get("title").toString();

                JsonObject pageMap = object.getAsJsonObject("pagemap");
                JsonArray cseImage = pageMap.getAsJsonArray("cse_image");
//                JsonObject imageobject = pageMap.getAsJsonObject("imageobject");

                urlIcon = ((JsonObject) cseImage.get(0)).get("src").toString();
                urlImage = ((JsonObject) cseImage.get(0)).get("src").toString();

//                Log.e("Image URL", ((JsonObject) cseImage.get(0)).get("src").toString());

                list.add(new ImageDescriptor(title.substring(1, title.length() - 1)
                        , urlIcon.substring(1, urlIcon.length() - 1)
                        , urlImage.substring(1, urlImage.length() - 1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
