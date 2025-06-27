package dev.serious.styledtext

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StyledTextExamples() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "StyledTextBuilder 사용 예시",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        HorizontalDivider()

        // 예시 1: 기본 스타일링
        Text("예시 1: 기본 스타일링", fontWeight = FontWeight.Bold)
        StyledText("안녕하세요! 이것은 테스트 문장입니다.") {
            styleText("안녕하세요", color = Color.Red, fontWeight = FontWeight.Bold)
            styleText("테스트", color = Color.Blue, fontSize = 20.sp)
            styleText("문장", background = Color.Yellow)
        }

        HorizontalDivider()

        // 예시 2: 클릭 가능한 텍스트
        Text("예시 2: 클릭 가능한 텍스트", fontWeight = FontWeight.Bold)
        var clickCount by remember { mutableStateOf(0) }
        Column {
            StyledText("여기를 클릭하면 카운트가 증가합니다. 현재: $clickCount") {
                styleText("여기를",
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                ) {
                    clickCount++
                }
                styleText("$clickCount",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        HorizontalDivider()

        // 예시 3: 인덱스 기반 스타일링
        Text("예시 3: 인덱스 기반 스타일링", fontWeight = FontWeight.Bold)
        val text = "0123456789"
        StyledText(text) {
            styleRange(0, 3, color = Color.Red, fontWeight = FontWeight.Bold)
            styleRange(3, 6, color = Color.Green, fontSize = 18.sp)
            styleRange(6, 10, color = Color.Blue, background = Color.LightGray)
        }

        HorizontalDivider()

        // 예시 4: 모든 발생 스타일링
        Text("예시 4: 모든 발생 스타일링", fontWeight = FontWeight.Bold)
        StyledText("Apple, Banana, Apple, Cherry, Apple, Banana") {
            styleAllOccurrences("Apple",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                background = Color(0xFFFFE0E0)
            )
            styleText("Banana", color = Color(0xFFFFD700))
            styleText("Cherry", color = Color(0xFF8B0000))
        }

        HorizontalDivider()

        // 예시 5: 정규표현식 사용
        Text("예시 5: 정규표현식 사용", fontWeight = FontWeight.Bold)
        StyledText("이메일: test@example.com, 전화: 010-1234-5678") {
            styleRegex(
                pattern = Regex("""\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b"""),
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
            styleRegex(
                pattern = Regex("""\d{3}-\d{4}-\d{4}"""),
                color = Color.Green,
                fontWeight = FontWeight.Bold
            )
        }

        HorizontalDivider()

        // 예시 6: 확장 함수 사용
        Text("예시 6: 확장 함수 사용", fontWeight = FontWeight.Bold)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "이 텍스트를 굵게 만들어보세요".bold("굵게")
            )
            Text(
                text = "이 중요한 내용을 강조해보세요".highlight("중요한", Color.Cyan)
            )
            Text(
                text = "이 빨간 글자를 확인하세요".colorize("빨간", Color.Red)
            )
        }

        HorizontalDivider()

        // 예시 7: 복합 스타일링
        Text("예시 7: 복합 스타일링", fontWeight = FontWeight.Bold)
        var notificationCount by remember { mutableIntStateOf(3) }
        StyledText("새로운 알림이 $notificationCount 개 있습니다. 모두 읽기를 클릭하세요.") {
            styleText("새로운", color = Color.Blue, fontWeight = FontWeight.Bold)
            styleText("$notificationCount 개",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                background = Color(0xFFFFE0E0)
            )
            styleText("모두 읽기",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            ) {
                notificationCount = 0
            }
        }
    }
}