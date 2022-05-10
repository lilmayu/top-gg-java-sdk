package dev.mayuna.topggsdk.api.entities.webhooks;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Webhook object holds data from top.gg webhook integration. You can find out webhook's type via {@link #getWebhookType()} method
 * @see <a href="https://docs.top.gg/resources/webhooks/">https://docs.top.gg/resources/webhooks/</a>
 */
public class Webhook {

    private @Getter @SerializedName("user") String userId;
    private @Getter String type;
    private @Getter String query;

    private @Getter @SerializedName("bot") String botId;
    private @Getter @SerializedName("isWeekend") boolean weekend;

    private @Getter @SerializedName("guild") String guildId;

    /**
     * Gets webhook's type
     * @return Non-null {@link Type} object
     */
    public @NonNull Type getWebhookType() {
        if (guildId == null) {
            return Type.BOT;
        }

        return Type.SERVER;
    }

    /**
     * Parses {@link #query} string in webhook body into array of {@link Query} objects
     * @return Nonnull array of {@link Query} (empty when there was no query, aka. query is null or empty)
     */
    public @NonNull Query[] parseQueries() {
        if (query == null || query.isEmpty()) {
            return new Query[0];
        }

        Pattern pattern = Pattern.compile("[\\?&]([^&=]+)=([^&=]+)");
        Matcher matcher = pattern.matcher(query);

        List<Query> list = new LinkedList<>();

        while(matcher.find()) {
            list.add(new Query(matcher.group(1), matcher.group(2)));
        }

        return list.toArray(new Query[0]);
    }

    /**
     * Determines webhook's type. Values: {@link #BOT}, {@link #SERVER}
     */
    public enum Type {
        BOT,
        SERVER;
    }

    /**
     * Holds information about single query (name and value)
     */
    public static class Query {

        private final @Getter String name;
        private final @Getter String value;

        public Query(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
