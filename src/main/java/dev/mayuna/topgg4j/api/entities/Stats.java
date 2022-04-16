package dev.mayuna.topgg4j.api.entities;

import com.google.gson.annotations.SerializedName;
import dev.mayuna.topgg4j.api.TopGGAPIResponse;
import lombok.Getter;

public class Stats extends TopGGAPIResponse {

    private @Getter @SerializedName("server_count") int serverCount;
    private @Getter String[] shards;
    private @Getter @SerializedName("shard_count") int shardCount;

}
