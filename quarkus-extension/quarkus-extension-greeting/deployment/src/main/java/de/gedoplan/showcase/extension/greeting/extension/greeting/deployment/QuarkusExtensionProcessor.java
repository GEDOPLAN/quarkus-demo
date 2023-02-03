package de.gedoplan.showcase.extension.greeting.extension.greeting.deployment;

import de.gedoplan.showcase.extension.greeting.extension.greeting.GreetingExtensionServlet;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.undertow.deployment.ServletBuildItem;

class QuarkusExtensionProcessor {

    private static final String FEATURE = "showcase-greeting";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    ServletBuildItem createServlet() {
      return ServletBuildItem.builder("greeting-servlet", GreetingExtensionServlet.class.getName())
        .addMapping("/showcase/greeting")
        .build();
    }
}
