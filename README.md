# Styled Text Compose

[![Maven Central](https://img.shields.io/maven-central/v/io.github.serioushyeon/styled-text-compose.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.github.serioushyeon/styled-text-compose)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A convenient library for styling text in Jetpack Compose with enhanced AnnotatedString functionality.

## Features

- 🎨 **Easy Text Styling**: Apply colors, fonts, sizes, and decorations to specific text parts
- 👆 **Click Events**: Add click handlers to specific text ranges
- 🔍 **Smart Text Finding**: Style text by content, indices, or regex patterns
- 📱 **Compose Integration**: Built specifically for Jetpack Compose
- 🚀 **Performance Optimized**: Efficient text processing and rendering
- 🛠 **Extension Functions**: Convenient helper functions for common use cases

## Installation

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("io.github.serioushyeon:styled-text-compose:1.0.0")
}
```

## Quick Start

### Basic Usage

```kotlin
import com.yourpackage.styledtext.*

@Composable
fun MyScreen() {
    StyledText("Hello World! This is amazing.") {
        styleText("Hello", color = Color.Red, fontWeight = FontWeight.Bold)
        styleText("World", color = Color.Blue, fontSize = 20.sp)
        styleText("amazing", background = Color.Yellow)
    }
}
```

### Using Extension Functions

```kotlin
// Bold text
Text(text = "Make this bold text stand out".bold("bold"))

// Highlight text
Text(text = "Highlight this important word".highlight("important"))

// Colorize text
Text(text = "Make this text red".colorize("red", Color.Red))
```

### Click Events

```kotlin
var clickCount by remember { mutableStateOf(0) }

StyledText("Click here to increment: $clickCount") {
    styleText("here", 
        color = Color.Blue, 
        textDecoration = TextDecoration.Underline
    ) {
        clickCount++
    }
}
```

## Advanced Features

### Style by Index Range

```kotlin
val text = "0123456789"
StyledText(text) {
    styleRange(0, 3, color = Color.Red)
    styleRange(3, 6, color = Color.Green)
    styleRange(6, 10, color = Color.Blue)
}
```

### Style All Occurrences

```kotlin
StyledText("Apple, Banana, Apple, Cherry") {
    styleAllOccurrences("Apple", 
        color = Color.Red, 
        fontWeight = FontWeight.Bold
    )
}
```

### Regular Expression Styling

```kotlin
StyledText("Email: test@example.com, Phone: 010-1234-5678") {
    // Style email addresses
    styleRegex(
        pattern = Regex("""\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b"""),
        color = Color.Blue,
        textDecoration = TextDecoration.Underline
    )
    
    // Style phone numbers
    styleRegex(
        pattern = Regex("""\d{3}-\d{4}-\d{4}"""),
        color = Color.Green
    )
}
```

### Complex Styling Example

```kotlin
@Composable
fun NotificationText() {
    var notificationCount by remember { mutableStateOf(5) }
    
    StyledText("You have $notificationCount new messages. Tap here to read all.") {
        styleText("$notificationCount", 
            color = Color.Red, 
            fontWeight = FontWeight.Bold,
            background = Color(0xFFFFE0E0)
        )
        styleText("here", 
            color = Color.Blue,
            textDecoration = TextDecoration.Underline
        ) {
            // Handle click
            notificationCount = 0
        }
    }
}
```

## API Reference

### StyledTextBuilder

Main builder class for creating styled text.

#### Methods

- `styleText(text: String, ...)`: Style first occurrence of text
- `styleText(text: String, occurrence: Int, ...)`: Style nth occurrence
- `styleAllOccurrences(text: String, ...)`: Style all occurrences
- `styleRange(start: Int, end: Int, ...)`: Style by index range
- `styleRegex(pattern: Regex, ...)`: Style by regex pattern
- `build()`: Create AnnotatedString

#### Style Parameters

- `color: Color?`: Text color
- `fontSize: TextUnit?`: Font size
- `fontWeight: FontWeight?`: Font weight (Bold, Normal, etc.)
- `fontStyle: FontStyle?`: Font style (Italic, Normal)
- `fontFamily: FontFamily?`: Font family
- `textDecoration: TextDecoration?`: Text decoration (Underline, LineThrough)
- `background: Color?`: Background color
- `onClick: (() -> Unit)?`: Click handler

### Extension Functions

- `String.styled()`: Create StyledTextBuilder
- `String.bold(text: String)`: Make text bold
- `String.highlight(text: String, color: Color)`: Highlight text
- `String.colorize(text: String, color: Color)`: Colorize text
- `String.clickable(text: String, onClick: () -> Unit)`: Make text clickable

### StyledText Composable

```kotlin
@Composable
fun StyledText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    styleBuilder: StyledTextBuilder.() -> Unit
)
```

## Requirements

- Android API 21+
- Jetpack Compose 1.5.0+
- Kotlin 1.9.0+

## License

```
Copyright 2025 Jihyeon Jin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Changelog

### 1.0.0
- Initial release
- Basic text styling functionality
- Click event support
- Regex pattern matching
- Extension functions
- Compose integration
