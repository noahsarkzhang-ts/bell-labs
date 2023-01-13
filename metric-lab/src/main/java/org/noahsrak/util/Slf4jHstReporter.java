package org.noahsrak.util;

import com.codahale.metrics.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: noahsark
 * @version:
 * @date: 2021/5/27
 */
public class Slf4jHstReporter extends ScheduledReporter {

    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private final Slf4jHstReporter.LoggerProxy loggerProxy;
    private final Marker marker;
    private final String prefix;
    private final Map<String, Object> lables;

    public static Slf4jHstReporter.Builder forRegistry(MetricRegistry registry) {
        return new Slf4jHstReporter.Builder(registry);
    }

    private Slf4jHstReporter(MetricRegistry registry, Slf4jHstReporter.LoggerProxy loggerProxy, Marker marker, String prefix, TimeUnit rateUnit, TimeUnit durationUnit, MetricFilter filter, Map<String, Object> lables) {
        super(registry, "logger-reporter", filter, rateUnit, durationUnit);
        this.loggerProxy = loggerProxy;
        this.marker = marker;
        this.prefix = prefix;
        this.lables = lables;
    }

    public void report(SortedMap<String, Gauge> gauges, SortedMap<String, Counter> counters, SortedMap<String, Histogram> histograms, SortedMap<String, Meter> meters, SortedMap<String, Timer> timers) {
        if (this.loggerProxy.isEnabled(this.marker)) {
            Iterator var6 = gauges.entrySet().iterator();

            Map.Entry entry;
            while (var6.hasNext()) {
                entry = (Map.Entry) var6.next();
                this.logGauge((String) entry.getKey(), (Gauge) entry.getValue());
            }

            var6 = counters.entrySet().iterator();

            while (var6.hasNext()) {
                entry = (Map.Entry) var6.next();
                this.logCounter((String) entry.getKey(), (Counter) entry.getValue());
            }

            var6 = histograms.entrySet().iterator();

            while (var6.hasNext()) {
                entry = (Map.Entry) var6.next();
                this.logHistogram((String) entry.getKey(), (Histogram) entry.getValue());
            }

            var6 = meters.entrySet().iterator();

            while (var6.hasNext()) {
                entry = (Map.Entry) var6.next();
                this.logMeter((String) entry.getKey(), (Meter) entry.getValue());
            }

            var6 = timers.entrySet().iterator();

            while (var6.hasNext()) {
                entry = (Map.Entry) var6.next();
                this.logTimer((String) entry.getKey(), (Timer) entry.getValue());
            }
        }

    }

    private void logTimer(String name, Timer timer) {
        Snapshot snapshot = timer.getSnapshot();
        Map<String, Object> mTime = new HashMap<>();
        mTime.put("name", name);
        mTime.put("timestamp", System.currentTimeMillis());

        Map<String, Object> labels = new HashMap<>();
        if (this.lables != null) {
            mTime.put("labels", labels);
        }

        Map<String, Object> fields = new HashMap<>();
        fields.put("count", timer.getCount());
        fields.put("min", this.rounding2(this.convertDuration((double) snapshot.getMin())));
        fields.put("max", this.rounding2(this.convertDuration((double) snapshot.getMax())));
        fields.put("mean", this.rounding2(this.convertDuration(snapshot.getMean())));
        fields.put("median", this.rounding2(this.convertDuration(snapshot.getMedian())));
        fields.put("p75", this.rounding2(this.convertDuration(snapshot.get75thPercentile())));
        fields.put("p95", this.rounding2(this.convertDuration(snapshot.get95thPercentile())));
        fields.put("p99", this.rounding2(this.convertDuration(snapshot.get99thPercentile())));
        fields.put("mean_rate", this.rounding2(this.convertRate(timer.getMeanRate())));
        fields.put("m5", this.rounding2(this.convertRate(timer.getFiveMinuteRate())));
        mTime.put("fields", fields);

        this.loggerProxy.log(this.marker, GSON.toJson(mTime));

        this.loggerProxy.log(this.marker, "type=TIMER, name={}, count={}, min={}, max={}, mean={}, stddev={}, median={}, p75={}, p95={}, p98={}, p99={}, p999={}, mean_rate={}, m1={}, m5={}, m15={}, rate_unit={}, duration_unit={}", this.prefix(name), timer.getCount(), this.convertDuration((double) snapshot.getMin()), this.convertDuration((double) snapshot.getMax()), this.convertDuration(snapshot.getMean()), this.convertDuration(snapshot.getStdDev()), this.convertDuration(snapshot.getMedian()), this.convertDuration(snapshot.get75thPercentile()), this.convertDuration(snapshot.get95thPercentile()), this.convertDuration(snapshot.get98thPercentile()), this.convertDuration(snapshot.get99thPercentile()), this.convertDuration(snapshot.get999thPercentile()), this.convertRate(timer.getMeanRate()), this.convertRate(timer.getOneMinuteRate()), this.convertRate(timer.getFiveMinuteRate()), this.convertRate(timer.getFifteenMinuteRate()), this.getRateUnit(), this.getDurationUnit());
    }

