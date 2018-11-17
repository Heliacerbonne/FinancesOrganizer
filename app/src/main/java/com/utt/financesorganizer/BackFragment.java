package com.utt.financesorganizer;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BackFragment extends Fragment {

    private Button back;
    private Activity myAc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.back, container, false);
        myAc = this.getActivity();

        back = (Button) view.findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //change activity

                Intent k = new Intent(myAc, Dashboard.class);
                startActivity(k);
            }
        });
        return view;
    }

}