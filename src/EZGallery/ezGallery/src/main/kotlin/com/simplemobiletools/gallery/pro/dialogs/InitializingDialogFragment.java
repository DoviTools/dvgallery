package com.simplemobiletools.gallery.pro.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.simplemobiletools.commons.R;

public class InitializingDialogFragment extends DialogFragment {

    public static InitializingDialogFragment newInstance() {
        InitializingDialogFragment dialogFragment = new InitializingDialogFragment();
        dialogFragment.setCancelable(false);
        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.dialog_initialing, null);
        return new AlertDialog.Builder(getContext())
                .setView(view).create();
    }
}
