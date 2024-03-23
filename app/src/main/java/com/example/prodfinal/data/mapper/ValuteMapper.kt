package com.example.prodfinal.data.mapper

import com.example.prodfinal.domain.model.ValuteExchangeModel
import com.example.prodfinal.domain.state.ValuteRate
import java.math.RoundingMode

// Класс маппера курса валют

class ValuteMapper {
    fun invoke(valute: ValuteExchangeModel): ValuteExchangeModel {
        // Корректируем и дополняем необходимые данные, а затем возвращаем
        var usdRate = ValuteRate.FALL
        if (valute.currentUsd.compareTo(valute.prevUsd) == 1) {
            usdRate = ValuteRate.RISE
        }
        var eurRate = ValuteRate.FALL
        if (valute.currentEur.compareTo(valute.prevEur) == 1) {
            eurRate = ValuteRate.RISE
        }

        return ValuteExchangeModel(
            valute.currentUsd.setScale(2, RoundingMode.HALF_EVEN),
            valute.currentUsd.setScale(2, RoundingMode.HALF_EVEN),
            usdRate,
            valute.currentEur.setScale(2, RoundingMode.HALF_EVEN),
            valute.currentEur.setScale(2, RoundingMode.HALF_EVEN),
            eurRate
        )
    }

}