    private void logMeter(String name, Meter meter) {

        this.loggerProxy.log(this.marker, "type=METER, name={}, count={}, mean_rate={}, m1={}, m5={}, m15={}, rate_unit={}", this.prefix(name), meter.getCount(), this.convertRate(meter.getMeanRate()), this.convertRate(meter.getOneMinuteRate()), this.convertRate(meter.getFiveMinuteRate()), this.convertRate(meter.getFifteenMinuteRate()), this.getRateUnit());
    }

    private void logHistogram(String name, Histogram histogram) {
        Snapshot snapshot = histogram.getSnapshot();
        this.loggerProxy.log(this.marker, "type=HISTOGRAM, name={}, count={}, min={}, max={}, mean={}, stddev={}, median={}, p75={}, p95={}, p98={}, p99={}, p999={}", this.prefix(name), histogram.getCount(), snapshot.getMin(), snapshot.getMax(), snapshot.getMean(), snapshot.getStdDev(), snapshot.getMedian(), snapshot.get75thPercentile(), snapshot.get95thPercentile(), snapshot.get98thPercentile(), snapshot.get99thPercentile(), snapshot.get999thPercentile());
    }

    private void logCounter(String name, Counter counter) {
        this.loggerProxy.log(this.marker, "type=COUNTER, name={}, count={}", this.prefix(name), counter.getCount());
    }

    private void logGauge(String name, Gauge gauge) {
        this.loggerProxy.log(this.marker, "type=GAUGE, name={}, value={}", this.prefix(name), gauge.getValue());
    }

    protected String getRateUnit() {
        return "events/" + super.getRateUnit();
    }

    private String prefix(String... components) {
        return MetricRegistry.name(this.prefix, components);
    }

