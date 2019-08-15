package katas.twitter

import org.koin.core.KoinComponent

internal object KoinProxy : KoinComponent

internal val koinProxy = KoinProxy.getKoin()