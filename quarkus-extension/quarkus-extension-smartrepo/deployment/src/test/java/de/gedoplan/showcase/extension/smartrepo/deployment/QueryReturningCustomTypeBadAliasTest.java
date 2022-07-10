package de.gedoplan.showcase.extension.smartrepo.deployment;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;

public class QueryReturningCustomTypeBadAliasTest {

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest().setArchiveProducer(
            () -> ShrinkWrap.create(JavaArchive.class)
                    .addAsResource("import_books.sql", "import.sql")
                    .addClasses(Book.class, BookRepositoryBadAlias.class))
            .withConfigurationResource("application.properties")
            .setExpectedException(IllegalArgumentException.class);

    @Test
    public void testBadAliases() {
        // an exception should be thrown
    }

}
