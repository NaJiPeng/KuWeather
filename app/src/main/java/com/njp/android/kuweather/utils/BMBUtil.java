package com.njp.android.kuweather.utils;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

/**
 * 用于返回BMB条目数目的标识符
 */

public class BMBUtil {

    public static PiecePlaceEnum getPiecePlaceEnum(int count) {
        switch (count) {
            case 1:
                return PiecePlaceEnum.HAM_1;
            case 2:
                return PiecePlaceEnum.HAM_2;
            case 3:
                return PiecePlaceEnum.HAM_3;
            case 4:
                return PiecePlaceEnum.HAM_4;
            case 5:
                return PiecePlaceEnum.HAM_5;
            case 6:
                return PiecePlaceEnum.HAM_6;
        }
        return null;
    }

    public static ButtonPlaceEnum getButtonPlaceEnum(int count) {
        switch (count) {
            case 1:
                return ButtonPlaceEnum.HAM_1;
            case 2:
                return ButtonPlaceEnum.HAM_2;
            case 3:
                return ButtonPlaceEnum.HAM_3;
            case 4:
                return ButtonPlaceEnum.HAM_4;
            case 5:
                return ButtonPlaceEnum.HAM_5;
            case 6:
                return ButtonPlaceEnum.HAM_6;
        }
        return null;
    }

}
