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

	 protected final File searchRootFolder;


	 public static void main( String args[]){
		 if (args == null || args.length < 1 || args[0] == null) {
			 System.err.println(MESSAGE_NO_PARAM);
			 throw new IllegalArgumentException(MESSAGE_NO_PARAM);
		 }

		 final DupFinderGUI gui = new DupFinderGUI();
		 gui.showView();

		 final DupFinder dupFinder = new DupFinder(new File(args[0]));
		 try {
			 dupFinder.startSearching( gui.getCallback());
		 } catch (Throwable ex) {
			 gui.close();
			 throw new IllegalStateException(SEARCH_FAILED, ex);
		 }

	 }

	public DupFinder( final File folder){
		if (folder == null) {
			System.err.println(MESSAGE_NO_PARAM);
			throw new IllegalArgumentException(MESSAGE_NO_PARAM);
		}
		if( folder.getPath().endsWith(File.separator)) {
			this.searchRootFolder = folder;
		}else{
			this.searchRootFolder=new File(folder.getAbsolutePath()+File.separator);
		}
	}

	protected void startSearching(final DuplicateFinderCallback duplicateFinderCallback) {

		long startTime = System.nanoTime();

		final ExecutorService threadPool = Executors.newWorkStealingPool();

		Cluster<Long, File> duplicatesByLength = null;
		try {

			duplicatesByLength = DuplicateLengthFinder.getResult(this.searchRootFolder, threadPool, duplicateFinderCallback);
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
