package com.heyaug.httpso;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import com.douyin.gorgon;
import com.yanzhenjie.andserver.annotation.*;
import com.yanzhenjie.andserver.framework.body.JsonBody;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yixia.utils.TinyEncode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class MyController {
    private static HashMap<String, String> JsonObjectToHashMap(JSONObject jsonObj) throws JSONException {
        HashMap<String, String> data = new HashMap<>();
        Iterator<String> it = jsonObj.keys();
        while (it.hasNext()) {
            String key = it.next();
            String value = jsonObj.get(key).toString();
            data.put(key, value);
        }
        return data;
    }

    @GetMapping("/test")
    void postSign(HttpResponse response) {
        Map<String, String> signMap = new HashMap<>();
        signMap.put("signature", "heyaug");
        JSONObject jsonObject = new JSONObject(signMap);
        response.setBody(new JsonBody(jsonObject));
    }

    @ResponseBody
    @PostMapping("/gorgon")
    String getNewGogon(HttpRequest request, @RequestBody String body, @RequestParam(required = false,name = "url") String url) {
        try {
            System.out.println(url);
            HashMap<String, String> headers = new HashMap<>();
            for (String headerName : request.getHeaderNames()) {
                headers.put(headerName, request.getHeader(headerName));
            }
            return gorgon.getGorgon(url, headers);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @PostMapping("/url")
    String getNewGogonUrl(@RequestBody String body) {
        return gorgon.getGorgon(body);
    }

    @ResponseBody
    @PostMapping("/urlAndHeaders")
    String getNewGogonUrl2(HttpRequest request, @RequestParam(required = false,name = "url") String url) {
        List<String> headerNames = request.getHeaderNames();
        HashMap<String, String> headers = new HashMap<>();
        for (String headerName : headerNames) {
            headers.put(headerName, request.getHeader(headerName));
        }
        return gorgon.getGorgon(url, headers);
    }

    @ResponseBody
    @PostMapping("/decode")
    String mPai(@RequestBody String body) {
        try {
            byte[] data = Base64.decode(body, Base64.DEFAULT);
            return TinyEncode.DecodeResult(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
