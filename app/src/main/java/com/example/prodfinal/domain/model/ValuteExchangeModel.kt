package com.example.prodfinal.domain.model

import com.example.prodfinal.domain.state.ValuteRate
import java.math.BigDecimal

data class ValuteExchangeModel(
    val currentUsd: BigDecimal,
    val prevUsd: BigDecimal,
    val usdRate: ValuteRate,
    val currentEur: BigDecimal,
    val prevEur: BigDecimal,
    val eurRate: ValuteRate,
)
