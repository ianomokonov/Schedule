package com.example.schedule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.fragment.app.DialogFragment;

import com.example.schedule.models.Datable;

public class SaveFavoriteGroupDialog extends DialogFragment {

    private Datable datable;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        datable = (Datable) context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View saveFavoriteView = inflater.inflate(R.layout.save_favorit_group_dialog, null);
        return builder.setTitle("Добавить группу в избранное?")
                .setView(saveFavoriteView)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean save = ((CheckBox) saveFavoriteView.findViewById(R.id.save_default)).isChecked();
                        datable.saveFavoriteGroup(save);
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}
