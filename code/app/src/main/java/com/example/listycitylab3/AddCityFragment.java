package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    public interface AddCityDialogListener {
        void addCity(com.example.listycitylab3.City city);
        void onCityUpdated();
    }

    private AddCityDialogListener listener;
    private static final String ARG_CITY = "city";

    // field used by the lambda
    private com.example.listycitylab3.City editingCity;

    public static AddCityFragment newInstance(@Nullable com.example.listycitylab3.City city) {
        AddCityFragment f = new AddCityFragment();
        if (city != null) {
            Bundle b = new Bundle();
            b.putSerializable(ARG_CITY, city);
            f.setArguments(b);
        }
        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_add_city, null);

        EditText editCity = view.findViewById(R.id.edit_text_city_text);
        EditText editProv = view.findViewById(R.id.edit_text_province_text);

        editingCity = null;
        Bundle args = getArguments();
        if (args != null) {
            Object obj = args.getSerializable(ARG_CITY);
            if (obj instanceof com.example.listycitylab3.City) editingCity = (com.example.listycitylab3.City) obj;
        }

        final boolean isEdit = (editingCity != null);
        if (isEdit) {
            editCity.setText(editingCity.getName());
            editProv.setText(editingCity.getProvince());
        }

        return new AlertDialog.Builder(requireContext())
                .setTitle(isEdit ? "Edit City" : "Add a city")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEdit ? "OK" : "Add", (d, w) -> {
                    String name = editCity.getText().toString().trim();
                    String prov = editProv.getText().toString().trim();
                    if (name.isEmpty() || prov.isEmpty()) return;

                    if (isEdit) {
                        // editingCity is a field, safe to use in lambda
                        editingCity.setName(name);
                        editingCity.setProvince(prov);
                        listener.onCityUpdated();
                    } else {
                        listener.addCity(new com.example.listycitylab3.City(name, prov));
                    }
                })
                .create();
    }
}
