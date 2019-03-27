package codingwithmitch.com.googlemapsgoogleplaces;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
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

import java.util.ArrayList;
import java.util.Arrays;

import codingwithmitch.com.googlemapsgoogleplaces.models.PlaceMode;

public class Main2Activity extends AppCompatActivity {
    EditText e;
    ArrayList<PlaceMode> data = new ArrayList<>();
    int dem=0;
    int size=0;
    Boolean iscount=false;
    CountDownTimer countDownTimer;
    SpinKitView sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        e = findViewById(R.id.edit_query);
        sp =findViewById(R.id.spin_kit);
        countDownTimer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                loaddulieu();
            }
        };
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!iscount){
                    countDownTimer.start();
                    sp.setVisibility(View.VISIBLE);
                    iscount=true;
                }else {
                    countDownTimer.cancel();
                    iscount=false;
                    countDownTimer = new CountDownTimer(3000,1000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                           loaddulieu();
                        }
                    }.start();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAqNkxxRylrHmphafxH0oGFfg3TbzPMPI8");
        }



// Initialize the AutocompleteSupportFragment.
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//        autocompleteFragment.setPlaceFields(Arrays.asList(PlaceMode.Field.ID, PlaceMode.Field.NAME));
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(PlaceMode place) {
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

    public void loaddulieu() {
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        // Create a RectangularBounds object.
        // Use the builder to create a FindAutocompletePredictionsRequest.
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                //.setLocationRestriction(bounds)
                .setCountry("vn")
                .setSessionToken(token)
                .setQuery(e.getText().toString())
                .build();
        PlacesClient placesClient=Places.createClient(this);

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(new OnSuccessListener<FindAutocompletePredictionsResponse>() {
            @Override
            public void onSuccess(FindAutocompletePredictionsResponse response) {
                data.clear();
                for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                    Log.d("listss", "onSuccess: "+prediction.getFullText(null).toString());

                    data.add(new PlaceMode(
                            prediction.getFullText(null).toString(),
                            prediction.getSecondaryText(null).toString(),null,null));
                    RecyclerView rv = findViewById(R.id.rv);
                    rv.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
                    rv.setAdapter(new Adapter(data));
                    sp.setVisibility(View.GONE);
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
//                Log.e("", "PlaceMode not found: " );
                }
            }
        });
    }
}
