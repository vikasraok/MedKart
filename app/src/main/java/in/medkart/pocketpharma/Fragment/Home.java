package in.medkart.pocketpharma.Fragment;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;

import in.medkart.pocketpharma.Adapter.GridAdapter;
import in.medkart.pocketpharma.Model.GridData;
import in.medkart.pocketpharma.R;

public class Home extends Fragment implements AbsListView.OnItemClickListener, View.OnClickListener{
    private View rootView;
    private static final String TAG = "HomeFragment";

    private StaggeredGridView mGridView;
    private String[] medTitle;
    private TypedArray medImages;
    private Button Search, QuickOrder;
    private ArrayList<GridData> mData;
    private GridAdapter mAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mGridView = (StaggeredGridView) rootView.findViewById(R.id.grid_view);
        medTitle = getResources().getStringArray(R.array.grid_view_array);
        medImages = getResources().obtainTypedArray(R.array.grid_view_images);
        Search = (Button) rootView.findViewById(R.id.search);
        QuickOrder = (Button) rootView.findViewById(R.id.quick_order);
        return rootView;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new GridAdapter(getActivity(), generateData());
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
        Search.setOnClickListener(this);
        QuickOrder.setOnClickListener(this);
    }
    private ArrayList<GridData> generateData() {
        mData = new ArrayList<GridData>();
        mData.add(new GridData(medTitle[0], medImages.getResourceId(0, -1)));
        mData.add(new GridData(medTitle[1], medImages.getResourceId(1, -1)));
        mData.add(new GridData(medTitle[2], medImages.getResourceId(2, -1)));
        mData.add(new GridData(medTitle[3], medImages.getResourceId(3, -1)));
        mData.add(new GridData(medTitle[4], medImages.getResourceId(4, -1)));
        mData.add(new GridData(medTitle[5], medImages.getResourceId(5, -1)));
        mData.add(new GridData(medTitle[6], medImages.getResourceId(6, -1)));
        mData.add(new GridData(medTitle[7], medImages.getResourceId(7, -1)));
        mData.add(new GridData(medTitle[8], medImages.getResourceId(8, -1)));
        mData.add(new GridData(medTitle[9], medImages.getResourceId(9, -1)));
        mData.add(new GridData(medTitle[10], medImages.getResourceId(10, -1)));
        mData.add(new GridData(medTitle[11], medImages.getResourceId(11, -1)));
        mData.add(new GridData(medTitle[12], medImages.getResourceId(12, -1)));
        mData.add(new GridData(medTitle[13], medImages.getResourceId(13, -1)));
        mData.add(new GridData(medTitle[14], medImages.getResourceId(14, -1)));
        mData.add(new GridData(medTitle[15], medImages.getResourceId(15, -1)));
        mData.add(new GridData(medTitle[16], medImages.getResourceId(16, -1)));
        mData.add(new GridData(medTitle[17], medImages.getResourceId(17, -1)));
        medImages.recycle();
        return mData;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),Medicine.class);
        intent.putExtra("med_name",medTitle[position]);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.search:
                intent = new Intent(getActivity(), in.medkart.pocketpharma.Activity.Search.class);
                //Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;
            case R.id.quick_order:
                intent = new Intent(getActivity(), in.medkart.pocketpharma.Activity.QuickOrder.class);
                // Toast.makeText(getActivity(), "Quick order", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;
            default:
                Log.d(TAG, Integer.toString(v.getId()));
        }
    }
}

