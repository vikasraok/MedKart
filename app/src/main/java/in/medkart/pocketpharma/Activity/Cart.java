package in.medkart.pocketpharma.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import in.medkart.pocketpharma.R;

/**
 * Created by SESA249903 on 9/21/2014.
 */
public class Cart extends Activity implements View.OnClickListener {
    private Button Confirm;
    private TextView CartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        DialogFragment mAddress = new AddressFragment();
        mAddress.show(getFragmentManager(), "address");
        Intent intent = getIntent();
        Confirm = (Button) findViewById(R.id.confirm);
        CartList = (TextView) findViewById(R.id.cart_list);
        CartList.setText(intent.getStringExtra("med_name"));
        Confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                Toast.makeText(this, "Your order has been confirmed", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public static class AddressFragment extends DialogFragment {
        private Button mSubmitAddress;
        public AddressFragment(){};
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_address, container);
            getDialog().setTitle("Enter your address");
            mSubmitAddress = (Button)view.findViewById(R.id.Submit);
            mSubmitAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().dismiss();
                }
            });

            return view;
        }
    }
}
