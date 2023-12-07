package algonquin.cst2335.demostore.data;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
public class DeezerViewModel extends ViewModel {
    public MutableLiveData<ArrayList<FavouriteSong>> songs = new MutableLiveData<>();
    public MutableLiveData<String> imageUrl = new MutableLiveData<>();
}