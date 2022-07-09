package de.gedoplan.showcase.extension.smartrepo.deployment;

import de.gedoplan.showcase.extension.smartrepo.SmartRepoExtensionServlet;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

import io.quarkus.undertow.deployment.ServletBuildItem;

class QuarkusExtensionProcessor {

    private static final String FEATURE = "showcase-smartrepo";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    ServletBuildItem createServlet() {
      return ServletBuildItem.builder("smartrepo-servlet", SmartRepoExtensionServlet.class.getName())
        .addMapping("/showcase/smartrepo")
        .build();
    }
}
