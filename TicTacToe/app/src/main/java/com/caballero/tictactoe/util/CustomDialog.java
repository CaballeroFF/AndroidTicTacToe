package com.caballero.tictactoe.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;

public class CustomDialog extends AppCompatDialogFragment {
    private static final String TAG = "TicTacToeCustomDialog";

    public static final String DIALOG_TITLE = "com.caballero.tictactoe.util.dialog.title";
    public static final String DIALOG_MESSAGE = "com.caballero.tictactoe.util.dialog.message";

    private OnClickListener clickListener;
    private String title = "";
    private String message = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        getBundleArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((OnClickListener) getActivity()).yesClicked();
                        if (clickListener != null) {
                            clickListener.yesClicked();
                        }
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((OnClickListener) getActivity()).noClicked();
                        if (clickListener != null) {
                            clickListener.noClicked();
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof OnClickListener)) {
            throw new ClassCastException(context.toString() + "Must implement OnClickListener");
        }
    }

    private void getBundleArguments() {
        Bundle bundle = getArguments();
        if (bundle != null && !bundle.isEmpty()) {
            title = bundle.getString(DIALOG_TITLE);
            message = bundle.getString(DIALOG_MESSAGE);
        }
    }

    public interface OnClickListener {
        void noClicked();

        void yesClicked();
    }

    public void setOnClickListener(OnClickListener listener) {
        clickListener = listener;
    }
}
