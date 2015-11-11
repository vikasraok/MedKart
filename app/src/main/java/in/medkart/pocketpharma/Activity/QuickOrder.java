package in.medkart.pocketpharma.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import in.medkart.pocketpharma.R;

public class QuickOrder extends Activity implements View.OnClickListener {

    private Intent intent;
    private EditText userNameQO,phoneNo;
    private Button captureButton;
    private ImageView prescription;
    private final int REQUEST_CODE = 1007;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_order);
        userNameQO = (EditText) findViewById(R.id.user_name_qo);
        phoneNo = (EditText) findViewById(R.id.phone_no_qo);
        prescription = (ImageView) findViewById(R.id.prescription);
        prescription.setVisibility(View.GONE);
        setTextChangedListener(userNameQO);
        setTextChangedListener(phoneNo);
        phoneNo.setEnabled(false);
        captureButton = (Button) findViewById(R.id.capture);
        captureButton.setOnClickListener(this);
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
                    if (field.equals(userNameQO)) {
                        if (isValidEmail(field.getText().toString())) {
                            phoneNo.setEnabled(true);
                        } else
                            phoneNo.setEnabled(false);
                    } else if (field.equals(phoneNo)) {
                        captureButton.setEnabled(true);
                    }
                } else {
                    if (field.equals(userNameQO)) {
                        phoneNo.setEnabled(false);
                    } else if (field.equals(phoneNo)) {
                        //check if password meets the password parameters
                        //else show toast asking them to enter a proper email address
                        //set highlight to red
                        captureButton.setEnabled(false);
                    }

                }
            }
        });
    }

    protected final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public void open(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE && data.getExtras().get("data")!=null){
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                prescription.setVisibility(View.VISIBLE);
                userNameQO.setVisibility(View.GONE);
                phoneNo.setVisibility(View.GONE);
                captureButton.setText(getResources().getString(R.string.send_prescription));
                prescription.setImageBitmap(bp);
            }
        }
    }
    @Override
    public void onClick(View v) {
        if (R.id.capture == v.getId()){
            open();
        }
    }
}
