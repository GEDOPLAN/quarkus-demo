package de.gedoplan.showcase.extension.smartrepo.deployment.generate;

import de.gedoplan.showcase.extension.smartrepo.deployment.DotNames;
import io.quarkus.gizmo.MethodDescriptor;
import org.jboss.jandex.*;

import java.util.*;

import static de.gedoplan.showcase.extension.smartrepo.deployment.DotNames.JPA_NAMED_QUERIES;
import static de.gedoplan.showcase.extension.smartrepo.deployment.DotNames.JPA_NAMED_QUERY;

public final class GenerationUtil {

  private GenerationUtil() {
  }

  static List<DotName> extendedSmartRepos(ClassInfo repositoryToImplement, IndexView index) {
    List<DotName> result = new ArrayList<>();
    for (DotName interfaceName : repositoryToImplement.interfaceNames()) {
      if (DotNames.SUPPORTED_REPOSITORIES.contains(interfaceName)) {
        result.add(interfaceName);
      } else {
        ClassInfo intermediateInterfaces = index.getClassByName(interfaceName);
        List<DotName> dns = intermediateInterfaces.interfaceNames();
        for (DotName in : dns) {
          result.addAll(extendedSmartRepos(intermediateInterfaces, index));
        }
      }
    }
    return result;
  }

  static boolean isIntermediateRepository(DotName interfaceName, IndexView indexView) {
    if (!DotNames.SUPPORTED_REPOSITORIES.contains(interfaceName)) {
      ClassInfo intermediateInterface = indexView.getClassByName(interfaceName);
      List<DotName> extendedSmartRepos = extendedSmartRepos(intermediateInterface, indexView);
      return DotNames.SUPPORTED_REPOSITORIES.stream().anyMatch(item -> extendedSmartRepos.contains(item));
    }
    return false;
  }

  static Set<MethodInfo> interfaceMethods(Collection<DotName> interfaces, IndexView index) {
    Set<MethodInfo> result = new HashSet<>();
    for (DotName dotName : interfaces) {
      ClassInfo classInfo = index.getClassByName(dotName);
      result.addAll(classInfo.methods());
      List<DotName> extendedInterfaces = classInfo.interfaceNames();
      if (!extendedInterfaces.isEmpty()) {
        result.addAll(interfaceMethods(extendedInterfaces, index));
      }
    }
    return result;
  }

  // Used in case where we can't simply use MethodDescriptor.of(MethodInfo)
  // because that used the class of the method
  static MethodDescriptor toMethodDescriptor(String generatedClassName, MethodInfo methodInfo) {
    final List<String> parameterTypesStr = new ArrayList<>();
    for (Type parameter : methodInfo.parameters()) {
      parameterTypesStr.add(parameter.name().toString());
    }
    return MethodDescriptor.ofMethod(generatedClassName, methodInfo.name(), methodInfo.returnType().name().toString(),
      parameterTypesStr.toArray(new String[0]));
  }

  static AnnotationInstance getNamedQueryForMethod(MethodInfo methodInfo, ClassInfo entityClassInfo) {
    // try @NamedQuery
    AnnotationInstance namedQueryAnnotation = getNamedQueryAnnotationForMethod(methodInfo, entityClassInfo);
    if (namedQueryAnnotation != null) {
      return namedQueryAnnotation;
    }

    // try @NamedQueries
    return getNamedQueriesAnnotationForMethod(methodInfo, entityClassInfo);
  }

  private static AnnotationInstance getNamedQueryAnnotationForMethod(MethodInfo methodInfo, ClassInfo entityClassInfo) {
    String methodName = methodInfo.name();
    AnnotationInstance namedQueryAnnotation = entityClassInfo.classAnnotation(JPA_NAMED_QUERY);
    if (namedQueryAnnotation != null && isMethodDeclaredInNamedQuery(entityClassInfo, methodName, namedQueryAnnotation)) {
      return namedQueryAnnotation;
    }

    return null;
  }

  private static AnnotationInstance getNamedQueriesAnnotationForMethod(MethodInfo methodInfo, ClassInfo entityClassInfo) {
    String methodName = methodInfo.name();
    AnnotationInstance namedQueriesAnnotation = entityClassInfo.classAnnotation(JPA_NAMED_QUERIES);
    if (namedQueriesAnnotation != null) {
      for (AnnotationValue annotationInstanceValues : namedQueriesAnnotation.values()) {
        for (AnnotationInstance annotationInstance : annotationInstanceValues.asNestedArray()) {
          if (isMethodDeclaredInNamedQuery(entityClassInfo, methodName, annotationInstance)) {
            return annotationInstance;
          }
        }
      }
    }

    return null;
  }

  private static boolean isMethodDeclaredInNamedQuery(ClassInfo entityClassInfo, String methodName,
    AnnotationInstance namedQuery) {
    AnnotationValue namedQueryName = namedQuery.value("name");
    if (namedQueryName == null) {
      return false;
    }

    return String.format("%s.%s", entityClassInfo.name().withoutPackagePrefix(), methodName).equals(namedQueryName.value());
  }

}
