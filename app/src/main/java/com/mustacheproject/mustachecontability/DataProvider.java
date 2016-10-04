package com.mustacheproject.mustachecontability;

import android.net.Uri;

/**
 * Created by Eugenio on 30/10/2015.
 */
public class DataProvider {
    private String _data;
    private Double _amount;
    private String _account;
    private String _reason;
    private Uri _imageUri;
    private int _id;

    public DataProvider (int id,String account, Double amount, String data, String reason, Uri imageUri){
        _id=id;
        _account=account;
        _amount=amount;
        _data=data;
        _reason=reason;
        _imageUri=imageUri;
    }


    public String get_data() {
        return _data;
    }

    public void set_data(String _data) {
        this._data = _data;
    }

    public Double get_amount() {
        return _amount;
    }

    public void set_amount(Double _amount) {
        this._amount = _amount;
    }

    public String get_account() {
        return _account;
    }

    public void set_account(String _account) {
        this._account = _account;
    }

    public String get_reason() {
        return _reason;
    }

    public void set_reason(String _reason) {
        this._reason = _reason;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }



    public Uri get_imageUri() {
        return _imageUri;
    }

    public void set_imageUri(Uri _imageUri) {
        this._imageUri = _imageUri;
    }



}
