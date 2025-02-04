package com.example.brewmanager;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class ChefSpinnerAdapter extends ArrayAdapter<String> {

    public ChefSpinnerAdapter(Context context, List<String> chefs) {
        super(context, android.R.layout.simple_spinner_item, chefs);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
