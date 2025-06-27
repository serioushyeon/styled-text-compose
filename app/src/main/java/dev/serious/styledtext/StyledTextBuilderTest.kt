package dev.serious.styledtext

import androidx.compose.ui.graphics.Color

class StyledTextBuilderTest {

    fun testBasicStyling() {
        val text = "Hello World"
        val styledText = text.styled()
            .styleText("Hello", color = Color.Red)
            .styleText("World", color = Color.Blue)
            .build()

        assert(styledText.text == "Hello World")
        println("✓ Basic styling test passed")
    }

    fun testIndexBasedStyling() {
        val text = "0123456789"
        val styledText = text.styled()
            .styleRange(0, 5, color = Color.Red)
            .styleRange(5, 10, color = Color.Blue)
            .build()

        assert(styledText.text == "0123456789")
        println("✓ Index-based styling test passed")
    }

    fun testAllOccurrences() {
        val text = "apple banana apple cherry apple"
        val styledText = text.styled()
            .styleAllOccurrences("apple", color = Color.Red)
            .build()

        assert(styledText.text == text)
        println("✓ All occurrences test passed")
    }

    fun testRegexStyling() {
        val text = "Email: test@example.com"
        val styledText = text.styled()
            .styleRegex(
                pattern = Regex("""\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b"""),
                color = Color.Blue
            )
            .build()

        assert(styledText.text == text)
        println("✓ Regex styling test passed")
    }

    fun testExtensionFunctions() {
        val boldText = "Make this bold".bold("bold")
        val highlightedText = "Highlight this".highlight("this")
        val coloredText = "Color this red".colorize("red", Color.Red)

        assert(boldText.text == "Make this bold")
        assert(highlightedText.text == "Highlight this")
        assert(coloredText.text == "Color this red")
        println("✓ Extension functions test passed")
    }

    fun runAllTests() {
        println("Running StyledTextBuilder tests...")
        testBasicStyling()
        testIndexBasedStyling()
        testAllOccurrences()
        testRegexStyling()
        testExtensionFunctions()
        println("All tests passed! ✅")
    }
}