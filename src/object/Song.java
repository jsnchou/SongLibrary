/**
 * Jason Chou
 * Ty Goldin
 * Group#44
 * 
 */



package object;

public class Song {

	public String name;
	public String artist;
	public String album;
	public String year;
	
	public Song(String name, String artist, String album, String year){
		this.setName(name);
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String toString(){
		return this.getName() + " by " + this.artist;
	}

	public String getName() {
		return name;
	}
	
}
