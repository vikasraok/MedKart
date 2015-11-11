package in.medkart.pocketpharma.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.medkart.pocketpharma.R;

/**
 * Created by SESA249903 on 9/21/2014.
 */
public class Information extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_information, container, false);

        return rootView;
    }
}
