package com.boostcamp.jaechol.movieng.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.boostcamp.jaechol.movieng.R;

/**
 * 검색 중 로딩을 표시하기위한 Dilalog 클래스
 */

public class ProgressDialog {
    Context context;
    AlertDialog dialog;
    AlertDialog.Builder dialogBuilder;

    public ProgressDialog(Context cOntext) {
        this.context = cOntext;
    }

    public void ShowProgressDialog() {
        dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.progress_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    public void HideProgressDialog() {
        dialog.dismiss();
    }
}
