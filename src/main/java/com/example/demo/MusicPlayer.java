package com.example.demo;

import java.time.LocalDate;
import java.util.*;

class Artist {
    String artistname;
    HashMap<Song, Integer> top10songs = new HashMap<>();
    List<Song> allsongs=new ArrayList<>();
    int minncount = Integer.MAX_VALUE;
    Song minimumtopsong;
    public void retreive(){
        for(Song it:allsongs){
            System.out.println(it.title);
        }
    }
    public Artist(String artistname) {
        this.artistname = artistname;
    }
    public void gettop10songartist(){
//        List<Song> result=new ArrayList<>();
        for(Song it:top10songs.keySet()){
            System.out.println(it.title +" - "+top10songs.get(it));
//            result.add(it);
        }
//        return result;
    }
    public void playsongartist(Song songname){
        if(top10songs.size()<10) {
            if (top10songs.containsKey(songname)) {
                top10songs.put(songname, top10songs.get(songname) + 1);
                if(minimumtopsong==songname) minncount++;
            } else {
                top10songs.put(songname, songname.playCount);
                if(songname.playCount>minncount){
                    minncount=Integer.MAX_VALUE;
                    minimumtopsong=null;
                    for(Song it:top10songs.keySet()){
                        if(minncount>it.playCount){
                            minncount=it.playCount;
                            minimumtopsong=songname;
                        }
                    }
                }
            }
        }
        else if(songname.playCount>minncount){
            //already 10 songs are there
            //need to find new minimum
            top10songs.remove(minimumtopsong);
            top10songs.put(songname,songname.playCount);
            minncount=Integer.MAX_VALUE;
            minimumtopsong=null;
            for(Song it:top10songs.keySet()){
                if(minncount>it.playCount){
                    minncount=it.playCount;
                    minimumtopsong=songname;
                }
            }
        }
    }

}
class Song{
    String title;
    int playCount=0;
    public Song(String name){
        title=name;
    }
}
class Dates{
    LocalDate myObj;
    HashMap<LocalDate, HashMap<Song, Integer>> map = new HashMap<>(); // date, top10songs
    HashMap<LocalDate, Song> minimummap = new HashMap<>();
    HashMap<LocalDate, Integer> minimumcount = new HashMap<>();

    public Dates() {
        myObj = LocalDate.now();
    }

    public void nextDay() {
        System.out.println("local date earlier was " + myObj);
        myObj = myObj.plusDays(1);
        System.out.println("local date now is " + myObj);
    }

    public void gettop10songsdate(LocalDate d) {
        if (map.containsKey(d)) {
            for (Song it : map.get(d).keySet()) {
                System.out.println(it.title + " - " + it.playCount);
            }
        } else {
            System.out.println("No songs played on this date.");
        }
    }

    public void playsongdate(LocalDate d, Song songname) {
        map.putIfAbsent(d, new HashMap<>());
        HashMap<Song, Integer> top10songs = map.get(d);

        Integer minncount = minimumcount.getOrDefault(d, Integer.MAX_VALUE);
        Song minimumtopsong = minimummap.get(d);

        if (top10songs.size() < 10) {
            if (top10songs.containsKey(songname)) {
                top10songs.put(songname, top10songs.get(songname) + 1);
                if (minimumtopsong == songname) minncount++;
            } else {
                top10songs.put(songname, songname.playCount);
                if (songname.playCount > minncount) {
                    minncount = Integer.MAX_VALUE;
                    minimumtopsong = null;
                    for (Song it : top10songs.keySet()) {
                        if (minncount > it.playCount) {
                            minncount = it.playCount;
                            minimumtopsong = it;
                        }
                    }
                }
            }
        } else if (songname.playCount > minncount) {
            top10songs.remove(minimumtopsong);
            top10songs.put(songname, songname.playCount);
            minncount = Integer.MAX_VALUE;
            minimumtopsong = null;
            for (Song it : top10songs.keySet()) {
                if (minncount > it.playCount) {
                    minncount = it.playCount;
                    minimumtopsong = it;
                }
            }
        }
        map.put(d, top10songs);
        minimumcount.put(d, minncount);
        minimummap.put(d, minimumtopsong);
    }
}
public class MusicPlayer{
    Dates date=new Dates();
    HashMap<Song,Integer> top10songs=new HashMap<>();//wrt total count
    HashMap<Song,Artist> songs=new HashMap<>();
    int minncount=Integer.MAX_VALUE;
    Song minimumtopsong;
    HashMap<String ,Song> findingmap1=new HashMap<>();
    HashMap<String ,Artist> findingmap2=new HashMap<>();
    public void AddSong(String songname, String artistname) {
        Song song = new Song(songname);
        Artist artist;
        if (findingmap2.containsKey(artistname)) {
            artist = findingmap2.get(artistname);
            artist.allsongs.add(song);
        } else {
            artist = new Artist(artistname);
            findingmap2.put(artistname, artist);
            artist.allsongs.add(song);
        }
        findingmap1.put(songname, song);
        songs.put(song, artist);
    }
    public void AddSong(String songname){
        Song song =new Song(songname);
        Artist artist=new Artist("dummy");
        artist.allsongs.add(song);
        findingmap1.put(songname,song);
        songs.put(song ,artist);
    }
    public void gettop10song(){
        for(Song it:top10songs.keySet()){
            System.out.println(it.title +" - "+top10songs.get(it));
        }
    }
    public void gettop10songbyartist(String artistName) {
        if (!findingmap2.containsKey(artistName)) {
            System.out.println("Artist not found: " + artistName);
            return;
        }
        Artist artist = findingmap2.get(artistName);
        System.out.println("Top 10 songs by " + artistName + ":");
        artist.gettop10songartist();
    }

    public void playsong(String s){
        if(!findingmap1.containsKey(s)) return;
        Song songname=findingmap1.get(s);
        if(songname==null) return;
        songname.playCount++;
        songs.get(songname).playsongartist(songname);
        date.playsongdate(date.myObj,songname);
        if (top10songs.size() < 10) {
            if (top10songs.containsKey(songname)) {
                top10songs.put(songname, top10songs.get(songname) + 1);
            } else {
                top10songs.put(songname, songname.playCount);
            }
        } else if (minncount < songname.playCount) {
            top10songs.remove(minimumtopsong);
            top10songs.put(songname, songname.playCount);
        }

        // Recalculate the minimum play count and minimum top song
        minncount = Integer.MAX_VALUE;
        minimumtopsong = null;
        for (Song it : top10songs.keySet()) {
            if (minncount > it.playCount) {
                minncount = it.playCount;
                minimumtopsong = it;
            }
        }
    }
    public void retrivesong(String artistName){
        if (!findingmap2.containsKey(artistName)) {
            System.out.println("Artist not found: " + artistName);
            return;
        }
        Artist artist = findingmap2.get(artistName);
        artist.retreive();
    }
    public void GotoNextDay(){
        date.nextDay();
    }
    public void gettop10songbydate(LocalDate d){
        date.gettop10songsdate(d);
    }
//    public void PrintSonglist(List<Song> l){
//        int n= l.size();
//        for(int i=0;i<n;i++){
//            System.out.println(l.get(i).title);
//        }
//    }
}