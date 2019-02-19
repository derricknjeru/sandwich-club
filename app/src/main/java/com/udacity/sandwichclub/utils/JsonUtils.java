package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        /**
         *
         *Json Keys
         */
        final String name = "name";
        final String mainName = "mainName";
        final String alsoKnownAs = "alsoKnownAs";
        final String placeOfOrigin = "placeOfOrigin";
        final String description = "description";
        final String image = "image";
        final String ingredients = "ingredients";

        /**
         * Json variables to hold json values
         */
        String mMainName = "";
        List<String> mAlsoKnownList = new ArrayList<>();
        String mPlaceOfOrigin = "";
        String mDescription = "";
        String mImage = "";
        List<String> mIngredientList = new ArrayList<>();

        // sandwich json object
        JSONObject sandwichJson = new JSONObject(json);
        Log.d(LOG_TAG, "@Sandwich sandwichJson::" + sandwichJson);

        if (sandwichJson.has(name)) {

            JSONObject namesJson = sandwichJson.getJSONObject(name);

            mMainName = namesJson.getString(mainName);

            JSONArray mAlsoKnownAsJsonArray = namesJson.getJSONArray(alsoKnownAs);

            for (int i = 0; i < mAlsoKnownAsJsonArray.length(); i++) {
                /**
                 * Adding alsoKnownAs items to {@link mAlsoKnownAs} list.
                 */
                if (mAlsoKnownAsJsonArray.getString(i) != null && !mAlsoKnownAsJsonArray.getString(i).isEmpty()) {
                    mAlsoKnownList.add(mAlsoKnownAsJsonArray.getString(i));
                }
            }
        }

        Log.d(LOG_TAG, "@Sandwich mMainName::" + mMainName);
        Log.d(LOG_TAG, "@Sandwich mAlsoKnownList::" + mAlsoKnownList);

        if (sandwichJson.has(placeOfOrigin)) {
            mPlaceOfOrigin = sandwichJson.getString(placeOfOrigin);
        }
        Log.d(LOG_TAG, "@Sandwich mPlaceOfOrigin::" + mPlaceOfOrigin);

        if (sandwichJson.has(description)) {
            mDescription = sandwichJson.getString(description);
        }
        Log.d(LOG_TAG, "@Sandwich mDescription::" + mDescription);

        if (sandwichJson.has(image)) {
            mImage = sandwichJson.getString(image);
        }
        Log.d(LOG_TAG, "@Sandwich mImage::" + mImage);

        if (sandwichJson.has(ingredients)) {
            JSONArray ingredientsJson = sandwichJson.getJSONArray(ingredients);

            for (int j = 0; j < ingredientsJson.length(); j++) {
                /**
                 * Adding ingredients items to {@link mIngredientList} list.
                 */
                if (ingredientsJson.getString(j) != null && !ingredientsJson.getString(j).isEmpty()) {

                    mIngredientList.add(ingredientsJson.getString(j));
                }
            }

        }

        Log.d(LOG_TAG, "@Sandwich mIngredientList::" + mIngredientList);


        return new Sandwich(mMainName, mAlsoKnownList, mPlaceOfOrigin, mDescription, mImage, mIngredientList);
    }
}
