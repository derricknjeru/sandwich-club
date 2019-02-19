package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import static android.text.TextUtils.*;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private ImageView ingredientsIv;
    private TextView alsoKnownAsDescTv;
    private TextView alsoKnownAsTv;
    private TextView originDescTv;
    private TextView originTv;
    private TextView ingredientsDescTv;
    private TextView ingredientsTv;
    private TextView descriptionDescTv;
    private TextView descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializingViews();


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        Log.d(LOG_TAG, "@position::" + position);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Log.d(LOG_TAG, "@json::" + json);

        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            Log.d(LOG_TAG, "@Sandwich e.getMessage()::" + e.getMessage());
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void initializingViews() {
        ingredientsIv = findViewById(R.id.image_iv);

        alsoKnownAsDescTv = findViewById(R.id.also_known_tv_desc);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);

        originDescTv = findViewById(R.id.origin_tv_desc);
        originTv = findViewById(R.id.origin_tv);

        ingredientsDescTv = findViewById(R.id.ingredients_tv_desc);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        descriptionDescTv = findViewById(R.id.description_tv_dec);
        descriptionTv = findViewById(R.id.description_tv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs() != null && !sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsTv.setVisibility(View.VISIBLE);
            alsoKnownAsDescTv.setVisibility(View.VISIBLE);
            alsoKnownAsTv.setText(join(",", sandwich.getAlsoKnownAs()));
        } else {
            alsoKnownAsTv.setVisibility(View.GONE);
            alsoKnownAsDescTv.setVisibility(View.GONE);
        }


        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setVisibility(View.VISIBLE);
            originDescTv.setVisibility(View.VISIBLE);
            originTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            originTv.setVisibility(View.GONE);
            originDescTv.setVisibility(View.GONE);
        }

        if (sandwich.getIngredients() != null && !sandwich.getIngredients().isEmpty()) {
            ingredientsTv.setVisibility(View.VISIBLE);
            ingredientsDescTv.setVisibility(View.VISIBLE);
            ingredientsTv.setText(join(",", sandwich.getIngredients()));
        } else {
            ingredientsTv.setVisibility(View.GONE);
            ingredientsDescTv.setVisibility(View.GONE);
        }

        if (sandwich.getDescription() != null && !sandwich.getDescription().isEmpty()) {
            descriptionTv.setVisibility(View.VISIBLE);
            descriptionDescTv.setVisibility(View.VISIBLE);
            descriptionTv.setText(sandwich.getDescription());
        } else {
            descriptionTv.setVisibility(View.GONE);
            descriptionDescTv.setVisibility(View.GONE);
        }
    }
}
