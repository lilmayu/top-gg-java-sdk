package dev.mayuna.topggsdk;

import com.google.gson.Gson;
import dev.mayuna.topggsdk.api.entities.webhooks.Webhook;
import io.javalin.Javalin;
import io.javalin.http.HttpCode;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class WebhookHandler {

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

    /**
     * Stops Javalin
     */
    public void stop() {
        logger.info("Stopping Javalin...");
        javalin.stop();
    }
}
