package codingwithmitch.com.googlemapsgoogleplaces;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {
    EditText e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        e = findViewById(R.id.edit_query);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAqNkxxRylrHmphafxH0oGFfg3TbzPMPI8");
        }



// Initialize the AutocompleteSupportFragment.
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                Toast.makeText(Main2Activity.this, place.getName() + ", " + place.getId(), Toast.LENGTH_SHORT).show();
//                }
//
//            @Override
//            public void onError(Status status) {
//                Toast.makeText(Main2Activity.this, ""+status.getStatusMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void Click(View view) {
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        // Create a RectangularBounds object.
        // Use the builder to create a FindAutocompletePredictionsRequest.
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                //.setLocationRestriction(bounds)
                .setCountry("vn")
                .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
                .setQuery(e.getText().toString())
                .build();
        PlacesClient placesClient=Places.createClient(this);

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(new OnSuccessListener<FindAutocompletePredictionsResponse>() {
            @Override
            public void onSuccess(FindAutocompletePredictionsResponse response) {
                for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                    Log.d("list", "id: "+prediction.getPlaceId());
                    Log.d("list", "text: "+prediction.getPrimaryText(null).toString());
//                    Toast.makeText(Main2Activity.this, ""+prediction.getPlaceId(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(Main2Activity.this, "" + prediction.getPrimaryText(null).toString(), To.LENGTH_SHORT).show();
//                Log.i(TAG, prediction.getPlaceId());
//                Log.i(TAG, prediction.getPrimaryText(null).toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    Toast.makeText(Main2Activity.this, ""+ apiException.getStatusCode() +apiException.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                Log.e("", "Place not found: " );
                }
            }
        });
    }
}
