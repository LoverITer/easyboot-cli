package top.easyboot.titan.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author: frank.huang
 * @date: 2021-12-04 22:23
 */
public class JsonUtils {

    private static final Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gson = gsonBuilder.create();
    }

    private JsonUtils() {
    }

    public static Gson getGson(){
        return gson;
    }

    public static String toJSONString(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return gson.fromJson(jsonStr, clazz);
    }


}
