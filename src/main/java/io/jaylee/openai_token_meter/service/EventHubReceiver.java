package io.jaylee.openai_token_meter.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.io.StringReader;
import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class EventHubReceiver {

    private final NonStreamingProcessor nonStreamingProcessor;
    private final StreamingProcessor streamingProcessor;
    private final JsonFactory jsonFactory;

    @Bean
    Consumer<Message<String>> consume() {
        return message -> {
            String payload = message.getPayload();

            String streamMode = "false";

            try (JsonParser mainParser = jsonFactory.createParser(new StringReader(payload))) {
                String requestBody = extractField(mainParser, "RequestBody");

                if (requestBody != null) {
                    try (JsonParser requestBodyParser = jsonFactory.createParser(new StringReader(requestBody))) {
                        streamMode = extractField(requestBodyParser, "stream");
                        log.debug("Stream value: " + streamMode);
                    }
                }

                if ("true".equals(streamMode)) {
                    streamingProcessor.process(payload);
                } else {
                    nonStreamingProcessor.process(payload);
                }
            } catch (JsonParseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private String extractField(JsonParser parser, String fieldName) throws IOException {
        while (!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();
            if (JsonToken.FIELD_NAME.equals(jsonToken) && fieldName.equals(parser.currentName())) {
                parser.nextToken(); // move to value
                return parser.getValueAsString();
            }
        }
        return null;
    }
}