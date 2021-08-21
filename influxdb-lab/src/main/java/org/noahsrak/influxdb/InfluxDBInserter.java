package org.noahsrak.influxdb;

import okhttp3.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: noahsark
 * @version:
 * @date: 2021/4/30
 */
public class InfluxDBInserter {

    public static void main(String[] args) {
        String url = "http://192.168.7.115:8086/write?db=mds";

        OkHttpClient client = new OkHttpClient();
        String content;
        Request.Builder request;
        // cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000
        //1434055562000000000
        //1619775315451000
        String caton = "mds_audio_caton,streamId=10001,issvc=0 ";
        StringBuffer buffer;
        Random random = new Random();

        Instant instant;

        while (true) {

            instant = Instant.now();

            buffer = new StringBuffer(caton)
                    .append(" value=")
                    .append(random.nextInt(10))
                    .append(" ")
                    .append(System.currentTimeMillis()*1000*1000);

            System.out.println("body:" + buffer.toString());

            request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), buffer.toString()));

            client.newCall(request.build()).enqueue(new Callback() {

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println(response.code());
                    System.out.println(response.body().string());
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
            });

            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
