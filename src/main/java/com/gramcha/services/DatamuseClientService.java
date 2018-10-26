package com.gramcha.services;


import com.gramcha.model.AntonymsResult;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.codec.BodyCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.core.buffer.Buffer;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.*;


/**
 * Created by gramachandran on 26/10/18.
 */
@Service
public class DatamuseClientService {
    @Autowired
    private VertxHolderService vertxHolderService;
    private WebClient client = null;

    private WebClient getClient() {
        if (client == null)
            client = WebClient.create(vertxHolderService.getVertx());
        return client;
    }

    private Class<ArrayList<AntonymsResult>> getResultListType() {
        return (Class<ArrayList<AntonymsResult>>) new ArrayList<AntonymsResult>().getClass();
    }

    private static Object getObject(byte[] byteArr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArr);
        ObjectInput in = new ObjectInputStream(bis);
        return in.readObject();
    }

    public Future<String> getAntonyms(String word) {
        Future<String> resultFuture = Future.future();
        String url = "https://api.datamuse.com/words?rel_ant=" + word;
//        String url = "https://google.com";
        System.out.println(url);
        getClient().getAbs(url)
//                .addQueryParam("rel_ant", word)
//                .as(BodyCodec.json(getResultListType()))
                .send(ayncResult -> {
                    if (ayncResult.succeeded()) {
//                        HttpResponse<ArrayList<AntonymsResult>> response = ayncResult.result();
//                        ArrayList<AntonymsResult> results = response.body();
//                        String resultAntonym = getBestAntonyms(results, word);
                        HttpResponse<Buffer> response = ayncResult.result();


                        JsonArray results = response.bodyAsJsonArray();

                        String resultAntonym = getBestAntonyms(results.getList(), word);
                        System.out.println("Got HTTP response with status " + response.statusCode());
//                        resultFuture.complete(response.body().toString());
                        resultFuture.complete(resultAntonym);
                    } else {

                        System.out.println("**********");
                        System.out.println(ayncResult.cause().getMessage());

                        System.out.println("**********");
//                        ayncResult.cause().printStackTrace();
                        resultFuture.fail(ayncResult.cause().getMessage());
                    }
                });
        return resultFuture;
    }

    public String getBestAntonyms(List<LinkedHashMap> synonymsResultList, String word) {
        int topScore = 0;
        String topWord = word;
        for (LinkedHashMap item : synonymsResultList) {
            Integer score = (Integer) item.get("score");
            String itemWord = (String) item.get("word");
            if (score > topScore) {
                topScore = score;
                topWord = itemWord;
            }
//            System.out.println(item.toString());
//            System.out.println(item.getClass());
        }
        return topWord;
    }
}
