package com.compression

import net.bramp.ffmpeg._
import net.bramp.ffmpeg.builder.FFmpegBuilder

object VideoCompression {
  def startCompression() = {
    val ffmpeg = new FFmpeg("/Users/bertmeeuws/Downloads/ffmpeg")
    val ffprobe = new FFprobe("/Users/bertmeeuws/Downloads/ffprobe_yes")

    val builder = new FFmpegBuilder().setInput("/Users/bertmeeuws/Movies/music.mp4") // Filename, or a FFmpegProbeResult
      .overrideOutputFiles(true) // Override the output if it exists
      .addOutput("/Users/bertmeeuws/Movies/music_output.mp4") // Filename for the destination
      .setFormat("mp4") // Format is inferred from filename, or can be set
      .disableSubtitle() // No subtiles
      .setAudioChannels(1) // Mono audio
      .setAudioCodec("aac") // using the aac codec
      .setAudioSampleRate(48_000) // at 48KHz
      .setAudioBitRate(32768) // at 32 kbit/s
      .setVideoCodec("libx264") // Video using x264
      .setVideoFrameRate(24, 1) // at 24 frames per second
      .setVideoResolution(640, 480) // at 640x480 resolution
      .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
      .done();

    val executor = new FFmpegExecutor(ffmpeg, ffprobe)

    // Run a one-pass encode
    executor.createJob(builder).run()

  }
}