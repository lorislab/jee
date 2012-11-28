/*
 * Copyright 2012 Andrej Petras <andrej@ajka-andrej.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ajkaandrej.test;

import java.io.File;
import java.util.logging.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.filter.ScopeFilter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * The abstract service test class.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public abstract class AbstractServiceTest extends Arquillian {

    /**
     * The UID for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(AbstractServiceTest.class.getName());
    /**
     * The JAR file name.
     */
    private static String NAME = "ServiceTestEjb.jar";
    /**
     * The MAVEN configuration.
     */
    private static String MAVEN_POM = "";
    /**
     * The debug flag.
     */
    private static boolean DEBUG = false;
    /**
     * The list of target directories.
     */
    private static String[] TARGET = null;
    /**
     * The MAVEN filter.
     */
    private static String[] FILTER = null;
    /**
     * The list of classes.
     */
    private static String[] CLASSES = null;
    /**
     * The list of packages.
     */
    private static String[] PACKAGES = null;
    private static final String SPLIT = ",";

    /**
     * This method is run before each test and suite.
     *
     * @param name the JAR file name. Default: <code>ServiceTestEjb.jar</code>.
     * @param debug the debug flag. Default: <code>false</code>.
     * @param filter the MAVEN filter. Default: <code>compile,runtime</code>.
     * @param pom the MAVEN pom file. Default: <code>""</code>.
     * @param classes the list of classes. Default: <code>""</code>.
     * @param target the list of directory. Default: <code>""</code>.
     * @param packages the list of packages. Default: <code>""</code>.
     */
    @BeforeSuite
    @BeforeTest
    @Parameters({"name", "debug", "filter", "pom", "classes", "target", "packages"})
    public static void before(@Optional("ServiceTestEjb.jar") String name,
            @Optional("false") String debug,
            @Optional("compile,runtime") String filter,
            @Optional("classpath:test-pom.xml") String pom,
            @Optional("") String classes,
            @Optional("") String target,
            @Optional("") String packages) {

        DEBUG = Boolean.parseBoolean(debug);

        if (pom != null && !pom.isEmpty()) {
            MAVEN_POM = pom;
        }
        if (name != null && !name.isEmpty()) {
            NAME = name;
        }
        if (filter != null && !filter.isEmpty()) {
            FILTER = filter.split(SPLIT);
        }
        if (target != null && !target.isEmpty()) {
            TARGET = target.split(SPLIT);
        }
        if (classes != null && !classes.isEmpty()) {
            CLASSES = classes.split(SPLIT);
        }
        if (packages != null && !packages.isEmpty()) {
            PACKAGES = packages.split(SPLIT);
        }

    }

    /**
     * Creates the deploy package.
     *
     * @return the archive to deploy.
     */
    @Deployment
    public static Archive<?> createDeployment() {
        JavaArchive result = ShrinkWrap.create(JavaArchive.class, NAME);

        File[] files = null;

        if (MAVEN_POM != null && !MAVEN_POM.isEmpty()) {
            try {
                files =
                        DependencyResolvers.use(MavenDependencyResolver.class)
                        .goOffline()
                        .disableMavenCentral()
                        .loadEffectivePom(MAVEN_POM)
                        .importAllDependencies()
                        .resolveAsFiles(new ScopeFilter(FILTER));
            } catch (IllegalArgumentException ex) {
                // empty list of files
            }
        }

        if (files != null) {
            for (File file : files) {
                JavaArchive imported = ShrinkWrap.createFromZipFile(JavaArchive.class, file);
                result.merge(imported);
            }
        }


        if (TARGET != null) {
            for (String item : TARGET) {
                result.addAsResource(new File(item), ArchivePaths.create("/"));
            }
        }
        if (PACKAGES != null) {
            for (String item : PACKAGES) {
                result.addPackages(true, item);
            }
        }
        if (CLASSES != null) {
            for (String item : CLASSES) {
                try {
                    Class<?> clazz = Class.forName(item);
                    while (!clazz.equals(Object.class)) {
                        result.addClass(clazz);
                        clazz = clazz.getSuperclass();
                    }
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException("Class not found " + item, ex);
                }
            }
        }

        if (DEBUG) {
            LOGGER.info(result.toString(true));
        }
        return result;
    }
}
