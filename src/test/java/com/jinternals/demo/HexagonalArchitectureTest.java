package com.jinternals.demo;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class HexagonalArchitectureTest {

    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("com.jinternals.demo");

    @Test
    void shouldRespectHexagonalArchitecture() {
        Architectures.layeredArchitecture()
                .consideringAllDependencies()

                // Define Layers
                .layer("Domain").definedBy("com.jinternals.demo.domain..")
                .layer("Application").definedBy("com.jinternals.demo.application..")
                .layer("Infrastructure").definedBy("com.jinternals.demo.infrastructure..")

                // Enforce rules
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure")
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure")
                .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()

                .check(importedClasses);
    }

    @Test
    void applicationShouldNotDependOnSpring() {
        noClasses()
                .that().resideInAPackage("com.jinternals.demo.application..")
                .should().dependOnClassesThat().resideInAnyPackage("org.springframework..")
                .because("The Domain layer should be free of framework dependencies")
                .check(importedClasses);
    }

    @Test
    void domainShouldNotDependOnSpring() {
        noClasses()
                .that().resideInAPackage("com.jinternals.demo.domain..")
                .should().dependOnClassesThat().resideInAnyPackage("org.springframework..")
                .because("The Domain layer should be free of framework dependencies")
                .check(importedClasses);
    }

    @Test
    void applicationLayerShouldOnlyDependOnDomain() {
        noClasses()
                .that().resideInAPackage("com.jinternals.demo.application..")
                .should().dependOnClassesThat().resideInAnyPackage("com.jinternals.demo.infrastructure..")
                .because("The Application layer should not depend on Infrastructure")
                .check(importedClasses);
    }
}

