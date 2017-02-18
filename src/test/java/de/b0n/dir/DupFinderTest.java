package de.b0n.dir;

import de.b0n.dir.view.DuplicateFinderCallback;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Queue;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

/**
 * Created by huluvu424242 on 15.01.17.
 */
public class DupFinderTest {

    final DuplicateFinderCallback duplicateFinderCallback = new DuplicateFinderCallback() {
        @Override
        public void sumAllFiles(int size) {

        }

        @Override
        public void failedFiles(int i) {

        }

        @Override
        public void duplicateGroup(Queue<File> queue) {

        }

        @Override
        public void uniqueFiles(int i) {

        }

        @Override
        public void enteredNewFolder(File file) {

        }

        @Override
        public void unreadableFolder(File file) {

        }
    };

    private static final String OS_NAME = System.getProperty("os.name");

    String unreadableFolder;

    @Before
    public void setUp() {

        // each OS must be add to supportedOS() too
        if ("Linux".equals(OS_NAME)) {
            unreadableFolder = "/root";
        } else {
            unreadableFolder = null;
        }
    }

    /**
     * OS currently supported by specific tests
     *
     * @return
     */
    private boolean supportedOS() {
        return Arrays.asList("Linux").contains(OS_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mainCallNullArguments() throws InterruptedException {
        DupFinder.main(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mainCallEmptyArguments() {
        DupFinder.main(new String[]{});
        fail();
    }

    @Test
    public void mainCallNullContentArguments() {
        try {
            DupFinder.main(new String[]{null});
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().startsWith("FEHLER: Parameter <Verzeichnis> fehlt"));
        }
    }

    @Test
    public void mainCallNoneExistFolderArguments() throws IOException, InterruptedException {
        try {
            DupFinder.main(new String[]{"abc"});
            fail();

        } catch (IllegalStateException ex) {
            assertTrue(ex.getMessage().startsWith("Suche fehlgeschlagen"));
            assertTrue(ex.getCause().getMessage().startsWith("FEHLER: Parameter <Verzeichnis> existiert nicht:"));
        }
    }


    @Test
    public void instanceCallNullArguments() throws InterruptedException {
        try {
            new DupFinder(null);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().startsWith("FEHLER: Parameter <Verzeichnis> fehlt"));
        }

    }

    @Test
    public void instanceCallNoneExistFolderArguments() throws IOException, InterruptedException {
        try {
            final DupFinder dupFinder=new DupFinder(new File("abc"));
            dupFinder.startSearching(this.duplicateFinderCallback);
            fail();

        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().startsWith("FEHLER: Parameter <Verzeichnis> existiert nicht:"));
        }
    }


    @Test
    public void instanceCallNoFolderArgument() throws IOException, InterruptedException {
        try {
            final DupFinder dupFinder=new DupFinder(new File("pom.xml"));
            dupFinder.startSearching(this.duplicateFinderCallback);
            fail();

        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().startsWith("FEHLER: Parameter <Verzeichnis> ist kein Verzeichnis:"));
        }
    }

    @Test
    public void instanceCallNoReadableFolderArgument() throws IOException, InterruptedException {
        // conditional test
        assumeTrue("Betriebssystem wird nicht unterstützt: " + OS_NAME, supportedOS());

        try {
            final DupFinder dupFinder = new DupFinder(new File(unreadableFolder));
            dupFinder.startSearching(this.duplicateFinderCallback);
            fail();

        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().startsWith("FEHLER: Parameter <Verzeichnis> ist nicht lesbar:"));
        }
    }


}
