package de.b0n.dir;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.b0n.dir.processor.Cluster;
import de.b0n.dir.processor.DuplicateContentFinder;
import de.b0n.dir.processor.DuplicateLengthFinder;
import de.b0n.dir.view.DuplicateFinderCallback;

public class DupFinder {

	private static final String SEARCH_FAILED = "Suche fehlgeschlagen";
	private static final String MESSAGE_NO_PARAM = "FEHLER: Parameter <Verzeichnis> fehlt\r\n usage: DupFinder <Verzeichnis>\r\n<Verzeichnis> = Verzeichnis in dem rekursiv nach Duplikaten gesucht wird";

	public static void main(String[] args) throws InterruptedException {
		// Lese Root-Verzeichnis aus Argumenten
		if (args == null || args.length < 1 || args[0] == null) {
			// exit(1): Kein Parameter übergeben
			System.err.println(MESSAGE_NO_PARAM);
			throw new IllegalArgumentException(MESSAGE_NO_PARAM);
		}
		final File folder = new File(args[0] + File.separator);

		final DupFinderGUI gui = new DupFinderGUI();
		gui.showView();
		final DupFinder dupFinder = new DupFinder();
		try {
			dupFinder.startSearching(folder, gui.getCallback());
		} catch (Throwable ex) {
			gui.forceClose();
			throw new IllegalStateException(SEARCH_FAILED, ex);
		}
	}

	public void startSearching(final File folder, DuplicateFinderCallback duplicateFinderCallback) {
		if (folder == null) {
			throw new IllegalArgumentException(MESSAGE_NO_PARAM);
		}

		long startTime = System.nanoTime();

		ExecutorService threadPool = Executors.newWorkStealingPool();

		Cluster<Long, File> duplicatesByLength = null;
		try {

			duplicatesByLength = DuplicateLengthFinder.getResult(folder, threadPool, duplicateFinderCallback);
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			throw ex;
		}
		
		duplicateFinderCallback.sumAllFiles(duplicatesByLength.size());
		duplicateFinderCallback.uniqueFiles(duplicatesByLength.removeUniques().size());
		
		DuplicateContentFinder.getResult(duplicatesByLength.values(), threadPool, duplicateFinderCallback);
		long duplicateTime = System.nanoTime();
		System.out.println("Zeit in Sekunden zum Finden der Duplikate: " + ((duplicateTime - startTime) / 1000000000));
	}
}
