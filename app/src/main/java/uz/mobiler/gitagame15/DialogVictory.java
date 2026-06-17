package uz.mobiler.gitagame15;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DialogVictory extends DialogFragment {

    private final String type;
    private String time;
    private String moves;

    public DialogVictory(String type, String time, String moves) {
        this.type = type;
        this.time = time;
        this.moves = moves;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_win, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == ORIENTATION_LANDSCAPE) {
            if (getDialog() != null && getDialog().getWindow() != null) {
                getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                InsetDrawable inset = new InsetDrawable(new ColorDrawable(Color.TRANSPARENT), dpToPx(19), dpToPx(0), dpToPx(19), dpToPx(0));
                getDialog().getWindow().setBackgroundDrawable(inset);
            }
        } else {
            if (getDialog() != null && getDialog().getWindow() != null) {
                getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                InsetDrawable inset = new InsetDrawable(new ColorDrawable(Color.TRANSPARENT), dpToPx(19), dpToPx(25), dpToPx(19), dpToPx(25));
                getDialog().getWindow().setBackgroundDrawable(inset);
            }
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(true);
        TextView number1 = view.findViewById(R.id.number_1);
        TextView number2 = view.findViewById(R.id.number_2);
        TextView moves = view.findViewById(R.id.count);
        TextView time = view.findViewById(R.id.time);
        FrameLayout okBtn = view.findViewById(R.id.ok_btn);
        RecyclerView rv = view.findViewById(R.id.rv);

        SharedPreferences pref = view.getContext().getSharedPreferences("MyGame", Context.MODE_PRIVATE);
        String[] data = pref.getString("records", "").split("\\$");
        List<ModelData> list = new ArrayList<>();
        int index = 0;
        while (index < data.length - 1) {
            int img = 0;
            if (index == 0) img = R.drawable.first_cup;
            else if (index == 4) img = R.drawable.second_cup;
            else img = R.drawable.flag_place;
            index++;
            list.add(new ModelData(img, data[index++], data[index++], data[index++]));
        }
        RvAdapter adapter = new RvAdapter(list);
        rv.setAdapter(adapter);

        number1.setText(type);
        number2.setText(type);
        moves.setText(this.moves);
        time.setText(this.time);

        okBtn.setOnClickListener(view1 -> {
            dismiss();
        });
    }

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