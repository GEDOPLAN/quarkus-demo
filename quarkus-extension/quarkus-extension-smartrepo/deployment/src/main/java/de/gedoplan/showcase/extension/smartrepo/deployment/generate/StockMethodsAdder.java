package de.gedoplan.showcase.extension.smartrepo.deployment.generate;

import de.gedoplan.showcase.extension.smartrepo.FunctionalityNotImplemented;
import de.gedoplan.showcase.extension.smartrepo.deployment.DotNames;
import io.quarkus.gizmo.ClassCreator;
import io.quarkus.gizmo.FieldDescriptor;
import io.quarkus.gizmo.MethodCreator;
import io.quarkus.gizmo.MethodDescriptor;
import io.quarkus.gizmo.ResultHandle;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.common.runtime.AbstractJpaOperations;
import io.quarkus.panache.common.deployment.TypeBundle;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationTarget;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.ClassType;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;
import org.jboss.jandex.MethodInfo;
import org.jboss.jandex.ParameterizedType;
import org.jboss.jandex.Type;
import org.jboss.jandex.TypeVariable;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.quarkus.gizmo.FieldDescriptor.of;
import static io.quarkus.gizmo.MethodDescriptor.ofMethod;

public class StockMethodsAdder {

  private final IndexView index;
  private final FieldDescriptor operationsField;

  public StockMethodsAdder(IndexView index, TypeBundle typeBundle) {
    this.index = index;
    String operationsName = typeBundle.operations().dotName().toString();
    operationsField = of(operationsName, "INSTANCE", operationsName);
  }

  public void add(ClassCreator classCreator, FieldDescriptor entityClassFieldDescriptor,
    String generatedClassName, ClassInfo repositoryToImplement, DotName entityDotName, String idTypeStr) {

    Set<MethodInfo> methodsOfExtendedSpringDataRepositories = methodsOfExtendedSpringDataRepositories(repositoryToImplement);
    Set<MethodInfo> stockMethodsAddedToInterface = stockMethodsAddedToInterface(repositoryToImplement);
    Set<MethodInfo> allMethodsToBeImplemented = new HashSet<>(methodsOfExtendedSpringDataRepositories);
    allMethodsToBeImplemented.addAll(stockMethodsAddedToInterface);

    Map<MethodDescriptor, Boolean> allMethodsToBeImplementedToResult = new HashMap<>();
    for (MethodInfo methodInfo : allMethodsToBeImplemented) {
      allMethodsToBeImplementedToResult.put(GenerationUtil.toMethodDescriptor(generatedClassName, methodInfo), false);
    }

    String entityTypeStr = entityDotName.toString();

    generatePersist(classCreator, generatedClassName, entityDotName, entityTypeStr, allMethodsToBeImplementedToResult);
    generateFindById(classCreator, entityClassFieldDescriptor, generatedClassName, entityTypeStr, idTypeStr, allMethodsToBeImplementedToResult);
    generateFindAll(classCreator, entityClassFieldDescriptor, generatedClassName, entityTypeStr, allMethodsToBeImplementedToResult);

    handleUnimplementedMethods(classCreator, allMethodsToBeImplementedToResult);
  }

  private void generatePersist(ClassCreator classCreator, String generatedClassName, DotName entityDotName, String entityTypeStr, Map<MethodDescriptor, Boolean> allMethodsToBeImplementedToResult) {

    MethodDescriptor persistDescriptor = MethodDescriptor.ofMethod(generatedClassName, "persist", void.class, entityTypeStr);
    MethodDescriptor bridgePersistDescriptor = MethodDescriptor.ofMethod(generatedClassName, "persist", void.class, Object.class);

    if (allMethodsToBeImplementedToResult.containsKey(persistDescriptor)
      || allMethodsToBeImplementedToResult.containsKey(bridgePersistDescriptor)) {

      if (!classCreator.getExistingMethods().contains(persistDescriptor)) {
        try (MethodCreator persist = classCreator.getMethodCreator(persistDescriptor)) {
          persist.addAnnotation(Transactional.class);

          ResultHandle entity = persist.getMethodParam(0);

          persist.invokeVirtualMethod(
            MethodDescriptor.ofMethod(AbstractJpaOperations.class, "persist", void.class, Object.class),
            persist.readStaticField(operationsField),
            entity);
          persist.returnValue(null);
        }
        try (MethodCreator bridgePersist = classCreator.getMethodCreator(bridgePersistDescriptor)) {
          MethodDescriptor persist = MethodDescriptor.ofMethod(generatedClassName, "persist", void.class, entityTypeStr);
          ResultHandle methodParam = bridgePersist.getMethodParam(0);
          ResultHandle castedMethodParam = bridgePersist.checkCast(methodParam, entityTypeStr);
          ResultHandle result = bridgePersist.invokeVirtualMethod(persist, bridgePersist.getThis(), castedMethodParam);
          bridgePersist.returnValue(result);
        }
      }

      allMethodsToBeImplementedToResult.put(persistDescriptor, true);
      allMethodsToBeImplementedToResult.put(bridgePersistDescriptor, true);
    }
  }

