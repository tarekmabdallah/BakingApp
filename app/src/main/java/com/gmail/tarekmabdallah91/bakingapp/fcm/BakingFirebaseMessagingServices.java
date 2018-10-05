/*
 Copyright 2018 tarekmabdallah91@gmail.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.gmail.tarekmabdallah91.bakingapp.fcm;

import android.os.Bundle;
import android.util.Log;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.data.room.RoomPresenter;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class BakingFirebaseMessagingServices extends FirebaseMessagingService {

    private static final String TAG = BakingFirebaseMessagingServices.class.getSimpleName();
    /**
     * to get the token of the device
     * @param token is the token
     */
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, String.format(getString(R.string.token_is), token));
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, remoteMessage.getFrom());
        Log.d(TAG, String.format(getString(R.string.notification_type_and_id_msg),
                remoteMessage.getMessageType(), remoteMessage.getMessageId()));

        // work when app is in foreground
        Bundle data = remoteMessage.toIntent().getExtras();
        if (null != data) Log.d(TAG, String.format(getString(R.string.bundle_is_msg), data));
        RoomPresenter.getInstance(this).getRecipeDataFromMessageStoreItInRoom(this, data, true);

        // get the data from msg by keys then insert them to Room and show notification
        // to send JSON object it must be sent from customized server as FCM is only for some specified forms.

        // example of notification and it's keys is like Map<Key,value>
//        "id": 1,
//        "name": "Nutella Pie",
//        "ingredients":[] ,
//        "steps":[],
//        "servings": 8,
//        "image": ""
        Map<String, String> bodyMsg = remoteMessage.getData();
        if (null != bodyMsg && !bodyMsg.isEmpty()) {
            RoomPresenter.getInstance(this).getRecipeDataFromMapStoreItInRoom(this, bodyMsg, true);
        }
    }
}
