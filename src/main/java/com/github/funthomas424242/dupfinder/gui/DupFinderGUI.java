package com.github.funthomas424242.dupfinder.gui;

//import com.github.funthomas424242.dupfinder.gui.model.PreferencesModel;
import org.gui4j.Gui4j;
import org.gui4j.Gui4jFactory;
import org.gui4j.Gui4jView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Created by huluvu424242 on 21.01.17.
 */
public class DupFinderGUI {

    final static String RESOUCE_NAME = "maindialog.xml";

    final protected static Logger logger = LoggerFactory.getLogger(DupFinderGUI.class);



    private DupFinderGUI() {
        super();

    }

    public static void main(final String args[]) {

        @SuppressWarnings("unused")
//        final PreferencesModel preferencesModel = new PreferencesModel();
        //
        final boolean validateXML = true;
        final boolean logInvoke = false;
        final int numberOfWorkerThreads = -1;


        final URL gui4jPropertieUrl = DupFinderGUI
                .getResource("dialogLayouts/"+GuiConstants.PATH_GUI4J_PROPERTIES);
        System.out.println("URL:"+gui4jPropertieUrl.toString());
        final Gui4j gui4jEngine = Gui4jFactory.createGui4j(validateXML,
                logInvoke, numberOfWorkerThreads, gui4jPropertieUrl);
        @SuppressWarnings("unused")
        final MainDialogController mainDialog = new MainDialogController(
                gui4jEngine);
        System.out.println("main ende");

    }

    public static URL getResource(final String resourcePath) {
        final ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        //final ClassLoader classLoader = this.getClass().getClassLoader();

        final URL resourceURL = classLoader.getResource(resourcePath);
        return resourceURL;
    }









//    public static void main(String[] args){
//        final DupFinderGUI gui=new DupFinderGUI();
//        Gui4jView view=gui.createGui4jView();
//
//    }
//
//    public static Gui4j createGui4j() {
//        int numberOfWorkerThreads = -1;
//        final boolean validateXML = true;
//        final boolean logInvoke = false;
//
//        final ClassLoader classLoader = DupFinderGUI.class.getClassLoader();
//        final URL url = classLoader.getResource("dialogLayouts/gui4jComponents.properties");
//
//        logger.info("Creating Gui4j instance.");
//        return Gui4jFactory.createGui4j(validateXML, logInvoke,
//                numberOfWorkerThreads, url);
//    }
//
//    private Gui4jView createGui4jView() {
//        final Gui4j gui4j = createGui4j();
//        final MainDialogController controller = new MainDialogController(gui4j);
//        final Gui4jView view = gui4j.createView(RESOUCE_NAME, controller, getTitle(), false);
//        return view;
//    }
//
//    // ab hier Controller
//    public String getTitle() {
//        return "Hello World!";
//    }
//
//    public void actionExit() {
//        // actions
//    }
}
