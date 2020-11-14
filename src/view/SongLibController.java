/**
 * Jason Chou
 * Ty Goldin
 * Group#44
 * 
 */


package view;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import object.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SongLibController {
	
    @FXML
    private ListView<Song> songList;
    private ObservableList<Song> obsSongList = FXCollections.observableArrayList();   
	File loader = new File("loader.txt");
    
    public void start(){
    	
    	try {
			Scanner input = new Scanner(loader);
			while(input.hasNext()) {
				String parse = input.nextLine();
				int index1 = parse.indexOf("-SONG");
				int index2 = parse.indexOf("-ARTIST");
				int index3 = parse.indexOf("-ALBUM");
				int index4 = parse.indexOf("-YEAR");
				
				String name = parse.substring(0, index1);
				String artist = parse.substring(index1 + 6, index2); 
				String album = parse.substring(index2 + 8, index3);
				String year = parse.substring(index3 + 7, index4);
			
				Song temp = new Song(name, artist, album, year);
				obsSongList.add(temp);
				/*if(songList.getSelectionModel().isEmpty()) {
		    		songList.getSelectionModel().select(temp);
		    		showSong();
		    	}*/
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	Collections.sort(obsSongList, new Comparator<Song>(){

			@Override
			public int compare(Song o1, Song o2) {
				// TODO Auto-generated method stub
				if(o1.getName().equals(o2.getName()))
					return o1.artist.compareTo(o2.artist);
				return o1.getName().compareTo(o2.getName());
			}
    		
    	});
    		
    	songList.setItems(obsSongList);
    	if(!obsSongList.isEmpty()) {
    		songList.getSelectionModel().select(0);
    		showSong();
    	}
    }
    
    public void shutDown() {
	    	try(FileWriter fw = new FileWriter("loader.txt", false);
	    		    BufferedWriter bw = new BufferedWriter(fw);
	    		    PrintWriter out = new PrintWriter(bw);

	    			)
	    		{
	    			for (Song s : obsSongList) {
	    				out.println(s.getName() + "-SONG " + s.artist + "-ARTIST " + s.album + "-ALBUM " + s.year + "-YEAR ");
	    			}
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
    }

    @FXML
    private VBox verticalBox;

    @FXML
    private Text songInfo;

    @FXML
    private TextField songName;

    @FXML
    private TextField songArtist;

    @FXML
    private TextField songAlbum;

    @FXML
    private TextField songYear;

    @FXML
    private Text currentStatus;

    @FXML
    private Button addSong;

    @FXML
    private Button delSong;

    @FXML
    private Button editSong;

    @FXML
    private Text musicCollection;
    
    @FXML
    private Button confirm;
    
    @FXML
    private Button cancel;
    
    @FXML
    void onAddSong(ActionEvent event) {
    	setStatus("adding");
    	songList.setDisable(true);
    	//songList.getSelectionModel().clearSelection();
    	clearDetails();
    }
    
    @FXML
    void onConfirm(ActionEvent event) {
    	
    	//add
    	if (currentStatus.getText().contentEquals("Currently adding song details")) {
    		
    		String name = songName.getText();
	    	String artist = songArtist.getText();
	    	String album = songAlbum.getText();
	    	String year = songYear.getText();
	    	Song add = new Song(name, artist, album, year);
	    	boolean flag = true;
	    	
	    	for(Song s : obsSongList) {
	    		if(s.getName().toLowerCase().equals(name.toLowerCase()) && s.artist.toLowerCase().equals(artist.toLowerCase())) {
		    		Alert alert = new Alert(AlertType.ERROR, "Duplicate name and artist is present", ButtonType.OK);
		    		alert.showAndWait();
		    		flag = false;
		    		
	    		}
	    	
	    	}
	    	if (!songYear.getText().contentEquals("")){
	    		try
	    		{	
	    			Integer.parseInt(songYear.getText());
	    		}
	    		catch (NumberFormatException e) {
	    			Alert alert = new Alert(AlertType.ERROR, "Year needs to be a number", ButtonType.OK);
		    		alert.showAndWait();
		    		return;
	    		}
    		}
	    	if(name.length() == 0 || artist.length() == 0) {
	    		//error message
	    		Alert alert = new Alert(AlertType.ERROR, "Song needs at least a name and artist", ButtonType.OK);
	    		alert.showAndWait();

	    	}
	    	else if(flag){
		    	obsSongList.add(add);
		    	
		    	songList.setDisable(false);
		    	Collections.sort(obsSongList, new Comparator<Song>(){
	
					@Override
					public int compare(Song o1, Song o2) {
						// TODO Auto-generated method stub
						if(o1.getName().toLowerCase().equals(o2.getName().toLowerCase()))
							return o1.artist.toLowerCase().compareTo(o2.artist.toLowerCase());
						return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
					}
		    		
		    	});
		    	songList.getSelectionModel().select(add);
		    	setStatus("viewing");
	    	}
	    	
	    	

    	}
    	
    	
    	//edit
    	if (currentStatus.getText().contentEquals("Currently editing song details")) {
    		Song selectedSong = songList.getSelectionModel().getSelectedItem();
	    	boolean flag = true;
    		
    		for(Song s : obsSongList) {

    			if(s.equals(selectedSong))
    				continue;
	    		if(s.getName().toLowerCase().equals(songName.getText().toLowerCase()) && s.artist.toLowerCase().equals(songArtist.getText().toLowerCase())) {
		    		Alert alert = new Alert(AlertType.ERROR, "Duplicate name and artist is present", ButtonType.OK);
		    		alert.showAndWait();
		    		flag = false;
		    		
	    		}
	    	
	    	}
	    	
    		if (!songYear.getText().contentEquals("")){
	    		try
	    		{	
	    			Integer.parseInt(songYear.getText());
	    		}
	    		catch (NumberFormatException e) {
	    			Alert alert = new Alert(AlertType.ERROR, "Year needs to be a number", ButtonType.OK);
		    		alert.showAndWait();
		    		return;
	    		}
    		}
	    	if(songName.getText().length() == 0 || songArtist.getText().length() == 0) {
	    		//error message
	    		Alert alert = new Alert(AlertType.ERROR, "Song needs at least a name and artist", ButtonType.OK);
	    		alert.showAndWait();
	    		
	    	}
	    	else if(flag){
	    		songList.setDisable(false);	    		
	    		
	    		selectedSong.setName(songName.getText());
	    		selectedSong.setArtist(songArtist.getText());
	    		selectedSong.setAlbum(songAlbum.getText());
	    		selectedSong.setYear(songYear.getText());
		    	Collections.sort(obsSongList, new Comparator<Song>(){
		    		
					@Override
					public int compare(Song o1, Song o2) {
						// TODO Auto-generated method stub
						if(o1.getName().toLowerCase().equals(o2.getName().toLowerCase()))
							return o1.artist.toLowerCase().compareTo(o2.artist.toLowerCase());
						return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
					}
		    		
		    	});
	    		
	    		
	    		songList.getSelectionModel().clearSelection();
	    		songList.getSelectionModel().select(selectedSong);
	    		setStatus("viewing");
	    	}
	    	

    	}
    	
    	
    	//delete
	    if(currentStatus.getText().contentEquals("Confirm delete?")) {
	    	songList.setDisable(false);
    		Song selectedSong = songList.getSelectionModel().getSelectedItem();
	    	songList.getSelectionModel().selectNext();
	    	if(songList.getSelectionModel().isEmpty()) {
	    		songList.getSelectionModel().select(selectedSong);;
	    	}
	    	obsSongList.remove(selectedSong);
	    	if(!songList.getSelectionModel().isEmpty())
	    		showSong();
	    	else
	    		clearDetails();
	    	
	    	setStatus("viewing");
    	}
    	
    }
    
    @FXML
    void onCancel(ActionEvent event) {
    	setStatus("viewing");
    	songList.setDisable(false);
    	if(!obsSongList.isEmpty())
    		showSong();
    }
    
    @FXML
    void onDelSong(ActionEvent event) {
    	setStatus("deleting");
    	songList.setDisable(true);
    }

    @FXML
    void onEditSong(ActionEvent event) {
    	if (songList.getSelectionModel().getSelectedItem()!=null) {
    		songList.setDisable(true);
    		setStatus("editing");
        	showSong();
    	}    	
    }
    
    @FXML
    void onSelectSong(MouseEvent event) {
    	if (songList.getSelectionModel().getSelectedItem()!=null) {
    		showSong();
    	}
    	System.out.println("1");
    }
    
    void setEditable(boolean edit) {
    	songName.setEditable(edit);
    	songArtist.setEditable(edit);
    	songAlbum.setEditable(edit);
    	songYear.setEditable(edit);
    }
    
    void setVisibility(boolean visible) {
    	confirm.setVisible(visible);
    	cancel.setVisible(visible);
    }
    
    void showSong() {
    	Song selectedSong = songList.getSelectionModel().getSelectedItem();
    	songName.setText(selectedSong.name);
    	songArtist.setText(selectedSong.artist);
    	songAlbum.setText(selectedSong.album);
    	songYear.setText(selectedSong.year);
    }
    
    void clearDetails() {
    	songName.setText("");
    	songArtist.setText("");
    	songAlbum.setText("");
    	songYear.setText("");
    }
    
    void setStatus(String status) {
    	if(!status.contentEquals("deleting")) {
    		currentStatus.setText("Currently " + status + " song details");
    	}
    	else {
    		currentStatus.setText("Confirm delete?");
    	}
    
    	if(status.contentEquals("deleting")) {
    		setVisibility(true);
    	}
    	
    	if (status.contentEquals("viewing")) {
    		setVisibility(false);
    		setEditable(false);
    	}
    	if (status.contentEquals("adding") || status.contentEquals("editing")) {
    		setVisibility(true);
    		setEditable(true);
    	}
    }
}
