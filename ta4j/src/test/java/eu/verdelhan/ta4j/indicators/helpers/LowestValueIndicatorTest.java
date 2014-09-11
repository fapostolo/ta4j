/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marc de Verdelhan & respective authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eu.verdelhan.ta4j.indicators.helpers;

import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.mocks.MockTimeSeries;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

public class LowestValueIndicatorTest {

    private TimeSeries data;

    @Before
    public void setUp() {
        data = new MockTimeSeries(1, 2, 3, 4, 3, 4, 5, 6, 4, 3, 2, 4, 3, 1);
    }

    @Test
    public void lowestValueIndicatorUsingTimeFrame5UsingClosePrice() throws Exception {
        LowestValueIndicator lowestValue = new LowestValueIndicator(new ClosePriceIndicator(data), 5);

        assertThat(lowestValue.getValue(4)).isEqualTo(1d);
        assertThat(lowestValue.getValue(5)).isEqualTo(2d);
        assertThat(lowestValue.getValue(6)).isEqualTo(3d);
        assertThat(lowestValue.getValue(7)).isEqualTo(3d);
        assertThat(lowestValue.getValue(8)).isEqualTo(3d);
        assertThat(lowestValue.getValue(9)).isEqualTo(3d);
        assertThat(lowestValue.getValue(10)).isEqualTo(2d);
        assertThat(lowestValue.getValue(11)).isEqualTo(2d);
        assertThat(lowestValue.getValue(12)).isEqualTo(2d);

    }

    @Test
    public void lowestValueShouldWorkJumpingIndexes() {
        LowestValueIndicator lowestValue = new LowestValueIndicator(new ClosePriceIndicator(data), 5);
        assertThat(lowestValue.getValue(10)).isEqualTo(2d);
        assertThat(lowestValue.getValue(6)).isEqualTo(3d);
    }

    @Test
    public void lowestValueIndicatorValueShouldBeEqualsToFirstDataValue() {
        LowestValueIndicator lowestValue = new LowestValueIndicator(new ClosePriceIndicator(data), 5);
        assertThat(lowestValue.getValue(0)).isEqualTo(1d);
    }

    @Test
    public void lowestValueIndicatorWhenTimeFrameIsGreaterThanIndex() {
        LowestValueIndicator lowestValue = new LowestValueIndicator(new ClosePriceIndicator(data), 500);
        assertThat(lowestValue.getValue(12)).isEqualTo(1d);
    }
}
