package dev.serious.styledtext

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

/**
 * 텍스트 스타일링을 위한 데이터 클래스
 */
data class TextStyleConfig(
    val modifier: Modifier = Modifier,
    val color: Color? = null,
    val fontSize: TextUnit? = null,
    val fontWeight: FontWeight? = null,
    val fontStyle: FontStyle? = null,
    val fontFamily: FontFamily? = null,
    val textDecoration: TextDecoration? = null,
    val background: Color? = null,
    val onClick: (() -> Unit)? = null
)

/**
 * 스타일을 적용할 텍스트 범위를 정의하는 클래스
 */
sealed class TextRange {
    data class ByIndices(val start: Int, val end: Int) : TextRange()
    data class ByText(val text: String, val occurrence: Int = 1) : TextRange()
    data class AllOccurrences(val text: String) : TextRange()
    data class ByRegex(val pattern: Regex) : TextRange()
}

/**
 * 스타일 규칙을 정의하는 클래스
 */
data class StyleRule(
    val range: TextRange,
    val style: TextStyleConfig,
    val tag: String? = null
)

/**
 * StyledTextBuilder - AnnotatedString을 쉽게 생성하기 위한 빌더 클래스
 */
class StyledTextBuilder(private val baseText: String) {
    private val styleRules = mutableListOf<StyleRule>()
    private var clickHandlers = mutableMapOf<String, () -> Unit>()

    /**
     * 인덱스 기반으로 스타일 적용
     */
    fun styleRange(
        start: Int,
        end: Int,
        color: Color? = null,
        fontSize: TextUnit? = null,
        fontWeight: FontWeight? = null,
        fontStyle: FontStyle? = null,
        fontFamily: FontFamily? = null,
        textDecoration: TextDecoration? = null,
        background: Color? = null,
        onClick: (() -> Unit)? = null
    ): StyledTextBuilder {
        val style = TextStyleConfig(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            background = background,
            onClick = onClick
        )
        styleRules.add(StyleRule(TextRange.ByIndices(start, end), style))
        return this
    }

    /**
     * 특정 텍스트를 찾아서 스타일 적용 (첫 번째 발견 위치에 적용)
     */
    fun styleText(
        text: String,
        modifier: Modifier = Modifier,
        color: Color? = null,
        fontSize: TextUnit? = null,
        fontWeight: FontWeight? = null,
        fontStyle: FontStyle? = null,
        fontFamily: FontFamily? = null,
        textDecoration: TextDecoration? = null,
        background: Color? = null,
        onClick: (() -> Unit)? = null
    ): StyledTextBuilder {
        val style = TextStyleConfig(
            modifier = modifier,
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            background = background,
            onClick = onClick
        )
        styleRules.add(StyleRule(TextRange.ByText(text), style))
        return this
    }

    /**
     * 특정 텍스트를 찾아서 스타일 적용 (n번째 발견 위치에 적용)
     */
    fun styleText(
        text: String,
        occurrence: Int,
        color: Color? = null,
        fontSize: TextUnit? = null,
        fontWeight: FontWeight? = null,
        fontStyle: FontStyle? = null,
        fontFamily: FontFamily? = null,
        textDecoration: TextDecoration? = null,
        background: Color? = null,
        onClick: (() -> Unit)? = null
    ): StyledTextBuilder {
        val style = TextStyleConfig(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            background = background,
            onClick = onClick
        )
        styleRules.add(StyleRule(TextRange.ByText(text, occurrence), style))
        return this
    }

    /**
     * 특정 텍스트의 모든 발견 위치에 스타일 적용
     */
    fun styleAllOccurrences(
        text: String,
        color: Color? = null,
        fontSize: TextUnit? = null,
        fontWeight: FontWeight? = null,
        fontStyle: FontStyle? = null,
        fontFamily: FontFamily? = null,
        textDecoration: TextDecoration? = null,
        background: Color? = null,
        onClick: (() -> Unit)? = null
    ): StyledTextBuilder {
        val style = TextStyleConfig(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            background = background,
            onClick = onClick
        )
        styleRules.add(StyleRule(TextRange.AllOccurrences(text), style))
        return this
    }

    /**
     * 정규표현식으로 매칭되는 텍스트에 스타일 적용
     */
    fun styleRegex(
        pattern: Regex,
        color: Color? = null,
        fontSize: TextUnit? = null,
        fontWeight: FontWeight? = null,
        fontStyle: FontStyle? = null,
        fontFamily: FontFamily? = null,
        textDecoration: TextDecoration? = null,
        background: Color? = null,
        onClick: (() -> Unit)? = null
    ): StyledTextBuilder {
        val style = TextStyleConfig(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            background = background,
            onClick = onClick
        )
        styleRules.add(StyleRule(TextRange.ByRegex(pattern), style))
        return this
    }

