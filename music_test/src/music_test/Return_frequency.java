package music_test;
import java.util.Arrays;

    // FFT based pitch detector. seems to work best with block sizes of 4096
    public class Return_frequency
    {
        private float sampleRate;
        static PitchShift shifter = new PitchShift(44100);

        public Return_frequency(float sampleRate)
        {
            this.sampleRate = sampleRate;
        }

        // http://en.wikipedia.org/wiki/Window_function
        private float HammingWindow(int n, int N) 
        {
            return 0.54f - 0.46f * (float)Math.cos((2 * Math.PI * n) / (N - 1));
        }

        private float[] fftBuffer;
        private float[] prevBuffer;
        public float DetectPitch(float[] buffer, int inFrames)
        {
            if (prevBuffer == null)
            {
                prevBuffer = new float[inFrames];
            }

            // double frames since we are combining present and previous buffers
            int frames = inFrames * 2;
            if (fftBuffer == null)
            {
                fftBuffer = new float[frames * 2]; // times 2 because it is complex input
            }

            for (int n = 0; n < frames; n++)
            {
                if (n < inFrames)
                {
                    fftBuffer[n * 2] = prevBuffer[n] * HammingWindow(n, frames);
                    fftBuffer[n * 2 + 1] = 0; // need to clear out as fft modifies buffer
                }
                else
                {
                    fftBuffer[n * 2] = buffer[n-inFrames] * HammingWindow(n, frames);
                    fftBuffer[n * 2 + 1] = 0; // need to clear out as fft modifies buffer
                }
            }

            // assuming frames is a power of 2
            shifter.smbFft(fftBuffer, frames, -1);

            float binSize = sampleRate / frames;
            int minBin = (int)(85 / binSize);
            int maxBin = (int)(300 / binSize);
            float maxIntensity = 0f;
            int maxBinIndex = 0;
            for (int bin = minBin; bin <= maxBin; bin++)
            {
                float real = fftBuffer[bin * 2];
                float imaginary = fftBuffer[bin * 2 + 1];
                float intensity = real * real + imaginary * imaginary;
                if (intensity > maxIntensity)
                {
                    maxIntensity = intensity;
                    maxBinIndex = bin;
                }
            }
            
            return binSize * maxBinIndex;
            
        }
    }


