package view;

import io.FileSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import CoreInterfaces.Savable;
import Exceptions.NothingToSaveException;
import Exceptions.WrongFileExtensionException;

public abstract class SaveSaveableAsListener implements ActionListener{

	protected TopFrame frame;
	private Savable saveItem;
	private String type, typeC;
	public FileSystem fs;
	private javax.swing.filechooser.FileFilter filter;


	public SaveSaveableAsListener(String type, String typeC, javax.swing.filechooser.FileFilter filter) {
		super();
		fs = new FileSystem();
		this.type = type;
		this.typeC = typeC;
		this.filter = filter;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			saveItem = getSavableItem();
			if(saveItem == null){
				JOptionPane.showMessageDialog(frame, "There's no " + type + " to save!", "SAVING FAILED", JOptionPane.ERROR_MESSAGE);
			}
			else{
				JFileChooser fsaver = createFileChooser();
				saveInterface(fsaver);
			}
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(frame, "There's no " + type + " to save!", "SAVING FAILED", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void saveInterface(JFileChooser fsaver) {
		int userOption = fsaver.showSaveDialog(frame);
		File selectedFile =  fsaver.getSelectedFile();
		if ((selectedFile != null) && selectedFile.exists()) {
			int response = JOptionPane.showConfirmDialog(frame, "The file " + selectedFile.getName() + " already exists. Do you want to replace the existing file?", "Ovewrite file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (response == JOptionPane.YES_OPTION)
				saveWithError(fs, userOption, selectedFile, fsaver);
		}
		else {
			saveWithError(fs, userOption, selectedFile, fsaver);
		}
	}

	private void saveWithError(FileSystem fs, int userOption, File selectedFile,
			JFileChooser fsaver) {
		try {
			save(userOption, selectedFile);
		} catch (WrongFileExtensionException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
			saveInterface(fsaver);
		}
	}

	private JFileChooser createFileChooser() {
		JFileChooser fsaver = new JFileChooser();
		fsaver.setDialogTitle("Save " + typeC + " As...");
		fsaver.setCurrentDirectory(new File(getDir()));
		fsaver.setSelectedFile(new File(getDir() + fsGetItemName()));
		fsaver.setFileFilter(filter);
		fsaver.setAcceptAllFileFilterUsed(false);
		return fsaver;
	}

	private void save(int userOption, File selectedFile)
			throws WrongFileExtensionException {
		if(userOption == JFileChooser.APPROVE_OPTION){
			try {
				if(!fsCheckExt(selectedFile)){
					if(!selectedFile.getName().contains(".")){
						selectedFile = new File(selectedFile.getAbsolutePath() + getExt());
					}
					else{
						throw new WrongFileExtensionException(selectedFile.getName(), type);
					}
				}
				fsSave(saveItem, selectedFile);
			} catch (NothingToSaveException e) {
				JOptionPane.showMessageDialog(null, "There is no " + type + " to save...", "No " + typeC + " Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public abstract void fsSave(Savable saveItem, File selectedFile) throws NothingToSaveException;
	public abstract String getExt();
	public abstract String getDir();
	public abstract boolean fsCheckExt(File selectedFile);
	public abstract String fsGetItemName();
	public abstract Savable getSavableItem();
}