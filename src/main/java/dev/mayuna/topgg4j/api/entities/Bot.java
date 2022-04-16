package dev.mayuna.topgg4j.api.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import dev.mayuna.simpleapi.Action;
import dev.mayuna.topgg4j.api.TopGGAPIResponse;
import lombok.Getter;

public class Bot extends TopGGAPIResponse {

    private @Getter @SerializedName("defAvatar") String defaultAvatar;
    private @Getter String invite;
    private @Getter String website;
    private @Getter String support; // TODO: funkce pro získání support linku
    private @Getter String github;
    private @Getter @SerializedName("longdesc") String descriptionLong;
    private @Getter @SerializedName("shortdesc") String descriptionShort;
    private @Getter String prefix;
    private @Getter @Deprecated String lib;
    private @Getter @SerializedName("clientid") String clientId;
    private @Getter String avatar;
    private @Getter String id;
    private @Getter String discriminator;
    private @Getter String username;
    private @Getter String date; // TODO: Čas
    private @Getter String[] guilds;
    private @Getter int[] shards;
    private @Getter int monthlyPoints;
    private @Getter int points;
    private @Getter boolean certifiedBot;
    private @Getter String[] owners;
    private @Getter String[] tags;
    private @Getter String bannerUrl;
    private @Getter @SerializedName("donatebotguildid") String donatebotGuildId;

    public Action<Stats> fetchStats() {
        return api.fetchBotStats();
    }

    public Action<TopGGAPIResponse> updateBotStats(int serverCount) {
        return api.updateBotStats(serverCount);
    }

    public Action<TopGGAPIResponse> updateBotStats(int serverCount, int shardCount) {
        return api.updateBotStats(serverCount, shardCount);
    }

    public Action<TopGGAPIResponse> updateBotStats(int serverCount, int shardId, int shardCount) {
        return api.updateBotStats(serverCount, shardId, shardCount);
    }

    public Action<TopGGAPIResponse> updateBotStats(int[] serverCount) {
        return api.updateBotStats(serverCount);
    }

    public Action<TopGGAPIResponse> updateBotStats(int[] serverCount, int shardCount) {
        return api.updateBotStats(serverCount, shardCount);
    }

    public Action<TopGGAPIResponse> updateBotStats(int serverCount, int[] shards) {
        return api.updateBotStats(serverCount, shards);
    }

    public Action<TopGGAPIResponse> updateBotStats(int serverCount, int[] shards, int shardCount) {
        return api.updateBotStats(serverCount, shards, shardCount);

    }
}
