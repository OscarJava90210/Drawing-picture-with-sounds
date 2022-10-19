package org.example;

import javax.inject.Inject;

import com.google.inject.Guice;
import lombok.extern.slf4j.Slf4j;
import org.example.modules.AudioModule;
import org.example.services.ByteFetcher;
import org.example.services.ImageDrawService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class App {

    @Inject
    private ByteFetcher byteFetcher;
    @Inject
    private ImageDrawService imageDrawService;

    public static void main(String[] args) {

        App app = Guice.createInjector(new AudioModule()).getInstance(App.class);
        Runtime.getRuntime().addShutdownHook(new Thread(app::stop));
        app.start();
    }

    private void start() {
        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in));) {
            List<String> commands = new ArrayList<>();
            String command;
            while ((command = console.readLine()) != null) {
                commands.add(0, command);
                if (byteFetcher.inProgress()) {

                    String target = commands.get(1);
                    byteFetcher.stop();
                    byte[] bytes  = byteFetcher.getBytes();
                    int w = 240;
                    int  h =240;
                    log.info("Draw started");
                    imageDrawService.draw(target , w,h,bytes);
                    log.info("Draw Finished");
                    commands = new ArrayList<>();
                } else {
                    byteFetcher.start();
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private void stop() {
        imageDrawService.clear();
    }
}
