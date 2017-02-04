package de.b0n.dir;

import de.b0n.dir.processor.Cluster;
import de.b0n.dir.processor.DuplicateContentFinder;
import de.b0n.dir.processor.DuplicateLengthFinder;
import de.b0n.dir.processor.DuplicateLengthFinderCallback;
import de.b0n.dir.view.TreeView;
import de.b0n.dir.view.ViewUpdater;

import javax.swing.*;
import java.io.File;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by huluvu424242 on 16.01.17.
 */
public class DupFinderGUI {

    private static final String MESSAGE_NO_PARAM = "FEHLER: Parameter <Verzeichnis> fehlt\r\n usage: DupFinder <Verzeichnis>\r\n<Verzeichnis> = Verzeichnis in dem rekursiv nach Duplikaten gesucht wird";
    private static final String MESSAGE_NO_INSTANCE_PARAM = "FEHLER: Parameter <TreeView> fehlt\r\n usage: new DupFinder(treeView);";



    protected JFrame frame;

    protected TreeView treeView = new TreeView();

    public TreeView getTreeView(){
        return treeView;
    }


    public static void main(String[] args) throws InterruptedException  {

        // Lese Root-Verzeichnis aus Argumenten
        if (args == null || args.length < 1 || args[0] == null) {
            // exit(1): Kein Parameter übergeben
            System.err.println(MESSAGE_NO_PARAM);
            throw new IllegalArgumentException(MESSAGE_NO_PARAM);
        }
        final File folder = new File(args[0] + File.separator);

        final DupFinderGUI gui = new DupFinderGUI();
        gui.showView();

        final DuplicateLengthFinderCallback callbackLengthFinderCallback= new DuplicateLengthFinderCallback(){

            @Override
            public void enteredNewFolder(String s) {
                System.out.println(">"+s);
            }
        };

        try {
            ExecutorService threadPool = Executors.newWorkStealingPool();
            Cluster<Long, File> cluster = DuplicateLengthFinder.getResult(folder, threadPool,callbackLengthFinderCallback);
            Collection<Queue<File>> fileQueues = cluster.values();
            Queue<Queue<File>> duplicateContentFilesQueues = DuplicateContentFinder.getResult(threadPool, fileQueues);

            final TreeView treeView=gui.getTreeView();
            final ViewUpdater viewUpdater=treeView.createViewUpdater(duplicateContentFilesQueues);
            viewUpdater.run();

        }catch (Throwable ex){
            gui.forceClose();
            throw ex;
        }

    }

    public void showView(){

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Couldn't use system look and feel.");
        }

        //Create and set up the window.
        frame = new JFrame("Duplikat-Finder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(treeView);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public boolean forceClose() throws InterruptedException {
        if(frame==null) {
            // TODO Es kann sein, dass frame noch nicht gestartet wurde wegen hoher Last
            // besser wäre irgendwas mit einem Future
            Thread.yield();
            Thread.sleep(1000);
        }
        if(frame!=null){
            frame.dispose();
            return true;
        }
        return false;
    }
}
