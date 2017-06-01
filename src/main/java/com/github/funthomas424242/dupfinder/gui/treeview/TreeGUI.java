package com.github.funthomas424242.dupfinder.gui.treeview;

import com.github.funthomas424242.dupfinder.gui.DuplicateFinderCallback;

import java.io.Closeable;
import java.io.File;
import java.util.Queue;

import javax.swing.*;


/**
 * Created by huluvu424242 on 16.01.17.
 */
public class TreeGUI implements Closeable {

    protected JFrame frame;

    protected TreeView treeView = new TreeView();

    public TreeView getTreeView(){
        return treeView;
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
    protected void createAndShowGUI() {
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

    public void close(){
        if(frame==null) {
            // TODO Es kann sein, dass frame noch nicht gestartet wurde wegen hoher Last
            // besser wäre irgendwas mit einem Future
            Thread.yield();
            boolean hasSlept=false;
            while(!hasSlept) {
                try {
                    Thread.sleep(1000);
                    hasSlept=true;
                } catch (InterruptedException e) {
                    // nix zu tun
                }
            }
        }
        if(frame!=null){
            frame.dispose();
        }
    }


	public DuplicateFinderCallback getCallback() {
		return new DuplicateFinderCallback() {
			
			@Override
			public void uniqueFiles(int removeUniques) {
                System.out.println("uniqueFiles"+removeUniques);
			}
			
			@Override
			public void failedFiles(int size) {
                System.out.println("failedFiles"+size);
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
			public void enteredNewFolder(File folder) {
				System.out.println("enteredNewFolder"+folder.toString());
			}

			@Override
			public void sumAllFiles(int size) {
                System.out.println("sumAllFiles"+size);
			}

			@Override
			public void unreadableFolder(File folder) {
                System.out.println("unreadableFolder"+folder.toString());
				
			}
		};
	}
}
