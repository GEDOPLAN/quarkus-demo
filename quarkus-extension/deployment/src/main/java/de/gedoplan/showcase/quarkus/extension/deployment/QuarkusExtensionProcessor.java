package de.gedoplan.showcase.quarkus.extension.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

import de.gedoplan.showcase.greeting.extension.GreetingExtensionServlet;
import io.quarkus.undertow.deployment.ServletBuildItem;

class QuarkusExtensionProcessor {

    private static final String FEATURE = "quarkus-extension";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    ServletBuildItem createServlet() {
      return ServletBuildItem.builder("quarkus-extension", GreetingExtensionServlet.class.getName())
        .addMapping("/greeting")
        .build();
    }
}
