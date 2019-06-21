package com.swordglowsblue.artifice.api.builder.assets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import com.swordglowsblue.artifice.api.resource.JsonResource;
import com.swordglowsblue.artifice.api.util.Processor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class ModelBuilder extends TypedJsonBuilder<JsonResource<JsonObject>> {
    public ModelBuilder() { super(new JsonObject(), JsonResource::new); }

    public ModelBuilder parent(Identifier id) {
        root.addProperty("parent", id.toString());
        return this;
    }

    public ModelBuilder texture(String name, Identifier path) {
        with("textures", JsonObject::new, textures -> textures.addProperty(name, path.toString()));
        return this;
    }

    public ModelBuilder display(String name, Processor<Display> settings) {
        with("display", JsonObject::new, display ->
            display.add(name, settings.process(new Display()).build()));
        return this;
    }

    public ModelBuilder element(Processor<ModelElementBuilder> settings) {
        with("elements", JsonArray::new, elements -> elements.add(settings.process(new ModelElementBuilder()).build()));
        return this;
    }

    public ModelBuilder ambientocclusion(boolean ambientocclusion) {
        this.root.addProperty("ambientocclusion", ambientocclusion);
        return this;
    }

    public ModelBuilder override(Processor<Override> settings) {
        with("overrides", JsonArray::new, overrides -> overrides.add(settings.process(new Override()).build()));
        return this;
    }

    @Environment(EnvType.CLIENT)
    public static final class Display extends TypedJsonBuilder<JsonObject> {
        private Display() { super(new JsonObject(), j->j); }

        public Display rotation(float x, float y, float z) {
            root.add("rotation", arrayOf(x, y, z));
            return this;
        }

        public Display translation(float x, float y, float z) {
            root.add("translation", arrayOf(x, y, z));
            return this;
        }

        public Display scale(float x, float y, float z) {
            root.add("scale", arrayOf(x, y, z));
            return this;
        }
    }

    @Environment(EnvType.CLIENT)
    public static final class Override extends TypedJsonBuilder<JsonObject> {
        private Override() { super(new JsonObject(), j->j); }

        public Override predicate(String name, int value) {
            with("predicate", JsonObject::new, predicate -> predicate.addProperty(name, value));
            return this;
        }

        public Override model(Identifier id) {
            root.addProperty("model", id.toString());
            return this;
        }
    }
}
