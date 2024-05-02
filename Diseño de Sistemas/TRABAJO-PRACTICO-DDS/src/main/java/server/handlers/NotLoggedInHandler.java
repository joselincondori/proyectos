package server.handlers;

import io.javalin.Javalin;
import server.exceptions.NotLoggedInException;

public class NotLoggedInHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.before("/app/*", ctx -> {
            if(ctx.sessionAttribute("usuario_id") == null) {
                throw new NotLoggedInException();
            }
        });

        app.exception(NotLoggedInException.class, (e, context) -> {
            context.redirect("/login");
        });
    }
}
