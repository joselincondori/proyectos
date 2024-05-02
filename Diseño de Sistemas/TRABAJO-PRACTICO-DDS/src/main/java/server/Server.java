package server;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import io.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import server.handlers.AppHandlers;
import server.middlewares.AuthMiddleware;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class Server {
    private static Javalin app = null;

    public static Javalin app() {
        if(app == null)
            throw new RuntimeException("App no inicializada");
        return app;
    }

    public static void init() {
        if(app == null) {
            configureEntityManagerProperties();
            Integer port = Integer.parseInt(System.getProperty("port", "8080"));
            app = Javalin.create(config()).start(port);
            initTemplateEngine();
            Router.init();
            AppHandlers.applyHandlers(app);
        }
    }

    private static void initTemplateEngine() {
        JavalinRenderer.register(
                (path, model, context) -> { // Funcion que renderiza el template
                    Handlebars handlebars = new Handlebars() ;

                    handlebars.registerHelper("distinto", (Helper<Long>) (num1, options) -> !Objects.equals(num1, (Long) options.get("num2")));

                    Template template = null;
                    try {
                        template = handlebars.compile(
                                "templates/" + path.replace(".hbs", ""));
                        return template.apply(model);
                    } catch (IOException e) {
                        e. printStackTrace();
                        context.status(HttpStatus.NOT_FOUND);
                        return "No se encuentra la pagina indicada...";
                    }
                }, ".hbs"
        );
    }

    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
            });
            AuthMiddleware.apply(config);
        };
    }

    public static void configureEntityManagerProperties() {
        // https://stackoverflow.com/questions/8836834/read-environment-variables-in-persistence-xml-file
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();

        String[] keys = new String[] {
                "hibernate.archive.autodetection",
                "hibernate.connection.driver_class",
                "hibernate.connection.url",
                "hibernate.connection.username",
                "hibernate.connection.password",
                "hibernate.dialect",
                "hibernate.show_sql",
                "hibernate.format_sql",
                "use_sql_comments",
                "hibernate.hbm2ddl.auto"};

        for (String key : keys) {
            if (env.containsKey(key)) {
                String value = env.get(key);
                System.out.println(key + ": " + value);
                configOverrides.put(key, value);
            }
        }
        Consumer<PerThreadEntityManagerProperties> propertiesConsumer = perThreadEntityManagerProperties -> {
            perThreadEntityManagerProperties.putAll(configOverrides);
        };

        WithSimplePersistenceUnit.configure(propertiesConsumer);
    }

}
