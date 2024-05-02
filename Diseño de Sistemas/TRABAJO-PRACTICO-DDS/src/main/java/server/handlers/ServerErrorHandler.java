package server.handlers;

import io.javalin.Javalin;

public class ServerErrorHandler implements IHandler{
    @Override
    public void setHandle(Javalin app) {
        app.after("/*", ctx -> {
            if(ctx.statusCode() == 500) {
                ctx.render("errorHandlers/500.hbs");
            }
        });
    }
}
