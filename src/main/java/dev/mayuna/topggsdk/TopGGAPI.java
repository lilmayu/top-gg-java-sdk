package dev.mayuna.topggsdk;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.mayuna.simpleapi.*;
import dev.mayuna.topggsdk.api.TopGGAPIResponse;
import dev.mayuna.topggsdk.api.entities.*;
import dev.mayuna.topggsdk.api.entities.webhooks.Webhook;
import io.javalin.Javalin;
import io.javalin.http.HttpCode;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpRequest;
import java.util.function.Consumer;

/**
 * Main class of Top-GG API wrapped in Java. Using this class, you can request stuff and post stuff to top.gg's API. Some endpoints/methods do not require bot ID.
 */
public class TopGGAPI extends SimpleAPI {

    private final @Getter String token;
    private final @Getter String botId;

    private @Getter WebhookHandler webhookHandler;

    public TopGGAPI(@NonNull String token, String botId, int port, String path, String authorization, Consumer<Webhook> webhookListener) {
        this.token = token;
        this.botId = botId;

        if (port != -1) {
            webhookHandler = new WebhookHandler(port, path, authorization, webhookListener);
        }
    }

    public TopGGAPI(@NonNull String token, String botId) {
        this(token, botId, -1, null, null, null);
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

    /**
     * Searches bots by specified arguments. Bot ID for this endpoint is <b>not required</b>. <br>
     * Default values:<br>
     * - Limit: 50<br>
     * - Offset: 0<br>
     * - Search empty<br>
     * - Sort empty<br>
     * - Fields empty<br>
     *
     * @return {@link Action} with {@link Bots} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#search-bots">https://docs.top.gg/api/bot/#search-bots</a>
     */
    public Action<Bots> searchBots() {
        return searchBots(50, 0, "", "", "");
    }

    // API

    /**
     * Searches bots by specified arguments. Bot ID for this endpoint is <b>not required</b>. <br>
     * Default values:<br>
     * - Offset: 0<br>
     * - Search empty<br>
     * - Sort empty<br>
     * - Fields empty<br>
     *
     * @param limit The amount of bots to search for. Max. 500
     *
     * @return {@link Action} with {@link Bots} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#search-bots">https://docs.top.gg/api/bot/#search-bots</a>
     */
    public Action<Bots> searchBots(int limit) {
        return searchBots(limit, 0, "", "", "");
    }

    /**
     * Searches bots by specified arguments. Bot ID for this endpoint is <b>not required</b>. <br>
     * Default values:<br>
     * - Search empty<br>
     * - Sort empty<br>
     * - Fields empty<br>
     *
     * @param limit  The amount of bots to search for. Max. 500
     * @param offset TAmount of bots to skip
     *
     * @return {@link Action} with {@link Bots} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#search-bots">https://docs.top.gg/api/bot/#search-bots</a>
     */
    public Action<Bots> searchBots(int limit, int offset) {
        return searchBots(limit, offset, "", "", "");
    }

    /**
     * Searches bots by specified arguments. Bot ID for this endpoint is <b>not required</b>. <br>
     * Default values:<br>
     * - Sort empty<br>
     * - Fields empty<br>
     *
     * @param limit  The amount of bots to search for. Max. 500
     * @param offset TAmount of bots to skip
     * @param search A search string in the format of <code>field: value field2: value2</code>
     *
     * @return {@link Action} with {@link Bots} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#search-bots">https://docs.top.gg/api/bot/#search-bots</a>
     */
    public Action<Bots> searchBots(int limit, int offset, String search) {
        return searchBots(limit, offset, search, "", "");
    }

    /**
     * Searches bots by specified arguments. Bot ID for this endpoint is <b>not required</b>. <br>
     * Default values:<br>
     * - Limit: 50<br>
     * - Offset: 0<br>
     * - Sort empty<br>
     * - Fields empty<br>
     *
     * @param search A search string in the format of <code>field: value field2: value2</code>
     *
     * @return {@link Action} with {@link Bots} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#search-bots">https://docs.top.gg/api/bot/#search-bots</a>
     */
    public Action<Bots> searchBots(String search) {
        return searchBots(50, 0, search, "", "");
    }

    /**
     * Searches bots by specified arguments. Bot ID for this endpoint is <b>not required</b>. <br>
     * Default values:<br>
     * - Limit: 50<br>
     * - Offset: 0<br>
     * - Fields empty<br>
     *
     * @param search A search string in the format of <code>field: value field2: value2</code>
     * @param sort   The field to sort by. Prefix with <code>-</code> to reverse the order
     *
     * @return {@link Action} with {@link Bots} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#search-bots">https://docs.top.gg/api/bot/#search-bots</a>
     */
    public Action<Bots> searchBots(String search, String sort) {
        return searchBots(50, 0, search, sort, "");
    }

    /**
     * Searches bots by specified arguments. Bot ID for this endpoint is <b>not required</b>. <br>
     * Default values:<br>
     * - Fields empty<br>
     *
     * @param limit  The amount of bots to search for. Max. 500
     * @param offset TAmount of bots to skip
     * @param search A search string in the format of <code>field: value field2: value2</code>
     * @param sort   The field to sort by. Prefix with <code>-</code> to reverse the order
     *
     * @return {@link Action} with {@link Bots} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#search-bots">https://docs.top.gg/api/bot/#search-bots</a>
     */
    public Action<Bots> searchBots(int limit, int offset, String search, String sort) {
        return searchBots(limit, offset, search, sort, "");
    }

    /**
     * Searches bots by specified arguments. Bot ID for this endpoint is <b>not required</b>.
     *
     * @param limit  The amount of bots to search for. Max. 500
     * @param offset TAmount of bots to skip
     * @param search A search string in the format of <code>field: value field2: value2</code>
     * @param sort   The field to sort by. Prefix with <code>-</code> to reverse the order
     * @param fields A comma separated list of fields to show
     *
     * @return {@link Action} with {@link Bots} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#search-bots">https://docs.top.gg/api/bot/#search-bots</a>
     */
    public Action<Bots> searchBots(int limit, int offset, String search, String sort, String fields) {
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

    /**
     * Fetches your Bot by specified Bot ID in arguments. Bot ID for this method is <b>not required</b>.
     *
     * @param botId Bot's Discord ID
     *
     * @return {@link Action} with {@link Bot} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#find-one-bot">https://docs.top.gg/api/bot/#find-one-bot</a>
     */
    public Action<Bot> fetchBot(String botId) {
        return new Action<>(this, Bot.class, new APIRequest.Builder()
                .setEndpoint("/bots/{bot_id}")
                .setMethod("GET")
                .addPathParameter(new PathParameter("bot_id", botId))
                .build());
    }

    /**
     * Fetches your Bot by specified Bot ID in constructor. Bot ID for this method is <b>required</b>.
     *
     * @return {@link Action} with {@link Bot} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#find-one-bot">https://docs.top.gg/api/bot/#find-one-bot</a>
     */
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

    /**
     * Fetches the last 1000 voters for your bot.<br>
     * Note: If your bot receives more than 1000 votes monthly you cannot use this endpoint and must use webhooks and implement your own caching instead.<br><br>
     * This endpoint only returns unique votes, it does not include double votes (weekend votes).
     *
     * @return {@link Action} with {@link User} array
     *
     * @see <a href="https://docs.top.gg/api/bot/#last-1000-votes">https://docs.top.gg/api/bot/#last-1000-votes</a>
     */
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

    /**
     * Fetches specific stats (server count, shards, shard count) about a bot.
     *
     * @return {@link Action} with {@link Stats} object
     */
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

    /**
     * Fetches status whether a user has voted for your bot. Safe to use even if you have over 1k monthly votes.
     *
     * @param userId User ID (Discord user ID)
     *
     * @return {@link Action} with {@link VoteStatus} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#individual-user-vote">https://docs.top.gg/api/bot/#individual-user-vote</a>
     */
    public Action<VoteStatus> fetchVoteStatus(String userId) {
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

    /**
     * Updates bot's stats.
     *
     * @param serverCount Amount of servers the bot is in
     *
     * @return {@link Action} with {@link TopGGAPIResponse} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#post-stats">https://docs.top.gg/api/bot/#post-stats</a>
     */
    public Action<TopGGAPIResponse> updateBotStats(int serverCount) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server_count", serverCount);

        return makeBotStatsAction(jsonObject);
    }

    /**
     * Updates bot's stats.
     *
     * @param serverCount Amount of servers the bot is in
     * @param shardCount  The amount of shards the bot has
     *
     * @return {@link Action} with {@link TopGGAPIResponse} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#post-stats">https://docs.top.gg/api/bot/#post-stats</a>
     */
    public Action<TopGGAPIResponse> updateBotStats(int serverCount, int shardCount) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server_count", serverCount);
        jsonObject.addProperty("shard_count", shardCount);

        return makeBotStatsAction(jsonObject);
    }

    /**
     * Updates bot's stats.
     *
     * @param serverCount Amount of servers the bot is in
     * @param shardId     The zero-indexed id of the shard posting. Makes server_count set the shard specific server count.
     * @param shardCount  The amount of shards the bot has
     *
     * @return {@link Action} with {@link TopGGAPIResponse} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#post-stats">https://docs.top.gg/api/bot/#post-stats</a>
     */
    public Action<TopGGAPIResponse> updateBotStats(int serverCount, int shardId, int shardCount) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server_count", serverCount);
        jsonObject.addProperty("shard_id", shardId);
        jsonObject.addProperty("shard_count", shardCount);

        return makeBotStatsAction(jsonObject);
    }

