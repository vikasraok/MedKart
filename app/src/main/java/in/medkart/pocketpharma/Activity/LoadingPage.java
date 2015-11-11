package in.medkart.pocketpharma.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import in.medkart.pocketpharma.R;


public class LoadingPage extends Activity {

    private String cred_name, cred_word;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);
        cred_name = getIntent().getStringExtra("credName");
        cred_word = getIntent().getStringExtra("credWord");
        handleActivity();

    }

    private void handleActivity() {
        if (cred_word != null && cred_name != null) {
            if (!(cred_word.isEmpty() || cred_name.isEmpty())) {
                //validate against api stored data
                Handler handler = new Handler();
                Intent intent = new Intent();
                intent.putExtra("result", false);
                setResult(RESULT_OK, intent);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(R.anim.anim_slide_out_right,
                                R.anim.anim_slide_in_right);
                    }
                }, 3000);
            }
        }
        else{
            final Intent intent = new Intent(this,DrawerActivity.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_left,
                            R.anim.anim_slide_out_left);
                }
            }, 3000);

        }
    }
}
