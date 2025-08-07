# Mobile Compatibility Documentation

## Overview

LiquidBounce has been enhanced to provide better compatibility with mobile Android environments, particularly with Fold Craft Launcher (FCL).

## Deep Learning Engine Compatibility

### Android Support

The Deep Learning Engine has been optimized for Android environments:

- **Automatic Platform Detection**: The system automatically detects Android runtime environments
- **FCL Integration**: When running under Fold Craft Launcher, the system uses FCL's runtime directory for optimal storage and performance
- **Fallback Handling**: On Android devices without FCL, the system provides clear guidance to users

### FCL (Fold Craft Launcher) Features

When FCL is detected (`/data/data/com.tungsten.fcl` directory exists):

1. **Runtime Directory Usage**: DJL cache and model storage uses `/data/data/com.tungsten.fcl/app_runtime/deeplearning/`
2. **Optimized Performance**: Better file system access and storage management
3. **Enhanced Compatibility**: Native integration with FCL's runtime environment

### Non-FCL Android Behavior

On Android devices without FCL:

- Deep learning features are disabled to prevent compatibility issues
- Clear error messages guide users to use FCL for full functionality
- Fallback to standard Android external storage paths when appropriate

## Directory Structure

```
FCL Runtime Directory:
/data/data/com.tungsten.fcl/app_runtime/
├── deeplearning/
│   ├── djl/          # DJL cache directory
│   ├── engines/      # Engine cache directory
│   └── models/       # ML models directory
```

## Error Messages

The system provides localized error messages when deep learning features are unavailable:

- English: "❌ The deep learning engine has not been initialized. Your system might not be supported. If you are on Android, try using Fold Craft Launcher to use deep learning."
- Similar messages available in German, Chinese, and Russian

## Technical Details

### Platform Detection Logic

```kotlin
private val isAndroid: Boolean = try {
    System.getProperty("java.vm.name")?.contains("Android", ignoreCase = true) == true ||
    System.getProperty("java.runtime.name")?.contains("Android", ignoreCase = true) == true ||
    File("/system/build.prop").exists()
} catch (_: Exception) {
    false
}
```

### FCL Detection Logic

```kotlin
private val isFCL: Boolean = try {
    isAndroid && File("/data/data/com.tungsten.fcl").exists()
} catch (_: Exception) {
    false
}
```

## Testing

Comprehensive test coverage includes:

- FCL detection logic validation
- Android platform detection
- DJL initialization requirements
- Runtime directory path management
- Error handling and fallback scenarios

## Requirements

- **For FCL Users**: FCL runtime directory must be accessible
- **For Non-FCL Android**: Deep learning features are disabled
- **For Desktop/Other Platforms**: Standard functionality maintained

This mobile compatibility layer ensures optimal performance and user experience across different Android environments while maintaining backward compatibility with existing desktop installations.