package com.orange_infinity.onlinepay.useCase

import android.content.Context
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import com.orange_infinity.onlinepay.entities.model.Sound
import com.orange_infinity.onlinepay.util.MAIN_TAG
import java.io.IOException

private const val SOUND_FOLDER = "sounds"
private const val MAX_SOUNDS = 1

const val SUCCESS_PAYMENT_SOUND = "success_payment"

class SoundPlayer private constructor(context: Context) {

    private val assets: AssetManager = context!!.assets
    private val sounds = mutableListOf<Sound>()
    private val soundPool = SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0)

    init {
        loadSounds()
    }

    companion object {
        private var instance: SoundPlayer? = null

        fun getInstance(context: Context): SoundPlayer {
            if (instance == null) {
                return SoundPlayer(context)
                    .also { instance = it }
            }
            return instance as SoundPlayer
        }
    }

    fun standardPlay(fileName: String) {
        val sound: Sound = sounds.find { fileName == it.name } ?: return
        soundPool.play(sound.soundId ?: return, 1.0f, 1.0f, 1, 0, 1.0f)
    }

    fun playWithLoop(fileName: String, loop: Int) {
        val sound: Sound = sounds.find { fileName == it.name } ?: return
        soundPool.play(sound.soundId ?: return, 1.0f, 1.0f, 1, loop, 1.0f)
    }

    private fun loadSounds() {
        val soundNames: Array<String>?
        try {
            soundNames = assets.list(SOUND_FOLDER)
            Log.i(MAIN_TAG, "Found ${soundNames.size} sounds")
        } catch (e: IOException) {
            Log.e(MAIN_TAG, "Could not list assets", e)
            return
        }

        for (fileName in soundNames) {
            try {
                val assetPath = "$SOUND_FOLDER/$fileName"
                val sound = Sound(assetPath, "mp3")
                load(sound)
                sounds.add(sound)
            } catch (e: IOException) {
                Log.e(MAIN_TAG, "Could not load sound $fileName", e)
            }
        }
    }

    private fun load(sound: Sound) {
        val afd = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }
}