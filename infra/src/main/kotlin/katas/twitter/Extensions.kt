package katas.twitter

import org.koin.core.KoinComponent

internal object KoinProxy : KoinComponent

val koinProxy = KoinProxy.getKoin()