package dev.mayuna.topgg4j;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.mayuna.simpleapi.*;
import dev.mayuna.topgg4j.api.TopGGAPIResponse;
import dev.mayuna.topgg4j.api.entities.*;
import lombok.Getter;
import lombok.NonNull;

import java.net.http.HttpRequest;

public class TopGGAPI extends SimpleAPI {

    private final @Getter String token;
    private final @Getter String botId;

    public TopGGAPI(@NonNull String token, String botId) {
        this.token = token;
        this.botId = botId;
    }

    public TopGGAPI(@NonNull String token) {
        this(token, null);
    }

    @Override
    public String getURL() {
        return "https://top.gg/api";
    }

    @Override
    public Header[] getDefaultHeads() {
        if (token != null) {
            return new Header[]{
                    new Header("Authorization", token)
            };
        } else {
            return super.getDefaultHeads();
        }
    }

    // API

    public Action<Bots> fetchBots() {
        return fetchBots(50, 0, "", "", "");
    }

    public Action<Bots> fetchBots(int limit) {
        return fetchBots(limit, 0, "", "", "");
    }

    public Action<Bots> fetchBots(int limit, int offset) {
        return fetchBots(limit, offset, "", "", "");
    }

    public Action<Bots> fetchBots(int limit, int offset, String search) {
        return fetchBots(limit, offset, search, "", "");
    }

    public Action<Bots> fetchBots(String search) {
        return fetchBots(50, 0, search, "", "");
    }

    public Action<Bots> fetchBots(String search, String sort) {
        return fetchBots(50, 0, search, sort, "");
    }

    public Action<Bots> fetchBots(int limit, int offset, String search, String sort) {
        return fetchBots(limit, offset, search, sort, "");
    }

    public Action<Bots> fetchBots(int limit, int offset, String search, String sort, String fields) {
        return new Action<>(this, Bots.class, new APIRequest.Builder()
                .setEndpoint("/bots")
                .setMethod("GET")
                .setQueries(new Query[]{
                        new Query("limit", String.valueOf(limit)),
                        new Query("offset", String.valueOf(offset)),
                        new Query("search", search),
                        new Query("sort", sort),
                        new Query("fields", fields)
                })
                .build());
    }

    public Action<Bot> fetchBot() {
        if (botId == null) {
            throw new IllegalArgumentException("Bot's ID is required for this endpoint! You can specify botId in TopGGAPI's constructor.");
        }

        return new Action<>(this, Bot.class, new APIRequest.Builder()
                .setEndpoint("/bots/{bot_id}")
                .setMethod("GET")
                .addPathParameter(new PathParameter("bot_id", botId))
                .build());
    }

    public Action<User[]> fetchLast1000Votes() {
        if (botId == null) {
            throw new IllegalArgumentException("Bot's ID is required for this endpoint! You can specify botId in TopGGAPI's constructor.");
        }

        return new Action<>(this, User[].class, new APIRequest.Builder()
                .setEndpoint("/bots/{bot_id}/votes")
                .setMethod("GET")
                .addPathParameter(new PathParameter("bot_id", botId))
                .build());
    }

    public Action<Stats> fetchBotStats() {
        if (botId == null) {
            throw new IllegalArgumentException("Bot's ID is required for this endpoint! You can specify botId in TopGGAPI's constructor.");
        }

        return new Action<>(this, Stats.class, new APIRequest.Builder()
                .setEndpoint("/bots/{bot_id}/stats")
                .setMethod("GET")
                .addPathParameter(new PathParameter("bot_id", botId))
                .build());
    }

    public Action<VoteStatus> fetchVoteStatus( String userId) {
        if (botId == null) {
            throw new IllegalArgumentException("Bot's ID is required for this endpoint! You can specify botId in TopGGAPI's constructor.");
        }

        return new Action<>(this, VoteStatus.class, new APIRequest.Builder()
                .setEndpoint("/bots/{bot_id}/check")
                .setMethod("GET")
                .addPathParameter(new PathParameter("bot_id", botId))
                .addQuery(new Query("userId", userId))
                .build());
    }

    public Action<TopGGAPIResponse> updateBotStats( int serverCount) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server_count", serverCount);

        return makeBotStatsAction(jsonObject);
    }

    public Action<TopGGAPIResponse> updateBotStats( int serverCount, int shardCount) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server_count", serverCount);
        jsonObject.addProperty("shard_count", shardCount);

        return makeBotStatsAction(jsonObject);
    }

    public Action<TopGGAPIResponse> updateBotStats( int serverCount, int shardId, int shardCount) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server_count", serverCount);
        jsonObject.addProperty("shard_id", shardId);
        jsonObject.addProperty("shard_count", shardCount);

        return makeBotStatsAction(jsonObject);
    }

    public Action<TopGGAPIResponse> updateBotStats( int[] serverCount) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (int number : serverCount) {
            jsonArray.add(number);
        }
        jsonObject.add("server_count", jsonArray);

        return makeBotStatsAction(jsonObject);
    }

    public Action<TopGGAPIResponse> updateBotStats( int[] serverCount, int shardCount) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (int number : serverCount) {
            jsonArray.add(number);
        }
        jsonObject.add("server_count", jsonArray);
        jsonObject.addProperty("shard_count", shardCount);

        return makeBotStatsAction(jsonObject);
    }

    public Action<TopGGAPIResponse> updateBotStats( int serverCount, int[] shards) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (int number : shards) {
            jsonArray.add(number);
        }
        jsonObject.add("shards", jsonArray);
        jsonObject.addProperty("server_count", serverCount);

        return makeBotStatsAction(jsonObject);
    }

    public Action<TopGGAPIResponse> updateBotStats( int serverCount, int[] shards, int shardCount) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (int number : shards) {
            jsonArray.add(number);
        }
        jsonObject.add("shards", jsonArray);
        jsonObject.addProperty("server_count", serverCount);
        jsonObject.addProperty("shard_count", shardCount);

        return makeBotStatsAction(jsonObject);
    }

    private Action<TopGGAPIResponse> makeBotStatsAction( JsonObject jsonObject) {
        if (botId == null) {
            throw new IllegalArgumentException("Bot's ID is required for this endpoint! You can specify botId in TopGGAPI's constructor.");
        }

        return new Action<>(this, TopGGAPIResponse.class, new APIRequest.Builder()
                .setEndpoint("/bots/{bot_id}/stats")
                .setMethod("POST")
                .addPathParameter(new PathParameter("bot_id", botId))
                .setContentType("application/json")
                .setBodyPublisher(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .build());
    }

    public Action<User> fetchUser(String userId) {
        return new Action<>(this, User.class, new APIRequest.Builder()
                .setEndpoint("/users/{user_id}")
                .setMethod("GET")
                .addPathParameter(new PathParameter("user_id", userId))
                .build());
    }
}
