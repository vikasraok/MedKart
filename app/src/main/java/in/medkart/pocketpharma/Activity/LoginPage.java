package in.medkart.pocketpharma.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.medkart.pocketpharma.R;


public class LoginPage extends Activity implements View.OnClickListener{

    private EditText userName, passWord;
    private TextView signUp;
    private Button signIn, guest;
    private int REQUEST_CODE = 1004;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        init();
    }

    private void init() {
        userName = (EditText) findViewById(R.id.user_name);
        passWord = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.sign_in);
        guest = (Button) findViewById(R.id.guest);
        signUp = (TextView) findViewById(R.id.sign_up);

        signIn.setEnabled(false);
        passWord.setEnabled(false);
        setTextChangedListener(userName);
        setTextChangedListener(passWord);
        signIn.setOnClickListener(this);
        guest.setOnClickListener(this);
        signUp.setOnClickListener(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void setTextChangedListener(final EditText field) {
        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!field.getText().toString().isEmpty()) {
                    if (field.equals(userName)) {
                        if (isValidEmail(field.getText().toString())) {
                            passWord.setEnabled(true);
                        } else
                            passWord.setEnabled(false);
                    } else if (field.equals(passWord)) {
                        signIn.setEnabled(true);
                    }
                } else {
                    if (field.equals(userName)) {
                        passWord.setEnabled(false);
                    } else if (field.equals(passWord)) {
                        //check if password meets the password parameters
                        //else show toast asking them to enter a proper email address
                        //set highlight to red
                        signIn.setEnabled(false);
                    }

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE && !data.getBooleanExtra("result", false)) {
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_cred), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_VERTICAL, 0, 100);
                toast.show();
            }
        }
    }

    protected final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.sign_in:
                intent = new Intent(v.getContext(), LoadingPage.class);
                intent.putExtra("credName", userName.getText().toString()).putExtra("credWord", passWord.getText().toString());
                startActivityForResult(intent, REQUEST_CODE);
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;
            case R.id.guest:
                intent = new Intent(getApplicationContext(), LoadingPage.class);
                startActivity(intent);
                this.finish();
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;
            case R.id.sign_up:
                intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;
            default:
                Log.d("LoginPage ",Integer.toString(v.getId()));
        }
    }
}
