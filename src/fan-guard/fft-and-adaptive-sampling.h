#ifndef FFT_ADAPTIVE_SAMPLING_H
#define FFT_ADAPTIVE_SAMPLING_H


#include <Arduino.h>
#include <arduinoFFT.h>
#include <math.h>
#include "freertos/FreeRTOS.h"
#include "freertos/task.h"
#include "driver/uart.h"
#include "esp_sleep.h"
#include "shared-defs.h"
#include <MPU6500_WE.h>


// Constants
#define FFT_SAMPLE_RATE 1024      // Fft sampling rate (Hz)
#define INIT_SAMPLE_RATE 512      // Initial sampling rate (Hz)
#define SESSION_DURATION_SEC 5    // Length of one RMS session
#define NOISE_THRESHOLD 0.5        // Minimum magnitude to consider a frequency component
#define NYQUIST_MULTIPLIER 2.5     // Safety factor for Nyquist rate
#define ANOMALY_THRESHOLD 0.5      // Threshold for anomaly detection

// FFT phase flags
extern volatile bool fft_init_complete;

// Global variables
extern  int g_sampling_frequency;
extern float samples_real[INIT_SAMPLE_RATE];
extern float samples_imag[INIT_SAMPLE_RATE];
extern  int g_window_size;
extern  int num_of_samples;
extern float features[3];
extern  float session_sum_sq[3];     // Σ(x²) per axis

// FFT instance
extern ArduinoFFT<float> FFT;

// Core functions
void fft_init();
void fft_sampling_task(void *pvParameters);
void send_anomaly_task(void *pvParameters);

// Signal processing
void fft_perform_analysis(void);
float fft_get_max_frequency(void);
void fft_sample_signal();
void fft_adjust_sampling_rate(float max_freq);

// Data handling
void read_sample(float* x, float* y, float* z);
int raw_feature_get_data(size_t offset, size_t length, float *out_ptr);

// Communication
void send_rms(float* avg);
void send_anomaly(unsigned long ts, float* rms_values);

// Power management
void light_sleep(int duration);
void deep_sleep(int duration);

// Anomaly detection
bool anomaly_detection(float rms_x, float rms_y, float rms_z);

#endif // FFT_ADAPTIVE_SAMPLING_H