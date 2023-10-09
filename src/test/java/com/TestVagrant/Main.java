package com.TestVagrant;
import java.util.*;

class LRUSongStore {
    private final int capacity;
    private final Map<String, Node> userSongMap;
    private final Deque<Node> recentlyPlayed;

    public LRUSongStore(int capacity) {
        this.capacity = capacity;
        this.userSongMap = new HashMap<>();
        this.recentlyPlayed = new LinkedList<>();
    }

    public void playSong(String user, String song) {
    	
        if (userSongMap.containsKey(user) && userSongMap.get(user).song.equals(song)) {
            return; 
        }

        if (recentlyPlayed.size() >= capacity) {
            Node removedNode = recentlyPlayed.removeLast();
            userSongMap.remove(removedNode.user);
        }

        Node newNode = new Node(user, song);
        recentlyPlayed.addFirst(newNode);
        userSongMap.put(user, newNode);
    }

    public List<String> getRecentlyPlayed(String user) {
        List<String> result = new ArrayList<>();
        Node node = userSongMap.get(user);
        while (node != null) {
            result.add(node.song);
            node = node.next;
        }
        return result;
    }

    private static class Node {
        String user;
        String song;
        Node next;

        Node(String user, String song) {
            this.user = user;
            this.song = song;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        LRUSongStore songStore = new LRUSongStore(3);
        
        songStore.playSong("user1", "S1");
        songStore.playSong("user1", "S2");
        songStore.playSong("user1", "S3");
        System.out.println(songStore.getRecentlyPlayed("user1")); 

        songStore.playSong("user1", "S4");
        System.out.println(songStore.getRecentlyPlayed("user1")); 

        songStore.playSong("user1", "S2");
        System.out.println(songStore.getRecentlyPlayed("user1")); 
    }
}
