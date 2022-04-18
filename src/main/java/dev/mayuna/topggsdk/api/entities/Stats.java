package dev.mayuna.topggsdk.api.entities;

import com.google.gson.annotations.SerializedName;
import dev.mayuna.topggsdk.api.TopGGAPIResponse;
import lombok.Getter;

/**
 * Specific stats about top.gg bot (server count, shards, shard count)
 */
public class Stats extends TopGGAPIResponse {

    private @Getter @SerializedName("server_count") int serverCount;
    private @Getter String[] shards;
    private @Getter @SerializedName("shard_count") int shardCount;

}
