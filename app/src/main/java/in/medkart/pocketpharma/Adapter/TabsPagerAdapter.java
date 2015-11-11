package in.medkart.pocketpharma.Adapter;

import in.medkart.pocketpharma.Fragment.Interactions;
import in.medkart.pocketpharma.Fragment.MedicineBrand;
import in.medkart.pocketpharma.Fragment.Information;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new Information();
		case 1:
			// Games fragment activity
			return new MedicineBrand();
		case 2:
			// Movies fragment activity
			return new Interactions();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
