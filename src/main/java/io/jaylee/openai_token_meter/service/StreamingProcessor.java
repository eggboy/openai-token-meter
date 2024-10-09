package io.jaylee.openai_token_meter.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.StringReader;

@Slf4j
@Service
@RequiredArgsConstructor
public class StreamingProcessor {

    private final JsonFactory jsonFactory;

    public void process(String payload) {
        try {
            StringBuilder contentString = new StringBuilder();

            JsonParser jsonParser = jsonFactory.createParser(new StringReader(payload));
            while (!jsonParser.isClosed()) {
                JsonToken jsonToken = jsonParser.nextToken();

                if (JsonToken.FIELD_NAME.equals(jsonToken) && "content".equals(jsonParser.currentName())) {
                    jsonParser.nextToken(); // move to value
                    String content = jsonParser.getValueAsString();
                    contentString.append(content);
                }
            }
        } catch (Exception ex) {
            log.error("Parsing JSON Exception : {}", ex.getMessage());
        }
    }

}
