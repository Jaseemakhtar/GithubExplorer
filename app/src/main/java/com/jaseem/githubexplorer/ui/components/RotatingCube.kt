package com.jaseem.githubexplorer.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun RotatingCube(color: Color , modifier: Modifier) {
    val transition = rememberInfiniteTransition()
    val density = LocalDensity.current

    val pointRadius = remember { with(density) { 1.6.dp.toPx() } }
    val lineStroke = remember { with(density) { 1.dp.toPx() } }

    val radians: Float by transition.animateFloat(
        initialValue = 0.0f,
        targetValue = 0.08f,
        animationSpec = infiniteRepeatable(tween(1200, 0, LinearEasing), RepeatMode.Reverse)
    )

    val points: MutableList<Point> = remember {
        mutableListOf(
            Point(-0.2f, 0.2f, -0.2f),
            Point(0.2f, 0.2f, -0.2f),
            Point(0.2f, -0.2f, -0.2f),
            Point(-0.2f, -0.2f, -0.2f),

            Point(-0.2f, 0.2f, 0.2f),
            Point(0.2f, 0.2f, 0.2f),
            Point(0.2f, -0.2f, 0.2f),
            Point(-0.2f, -0.2f, 0.2f),
        )
    }

    Canvas(modifier = modifier) {

        val width = size.width
        val height = size.height

        val boxEdge = min(width, height)

        translate(width / 2f, height / 2f) {
            for (i in points.indices) {
                points[i] = matrixMultiply(points[i], getYRotationMatrix(radians))
                points[i] = matrixMultiply(points[i], getZRotationMatrix(radians))
                points[i] = matrixMultiply(points[i], getXRotationMatrix(radians))

                drawCircle(color, pointRadius, center = points[i].scaleToOffset(boxEdge))
            }

            for (i in 0..3) {
                val nextNeighbour = (i + 1) % 4

                drawLine(
                    color = color,
                    start = points[i].scaleToOffset(boxEdge),
                    end = points[nextNeighbour].scaleToOffset(boxEdge),
                    strokeWidth = lineStroke
                )

                drawLine(
                    color = color,
                    start = points[i].scaleToOffset(boxEdge),
                    end = points[i + 4].scaleToOffset(boxEdge),
                    strokeWidth = lineStroke
                )

                drawLine(
                    color = color,
                    start = points[i + 4].scaleToOffset(boxEdge),
                    end = points[nextNeighbour + 4].scaleToOffset(boxEdge),
                    strokeWidth = lineStroke
                )
            }
        }
    }
}

data class Point(
    val x: Float,
    val y: Float,
    val z: Float = 0f
) {
    fun toMatrix() = listOf(listOf(x), listOf(y), listOf(z))

    fun scaleToOffset(factor: Float) = Offset(x * factor, y * factor)
}

fun matrixMultiply(point: Point, matrix: List<List<Float>>): Point {
    val currentPoint = point.toMatrix()
    val result = Array(3) { 0f }

    for (i in matrix.indices) {
        var summation = 0f
        for (j in matrix.first().indices) {
            summation += matrix[i][j] * currentPoint[j][0]
        }
        result[i] = summation
    }

    return Point(result[0], result[1], result[2])
}

fun getXRotationMatrix(radians: Float) = listOf(
    listOf(1.0f, 0.0f, 0.0f),
    listOf(0.0f, cos(radians), -sin(radians)),
    listOf(0.0f, sin(radians), cos(radians)),
)

fun getYRotationMatrix(radians: Float) = listOf(
    listOf(cos(radians), 0.0f, sin(radians)),
    listOf(0.0f, 1.0f, 0.0f),
    listOf(-sin(radians), 0.0f, cos(radians)),
)

fun getZRotationMatrix(radians: Float) = listOf(
    listOf(cos(radians), -sin(radians), 0.0f),
    listOf(sin(radians), cos(radians), 0.0f),
    listOf(0.0f, 0.0f, 1.0f),
)
