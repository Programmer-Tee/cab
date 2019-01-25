package com.example.tobeisun.bayo.communication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


// i used a dependency called retrofit, it is used to make api request. i hope you understand
public interface FirebaseMessageAPI {


    //what we are doing here is we are sending a post request to the FCM server, with a message object as the body of the post request
    //according to this here, we are expecting that the FCM server returns a message object if the post request is successful
    //i was wrong tho, its returning another type of object, will fix it later
    //it still works like this.
    @POST("/fcm/send")
    Call<Message> sendMessage(@Body Message message);
}
