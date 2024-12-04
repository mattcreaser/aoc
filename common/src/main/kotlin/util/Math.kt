package util

import kotlin.math.sqrt

fun lcm(
    a: Long,
    b: Long,
) = (a * b) / gcd(a, b)

tailrec fun gcd(
    a: Long,
    b: Long,
): Long = if (b == 0L) a else gcd(b, a % b)

fun quadraticRoots(
    a: Float,
    b: Float,
    c: Float,
): Pair<Float, Float> {
    val discriminant = b * b - 4 * a * c
    val sqrtDiscriminant = sqrt(discriminant)
    return (-b + sqrtDiscriminant) / (2 * a) to (-b - sqrtDiscriminant) / (2 * a)
}
