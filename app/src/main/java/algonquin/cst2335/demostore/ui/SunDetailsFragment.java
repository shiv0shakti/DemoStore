package algonquin.cst2335.demostore.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import algonquin.cst2335.demostore.data.SunsetData;
import algonquin.cst2335.demostore.data.SunsetViewModel;
import algonquin.cst2335.demostore.databinding.SunsetDetailsBinding;

public class SunDetailsFragment extends Fragment {
    private SunsetData sunsetData;
    private SunsetViewModel sunsetViewModel;

    private boolean isFavourite;

    public SunDetailsFragment(SunsetData sunsetData, boolean isFavourite) {
        this.sunsetData = sunsetData;
        this.isFavourite = isFavourite;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SunsetDetailsBinding binding = SunsetDetailsBinding.inflate(inflater);

        binding.sunriseValue.setText(sunsetData.getSunrise());
        binding.sunsetValue.setText(sunsetData.getSunset());
        binding.firstLightValue.setText(sunsetData.getFirstLight());
        binding.lastLightValue.setText(sunsetData.getLastLight());
        binding.dawnValue.setText(sunsetData.getDawn());
        binding.duskValue.setText(sunsetData.getDusk());
        binding.solarNoonValue.setText(sunsetData.getSolarNoon());
        binding.goldenHourValue.setText(sunsetData.getGoldenHour());
        binding.dayLengthValue.setText(sunsetData.getDayLength());

        sunsetViewModel = new ViewModelProvider(requireActivity()).get(SunsetViewModel.class);

        if (this.isFavourite) {
            binding.sunsetSaveBtn.setVisibility(View.INVISIBLE);
        } else {
            binding.sunsetSaveBtn.setOnClickListener(click -> {
                ArrayList<SunsetData> list = sunsetViewModel.dataList.getValue();
                Log.d("SunActivityFragment", "TEST_LIST" + list.toString());

                list.add(sunsetData);
                sunsetViewModel.dataList.postValue(list);

                getParentFragmentManager().beginTransaction().remove(this).commit();
            });
        }
        return binding.getRoot();
    }
}
