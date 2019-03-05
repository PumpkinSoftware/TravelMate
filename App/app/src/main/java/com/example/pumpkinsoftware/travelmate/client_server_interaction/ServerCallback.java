package com.example.pumpkinsoftware.travelmate.client_server_interaction;

import org.json.JSONObject;

public interface ServerCallback {
    void onSuccess(JSONObject result);
}
