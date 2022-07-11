package de.gedoplan.showcase.extension.smartrepo.deployment;

import de.gedoplan.showcase.extension.smartrepo.SmartRepo;
import de.gedoplan.showcase.extension.smartrepo.deployment.generate.RepositoryCreator;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.GeneratedBeanBuildItem;
import io.quarkus.arc.deployment.GeneratedBeanGizmoAdaptor;
import io.quarkus.deployment.GeneratedClassGizmoAdaptor;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.AdditionalIndexedClassesBuildItem;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.GeneratedClassBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.gizmo.ClassOutput;
import io.quarkus.hibernate.orm.panache.deployment.JavaJpaTypeBundle;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;
import org.jboss.logging.Logger;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SmartRepoProcessor {

  private static final Logger LOGGER = Logger.getLogger(SmartRepoProcessor.class.getName());

  @BuildStep
  FeatureBuildItem registerFeature() {
    return new FeatureBuildItem("showcase-smartrepo");
  }

  /**
   * Add SmartRepo interface to class index.
   * <p>
   * This is required because we need to pull the generic types for entity and id from it.
   *
   * @param additionalIndexedClasses Producer for indexed classes
   */
  @BuildStep
  void contributeClassesToIndex(BuildProducer<AdditionalIndexedClassesBuildItem> additionalIndexedClasses) {
    additionalIndexedClasses.produce(new AdditionalIndexedClassesBuildItem(SmartRepo.class.getName()));
  }

  @BuildStep
  void build(CombinedIndexBuildItem index,
    BuildProducer<GeneratedClassBuildItem> generatedClasses,
    BuildProducer<GeneratedBeanBuildItem> generatedBeans,
    BuildProducer<AdditionalBeanBuildItem> additionalBeans,
    BuildProducer<ReflectiveClassBuildItem> reflectiveClasses) {

    IndexView indexView = index.getIndex();

    List<ClassInfo> interfacesExtendingRepository = getAllInterfacesExtending(DotNames.SUPPORTED_REPOSITORIES, indexView);

    implementCrudRepositories(generatedBeans, generatedClasses, additionalBeans, reflectiveClasses, interfacesExtendingRepository, indexView);
  }

  private List<ClassInfo> getAllInterfacesExtending(Collection<DotName> targets, IndexView index) {
    List<ClassInfo> result = new ArrayList<>();
    Collection<ClassInfo> knownClasses = index.getKnownClasses();
    for (ClassInfo clazz : knownClasses) {
      if (!Modifier.isInterface(clazz.flags())) {
        continue;
      }
      List<DotName> interfaceNames = clazz.interfaceNames();
      boolean found = false;
      for (DotName interfaceName : interfaceNames) {
        if (targets.contains(interfaceName)) {
          found = true;
          break;
        }
      }
      if (found) {
        result.add(clazz);
      }
    }
    return result;
  }

  // generate a concrete class that will be used by Arc to resolve injection points
  private void implementCrudRepositories(BuildProducer<GeneratedBeanBuildItem> generatedBeans,
    BuildProducer<GeneratedClassBuildItem> generatedClasses,
    BuildProducer<AdditionalBeanBuildItem> additionalBeans,
    BuildProducer<ReflectiveClassBuildItem> reflectiveClasses,
    List<ClassInfo> crudRepositoriesToImplement, IndexView index) {

    ClassOutput beansClassOutput = new GeneratedBeanGizmoAdaptor(generatedBeans);
    ClassOutput otherClassOutput = new GeneratedClassGizmoAdaptor(generatedClasses, true);

    RepositoryCreator repositoryCreator = new RepositoryCreator(beansClassOutput, otherClassOutput,
      index, (n) -> {
      // the implementation of fragments don't need to be beans themselves
      additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(n));
    },
      (className -> {
        // the generated classes that implement interfaces for holding custom query results need
        // to be registered for reflection here since this is the only point where the generated class is known
        reflectiveClasses.produce(new ReflectiveClassBuildItem(true, false, className));
      }), JavaJpaTypeBundle.BUNDLE);

    for (ClassInfo crudRepositoryToImplement : crudRepositoriesToImplement) {
      LOGGER.debugf("Implementing %s", crudRepositoryToImplement);
      repositoryCreator.implementCrudRepository(crudRepositoryToImplement, index);
    }
  }
}
