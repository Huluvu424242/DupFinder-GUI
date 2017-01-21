package com.github.funthomas424242.dupfinder.gui;

import org.gui4j.event.SimpleEvent;

/**
 * Created by huluvu424242 on 21.01.17.
 */
public interface EventConstants {

    // to mark modify of data
    public final SimpleEvent eventChanged = new SimpleEvent();

    public final SimpleEvent eventSaved = new SimpleEvent();

    public final SimpleEvent eventNavigationChanged = new SimpleEvent();


    // Reiter 1
    public final SimpleEvent eventInputActionChanged = new SimpleEvent();

    // evtl. nicht benötigt
    public final SimpleEvent eventViewChanged = new SimpleEvent();
}