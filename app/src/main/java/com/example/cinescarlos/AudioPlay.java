package com.example.cinescarlos;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlay {


        public static MediaPlayer mediaPlayer;
        public static boolean isplayingAudio=false;
        public static void playAudio(Context c, int id){

        }


        public static void Sonidoinicio(Context c,int id){
            mediaPlayer = MediaPlayer.create(c,id);
            if(!mediaPlayer.isPlaying())
            {
                isplayingAudio=true;
                mediaPlayer.start();
            }
        }

        public static void Volumen(Context c, int id){
            mediaPlayer = MediaPlayer.create(c,id);
            mediaPlayer.setVolume(0.02f,0.02f);
            if(!mediaPlayer.isPlaying())
            {
                isplayingAudio=true;
                mediaPlayer.start();
            }
        }

}
