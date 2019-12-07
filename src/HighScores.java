import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

// this class is to keep track and display the high scores menu using file I/O
public class HighScores {
	
    private static final String FILE = System.getProperty("user.dir") + "/files/high_scores.txt";
    private static final String IMG_FILE = System.getProperty("user.dir") + "/files/title.png";
    
    private static BufferedImage img;
    private BufferedReader br;
    private BufferedWriter bw;
    private TreeMap<Double, List<String>> map;
    
    public HighScores() {
    	
    	// map from score to list of scorers
	    map = new TreeMap<Double, List<String>>();
	    
	    File f = new File(FILE);


	    try {
	    	
	    	if (!f.exists()) {
	    		f.createNewFile();
	    	}
	    	
	        if (br == null) {
	            br = new BufferedReader(new FileReader(FILE));
	            
	            String currentLine = br.readLine();
	            while (currentLine != null) {
	            	String[] currentLineSplit = currentLine.split(",");
	            	
	            	Double time = Double.parseDouble(currentLineSplit[0]);
	            	String name = currentLineSplit[1];
	            	
	            	List<String> ls;
	            	
	            	if (map.containsKey(time)) {
	            		ls = map.get(time);
	            		ls.add(name);
	            	}
	            	else {
	            		ls = new LinkedList<String>();
	            		ls.add(name);
	            	}
	            	
					map.put(time, ls);
	            	currentLine = br.readLine();
	            }
	            br.close();
	        }
	    } catch (IOException e) {
	        System.out.println("Internal Error:" + e.getMessage());
	    	System.out.println(FILE);
	    }	    
    }
    
    // function that will update the score list to the new top 10 given a new score and name
    public void updateScore(double t, String n) {
    	
    	List<String> ls;
    	
		if (map.containsKey(t)) {
    		ls = map.get(t);
    		ls.add(n);
    	}
    	else {
    		ls = new LinkedList<String>();
    		ls.add(n);
    	}   
		
		map.put(t, ls);
		
	    try {
	        if (bw == null) {
				bw = new BufferedWriter(new FileWriter(FILE));
				
				int mapSize = 0;
				
				for (Double d : map.keySet()) {
					mapSize += map.get(d).size();
				}
				
				int iterNum = Math.min(mapSize, 10);
				
				for (Double d : map.keySet()) {
					if (iterNum >= 1) {
						for (String s : map.get(d)) {
							if (iterNum >= 1) {
								bw.write(d +","+s+'\n');
								iterNum--;
							}
							else {
								break;
							}
						}
					}
					else {
						break;
					}
					
				}
				bw.close();
				bw = null;
	        }
	    } catch (IOException b) {
	        System.out.println("Internal Error:" + b.getMessage());
	    	System.out.println(FILE);
	    }    	
    }
    
    public void draw(Graphics g) {
    	
	    try {
	        if (img == null) {
	            img = ImageIO.read(new File(IMG_FILE));
	        }
	    } catch (IOException e) {
	        System.out.println("Internal Error:" + e.getMessage());
	    	System.out.println(IMG_FILE);
	    }
        
    	int size = 30;
    	
    	Font f = new Font("Courier New", Font.BOLD, size);
    	
        g.setFont(f);
        g.setColor(Color.WHITE);
        
        g.drawImage(img, 0, 0, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT, null);
        
        BufferedReader br1 = null;
        
	    try {
	        if (br1 == null) {
	            br1 = new BufferedReader(new FileReader(FILE));
				
				int mapSize = 0;
				
				for (Double d : map.keySet()) {
					mapSize += map.get(d).size();
				}
								
				int iterNum = Math.min(mapSize, 10);
				
				for (Double d : map.descendingKeySet()) {
					if (iterNum >= 1) {
						for (String s : map.get(d)) {
							if (iterNum >= 1) {
								g.drawString(s + "\t\t\t\t\t" + d + " s", 195, 125 + iterNum*50);
								iterNum--;
							}
							else {
								break;
							}
						}
					}
					else {
						break;
					}
					
				}
				br1.close();
	        }
	    } catch (IOException b) {
	        System.out.println("Internal Error:" + b.getMessage());
	    	System.out.println(FILE);
	    }
    	
        FontMetrics metrics = g.getFontMetrics(f);
        
        g.drawString("High Scores", GameCourt.COURT_WIDTH/2 - metrics.stringWidth("High Scores")/2, 
        		100);
    }
    


}
