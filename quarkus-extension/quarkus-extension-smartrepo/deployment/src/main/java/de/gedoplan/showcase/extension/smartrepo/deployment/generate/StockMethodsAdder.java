package de.gedoplan.showcase.extension.smartrepo.deployment.generate;

import static io.quarkus.gizmo.FieldDescriptor.of;
import static io.quarkus.gizmo.MethodDescriptor.ofMethod;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationTarget;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.ClassType;
import org.jboss.jandex.DotName;
import org.jboss.jandex.FieldInfo;
import org.jboss.jandex.IndexView;
import org.jboss.jandex.MethodInfo;
import org.jboss.jandex.ParameterizedType;
import org.jboss.jandex.PrimitiveType;
import org.jboss.jandex.Type;
import org.jboss.jandex.TypeVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.quarkus.deployment.bean.JavaBeanUtil;
import io.quarkus.gizmo.AssignableResultHandle;
import io.quarkus.gizmo.BranchResult;
import io.quarkus.gizmo.BytecodeCreator;
import io.quarkus.gizmo.ClassCreator;
import io.quarkus.gizmo.FieldDescriptor;
import io.quarkus.gizmo.MethodCreator;
import io.quarkus.gizmo.MethodDescriptor;
import io.quarkus.gizmo.ResultHandle;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.common.runtime.AbstractJpaOperations;
import io.quarkus.hibernate.orm.panache.runtime.AdditionalJpaOperations;
import io.quarkus.panache.common.deployment.TypeBundle;
import de.gedoplan.showcase.extension.smartrepo.deployment.DotNames;
import io.quarkus.spring.data.runtime.FunctionalityNotImplemented;
import io.quarkus.spring.data.runtime.RepositorySupport;
import io.quarkus.spring.data.runtime.TypesConverter;

public class StockMethodsAdder {

    private static Set<MethodInfo> ALL_SPRING_DATA_REPOSITORY_METHODS = null;

    private final IndexView index;
    private final FieldDescriptor operationsField;

    public StockMethodsAdder(IndexView index, TypeBundle typeBundle) {
        this.index = index;
        String operationsName = typeBundle.operations().dotName().toString();
        operationsField = of(operationsName, "INSTANCE", operationsName);
    }

    public void add(ClassCreator classCreator, FieldDescriptor entityClassFieldDescriptor,
            String generatedClassName, ClassInfo repositoryToImplement, DotName entityDotName, String idTypeStr) {

        Set<MethodInfo> methodsOfExtendedSpringDataRepositories = methodsOfExtendedSpringDataRepositories(
                repositoryToImplement);
        Set<MethodInfo> stockMethodsAddedToInterface = stockMethodsAddedToInterface(repositoryToImplement);
        Set<MethodInfo> allMethodsToBeImplemented = new HashSet<>(methodsOfExtendedSpringDataRepositories);
        allMethodsToBeImplemented.addAll(stockMethodsAddedToInterface);

        Map<MethodDescriptor, Boolean> allMethodsToBeImplementedToResult = new HashMap<>();
        for (MethodInfo methodInfo : allMethodsToBeImplemented) {
            allMethodsToBeImplementedToResult.put(GenerationUtil.toMethodDescriptor(generatedClassName, methodInfo), false);
        }

        String entityTypeStr = entityDotName.toString();

        // for all Spring Data repository methods we know how to implement, check if the generated class actually needs the method
        // and if so generate the implementation while also keeping the proper records

        generateSave(classCreator, generatedClassName, entityDotName, entityTypeStr,
                allMethodsToBeImplementedToResult);
        generateFindById(classCreator, entityClassFieldDescriptor, generatedClassName, entityTypeStr, idTypeStr,
                allMethodsToBeImplementedToResult);
        generateFindAll(classCreator, entityClassFieldDescriptor, generatedClassName, entityTypeStr,
                allMethodsToBeImplementedToResult);

        handleUnimplementedMethods(classCreator, allMethodsToBeImplementedToResult);
    }

