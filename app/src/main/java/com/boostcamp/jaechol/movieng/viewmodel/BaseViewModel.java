package com.boostcamp.jaechol.movieng.viewmodel;

import android.text.Editable;

/**
 * Created by jaechol on 2018. 12. 15..
 */

public interface BaseViewModel {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();
    void onTextAfterChanged(Editable editable);
}
