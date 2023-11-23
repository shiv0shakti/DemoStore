package algonquin.cst2335.demostore.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SunsetViewModel extends ViewModel {
    public MutableLiveData<ArrayList<SunsetData>> dataList = new MutableLiveData<>();
    public MutableLiveData<SunsetData> selectedSunsetData = new MutableLiveData<>();

}