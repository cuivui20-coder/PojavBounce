# PojavBounce Mobile Compatibility Guide

This document describes the mobile/Android compatibility enhancements made to PojavBounce for running on PojavLauncher.

## Overview

PojavBounce has been updated to support mobile environments, specifically when running under PojavLauncher on Android devices. The changes ensure that DJL (Deep Java Library) can function properly in the Android environment while maintaining compatibility with desktop systems.

## Changes Made

### 1. Mobile-Compatible Dependencies

- **TensorFlow Lite**: Added `org.tensorflow:tensorflow-lite:2.16.1` for lightweight mobile inference
- **DJL Engine**: Configured PyTorch engine with mobile-friendly settings
- **Native Libraries**: Prepared jniLibs directory structure for ARM native libraries

### 2. Android-Aware Cache Directories

The `DeepLearningEngine` now detects Android environments and adjusts cache directories accordingly:

- **Desktop**: Uses standard `rootFolder/deeplearning/` structure
- **Android**: Uses `/storage/emulated/0/Android/data/deeplearning/` for better compatibility

### 3. JNI Libraries Structure

Created the standard Android JNI directory structure:
```
src/main/jniLibs/
├── armeabi-v7a/    # 32-bit ARM libraries
└── arm64-v8a/      # 64-bit ARM libraries
```

### 4. Mobile-Optimized Configuration

When running on Android, the following optimizations are applied:
- Disabled graph optimization for better mobile performance
- Set inference-only mode (no training)
- Configured Android-friendly file paths
- Disabled DJL tracking for privacy

## Usage on PojavLauncher

When this mod runs on PojavLauncher:

1. **Automatic Detection**: The system automatically detects the Android environment
2. **Cache Setup**: Cache directories are configured for Android storage
3. **Engine Selection**: DJL selects the most appropriate engine for the platform
4. **Native Libraries**: Native ARM libraries are loaded from the jniLibs structure

## Testing

A comprehensive test suite (`MobileCompatibilityTest`) validates:
- DJL engine initialization
- Cache directory configuration
- JNI library structure
- Privacy settings (tracking disabled)

## Build Configuration

The Gradle build includes:
- Mobile-compatible dependencies
- Custom task for native library extraction
- Android-friendly directory structure creation

## Performance Considerations

For optimal performance on mobile devices:
- Models are limited to inference-only operations
- CPU execution is preferred over GPU for broader compatibility
- Cache directories use Android external storage for better access

## Compatibility

This implementation maintains full backward compatibility with desktop environments while adding mobile support. The system automatically adapts based on the detected runtime environment.