    /**
     * Updates bot's stats.
     *
     * @param serverCount Amount of servers the bot is in per shard
     *
     * @return {@link Action} with {@link TopGGAPIResponse} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#post-stats">https://docs.top.gg/api/bot/#post-stats</a>
     */
    public Action<TopGGAPIResponse> updateBotStats(int[] serverCount) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (int number : serverCount) {
            jsonArray.add(number);
        }
        jsonObject.add("server_count", jsonArray);

        return makeBotStatsAction(jsonObject);
    }

    /**
     * Updates bot's stats.
     *
     * @param serverCount Amount of servers the bot is in per shard
     * @param shardCount  The amount of shards the bot has
     *
     * @return {@link Action} with {@link TopGGAPIResponse} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#post-stats">https://docs.top.gg/api/bot/#post-stats</a>
     */
    public Action<TopGGAPIResponse> updateBotStats(int[] serverCount, int shardCount) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (int number : serverCount) {
            jsonArray.add(number);
        }
        jsonObject.add("server_count", jsonArray);
        jsonObject.addProperty("shard_count", shardCount);

        return makeBotStatsAction(jsonObject);
    }

    /**
     * Updates bot's stats. This method seems quite useless, however I am keeping this method just in case.
     *
     * @param serverCount Amount of servers the bot is in
     * @param shards      Amount of servers the bot is in per shard
     *
     * @return {@link Action} with {@link TopGGAPIResponse} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#post-stats">https://docs.top.gg/api/bot/#post-stats</a>
     */
    public Action<TopGGAPIResponse> updateBotStats(int serverCount, int[] shards) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (int number : shards) {
            jsonArray.add(number);
        }
        jsonObject.add("shards", jsonArray);
        jsonObject.addProperty("server_count", serverCount);

        return makeBotStatsAction(jsonObject);
    }

    /**
     * Updates bot's stats. This method seems quite useless, however I am keeping this method just in case.
     *
     * @param serverCount Amount of servers the bot is in per shard
     * @param shards      Amount of servers the bot is in per shard
     * @param shardCount  The amount of shards the bot has
     *
     * @return {@link Action} with {@link TopGGAPIResponse} object
     *
     * @see <a href="https://docs.top.gg/api/bot/#post-stats">https://docs.top.gg/api/bot/#post-stats</a>
     */
    public Action<TopGGAPIResponse> updateBotStats(int serverCount, int[] shards, int shardCount) {
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

    private Action<TopGGAPIResponse> makeBotStatsAction(JsonObject jsonObject) {
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

    /**
     * Fetches {@link User} by their Discord user ID
     *
     * @param userId User ID (Discord user ID)
     *
     * @return {@link Action} with {@link User} object
     */
    public Action<User> fetchUser(String userId) {
        return new Action<>(this, User.class, new APIRequest.Builder()
                .setEndpoint("/users/{user_id}")
                .setMethod("GET")
                .addPathParameter(new PathParameter("user_id", userId))
                .build());
    }

    /**
     * Fetches {@link MultiplierStatus} which tells if currently it is weekend (during weekends, bots receive double votes).
     *
     * @return {@link Action} with {@link MultiplierStatus} object
     */
    public Action<MultiplierStatus> fetchMultiplierStatus() {
        return new Action<>(this, MultiplierStatus.class, new APIRequest.Builder()
                .setEndpoint("/weekend")
                .setMethod("GET")
                .build());
    }

    public static class Builder {

        private String token = null;
        private String botId = null;

        private int port = -1;
        private String path = null;
        private String authorization = null;
        private Consumer<Webhook> webhookListener = webhook -> {};

        private Builder() {}

        public static Builder create() {
            return new Builder();
        }

        public Builder withToken(@NonNull String token) {
            this.token = token;
            return this;
        }

        public Builder withBotId(@NonNull String botId) {
            this.botId = botId;
            return this;
        }

        public Builder withWebhookListener(int port, @NonNull String path, @NonNull String authorization, @NonNull Consumer<Webhook> webhookListener) {
            if (port < 0) {
                throw new IllegalArgumentException("Port cannot be smaller than zero! You should use any port between 1024 and 65535. Use 0 for random open port.");
            }

            this.port = port;
            this.path = path;
            this.authorization = authorization;
            this.webhookListener = webhookListener;
            return this;
        }

        public TopGGAPI build() {
            return new TopGGAPI(token, botId, port, path, authorization, webhookListener);
        }
    }

    private class WebhookHandler {

        private final Logger logger;
        private final @Getter int port;
        private final @Getter String path;
        private final @Getter String authorization;
        private final @Getter Consumer<Webhook> webhookListener;

        private @Getter Javalin javalin;

        public WebhookHandler(int port, String path, String authorization, Consumer<Webhook> webhookListener) {
            this.port = port;
            this.path = path;
            this.authorization = authorization;
            this.webhookListener = webhookListener;

            logger = LoggerFactory.getLogger(WebhookHandler.class);

            startJavalin();
        }

        private void startJavalin() {
            logger.info("Starting Javalin at port " + port + " with path " + path + " for Top.gg's Webhooks.");
            javalin = Javalin.create();

            javalin.post(path, ctx -> {
                logger.debug("Received POST request on " + path);

                String authorizationHeader = ctx.header("Authorization");

                if (!authorization.equals(authorizationHeader)) {
                    logger.warn("Received POST has suspicious Authorization header! Ignoring this request with HTTP Code 418...");
                    ctx.status(HttpCode.IM_A_TEAPOT);
                    return;
                }

                Webhook webhook;

                try {
                    webhook = new Gson().fromJson(ctx.body(), Webhook.class);
                } catch (Exception exception) {
                    logger.error("Exception occurred while parsing top.gg's webhook! Possibly a bug!", exception);
                    ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
                    return;
                }

                logger.debug("Received Webhook from top.gg from user " + webhook.getUserId());

                try {
                    webhookListener.accept(webhook);
                } catch (Exception exception) {
                    logger.error("Exception occurred while processing provided webhook listener!", exception);
                    ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
                    return;
                }

                ctx.status(HttpCode.OK);
            });

            try {
                javalin.start(port);
            } catch (Exception exception) {
                logger.error("Exception occurred while starting Javalin!", exception);
                return;
            }

            logger.info("Javalin has been started.");
        }

        public void stop() {
            logger.info("Stopping Javalin...");
            javalin.stop();
        }
    }
}
