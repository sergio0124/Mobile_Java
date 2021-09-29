package com.example.firstlab;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentMain extends Fragment {

    ArrayAdapter<String> adapter;
    String[] names_array;
    ArrayList<String> res_names;

    public FragmentMain(){
        super(R.layout.fragment_container_view);
    }

    interface OnFragmentSendDataListener {
        void onSendData(Fragment fragment);
    }
    private OnFragmentSendDataListener fragmentSendDataListener;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        names_array = getResources().getStringArray(R.array.names);
        res_names = new ArrayList<>(Arrays.asList(names_array));

        super.onCreate(savedInstanceState);
        ListView lw = (ListView) view.findViewById(R.id.item_list);
        lw.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, res_names);
        lw.setAdapter(adapter);
        EditText textField = (EditText) view.findViewById(R.id.text_field);

        textField.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    res_names.add(0, textField.getText().toString());
                    adapter.notifyDataSetChanged();
                    textField.setText("");
                    return true;
                }
            return false;
        });

        Button addButton = (Button) view.findViewById(R.id.addbutton);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentSendDataListener.onSendData(new FragmentAddRef());
            }
        };
        addButton.setOnClickListener(onClickListener);
    }

}