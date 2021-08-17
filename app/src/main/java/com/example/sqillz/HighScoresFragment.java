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

//import com.google.gson.Gson;

import com.example.sqillz.logic.Utils;


import java.lang.reflect.Type;
import java.util.List;


public class HighScoresFragment extends Fragment {

    private Button backBtn;
    private Context contextReference;
    private View viewReference;
    private List<Integer> scores;

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
        backBtn  = viewReference.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> closeHighestScoreFragment());

//        loadHighestScoresFromFile();

        return this.viewReference;
    }

    private void closeHighestScoreFragment() {
        Log.d("highScoresFragment","closeHighestScoreFragment");
        getActivity().onBackPressed();
    }

    private void loadHighestScoresFromFile() {
        Log.d("fragment", "loadHighestScoresFromFile");
        String filename = getResources().getString(R.string.Scores_Json_File);
        SharedPreferences sharedPref = contextReference.getSharedPreferences(filename,Context.MODE_PRIVATE);
        String jsonFileString = sharedPref.getString(getString(R.string.Scores_Json_String),
                Utils.getJsonFromAssets(contextReference, "template_scores_json.json"));
//        Gson gson = new Gson();
//        Type listScoreType = new TypeToken<List<Score>>() {}.getType();
//        scores = gson.fromJson(jsonFileString, listScoreType);
    }


    private void saveScoresToFragmentTable() {
        int viewId;
        String idName;
        TextView tv;
//        for (int i = 0; i < scores.size(); i++)
//        {
//            idName = "row" + scores.get(i).getPlace();  //  the text "row"+index is by the id-names in the fragment layout
//            viewId = getResources().getIdentifier(idName, "id", contextReference.getPackageName());
//            tv = (TextView) viewReference.findViewById(viewId).findViewWithTag(scores.get(i).getStringDifficulty());
//            tv.setText(scores.get(i).getTimeScore());
//        }
    }

    @Override
    public void onStart() {
        Log.d("fragment", "onStart");
        super.onStart();
//        saveScoresToFragmentTable();
    }

}