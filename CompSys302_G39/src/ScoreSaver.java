import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

public class ScoreSaver {
	
	private FileWriter fileWriter;
	private File HighScoreFile;
	private String[] HighScores = new String[10];
	 
	
	private void createNewHighScoreFile() {
		try {
			fileWriter = new FileWriter("saves/HighScoreList.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		for (int i = 0; i<10; i++) {
			printWriter.printf(("000000\n"));
		}
		printWriter.close();
	}
	
	
	
	private void fileOpener() {
		HighScoreFile = null;
		int i = 0;
		String currentHighScore;
			HighScoreFile = new File("saves/HighScoreList.txt");
		try {
			Scanner HighScoreReader = new Scanner(HighScoreFile);
			while(HighScoreReader.hasNextLine()) {
				HighScores[i] = HighScoreReader.nextLine();
				i++;
			}
			HighScoreReader.close();
			
		} catch (IOException e) {
			createNewHighScoreFile();
			HighScoreFile = new File("saves/HighScoreList.txt");
			try {
				Scanner HighScoreReader = new Scanner(HighScoreFile);
				while(HighScoreReader.hasNextLine()) {
					HighScores[i] = HighScoreReader.nextLine();
					i++;
				}
				HighScoreReader.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}

		
	}
	
	public void compareCurrentToHigh(int currentScore) {
		String Score = String.format("%06d", currentScore);
		String scoreShift = null;
		String temp;
		int HighScore;
		for (int i=0;i<10;i++) {
			HighScore = Integer.parseUnsignedInt(HighScores[i]);
			if (scoreShift==null && currentScore > HighScore) {
				scoreShift = HighScores[i];
				HighScores[i] = Score;
			} else if(scoreShift!= null) {
				temp = HighScores[i];
				HighScores[i] = scoreShift;
				scoreShift = temp;
			}
		}
		
		try {
			fileWriter = new FileWriter("saves/HighScoreList.txt");
			PrintWriter scoreEditor = new PrintWriter(fileWriter);
			for (int i = 0; i<10; i++) {
				scoreEditor.printf((HighScores[i] + "\n"));
				
			}
			scoreEditor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
	
	}
	
	public ScoreSaver(){
		fileOpener();
	
	}
	
	public String getHighScore(int i) {
		return HighScores[i];
	}
	
}
