package com.ghc.edashboard.web.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ghc.edashboard.web.util.karaoke.Song;
import com.ghc.edashboard.web.util.karaoke.SongJson;

public class KaraokeParser {
	private final String mPageUrl; 
	private final int mFirstPage;
	private final int mLastPage;
	
	public KaraokeParser(String pageUrlFormat, int firstPage, int lastPage){
		mPageUrl = pageUrlFormat;
		mFirstPage = firstPage;
		mLastPage = lastPage;
	}
	
	public List<Song> parseSongs(){
		List<Song> songs = new ArrayList<Song>();
		
		for(int i = mFirstPage; i <= mLastPage; i++){
			String pageUrl = String.format(mPageUrl, i);
			
			List<Song> pageSongs = parseSongs(pageUrl);
			songs.addAll(pageSongs);
			
			// Sleep 1s
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
		
		return songs;
	}
	
	private List<Song> parseSongs(String pageUrl){
		List<Song> songs = new ArrayList<Song>();
		
		try {
			// timeout = 1 minute
			Document document = Jsoup.connect(pageUrl).timeout(60 * 1000).get();
			Element resultSongDiv = document.getElementById("resultSong");
			if(resultSongDiv != null){
				Elements songsDiv = resultSongDiv.getElementsByClass("song");
				if(songsDiv != null){
					for(Element songDiv : songsDiv){
						// ID
						String songId = "";
						Element songIdDiv = songDiv.getElementsByClass("songID").first();
						if(songIdDiv != null){
							songId = songIdDiv.text();
						}
						if(songId.length() == 0){
							continue;
						}
						
						// Vol
						String vol = "";
						if(songIdDiv != null){
							Element p = songIdDiv.select("span").first();
							if(p != null){
								vol = p.text();
							}
						}	
						// Name
						String name = "";
						Element nameDiv = songDiv.getElementsByClass("songName").first();
						if(nameDiv != null){
							name = nameDiv.text();
						}	
						// Lyric
						String lyric = "";
						Element lyricDiv = songDiv.getElementsByClass("songLyric").first();
						if(lyricDiv != null){
							lyric = lyricDiv.text();
						}	
						// Author
						String author = "";
						Element authorDiv = songDiv.getElementsByClass("author").first();
						if(authorDiv != null){
							author = authorDiv.text();
						}
											
						Song song = new Song();
						song.setCode(getCode(songId));
						song.setVol(vol);
						song.setTitle(name);
						song.setLyric(lyric);
						song.setMusician(author);
						
						songs.add(song);						
					}
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return songs;
	}
	
	public SongJson convertToJson(List<Song> songs){
		SongJson songJson = new SongJson();		
		songJson.setStatus("ok");
		songJson.setTotal(songs.size());
		songJson.setData(songs);
		
		return songJson;
	}	
	
	public String getCode(String text){
		Pattern pattern = Pattern.compile("^\\d+\\S");
		Matcher matcher = pattern.matcher(text);
		if(matcher.find()){
			return matcher.group(0);
		}
		return text;
	}
}
