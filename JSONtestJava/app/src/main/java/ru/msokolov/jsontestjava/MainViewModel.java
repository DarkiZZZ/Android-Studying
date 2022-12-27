package ru.msokolov.jsontestjava;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String BASE_URL = "https://dog.ceo/api/breeds/image/random";
    private static final String TAG = "MAIN_VIEW_MODEL";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_STATUS = "status";

    private MutableLiveData<DogImage> _dogImage = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<DogImage> getDogImage() {
        return _dogImage;
    }

    public void loadImage(){
        Disposable disposable = rxLoadImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (Consumer<DogImage>) image ->
                                _dogImage.setValue(image),

                        (Consumer<Throwable>) throwable ->
                                Log.d(TAG, "Error is " + throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    private Single<DogImage> rxLoadImage(){
        return Single.fromCallable(() -> {
            URL url = new URL(BASE_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder data = new StringBuilder();
            String result;
            do{
                result = bufferedReader.readLine();
                if (result != null){
                    data.append(result);
                }
            }
            while (result != null);

            JSONObject jsonObject = new JSONObject(data.toString());
            String message = jsonObject.getString(KEY_MESSAGE);
            String status = jsonObject.getString(KEY_STATUS);
            return new DogImage(
                    message,
                    status);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