    private void generateSave(ClassCreator classCreator, String generatedClassName,
            DotName entityDotName, String entityTypeStr,
            Map<MethodDescriptor, Boolean> allMethodsToBeImplementedToResult) {

        MethodDescriptor saveDescriptor = MethodDescriptor.ofMethod(generatedClassName, "save", entityTypeStr,
                entityTypeStr);
        MethodDescriptor bridgeSaveDescriptor = MethodDescriptor.ofMethod(generatedClassName, "save", Object.class,
                Object.class);

        if (allMethodsToBeImplementedToResult.containsKey(saveDescriptor)
                || allMethodsToBeImplementedToResult.containsKey(bridgeSaveDescriptor)) {

            if (!classCreator.getExistingMethods().contains(saveDescriptor)) {
                try (MethodCreator save = classCreator.getMethodCreator(saveDescriptor)) {
                    save.addAnnotation(Transactional.class);

                    ResultHandle entity = save.getMethodParam(0);

                    // if an entity is Persistable, then all we need to do is call isNew to determine if it's new or not
                    if (isPersistable(entityDotName)) {
                        ResultHandle isNew = save.invokeVirtualMethod(
                                ofMethod(entityDotName.toString(), "isNew", boolean.class.toString()),
                                entity);
                        BranchResult isNewBranch = save.ifTrue(isNew);
                        generatePersistAndReturn(entity, isNewBranch.trueBranch());
                        generateMergeAndReturn(entity, isNewBranch.falseBranch());
                    } else {
                        AnnotationTarget idAnnotationTarget = getIdAnnotationTarget(entityDotName, index);
                        ResultHandle idValue = generateObtainValue(save, entityDotName, entity, idAnnotationTarget);
                        Type idType = getTypeOfTarget(idAnnotationTarget);
                        Optional<AnnotationTarget> versionValueTarget = getVersionAnnotationTarget(entityDotName, index);

                        // the following code generated bytecode that:
                        // if there is a field annotated with @Version, calls 'persist' if the field is null, 'merge' otherwise
                        // if there is no field annotated with @Version, then if the value of the field annotated with '@Id'
                        // is "falsy", 'persist' is called, otherwise 'merge' is called

                        if (versionValueTarget.isPresent()) {
                            Type versionType = getTypeOfTarget(versionValueTarget.get());
                            if (versionType instanceof PrimitiveType) {
                                throw new IllegalArgumentException(
                                        "The '@Version' annotation cannot be used on primitive types. Offending entity is '"
                                                + entityDotName + "'.");
                            }
                            ResultHandle versionValue = generateObtainValue(save, entityDotName, entity,
                                    versionValueTarget.get());
                            BranchResult versionValueIsNullBranch = save.ifNull(versionValue);
                            generatePersistAndReturn(entity, versionValueIsNullBranch.trueBranch());
                            generateMergeAndReturn(entity, versionValueIsNullBranch.falseBranch());
                        }

                        BytecodeCreator idValueUnset;
                        BytecodeCreator idValueSet;
                        if (idType instanceof PrimitiveType) {
                            if (!idType.name().equals(DotNames.PRIMITIVE_LONG)
                                    && !idType.name().equals(DotNames.PRIMITIVE_INTEGER)) {
                                throw new IllegalArgumentException("Id type of '" + entityDotName + "' is invalid.");
                            }
                            if (idType.name().equals(DotNames.PRIMITIVE_LONG)) {
                                ResultHandle longObject = save.invokeStaticMethod(
                                        MethodDescriptor.ofMethod(Long.class, "valueOf", Long.class, long.class), idValue);
                                idValue = save.invokeVirtualMethod(MethodDescriptor.ofMethod(Long.class, "intValue", int.class),
                                        longObject);
                            }
                            BranchResult idValueNonZeroBranch = save.ifNonZero(idValue);
                            idValueSet = idValueNonZeroBranch.trueBranch();
                            idValueUnset = idValueNonZeroBranch.falseBranch();
                        } else {
                            BranchResult idValueNullBranch = save.ifNull(idValue);
                            idValueSet = idValueNullBranch.falseBranch();
                            idValueUnset = idValueNullBranch.trueBranch();
                        }
                        generatePersistAndReturn(entity, idValueUnset);
                        generateMergeAndReturn(entity, idValueSet);
                    }
                }
                try (MethodCreator bridgeSave = classCreator.getMethodCreator(bridgeSaveDescriptor)) {
                    MethodDescriptor save = MethodDescriptor.ofMethod(generatedClassName, "save", entityTypeStr,
                            entityTypeStr);
                    ResultHandle methodParam = bridgeSave.getMethodParam(0);
                    ResultHandle castedMethodParam = bridgeSave.checkCast(methodParam, entityTypeStr);
                    ResultHandle result = bridgeSave.invokeVirtualMethod(save, bridgeSave.getThis(), castedMethodParam);
                    bridgeSave.returnValue(result);
                }
            }

            allMethodsToBeImplementedToResult.put(saveDescriptor, true);
            allMethodsToBeImplementedToResult.put(bridgeSaveDescriptor, true);
        }
    }

    private boolean isPersistable(DotName entityDotName) {
        ClassInfo classInfo = index.getClassByName(entityDotName);
        if (classInfo == null) {
            throw new IllegalStateException("Entity " + entityDotName + " was not part of the Quarkus index");
        }

        if (classInfo.interfaceNames().contains(DotNames.SPRING_DATA_PERSISTABLE)) {
            return true;
        }

        DotName superDotName = classInfo.superName();
        if (superDotName.equals(DotNames.OBJECT)) {
            return false;
        }

        return isPersistable(superDotName);
    }

    private void generatePersistAndReturn(ResultHandle entity, BytecodeCreator bytecodeCreator) {
        bytecodeCreator.invokeVirtualMethod(
                MethodDescriptor.ofMethod(AbstractJpaOperations.class, "persist", void.class, Object.class),
                bytecodeCreator.readStaticField(operationsField),
                entity);
        bytecodeCreator.returnValue(entity);
    }

