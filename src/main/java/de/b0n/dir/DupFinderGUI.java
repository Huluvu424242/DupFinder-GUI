package de.b0n.dir;

import de.b0n.dir.view.DuplicateFinderCallback;
import de.b0n.dir.view.TreeView;

import java.io.File;
import java.util.Queue;

import javax.swing.*;


/**
 * Created by huluvu424242 on 16.01.17.
 */
public class DupFinderGUI {

    protected JFrame frame;

    protected TreeView treeView = new TreeView();

    public TreeView getTreeView(){
        return treeView;
    }


    public static void main(String[] args) {

        final DupFinderGUI gui = new DupFinderGUI();
        gui.showView();

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


	public DuplicateFinderCallback getCallback() {
		return new DuplicateFinderCallback() {
			
			@Override
			public void uniqueFiles(int removeUniques) {
				// TODO Auto-generated method stub				
			}
			
			@Override
			public void failedFiles(int size) {
				// TODO Auto-generated method stub				
			}
			
			@Override
			public void duplicateGroup(Queue<File> queue) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {						
						treeView.insertDuplicates(queue);		
					}					
				});
			}
			
			@Override
			public void enteredNewFolder(String canonicalPath) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void sumAllFiles(int size) {
				// TODO Auto-generated method stub				
			}
		};
	}
}
