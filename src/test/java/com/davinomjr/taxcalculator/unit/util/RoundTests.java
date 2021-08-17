package com.davinomjr.taxcalculator.unit.util;

import com.davinomjr.taxcalculator.core.util.Round;
import com.davinomjr.taxcalculator.web.TaxCalculatorApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ContextConfiguration(classes = TaxCalculatorApplication.class)
public class RoundTests {

    @Test
    public void roundToNearestDotZeroFive_whenGivenAValueX_shouldRoundToNearestDotZeroFive(){
        BigDecimal value = BigDecimal.valueOf(16.489);
        BigDecimal expectedValueAfterRounding = new BigDecimal("16.50");

        BigDecimal resultValue = Round.roundToNearestDotZeroFive(value);

        assertThat(resultValue).isEqualTo(expectedValueAfterRounding);
    }

    @Test
    public void roundToNearestDotZeroFive_whenGivenAValueY_shouldRoundToNearestDotZeroFive(){
        BigDecimal value = BigDecimal.valueOf(54.625);
        BigDecimal expectedValueAfterRounding = new BigDecimal("54.65");

        BigDecimal resultValue = Round.roundToNearestDotZeroFive(value);

        assertThat(resultValue).isEqualTo(expectedValueAfterRounding);
    }

    @Test
    public void roundToNearestDotZeroFive_whenGivenAValueZ_shouldRoundToNearestDotZeroFive(){
        BigDecimal value = BigDecimal.valueOf(12.5918);
        BigDecimal expectedValueAfterRounding = new BigDecimal("12.60");

        BigDecimal resultValue = Round.roundToNearestDotZeroFive(value);

        assertThat(resultValue).isEqualTo(expectedValueAfterRounding);
    }

    @Test
    public void roundToNearestDotZeroFive_whenGivenAValueO_shouldRoundToNearestDotZeroFive(){
        BigDecimal value = BigDecimal.valueOf(193.2967);
        BigDecimal expectedValueAfterRounding = new BigDecimal("193.30");

        BigDecimal resultValue = Round.roundToNearestDotZeroFive(value);

        assertThat(resultValue).isEqualTo(expectedValueAfterRounding);
    }

    @Test
    public void roundToMoney_whenGivenAValueX_shouldRoundToMoney(){
        BigDecimal value = BigDecimal.valueOf(15.623);
        BigDecimal expectedValueAfterRounding = new BigDecimal("15.62");

        BigDecimal resultValue = Round.roundToMoney(value);

        assertThat(resultValue).isEqualTo(expectedValueAfterRounding);
    }

    @Test
    public void roundToMoney_whenGivenAValueY_shouldRoundToMoney(){
        BigDecimal value = BigDecimal.valueOf(92182.4176);
        BigDecimal expectedValueAfterRounding = new BigDecimal("92182.42");

        BigDecimal resultValue = Round.roundToMoney(value);

        assertThat(resultValue).isEqualTo(expectedValueAfterRounding);
    }
}
