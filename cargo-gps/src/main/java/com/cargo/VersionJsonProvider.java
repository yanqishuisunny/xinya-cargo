package com.cargo;

import ch.qos.logback.core.spi.DeferredProcessingAware;
import com.fasterxml.jackson.core.JsonGenerator;
import net.logstash.logback.composite.AbstractJsonProvider;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author 梁建军
 * 创建日期： 2019/7/27
 * 创建时间： 14:44
 * @version 1.0
 * @since 1.0
 */
public class VersionJsonProvider<Event extends DeferredProcessingAware> extends AbstractJsonProvider<Event> {

    private static final String FIELD_VERSION = "app_version";

    private static final String DEFAULT_VERSION = "null";

    private static final String BUILD_INFO = "META-INF/build-info.properties";

    private String version;

    public VersionJsonProvider() {
        Resource location = new ClassPathResource(BUILD_INFO);
        try {
            BuildProperties buildProperties = new BuildProperties(loadFrom(location, "build", StandardCharsets.UTF_8));
            version = buildProperties.getVersion();
        } catch (IOException e) {
            version = DEFAULT_VERSION;
        }

    }

    @Override
    public void writeTo(JsonGenerator generator, Event event) throws IOException {
        generator.writeFieldName(FIELD_VERSION);
        generator.writeString(version);
    }

    protected Properties loadFrom(Resource location, String prefix, Charset encoding) throws IOException {
        prefix = prefix.endsWith(".") ? prefix : prefix + ".";
        Properties source = loadSource(location, encoding);
        Properties target = new Properties();
        for (String key : source.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                target.put(key.substring(prefix.length()), source.get(key));
            }
        }
        return target;
    }

    private Properties loadSource(Resource location, Charset encoding) throws IOException {
        if (encoding != null) {
            return PropertiesLoaderUtils.loadProperties(new EncodedResource(location, encoding));
        }
        return PropertiesLoaderUtils.loadProperties(location);
    }
}
