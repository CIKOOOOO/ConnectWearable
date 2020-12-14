package com.example.connectwearable.ui.login.authenticate;

import com.example.connectwearable.model.DeviceAuthenticate;

public interface IAuthenticateCallback {
    void onResponse(DeviceAuthenticate deviceAuthenticate);
}
