package com.overhauledmusic.client;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

/**
 * Looping, non-positional music that can fade in/out. We keep an "inactive" track
 * alive at volume 0 for a while so it can resume seamlessly if selected again.
 */
final class FadingMusicInstance extends AbstractTickableSoundInstance {
    private int fadeTicksRemaining = 0;
    private int fadeTicksTotal = 0;
    private float fadeFrom = 0f;
    private float fadeTo = 0f;

    private boolean active = false;
    private int inactiveTicks = 0;

    FadingMusicInstance(SoundEvent event) {
        super(event, SoundSource.MUSIC, net.minecraft.util.RandomSource.create());
        this.looping = true;
        this.delay = 0;
        this.volume = 0f;
        this.pitch = 1.0f;
        this.relative = true;
        this.attenuation = Attenuation.NONE;
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    void setActive(boolean active) {
        this.active = active;
        if (active) this.inactiveTicks = 0;
    }

    boolean isActiveTrack() {
        return active;
    }

    int getInactiveTicks() {
        return inactiveTicks;
    }

    void fadeTo(float targetVolume, int ticks) {
        this.fadeFrom = this.volume;
        this.fadeTo = targetVolume;
        this.fadeTicksTotal = Math.max(1, ticks);
        this.fadeTicksRemaining = this.fadeTicksTotal;
    }

    @Override
    public void tick() {
        if (!active) {
            inactiveTicks++;
        }

        if (fadeTicksRemaining > 0) {
            fadeTicksRemaining--;
            float t = 1.0f - (fadeTicksRemaining / (float) fadeTicksTotal);
            this.volume = fadeFrom + (fadeTo - fadeFrom) * t;
        } else {
            // Clamp a tiny negative/positive float jitter
            if (volume < 0f) volume = 0f;
            if (volume > 1f) volume = 1f;
        }
    }

    /**
     * Public-safe stop entry point.
     *
     * AbstractTickableSoundInstance.stop() is protected, so it cannot be called
     * directly from MusicDirector. This wrapper keeps the class in control of
     * how it stops while allowing the director to request a stop.
     */
    void stopSound() {
        super.stop();
    }
}
