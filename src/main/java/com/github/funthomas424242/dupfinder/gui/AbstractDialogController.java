package com.github.funthomas424242.dupfinder.gui;


import java.awt.Image;
import java.awt.SystemTray;

//import com.github.funthomas424242.dupfinder.gui.model.PreferencesModel;
import org.gui4j.Gui4j;
import org.gui4j.Gui4jController;
import org.gui4j.Gui4jControllerAdvanced;
import org.gui4j.Gui4jView;
import org.gui4j.exception.Gui4jDefaultErrorHandler;
import org.gui4j.exception.Gui4jErrorHandler;
import org.gui4j.exception.Gui4jExceptionHandler;

/**
 * Created by huluvu424242 on 21.01.17.
 */
public abstract class AbstractDialogController implements
        Gui4jControllerAdvanced {

    protected final Gui4j gui4jEngine;

    protected boolean shutdownEngine = false;

    protected Gui4jView gui4jView = null;

    /**
     *
     */
    protected AbstractDialogController(final Gui4j gui4jEngine) {
        super();
        this.gui4jEngine = gui4jEngine;
    }

    public void createView(final String resourceName, final String title,
                           final Image appIcon) {
        final boolean readOnlyMode = false;
        final Gui4jController controller = (Gui4jController) this;
        gui4jView = this.gui4jEngine.createView(resourceName, controller,
                title, readOnlyMode);
        gui4jView.setIconImage(appIcon);
        gui4jView.setResizable(true);
        gui4jView.maximize();
        gui4jView.prepare();
        gui4jView.show();
    }

    /**
     * Section of forced methods for interface
     */
    public boolean onWindowClosing() {
//        PreferencesModel.exportPreferences();
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            tray.remove(GuiConstants.TRAY_ICON);
        }
        System.out.println("Standardrückfrage Wirklich Schließen?");
        System.out.println("call onWindowClosing");
        this.shutdownEngine = true;
        if (this.gui4jView != null) {
            this.gui4jView.close();
        }
        //permit closing
        return true;
    }

    public void windowClosed() {
        System.out.println("call windowClosed");
        if (this.shutdownEngine) {
            gui4jEngine.shutdown();
        }
    }

    public Gui4jExceptionHandler getExceptionHandler() {
        System.out.println("call getExceptionHandler");
        return null;
//        return new Gui4jExceptionHandler() {
//            @Override
//            public Gui4jExceptionHandler getDelegationExceptionHandler() {
//                return null;
//            }
//            public void handleException(Exception ex){
//                System.out.println(ex.toString());
//            }
//        };
    }

    public Gui4j getGui4j() {
        return this.gui4jEngine;
    }

    public void windowActivated() {
        System.out.println("call windowActivated");

    }

    public void windowDeactivated() {
        System.out.println("call windowDeactivated");

    }

    public void windowDeiconified() {
        System.out.println("call windowDeiconified");

    }

    public void windowIconified() {
        System.out.println("call windowIconified");

    }

    public void windowOpened() {
        System.out.println("call windowOpended");

    }

}

