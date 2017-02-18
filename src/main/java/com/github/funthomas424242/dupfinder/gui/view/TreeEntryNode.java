package com.github.funthomas424242.dupfinder.gui.view;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Saves absolute path and size of file that this is shown within a TreeNode.
 * 
 * @author niklas.polke
 */
public class TreeEntryNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;

	private final String fileAbsolutePath;

	private final long sizeInKB;

	public TreeEntryNode(final String fileAbsolutePath) {
		super(fileAbsolutePath);
		this.fileAbsolutePath = fileAbsolutePath;
		this.sizeInKB = -1;
	}

	public TreeEntryNode(final String fileAbsolutePath, final long sizeInKB) {
		super(fileAbsolutePath + " Größe: " + sizeInKB + "kB");
		this.fileAbsolutePath = fileAbsolutePath;
		this.sizeInKB = sizeInKB;
	}

	public String getFileAbsolutePath() {
		return this.fileAbsolutePath;
	}

	public long getSizeInKB() {
		return this.sizeInKB;
	}
}
