package com.swordglowsblue.artifice.api.resource;

import com.google.gson.JsonElement;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class JsonResource implements ArtificeResource<JsonElement> {
    private final JsonElement root;
    public JsonResource(JsonElement root) { this.root = root; }

    public JsonElement getData() { return this.root; }
    public InputStream toInputStream() {
        return new ReaderInputStream(new StringReader(this.getData().toString()), StandardCharsets.UTF_8);
    }
}
