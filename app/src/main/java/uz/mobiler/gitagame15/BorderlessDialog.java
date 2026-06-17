package uz.mobiler.gitagame15;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BorderlessDialog extends DialogFragment {

    private final int index;

    public BorderlessDialog(int index) {
        this.index = index;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate your custom layout here
        return inflater.inflate(R.layout.info_dialog, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (getDialog() != null && getDialog().getWindow() != null) {
                getDialog().getWindow().setLayout(
                        dpToPx(543),
                        dpToPx(355)
                );
//            // **Crucial step**: Make the dialog background transparent to remove borders/margins
                getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        } else {
            if (getDialog() != null && getDialog().getWindow() != null) {
                getDialog().getWindow().setLayout(
                        dpToPx(355),
                        dpToPx(543)
                );
//            // **Crucial step**: Make the dialog background transparent to remove borders/margins
                getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(true);
        // Access and interact with views here
//        TextView levelNumber = view.findViewById(R.id.level_tv);
//        TextView textTv = view.findViewById(R.id.text_tv);
//        TextView okayBtn = view.findViewById(R.id.btn_okay);
//
//        if (index == Integer.MIN_VALUE) {
//            levelNumber.setText("");
//
//            textTv.setText("Bosqichlar yakunlandi");
//            okayBtn.setText("Uyga");
//            okayBtn.setOnClickListener(v -> {
//                Intent i = new Intent(getActivity(), MainActivity.class);
//                startActivity(i);
//                if (getActivity() != null) {
//                    getActivity().finishAffinity();
//                }
//            });
//        } else {
//            // You can set dynamic text if needed
//            levelNumber.setText(String.valueOf(1 + index));
//            textTv.setText("Bosqich yakunlandi");
//            okayBtn.setText("Keyingi");
//            // Set up a click listener for the button
//            okayBtn.setOnClickListener(v -> {
//                // Perform an action, e.g., show a Toast and dismiss the dialog
//                dismiss(); // Dismiss the dialog after the action
//            });
//        }
    }

    // Optional: Hide the title feature if it's present by default
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                requireContext().getResources().getDisplayMetrics()
        );
    }
}