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

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.room.PresenterRoom;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import timber.log.Timber;

public class BakingFirebaseMessagingServices extends FirebaseMessagingService {

    /**
     * to get the token of the device
     * @param token is the token
     */
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Timber.d(getString(R.string.token_is), token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Timber.d(remoteMessage.getFrom());
        Timber.d( getString(R.string.notification_type_and_id_msg) ,remoteMessage.getMessageType() , remoteMessage.getMessageId());

        // work when app is in foreground like Map
        Bundle data = remoteMessage.toIntent().getExtras();
        if (null != data ) Timber.d( getString(R.string.bundle_is_msg) ,data);
        PresenterRoom.getInstance(this).getRecipeDataFromMessageStoreItInRoom(this, data, true);

        // get the data from msg by keys then insert them to Room and show notification
        // to send JSON object it must be sent from customized server as FCM is only for some specified forms.

        // example of notification and it's keys
//        Map<String , String> data = remoteMessage.getData();
//        if (null != data && !data.isEmpty()){
//            Timber.d( getString(R.string.data_is) ,data.toString());
//            String title = data.get("title");
//            String instructions = data.get("instructions");
//            String ingredients =  data.get("ingredients");
//            String videos =  data.get("videos");
//            String images =  data.get("images");
//            RecipeEntry recipeEntry = new RecipeEntry(title,ingredients,instructions,images,videos);
//            insertDataToRoom(recipeEntry);
//            String[] notificationContents = {title, instructions};
//            NotificationUtils.startNotification(this,notificationContents);
//        }

    }
}