    /**
     * AnnotatedString 빌드
     */
    fun build(): AnnotatedString {
        return buildAnnotatedString {
            append(baseText)

            styleRules.forEachIndexed { ruleIndex, rule ->
                val ranges = when (rule.range) {
                    is TextRange.ByIndices -> {
                        listOf(rule.range.start to rule.range.end)
                    }
                    is TextRange.ByText -> {
                        findTextOccurrence(baseText, rule.range.text, rule.range.occurrence)
                    }
                    is TextRange.AllOccurrences -> {
                        findAllTextOccurrences(baseText, rule.range.text)
                    }
                    is TextRange.ByRegex -> {
                        findRegexMatches(baseText, rule.range.pattern)
                    }
                }

                ranges.forEach { (start, end) ->
                    if (start >= 0 && end <= baseText.length && start < end) {
                        val spanStyle = SpanStyle(
                            color = rule.style.color ?: Color.Unspecified,
                            fontSize = rule.style.fontSize ?: TextUnit.Unspecified,
                            fontWeight = rule.style.fontWeight,
                            fontStyle = rule.style.fontStyle,
                            fontFamily = rule.style.fontFamily,
                            textDecoration = rule.style.textDecoration,
                            background = rule.style.background ?: Color.Unspecified,
                        )
                        addStyle(spanStyle, start, end)

                        if (rule.style.onClick != null) {
                            val tag = "clickable_${ruleIndex}_${start}_${end}"
                            addStringAnnotation(
                                tag = "clickable",
                                annotation = tag,
                                start = start,
                                end = end
                            )
                            clickHandlers[tag] = rule.style.onClick
                        }
                    }
                }
            }
        }
    }

    /**
     * 클릭 핸들러 맵 반환
     */
    fun getClickHandlers(): Map<String, () -> Unit> = clickHandlers.toMap()

    private fun findTextOccurrence(text: String, target: String, occurrence: Int): List<Pair<Int, Int>> {
        var count = 0
        var startIndex = 0

        while (startIndex < text.length) {
            val index = text.indexOf(target, startIndex)
            if (index == -1) break

            count++
            if (count == occurrence) {
                return listOf(index to index + target.length)
            }
            startIndex = index + 1
        }
        return emptyList()
    }

    private fun findAllTextOccurrences(text: String, target: String): List<Pair<Int, Int>> {
        val occurrences = mutableListOf<Pair<Int, Int>>()
        var startIndex = 0

        while (startIndex < text.length) {
            val index = text.indexOf(target, startIndex)
            if (index == -1) break

            occurrences.add(index to index + target.length)
            startIndex = index + 1
        }
        return occurrences
    }

    private fun findRegexMatches(text: String, pattern: Regex): List<Pair<Int, Int>> {
        return pattern.findAll(text).map { matchResult ->
            matchResult.range.first to matchResult.range.last + 1
        }.toList()
    }
}

/**
 * 확장 함수들
 */

/**
 * String에서 StyledTextBuilder 생성
 */
fun String.styled(): StyledTextBuilder = StyledTextBuilder(this)

/**
 * 간편한 스타일 적용을 위한 확장 함수들
 */

fun String.bold(text: String): AnnotatedString {
    return this.styled()
        .styleText(text, fontWeight = FontWeight.Bold)
        .build()
}

fun String.highlight(text: String, color: Color = Color.Yellow): AnnotatedString {
    return this.styled()
        .styleText(text, background = color)
        .build()
}

fun String.colorize(text: String, color: Color): AnnotatedString {
    return this.styled()
        .styleText(text, color = color)
        .build()
}

fun String.clickable(text: String, onClick: () -> Unit): Pair<AnnotatedString, Map<String, () -> Unit>> {
    val builder = this.styled()
        .styleText(text, color = Color.Blue, textDecoration = TextDecoration.Underline, onClick = onClick)
    return builder.build() to builder.getClickHandlers()
}

/**
 * Compose UI를 위한 컴포넌트들
 */

/**
 * 스타일이 적용된 텍스트를 표시하는 Composable
 */
@Composable
fun StyledText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    styleBuilder: StyledTextBuilder.() -> Unit
) {
    val builder = remember(text) { StyledTextBuilder(text) }
    builder.styleBuilder()

    val annotatedString = remember(builder) { builder.build() }
    val clickHandlers = remember(builder) { builder.getClickHandlers() }

    if (clickHandlers.isNotEmpty()) {
        var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

        Text(
            text = annotatedString,
            modifier = modifier.pointerInput(Unit) {
                detectTapGestures { offsetPos ->
                    layoutResult?.let { layout ->
                        val offset = layout.getOffsetForPosition(offsetPos)
                        annotatedString.getStringAnnotations(
                            tag = "clickable",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let { annotation ->
                            clickHandlers[annotation.item]?.invoke()
                        }
                    }
                }
            },
            onTextLayout = { layoutResult = it },
            style = style
        )
    } else {
        Text(
            text = annotatedString,
            modifier = modifier,
            style = style
        )
    }
}
/**
 * 사용 예시
 */
/*
// 기본 사용법
val styledText = "Hello World! This is a test."
    .styled()
    .styleText("Hello", color = Color.Red, fontWeight = FontWeight.Bold)
    .styleText("World", color = Color.Blue, fontSize = 20.sp)
    .styleText("test", background = Color.Yellow)
    .build()

// 확장 함수 사용법
val boldText = "Make this bold text stand out".bold("bold text")
val highlightedText = "Highlight this important text".highlight("important")
val coloredText = "Color this red text".colorize("red", Color.Red)

// 클릭 가능한 텍스트
val (clickableText, handlers) = "Click here to continue".clickable("here") {
    println("Clicked!")
}

// Composable에서 사용
@Composable
fun MyScreen() {
    StyledText("Welcome to our app! Click here to get started.") {
        styleText("Welcome", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        styleText("here", color = Color.Blue, textDecoration = TextDecoration.Underline) {
            // 클릭 이벤트 처리
        }
    }
}
*/