  private void generateFindById(ClassCreator classCreator, FieldDescriptor entityClassFieldDescriptor,
    String generatedClassName, String entityTypeStr, String idTypeStr,
    Map<MethodDescriptor, Boolean> allMethodsToBeImplementedToResult) {

    MethodDescriptor findByIdDescriptor = MethodDescriptor.ofMethod(generatedClassName, "findById",
      Optional.class.getName(), idTypeStr);
    MethodDescriptor bridgeFindByIdDescriptor = MethodDescriptor.ofMethod(generatedClassName, "findById",
      Optional.class.getName(), Object.class);

    if (allMethodsToBeImplementedToResult.containsKey(findByIdDescriptor)
      || allMethodsToBeImplementedToResult.containsKey(bridgeFindByIdDescriptor)) {

      if (!classCreator.getExistingMethods().contains(findByIdDescriptor)) {
        try (MethodCreator findById = classCreator.getMethodCreator(findByIdDescriptor)) {
          findById.setSignature(String.format("(L%s;)Ljava/util/Optional<L%s;>;",
            idTypeStr.replace('.', '/'), entityTypeStr.replace('.', '/')));
          ResultHandle id = findById.getMethodParam(0);
          ResultHandle entity = findById.invokeVirtualMethod(
            MethodDescriptor.ofMethod(AbstractJpaOperations.class, "findById", Object.class, Class.class,
              Object.class),
            findById.readStaticField(operationsField),
            findById.readInstanceField(entityClassFieldDescriptor, findById.getThis()), id);
          ResultHandle optional = findById.invokeStaticMethod(
            MethodDescriptor.ofMethod(Optional.class, "ofNullable", Optional.class, Object.class),
            entity);
          findById.returnValue(optional);
        }
        try (MethodCreator bridgeFindById = classCreator.getMethodCreator(bridgeFindByIdDescriptor)) {
          MethodDescriptor findById = MethodDescriptor.ofMethod(generatedClassName, "findById",
            Optional.class.getName(),
            idTypeStr);
          ResultHandle methodParam = bridgeFindById.getMethodParam(0);
          ResultHandle castedMethodParam = bridgeFindById.checkCast(methodParam, idTypeStr);
          ResultHandle result = bridgeFindById.invokeVirtualMethod(findById, bridgeFindById.getThis(),
            castedMethodParam);
          bridgeFindById.returnValue(result);
        }
      }

      allMethodsToBeImplementedToResult.put(findByIdDescriptor, true);
      allMethodsToBeImplementedToResult.put(bridgeFindByIdDescriptor, true);
    }
  }

  private void generateFindAll(ClassCreator classCreator, FieldDescriptor entityClassFieldDescriptor,
    String generatedClassName, String entityTypeStr, Map<MethodDescriptor, Boolean> allMethodsToBeImplementedToResult) {

    MethodDescriptor findAllDescriptor = MethodDescriptor.ofMethod(generatedClassName, "findAll", List.class);
    MethodDescriptor bridgeFindAllDescriptor = MethodDescriptor.ofMethod(generatedClassName, "findAll", Iterable.class);

    if (allMethodsToBeImplementedToResult.containsKey(findAllDescriptor)
      || allMethodsToBeImplementedToResult.containsKey(bridgeFindAllDescriptor)) {

      if (!classCreator.getExistingMethods().contains(findAllDescriptor)) {
        try (MethodCreator findAll = classCreator.getMethodCreator(findAllDescriptor)) {
          findAll.setSignature(String.format("()Ljava/util/List<L%s;>;",
            entityTypeStr.replace('.', '/')));
          ResultHandle panacheQuery = findAll.invokeVirtualMethod(
            ofMethod(AbstractJpaOperations.class, "findAll", Object.class, Class.class),
            findAll.readStaticField(operationsField),
            findAll.readInstanceField(entityClassFieldDescriptor, findAll.getThis()));
          ResultHandle list = findAll.invokeInterfaceMethod(
            ofMethod(PanacheQuery.class, "list", List.class),
            panacheQuery);
          findAll.returnValue(list);
        }
        try (MethodCreator bridgeFindAll = classCreator.getMethodCreator(bridgeFindAllDescriptor)) {
          MethodDescriptor findAll = MethodDescriptor.ofMethod(generatedClassName, "findAll", List.class.getName());
          ResultHandle result = bridgeFindAll.invokeVirtualMethod(findAll, bridgeFindAll.getThis());
          bridgeFindAll.returnValue(result);
        }
      }

      allMethodsToBeImplementedToResult.put(findAllDescriptor, true);
      allMethodsToBeImplementedToResult.put(bridgeFindAllDescriptor, true);
    }
  }

