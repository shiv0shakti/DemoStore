package algonquin.cst2335.demostore.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * ViewModel to store SunsetData objects and pass them between different activities/fragments
 *
 * @author Roy S
 * @version 1.0
 */
public class SunsetViewModel extends ViewModel {
    /** List of stored SunsetData objects */
    public MutableLiveData<ArrayList<SunsetData>> dataList = new MutableLiveData<>();

    /** Selected SunsetData object */
    public MutableLiveData<SunsetData> selectedSunsetData = new MutableLiveData<>();
}