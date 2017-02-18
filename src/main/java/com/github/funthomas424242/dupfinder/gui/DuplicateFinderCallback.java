package com.github.funthomas424242.dupfinder.gui;


import de.b0n.dir.processor.DuplicateContentFinderCallback;
import de.b0n.dir.processor.DuplicateLengthFinderCallback;

/**
 * Created by huluvu424242 on 16.01.17.
 */
public interface DuplicateFinderCallback extends DuplicateLengthFinderCallback, DuplicateContentFinderCallback {

	/**
	 * Liefert die Gesamtanzahl der untersuchbaren Dateien im Verzeichnisbaum.
	 * Falls der Verzeichnisbaum mehr als Integer.MAX_VALUE Elemente enthält, liefert er Integer.MAX_VALUE.
	 * @return Anzahl aller Elemente im Verzeichnisbaum
	 */
	void sumAllFiles(int size);
}
