package utlis

import kotlin.random.Random
import kotlin.random.nextInt


fun providerId(): Int = Random.nextInt(0..10)
fun page(): Int = Random.nextInt(0..10)
fun size(): Int = Random.nextInt(0..100)