  private void handleUnimplementedMethods(ClassCreator classCreator,
    Map<MethodDescriptor, Boolean> allMethodsToBeImplementedToResult) {
    for (Map.Entry<MethodDescriptor, Boolean> entry : allMethodsToBeImplementedToResult.entrySet()) {
      if (entry.getValue()) { // ignore implemented methods
        continue;
      }

      try (MethodCreator methodCreator = classCreator.getMethodCreator(entry.getKey())) {
        ResultHandle res = methodCreator.newInstance(
          MethodDescriptor.ofConstructor(FunctionalityNotImplemented.class, String.class, String.class),
          methodCreator.load(classCreator.getClassName().replace('/', '.')),
          methodCreator.load(entry.getKey().getName()));
        methodCreator.throwException(res);
      }
    }
  }

  private Set<MethodInfo> methodsOfExtendedSpringDataRepositories(ClassInfo repositoryToImplement) {
    return GenerationUtil.interfaceMethods(GenerationUtil.extendedSmartRepos(repositoryToImplement, index), index);
  }

  // Spring Data allows users to add any of the methods of CrudRepository, PagingAndSortingRepository, JpaRepository
  // to their interface declaration without having to make their repository extend any of those
  // this is done so users have the ability to add only what they need
  private Set<MethodInfo> stockMethodsAddedToInterface(ClassInfo repositoryToImplement) {
    Set<MethodInfo> result = new HashSet<>();

    Set<MethodInfo> allSpringDataRepositoryMethods = allSpringDataRepositoryMethods();
    for (MethodInfo method : repositoryToImplement.methods()) {
      for (MethodInfo springDataRepositoryMethod : allSpringDataRepositoryMethods) {
        if (canMethodsBeConsideredSame(method, springDataRepositoryMethod)) {
          result.add(method);
        }
      }
    }

    return result;
  }

  private Set<MethodInfo> allSpringDataRepositoryMethods() {
    return GenerationUtil.interfaceMethods(new HashSet<>(DotNames.SUPPORTED_REPOSITORIES), index);
  }

  // Used to determine if a method with captured generic types can be considered the same as a target method
  // This is rather naive but works in the constraints of Spring Data
  private boolean canMethodsBeConsideredSame(MethodInfo candidate, MethodInfo target) {
    if (!candidate.name().equals(target.name())) {
      return false;
    }

    if (candidate.parameters().size() != target.parameters().size()) {
      return false;
    }

    if (!canTypesBeConsideredSame(candidate.returnType(), target.returnType())) {
      return false;
    }

    for (int i = 0; i < candidate.parameterTypes().size(); i++) {
      if (!canTypesBeConsideredSame(candidate.parameterTypes().get(i), target.parameterTypes().get(i))) {
        return false;
      }
    }

    return true;
  }

  private boolean canTypesBeConsideredSame(Type candidate, Type target) {
    if (candidate.equals(target)) {
      return true;
    }

    if ((candidate instanceof ParameterizedType) && target instanceof ParameterizedType) {
      return candidate.asParameterizedType().name().equals(target.asParameterizedType().name());
    }

    return (candidate instanceof ClassType) && (target instanceof TypeVariable);
  }

  private AnnotationTarget getIdAnnotationTargetRec(DotName currentDotName, IndexView index, DotName originalEntityDotName) {
    ClassInfo classInfo = index.getClassByName(currentDotName);
    if (classInfo == null) {
      throw new IllegalStateException("Entity " + originalEntityDotName + " was not part of the Quarkus index");
    }

    List<AnnotationInstance> annotationInstances = Stream.of(DotNames.JPA_ID, DotNames.JPA_EMBEDDED_ID)
      .map(classInfo.annotationsMap()::get)
      .filter(Objects::nonNull)
      .flatMap(List::stream)
      .collect(Collectors.toList());
    if (annotationInstances.isEmpty()) {
      if (DotNames.OBJECT.equals(classInfo.superName())) {
        throw new IllegalArgumentException(
          "Currently only Entities with the @Id or @EmbeddedId annotation are supported. Offending class is "
            + originalEntityDotName);
      }
      return getIdAnnotationTargetRec(classInfo.superName(), index, originalEntityDotName);
    }

    if (annotationInstances.size() > 1) {
      throw new IllegalArgumentException(
        "Currently the @Id or @EmbeddedId annotation can only be placed on a single field or method. " +
          "Offending class is " + originalEntityDotName);
    }

    return annotationInstances.get(0).target();
  }

}
