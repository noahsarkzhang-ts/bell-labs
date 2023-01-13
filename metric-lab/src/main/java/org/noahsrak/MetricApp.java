package org.noahsrak;

        import com.codahale.metrics.ConsoleReporter;
        import com.codahale.metrics.Slf4jReporter;
        import org.noahsrak.util.Slf4jHstReporter;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.context.ApplicationContext;

        import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
@SpringBootApplication
public class MetricApp {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(MetricApp.class, args);

        // 启动Reporter
        ConsoleReporter reporter = ctx.getBean(ConsoleReporter.class);
        reporter.start(15, TimeUnit.SECONDS);

        /*Slf4jHstReporter logReporter = ctx.getBean(Slf4jHstReporter.class);
        logReporter.start(15, TimeUnit.SECONDS);*/
    }
}