    private double rounding2(double value) {
        BigDecimal bd = new BigDecimal(value);
        double result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    private static class ErrorLoggerProxy extends LoggerProxy {
        public ErrorLoggerProxy(Logger logger) {
            super(logger);
        }

        public void log(Marker marker, String format, Object... arguments) {
            this.logger.error(marker, format, arguments);
        }

        public boolean isEnabled(Marker marker) {
            return this.logger.isErrorEnabled(marker);
        }
    }

    private static class WarnLoggerProxy extends LoggerProxy {
        public WarnLoggerProxy(Logger logger) {
            super(logger);
        }

        public void log(Marker marker, String format, Object... arguments) {
            this.logger.warn(marker, format, arguments);
        }

        public boolean isEnabled(Marker marker) {
            return this.logger.isWarnEnabled(marker);
        }
    }

    private static class InfoLoggerProxy extends LoggerProxy {
        public InfoLoggerProxy(Logger logger) {
            super(logger);
        }

        public void log(Marker marker, String format, Object... arguments) {
            this.logger.info(marker, format, arguments);
        }

        public boolean isEnabled(Marker marker) {
            return this.logger.isInfoEnabled(marker);
        }
    }

    private static class TraceLoggerProxy extends LoggerProxy {
        public TraceLoggerProxy(Logger logger) {
            super(logger);
        }

        public void log(Marker marker, String format, Object... arguments) {
            this.logger.trace(marker, format, arguments);
        }

        public boolean isEnabled(Marker marker) {
            return this.logger.isTraceEnabled(marker);
        }
    }

    private static class DebugLoggerProxy extends LoggerProxy {
        public DebugLoggerProxy(Logger logger) {
            super(logger);
        }

        public void log(Marker marker, String format, Object... arguments) {
            this.logger.debug(marker, format, arguments);
        }

        public boolean isEnabled(Marker marker) {
            return this.logger.isDebugEnabled(marker);
        }
    }

    abstract static class LoggerProxy {
        protected final Logger logger;

        public LoggerProxy(Logger logger) {
            this.logger = logger;
        }

        abstract void log(Marker var1, String var2, Object... var3);

        abstract boolean isEnabled(Marker var1);
    }

    public static class Builder {
        private final MetricRegistry registry;
        private Logger logger;
        private LoggingLevel loggingLevel;
        private Marker marker;
        private String prefix;
        private TimeUnit rateUnit;
        private TimeUnit durationUnit;
        private MetricFilter filter;
        private Map<String, Object> lables;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.logger = LoggerFactory.getLogger("metrics");
            this.marker = null;
            this.prefix = "";
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.filter = MetricFilter.ALL;
            this.loggingLevel = LoggingLevel.INFO;
        }

        public Slf4jHstReporter.Builder outputTo(Logger logger) {
            this.logger = logger;
            return this;
        }

        public Slf4jHstReporter.Builder markWith(Marker marker) {
            this.marker = marker;
            return this;
        }

        public Slf4jHstReporter.Builder prefixedWith(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Slf4jHstReporter.Builder convertRatesTo(TimeUnit rateUnit) {
            this.rateUnit = rateUnit;
            return this;
        }

        public Slf4jHstReporter.Builder convertDurationsTo(TimeUnit durationUnit) {
            this.durationUnit = durationUnit;
            return this;
        }

        public Slf4jHstReporter.Builder filter(MetricFilter filter) {
            this.filter = filter;
            return this;
        }

        public Slf4jHstReporter.Builder withLoggingLevel(LoggingLevel loggingLevel) {
            this.loggingLevel = loggingLevel;
            return this;
        }

        public Slf4jHstReporter.Builder labels(Map<String, Object> lables) {
            this.lables = lables;
            return this;
        }

        public Slf4jHstReporter build() {
            Object loggerProxy;
            switch (this.loggingLevel) {
                case TRACE:
                    loggerProxy = new Slf4jHstReporter.TraceLoggerProxy(this.logger);
                    break;
                case INFO:
                    loggerProxy = new Slf4jHstReporter.InfoLoggerProxy(this.logger);
                    break;
                case WARN:
                    loggerProxy = new Slf4jHstReporter.WarnLoggerProxy(this.logger);
                    break;
                case ERROR:
                    loggerProxy = new Slf4jHstReporter.ErrorLoggerProxy(this.logger);
                    break;
                case DEBUG:
                default:
                    loggerProxy = new Slf4jHstReporter.DebugLoggerProxy(this.logger);
            }

            return new Slf4jHstReporter(this.registry, (Slf4jHstReporter.LoggerProxy) loggerProxy, this.marker,
                    this.prefix, this.rateUnit, this.durationUnit, this.filter, this.lables);
        }
    }

    public static enum LoggingLevel {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR;

        private LoggingLevel() {
        }
    }
}

