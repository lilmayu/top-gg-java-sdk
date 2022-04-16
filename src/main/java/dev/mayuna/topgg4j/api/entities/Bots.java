package dev.mayuna.topgg4j.api.entities;

import com.google.gson.annotations.SerializedName;
import dev.mayuna.topgg4j.api.TopGGAPIResponse;
import lombok.Getter;

public class Bots extends TopGGAPIResponse {

    private @Getter Bot[] results;
    private @Getter int limit;
    private @Getter int offset;
    private @Getter int count;
    private @Getter int total;
}
