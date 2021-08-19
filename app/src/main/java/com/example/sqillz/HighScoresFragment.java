package com.example.sqillz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sqillz.logic.Game;
import com.example.sqillz.logic.Score;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.sqillz.logic.Utils;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class HighScoresFragment extends Fragment {

    private Button backBtn;
    private Context contextReference;
    private View viewReference;

    public HighScoresFragment() {
        // Required empty public constructor
    }

    public static HighScoresFragment newInstance() {
        HighScoresFragment fragment = new HighScoresFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("fragment", "onCreate");
        super.onCreate(savedInstanceState);
        contextReference = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.viewReference = inflater.inflate(R.layout.high_scores_fragment, container, false);
        backBtn = viewReference.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> closeHighestScoreFragment());

        loadHighestScoresFromFile();
        Game.highestScoreInteger = Game.highestScores.get(0).getScore();

        return this.viewReference;
    }

    private void closeHighestScoreFragment() {
        Log.d("highScoresFragment", "closeHighestScoreFragment");
        getActivity().onBackPressed();
    }

    public void loadHighestScoresFromFile() {
        Log.d("fragment", "loadHighestScoresFromFile");

        String filename = getResources().getString(R.string.Scores_Json_File);
        SharedPreferences sharedPref = contextReference.getSharedPreferences(filename, Context.MODE_PRIVATE);

        // for reset all highest results: (!!!!!!!)
        // sharedPref.edit().clear().commit();

        String jsonFileString = sharedPref.getString(getString(R.string.Scores_Json_String),
                Utils.getJsonFromAssets(contextReference, "template_scores_json.json"));
        Log.d("jsonFileString", jsonFileString);
        Gson gson = new Gson();
        Type listScoreType = new TypeToken<ArrayList<Score>>() {
        }.getType();
        Game.highestScores = gson.fromJson(jsonFileString, listScoreType);
    }

    private void saveScoresToFragmentTable() {
        int viewId;
        String idName;
        TextView tv;
        for (int i = 0; i < Game.highestScores.size(); i++) {
            idName = "row" + Game.highestScores.get(i).getPlace();  //  the text "row"+index is by the id-names in the fragment layout
            viewId = getResources().getIdentifier(idName, "id", contextReference.getPackageName());
            tv = (TextView) viewReference.findViewById(viewId).findViewWithTag("name");
            tv.setText(Game.highestScores.get(i).getName());
            tv = (TextView) viewReference.findViewById(viewId).findViewWithTag("score");
            tv.setText("" + Game.highestScores.get(i).getScore());
        }
    }

    @Override
    public void onStart() {
        Log.d("fragment", "onStart");
        super.onStart();
        saveScoresToFragmentTable();
    }

    public static int checkIfIn10BestScoresAndSave(Score newScore) {
        int i, thePlaceScore = Integer.MAX_VALUE;

        // loop for check where insert the newScore
        for (i = 0; i < Game.highestScores.size(); i++) {
            if (newScore.getScore() > Game.highestScores.get(i).getScore()) {
                newScore.setPlace(Game.highestScores.get(i).getPlace());
                thePlaceScore = newScore.getPlace();
                Game.highestScores.add(i, newScore);
                Game.highestScores.remove(10);  // drop last result
                break;
            }
        }
        // loop for update the rest scores's places.
        for (i = i + 1; i < Game.highestScores.size(); i++) {
            Game.highestScores.get(i).setPlace(Game.highestScores.get(i).getPlace() + 1);
        }

        Game.highestScoreInteger = Game.highestScores.get(0).getScore();

        return thePlaceScore;
    }

}