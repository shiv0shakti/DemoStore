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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.demostore.data.SunsetData;
import algonquin.cst2335.demostore.data.SunsetViewModel;
import algonquin.cst2335.demostore.databinding.SunsetDetailsBinding;

public class SunDetailsFragment extends Fragment {
    private SunsetData sunsetData;
    protected SunsetViewModel sunsetViewModel;

    public SunDetailsFragment(SunsetData sunsetData) {
        this.sunsetData = sunsetData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SunsetDetailsBinding binding = SunsetDetailsBinding.inflate(inflater);

        binding.sunriseValue.setText(sunsetData.getSunrise());
        sunsetViewModel = new ViewModelProvider(requireActivity()).get(SunsetViewModel.class);

        binding.sunsetSaveBtn.setOnClickListener(click -> {
            ArrayList<SunsetData> list = sunsetViewModel.dataList.getValue();
            Log.d("SunActivityFragment", "TEST_LIST" + list.toString());

            SunsetData sunsetDataTest2 = new SunsetData(
                    "TEST",
                    "8:00 PM",
                    "6:45 AM",
                    "8:30 PM"
            );
            list.add(sunsetDataTest2);
            sunsetViewModel.dataList.postValue(list);
        });
        return binding.getRoot();
    }
}
