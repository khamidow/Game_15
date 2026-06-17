package uz.mobiler.gitagame15;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Space;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity2 extends AppCompatActivity {
    private FrameLayout btn3x3;
    private FrameLayout btn4x4;
    private FrameLayout btn5x5;
    private ImageView infoBtn;
    private FrameLayout resume;
    private FrameLayout leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

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
        loadViews();


        btn3x3.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Size", 3);
            startActivity(intent);
        });
        btn4x4.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Size", 4);
            startActivity(intent);
        });
        btn5x5.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Size", 5);
            startActivity(intent);
        });
        infoBtn.setOnClickListener(v -> {
            BorderlessDialog dialogFragment = new BorderlessDialog(0);
            dialogFragment.show(getSupportFragmentManager(), "BorderlessDialogTag");
        });
        resume.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Size", 0);
            startActivity(intent);
        });
        leaderboard.setOnClickListener(v -> {
            DialogLeaders dialogFragment = new DialogLeaders();
            dialogFragment.show(getSupportFragmentManager(), "DialogLeadersTag");
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = this.getSharedPreferences("MyGame", Context.MODE_PRIVATE);
        if (pref.getString("HaveData", "").isEmpty()) resume.setVisibility(View.GONE);
        else if (pref.getString("HaveData", "bor").equals("bor"))
            resume.setVisibility(View.VISIBLE);
    }

    private void loadViews() {
        btn3x3 = findViewById(R.id.btn3);
        btn4x4 = findViewById(R.id.btn4);
        btn5x5 = findViewById(R.id.btn5);
        infoBtn = findViewById(R.id.info_btn);
        resume = findViewById(R.id.btn_resume);
        leaderboard = findViewById(R.id.btn_leaders);
    }
}