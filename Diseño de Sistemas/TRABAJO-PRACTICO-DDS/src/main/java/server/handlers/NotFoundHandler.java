package server.handlers;

import io.javalin.Javalin;

public class NotFoundHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.after("/*", ctx -> {
            if(ctx.statusCode() == 404) {
                ctx.render("errorHandlers/404.hbs");
            }
        });
    }
}
