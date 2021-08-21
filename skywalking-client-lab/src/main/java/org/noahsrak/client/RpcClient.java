package org.noahsrak.client;

import okhttp3.*;

import java.io.IOException;

/**
 * @author: noahsark
 * @version:
 * @date: 2020/8/22
 */
public class RpcClient {


    public static void main(String[] args) {
//        invokeGetServerAddress();

        invokeLogin();
    }

    public static void invokeGetServerAddress() {

        //Tracer tracer = JaegerTracerHelper.initTracer("");

        /*String url = "http://192.168.7.115:8888/getServerAddress";*/
        String url = "http://localhost:8888/login";
        OkHttpClient client = new OkHttpClient();
        Request.Builder request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), "FSP"));


        client.newCall(request.build()).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });

    }

    public static void invokeLogin() {

        /*String url = "http://192.168.7.115:8888/login";*/
        String url = "http://localhost:8888/login";
        OkHttpClient client = new OkHttpClient();
        Request.Builder request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), "allen"));


        client.newCall(request.build()).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });


    }

}
