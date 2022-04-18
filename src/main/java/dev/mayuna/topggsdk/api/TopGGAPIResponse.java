package dev.mayuna.topggsdk.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import dev.mayuna.simpleapi.APIResponse;
import dev.mayuna.simpleapi.deserializers.GsonDeserializer;
import dev.mayuna.topggsdk.TopGGAPI;
import lombok.Getter;

/**
 * top.gg's API response. You can check if the request was successful by calling {@link #wasSuccessful()} (if it was not successful, error message should be in #getError())
 */
public class TopGGAPIResponse extends APIResponse<TopGGAPI> implements GsonDeserializer {

    private final @Getter String error = null;
    private @Getter @SerializedName("retry-after") long retryAfter = -1;

    public boolean wasSuccessful() {
        return error == null && retryAfter == -1;
    }

    @Override
    public Gson getGson() {
        return new Gson();
    }
}
