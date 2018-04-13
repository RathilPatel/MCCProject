package com.example.edwin.moodlev2;

import android.util.Log;

import com.mongodb.stitch.android.AuthListener;
import com.mongodb.stitch.android.StitchClient;
import com.mongodb.stitch.android.StitchClientFactory;

import com.mongodb.stitch.android.auth.AvailableAuthProviders;
import com.mongodb.stitch.android.auth.anonymous.AnonymousAuthProvider;
import com.mongodb.stitch.android.auth.oauth2.facebook.FacebookAuthProvider;
import com.mongodb.stitch.android.auth.oauth2.google.GoogleAuthProvider;
import com.mongodb.stitch.android.services.mongodb.MongoClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class DataAPI {

    String boards;
    OkHttpClient client;

    DataAPI() {
        boards = "[ {name: 'TE-Comps', lastMessage:'essdf', tagColor:'#eee111'}," +
                "{name: 'Robocon', lastMessage:'ghhffghgghfhf', tagColor:'#227222'}," +
                "{name: 'Vaayushastra', lastMessage:'Elections in Progress', tagColor:'#abcabc'}," +
                "{name: 'Vaayushastra', lastMessage:'Elections in Progress', tagColor:'#abcabc'}," +
                "{name: 'Vaayushastra', lastMessage:'Elections in Progress', tagColor:'#abcabc'}," +
                "{name: 'Vaayushastra', lastMessage:'Elections in Progress', tagColor:'#abcabc'}," +
                "{name: 'Vaayushastra', lastMessage:'Elections in Progress', tagColor:'#abcabc'}," +
                "{name: 'Vaayushastra', lastMessage:'Elections in Progress', tagColor:'#abcabc'}," +
                "{name: 'Vaayushastra', lastMessage:'Elections in Progress', tagColor:'#abcabc'}," +
                "{name: 'Vaayushastra', lastMessage:'Elections in Progress', tagColor:'#abcabc'}," +
                "{name: 'Vaayushastra', lastMessage:'Elections in Progress', tagColor:'#abcabc'}," +
                "{name: 'Vaayushastra', lastMessage:'Elections in Progress', tagColor:'#abcabc'}," +
                "{name: 'Vaayushastra', lastMessage:'Elections in Progress', tagColor:'#abcabc'}]";


//       client = new OkHttpClient();
//
//       try  {
//           Log.i("JSON", run("https://webhooks.mongodb-stitch.com/api/client/v2.0/app/mcc-jnrsm/service/httpServer/incoming_webhook/webhook0?secret=test"));
//       }    catch (IOException e)   {
//
//       }


    }
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = this.client.newCall(request).execute();
        return response.body().toString();
    }

    String getBoards() {

        return boards;


    }
}