    private void generateMergeAndReturn(ResultHandle entity, BytecodeCreator bytecodeCreator) {
        ResultHandle entityManager = bytecodeCreator.invokeVirtualMethod(
                ofMethod(AbstractJpaOperations.class, "getEntityManager", EntityManager.class),
                bytecodeCreator.readStaticField(operationsField));
        entity = bytecodeCreator.invokeInterfaceMethod(
                MethodDescriptor.ofMethod(EntityManager.class, "merge", Object.class, Object.class),
                entityManager, entity);
        bytecodeCreator.returnValue(entity);
    }

    /**
     * Given an annotation target, generate the bytecode that is needed to obtain its value
     * either by reading the field or by calling the method.
     * Meant to be called for annotations alike {@code @Id} or {@code @Version}
     */
    private ResultHandle generateObtainValue(MethodCreator methodCreator, DotName entityDotName, ResultHandle entity,
            AnnotationTarget annotationTarget) {
        if (annotationTarget instanceof FieldInfo) {
            FieldInfo fieldInfo = annotationTarget.asField();
            if (Modifier.isPublic(fieldInfo.flags())) {
                return methodCreator.readInstanceField(of(fieldInfo), entity);
            }

            String getterMethodName = JavaBeanUtil.getGetterName(fieldInfo.name(), fieldInfo.type().name());
            return methodCreator.invokeVirtualMethod(
                    MethodDescriptor.ofMethod(entityDotName.toString(), getterMethodName, fieldInfo.type().name().toString()),
                    entity);
        }
        MethodInfo methodInfo = annotationTarget.asMethod();
        return methodCreator.invokeVirtualMethod(
                MethodDescriptor.ofMethod(entityDotName.toString(), methodInfo.name(),
                        methodInfo.returnType().name().toString()),
                entity);
    }

    private Type getTypeOfTarget(AnnotationTarget idAnnotationTarget) {
        if (idAnnotationTarget instanceof FieldInfo) {
            return idAnnotationTarget.asField().type();
        }
        return idAnnotationTarget.asMethod().returnType();
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
        return GenerationUtil.interfaceMethods(GenerationUtil.extendedSpringDataRepos(repositoryToImplement, index), index);
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
        if (ALL_SPRING_DATA_REPOSITORY_METHODS != null) {
            return ALL_SPRING_DATA_REPOSITORY_METHODS;
        }

        ALL_SPRING_DATA_REPOSITORY_METHODS = GenerationUtil.interfaceMethods(new HashSet<>(DotNames.SUPPORTED_REPOSITORIES),
                index);

        return ALL_SPRING_DATA_REPOSITORY_METHODS;
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

        for (int i = 0; i < candidate.parameters().size(); i++) {
            if (!canTypesBeConsideredSame(candidate.parameters().get(i), target.parameters().get(i))) {
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

    private AnnotationTarget getIdAnnotationTarget(DotName entityDotName, IndexView index) {
        return getIdAnnotationTargetRec(entityDotName, index, entityDotName);
    }

    private AnnotationTarget getIdAnnotationTargetRec(DotName currentDotName, IndexView index, DotName originalEntityDotName) {
        ClassInfo classInfo = index.getClassByName(currentDotName);
        if (classInfo == null) {
            throw new IllegalStateException("Entity " + originalEntityDotName + " was not part of the Quarkus index");
        }

        List<AnnotationInstance> annotationInstances = Stream.of(DotNames.JPA_ID, DotNames.JPA_EMBEDDED_ID)
                .map(classInfo.annotations()::get)
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

    private Optional<AnnotationTarget> getVersionAnnotationTarget(DotName entityDotName, IndexView index) {
        return getVersionAnnotationTargetRec(entityDotName, index, entityDotName);
    }

    private Optional<AnnotationTarget> getVersionAnnotationTargetRec(DotName currentDotName, IndexView index,
            DotName originalEntityDotName) {
        ClassInfo classInfo = index.getClassByName(currentDotName);
        if (classInfo == null) {
            throw new IllegalStateException("Entity " + originalEntityDotName + " was not part of the Quarkus index");
        }

        if (!classInfo.annotations().containsKey(DotNames.VERSION)) {
            if (DotNames.OBJECT.equals(classInfo.superName())) {
                return Optional.empty();
            }
            return getVersionAnnotationTargetRec(classInfo.superName(), index, originalEntityDotName);
        }

        List<AnnotationInstance> annotationInstances = classInfo.annotations().get(DotNames.VERSION);
        if (annotationInstances.size() > 1) {
            throw new IllegalArgumentException(
                    "Currently the @Version annotation can only be placed on a single field or method. " +
                            "Offending class is " + originalEntityDotName);
        }

        return Optional.of(annotationInstances.get(0).target());
    }
}
