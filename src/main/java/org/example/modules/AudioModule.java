package org.example.modules;

import com.google.inject.AbstractModule;
import org.example.App;
import org.example.services.AudioBytesFetcher;
import org.example.services.ByteFetcher;
import org.example.services.ImageDrawService;

public class AudioModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(App.class);
        bind(ImageDrawService.class);
        bind(ByteFetcher.class).to(AudioBytesFetcher.class);
    }
}
