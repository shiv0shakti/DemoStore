package algonquin.cst2335.demostore.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import algonquin.cst2335.demostore.R;
import algonquin.cst2335.demostore.data.SunsetApiData;
import algonquin.cst2335.demostore.data.SunsetData;
import algonquin.cst2335.demostore.data.SunsetViewModel;
import algonquin.cst2335.demostore.databinding.SunsetDetailsBinding;

/**
 * Fragment to display details from the Sunset &amp; Sunrise API request
 *
 * @author Roy S
 * @version 1.0
 */
public class SunDetailsFragment extends Fragment {
    /** Sunset API data object to store details */
    private SunsetApiData sunsetData;

    /** View model to work with SunsetData objects and save them to favourite locations */
    private SunsetViewModel sunsetViewModel;

    /** if this details fragment is a saved favourite from RecyclerView or search details */
    private boolean isFavourite;

    /**
     * Constructor to create fragment
     *
     * @param sunsetData The Sunset API data to display in the fragment
     * @param isFavourite If this fragment is a saved favourite location or search details fragment
     */
    public SunDetailsFragment(SunsetApiData sunsetData, boolean isFavourite) {
        this.sunsetData = sunsetData;
        this.isFavourite = isFavourite;
    }

    /**
     * Run when  creating the fragment. Binds the data to the proper TextViews and handles button
     * clicks like closing the fragment or adding a saved favourite to the SunseData ViewModel for
     * processing in the Main Sunset Activity.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return The inflated view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SunsetDetailsBinding binding = SunsetDetailsBinding.inflate(inflater);

        binding.locationLatValue.setText(sunsetData.getLat());
        binding.locationLngValue.setText(sunsetData.getLng());
        binding.sunriseValue.setText(sunsetData.getSunrise());
        binding.sunsetValue.setText(sunsetData.getSunset());
        binding.firstLightValue.setText(sunsetData.getFirstLight());
        binding.lastLightValue.setText(sunsetData.getLastLight());
        binding.dawnValue.setText(sunsetData.getDawn());
        binding.duskValue.setText(sunsetData.getDusk());
        binding.solarNoonValue.setText(sunsetData.getSolarNoon());
        binding.goldenHourValue.setText(sunsetData.getGoldenHour());
        binding.dayLengthValue.setText(sunsetData.getDayLength());

        if (this.isFavourite) {
            binding.detailsTitle.setText(R.string.sunset_details_fav_title);
            binding.sunsetSaveBtn.setText(R.string.sunsnet_close_details);
            binding.sunsetSaveBtn.setBackgroundColor(
                    getResources().getColor(R.color.sunset_dark_btn,
                    getActivity().getTheme())
            );
            binding.sunsetDetailsCloseBtn.setVisibility(View.INVISIBLE);
        } else {
            binding.sunsetDetailsCloseBtn.setBackgroundColor(
                    getResources().getColor(R.color.sunset_dark_btn,
                    getActivity().getTheme())
            );
        }

        sunsetViewModel = new ViewModelProvider(requireActivity()).get(SunsetViewModel.class);
        ArrayList<SunsetData> list = sunsetViewModel.dataList.getValue();

        binding.sunsetDetailsCloseBtn.setOnClickListener(click -> {
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        binding.sunsetSaveBtn.setOnClickListener(click -> {
            if (!this.isFavourite) {
                SunsetData newSunsetData = new SunsetData(sunsetData.getLat(), sunsetData.getLng());

                list.add(newSunsetData);
                sunsetViewModel.dataList.postValue(list);
            }
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        return binding.getRoot();
    }
}
