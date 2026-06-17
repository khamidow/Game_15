package uz.mobiler.gitagame15;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private Random rd = new Random();
    private static int SIZE = 4;
    private TextView count;
    private AppCompatButton[][] texts;
    private Coordinator emptyCoordinate;
    private ArrayList<Integer> values;
    private int clicks = 0;
    private boolean clickable = true;
    private SharedPreferences pref;

    private boolean landscapeChanged = false;
    private MediaPlayer mediaPlayer;
    private ImageView sound;
    private boolean changedOrientation;
    private TextView timerTextView;
    private long startTime = 0;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        int statusBarHeight = getResources().getDimensionPixelSize(
                getResources().getIdentifier("status_bar_height", "dimen", "android"));
        Space topSpace = findViewById(R.id.space);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) topSpace.getLayoutParams();
        params.height = statusBarHeight;
        topSpace.setLayoutParams(params);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        pref = this.getSharedPreferences("MyGame", Context.MODE_PRIVATE);
        timerTextView = findViewById(R.id.time);
        LinearLayout container = findViewById(R.id.container);
        ImageView resetBtn = findViewById(R.id.reset_btn);
        count = findViewById(R.id.count);
        Intent intent = getIntent();
        Log.d("INTENT", intent.getIntExtra("Size", 0) + "");
        SIZE = intent.getIntExtra("Size", pref.getInt("S", 4));
        if (SIZE == 0) SIZE = pref.getInt("S", 4);
        Log.d("SIZE", SIZE + "");
        texts = new AppCompatButton[SIZE][SIZE];

        findViewById(R.id.back).setOnClickListener(view -> {
            pref.edit().putString("HaveData", "bor").apply();
            finish();
        });
        resetBtn.setOnClickListener(it -> {
            clickable = true;
            texts = new AppCompatButton[SIZE][SIZE];
            emptyCoordinate = new Coordinator(rd.nextInt(SIZE), rd.nextInt(SIZE));
            values = new ArrayList<>(SIZE * SIZE - 1);
            clicks = 0;
            count.setText("0");
            for (int i = 0; i < container.getChildCount(); i++) {
                LinearLayout current = (LinearLayout) container.getChildAt(i);
                for (int j = 0; j < current.getChildCount(); j++) {
                    AppCompatButton b = (AppCompatButton) current.getChildAt(j);
                    b.setOnClickListener(this::onClick);
                    Coordinator currentCoordinate = new Coordinator(i, j);
                    b.setTag(currentCoordinate);
                    texts[i][j] = b;
                }

            }
            generateNumbers();
            loadData(true);
        });


        container.removeAllViews();
        for (int i = 0; i < SIZE; i++) {
            LinearLayout row = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_row, container, false);
            for (int j = 0; j < SIZE; j++) {
                AppCompatButton item = (AppCompatButton) LayoutInflater.from(this).inflate(R.layout.item_puzzle, row, false);
                row.addView(item);
            }
            container.addView(row);
            Log.v("CONTAINER", container.getChildCount() + "");
        }

        for (int i = 0; i < container.getChildCount(); i++) {
            LinearLayout current = (LinearLayout) container.getChildAt(i);
            for (int j = 0; j < current.getChildCount(); j++) {
                AppCompatButton b = (AppCompatButton) current.getChildAt(j);
                b.setOnClickListener(this::onClick);
                Coordinator currentCoordinate = new Coordinator(i, j);
                b.setTag(currentCoordinate);
                b.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        b.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int buttonHeight = b.getHeight();
                        float newTextSize = buttonHeight * 0.4f;
                        b.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize);
                    }
                });
                texts[i][j] = b;
            }

        }
        Log.v("AAAAA", "IIIIIIIII");
    }

    private void loadData(boolean shuffle) {
        if (shuffle) {
            startTime = System.currentTimeMillis();
            Collections.shuffle(values);
            if (!checkForLoad()) {
                loadData(true);
                return;
            }
        } else {
            startTime = System.currentTimeMillis() - (long) Integer.parseInt(pref.getString("time", "00:00").substring(0, 2)) * 60000 - Integer.parseInt(pref.getString("time", "00:00").substring(3, 5)) * 1000L;
        }
        timerHandler.post(timerRunnable);
        int idx = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                if (i == emptyCoordinate.getX() && j == emptyCoordinate.getY()) {
                    texts[i][j].setText("");
                    texts[i][j].setTextColor(Color.parseColor("#90adba"));
                    texts[i][j].setBackgroundResource(R.drawable.item_bcg);
                    texts[i][j].setTypeface(ResourcesCompat.getFont(this, R.font.ebon));
                    texts[i][j].setVisibility(View.INVISIBLE);
                    continue;
                }
                texts[i][j].setVisibility(View.VISIBLE);
                texts[i][j].setText(String.valueOf(values.get(idx)));
                texts[i][j].setTextColor(Color.parseColor("#90adba"));
                texts[i][j].setBackgroundResource(R.drawable.item_bcg);
                texts[i][j].setTypeface(ResourcesCompat.getFont(this, R.font.ebon));
                Log.v("TEXTS", texts[i][j].getText().toString());
                idx++;
            }
        }
    }

    private boolean checkForLoad() {
        int invCount = getInvCount(values);
        if (SIZE % 2 == 1)
            return invCount % 2 == 0;
        else {
            int pos = SIZE - emptyCoordinate.getX();
            if (pos % 2 == 1)
                return invCount % 2 == 0;
            else
                return invCount % 2 == 1;
        }
    }

    private void generateNumbers() {
        for (int i = 1; i < SIZE * SIZE; i++) {
            values.add(i);
        }
    }

    private void onClick(View view) {
        if (clickable) {
            AppCompatButton currentButton = (AppCompatButton) view;
            Coordinator currentCoordinate = (Coordinator) view.getTag();
            int difX = Math.abs(currentCoordinate.getX() - emptyCoordinate.getX());
            int difY = Math.abs(currentCoordinate.getY() - emptyCoordinate.getY());
            if (difX + difY == 1) {
                clicks++;
                count.setText("" + clicks);
                AppCompatButton emptyButton = texts[emptyCoordinate.getX()][emptyCoordinate.getY()];
                emptyButton.setText(currentButton.getText());
                currentButton.setText("");
                emptyButton.setVisibility(View.VISIBLE);
                currentButton.setVisibility(View.INVISIBLE);
                emptyCoordinate = currentCoordinate;
                if (checkWin()) {
                    // Win
                    StringBuilder sb = new StringBuilder();
                    sb.append("img");
                    sb.append("$");
                    sb.append(SIZE);
                    sb.append("$");
                    sb.append(timerTextView.getText().toString());
                    sb.append("$");
                    sb.append(count.getText().toString());
                    sb.append("$");
                    pref.edit().putString("records", pref.getString("records", "")+ sb.toString()).apply();

                    DialogVictory dialogFragment = new DialogVictory(SIZE + "", timerTextView.getText().toString(), count.getText().toString());
                    dialogFragment.show(getSupportFragmentManager(), "DialogVictoryTag");
                    String[] oldData = pref.getString("records", "").split("\\$");
                    List<ModelData> list = new ArrayList<>();
                    int index = 0;
                    while (index < oldData.length - 1) {
                        int img = 0;
                        if (index == 0) img = R.drawable.first_cup;
                        else if (index == 4) img = R.drawable.second_cup;
                        else img = R.drawable.flag_place;
                        index++;
                        list.add(new ModelData(img, oldData[index++], oldData[index++], oldData[index++]));
                    }
                    for (int i = 0; i < list.size(); i++) {
                        for (int j = i + 1; j < list.size(); j++) {
                            ModelData temp;
                            if (Integer.parseInt(list.get(j).getMove()) < Integer.parseInt(list.get(i).getMove())) {
                                temp = new ModelData(list.get(i).getImg(), list.get(i).getType(), list.get(i).getTime(), list.get(i).getMove());
                                list.set(i, new ModelData(list.get(j).getImg(), list.get(j).getType(), list.get(j).getTime(), list.get(j).getMove()));
                                list.set(j, temp);
                            }
                        }
                    }

                    StringBuilder st = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        st.append(list.get(i).getImg() + "$" + list.get(i).getType() + "$" + list.get(i).getTime() + "$" + list.get(i).getMove() + "$");
                    }
                    pref.edit().putString("records", st.toString()).apply();
                    pref.edit().putString("time", timerTextView.getText().toString()).apply();
                    timerHandler.removeCallbacks(timerRunnable);
                }
            }
        } else {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    (ViewGroup) findViewById(R.id.toast_layout_root));

            ImageView image = (ImageView) layout.findViewById(R.id.img);
            image.setImageResource(R.drawable.refresh);
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Press RESTART button\nto continue!");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }

    private boolean checkWin() {
        int index = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                index++;
                if (index == SIZE * SIZE) continue;
                if (!texts[i][j].getText().toString().equals(index + "")) return false;
            }
        }
        clickable = false;
        return true;
    }

    private int getInvCount(ArrayList arr) {
        int inv_count = 0;
        for (int i = 0; i < SIZE * SIZE - 2; i++) {
            for (int j = i + 1; j < SIZE * SIZE - 1; j++) {
                if (!Objects.equals(arr.get(j), 0) && !Objects.equals(arr.get(i), 0)
                        && Integer.parseInt(arr.get(i).toString()) > Integer.parseInt(arr.get(j).toString()))
                    inv_count++;
            }
        }
        return inv_count;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        pref.edit().putString("time", timerTextView.getText().toString()).apply();
        timerHandler.removeCallbacks(timerRunnable);
        landscapeChanged = true;
        changedOrientation = true;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        landscapeChanged = false;
        changedOrientation = true;
        SIZE = pref.getInt("S", 4);
        Log.v("AAAAA", "Old");
        values = new ArrayList<>(SIZE * SIZE - 1);
        String[] s = pref.getString("A", "").split("\\*");

        String[] c = pref.getString("B", "").split("\\*");

        clicks = pref.getInt("C", 0);
        count.setText("" + clicks);
        clickable = pref.getBoolean("D", true);
        emptyCoordinate = new Coordinator(Integer.parseInt(c[0]), Integer.parseInt(c[1]));
        for (int i = 0; i < s.length; i++) {
            if (Objects.equals(s[i], "")) continue;
            values.add(Integer.parseInt(s[i]));
            Log.v("TTT", s.length + "");
        }
        loadData(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer = MediaPlayer.create(this, R.raw.island_of_the_lost);

        sound = findViewById(R.id.sound);
        if (pref.getString("Sound", "on").equals("on")) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            sound.setImageResource(R.drawable.sound);
            pref.edit().putString("Sound", "on").apply();
        } else {
            mediaPlayer.start();
            mediaPlayer.pause();
            sound.setImageResource(R.drawable.mute);
            pref.edit().putString("Sound", "off").apply();
        }
        sound.setOnClickListener(v -> {
            if (pref.getString("Sound", "").equals("on")) {
                mediaPlayer.pause();
                sound.setImageResource(R.drawable.mute);
                pref.edit().putString("Sound", "off").apply();
            } else {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
                sound.setImageResource(R.drawable.sound);
                pref.edit().putString("Sound", "on").apply();
            }
        });

        Intent intent = getIntent();
        SIZE = intent.getIntExtra("Size", pref.getInt("S", 4));
        if (!landscapeChanged && (SIZE != 0 || pref.getString("B", "").equals(""))) {
            Log.v("AAAAA", "New");
            emptyCoordinate = new Coordinator(rd.nextInt(SIZE), rd.nextInt(SIZE));
            values = new ArrayList<>(SIZE * SIZE - 1);

            generateNumbers();
            loadData(true);
        } else {
            landscapeChanged = false;
            SIZE = pref.getInt("S", 4);
            Log.v("AAAAA", "Old");
            values = new ArrayList<>(SIZE * SIZE - 1);
            String[] s = pref.getString("A", "").split("\\*");

            String[] c = pref.getString("B", "").split("\\*");

            clicks = pref.getInt("C", 0);
            count.setText("" + clicks);
            clickable = pref.getBoolean("D", true);
            emptyCoordinate = new Coordinator(Integer.parseInt(c[0]), Integer.parseInt(c[1]));
            for (int i = 0; i < s.length; i++) {
                if (Objects.equals(s[i], "")) continue;
                values.add(Integer.parseInt(s[i]));
                Log.v("TTT", s.length + "");
            }
            loadData(false);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        pref.edit().putString("time", timerTextView.getText().toString()).apply();
        timerHandler.removeCallbacks(timerRunnable);

        mediaPlayer.stop();
        LinearLayout container = findViewById(R.id.container);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < container.getChildCount(); i++) {
            LinearLayout current = (LinearLayout) container.getChildAt(i);
            for (int j = 0; j < current.getChildCount(); j++) {
                AppCompatButton a = (AppCompatButton) current.getChildAt(j);
                if (a.getText() == "") continue;
                sb.append(a.getText()).append("*");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        pref.edit().putString("A", sb.toString()).apply();
        pref.edit().putString("B", emptyCoordinate.getX() + "*" + emptyCoordinate.getY()).apply();
        pref.edit().putInt("C", clicks).apply();
        pref.edit().putBoolean("D", clickable).apply();
        pref.edit().putInt("S", SIZE).apply();
    }
}