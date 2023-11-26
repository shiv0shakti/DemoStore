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

public class SunDetailsFragment extends Fragment {
    private SunsetApiData sunsetData;
    private SunsetViewModel sunsetViewModel;
    private boolean isFavourite;

    public SunDetailsFragment(SunsetApiData sunsetData, boolean isFavourite) {
        this.sunsetData = sunsetData;
        this.isFavourite = isFavourite;
    }

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
        }

        sunsetViewModel = new ViewModelProvider(requireActivity()).get(SunsetViewModel.class);
        ArrayList<SunsetData> list = sunsetViewModel.dataList.getValue();

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
