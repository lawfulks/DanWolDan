package com.woongyi.lawfulks.danwoldan.Utils;

import java.util.ArrayList;

public class Hangeul {
    private static final char INITIAL_SOUND_BEGIN_UNICODE = 12593;
    private static final char INITIAL_SOUND_LAST_UNICODE = 12622;
    private static final char HANGUL_BEGIN_UNICODE = 44032;
    private static final char HANGUL_LAST_UNICODE = 55203;
    private static final char NUMBER_BEGIN_UNICODE = 48;
    private static final char NUMBER_LAST_UNICODE = 57;
    private static final char ENGLISH_ROWER_BEGIN_UNICODE = 65;
    private static final char ENGLISH_ROWER_LAST_UNICODE = 90;
    private static final char ENGLISH_UPPER_BEGIN_UNICODE = 97;
    private static final char ENGLISH_UPPER_LAST_UNICODE = 122;
    private static final char HANGUL_BASE_UNIT = 588;

    private static final char[] INITIAL_SOUND =
            {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ'
                    , 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    private static boolean isInitialSound(char c) {
        for (int i = 0; i < INITIAL_SOUND.length; i++) {
            if (INITIAL_SOUND[i] == c) {
                return true;
            }
        }

        return false;
    }

    private static char getInitialSound(char c) {
        int hanBegin = (c - HANGUL_BEGIN_UNICODE);
        int index = hanBegin / HANGUL_BASE_UNIT;

        return INITIAL_SOUND[index];
    }

    private static boolean isHangul(char c) {
        return HANGUL_BEGIN_UNICODE <= c && c <= HANGUL_LAST_UNICODE;
    }

    private static boolean checkUnicode(char c) {
        if ((   (c >= NUMBER_BEGIN_UNICODE && c <= NUMBER_LAST_UNICODE)
                || (c >= ENGLISH_UPPER_BEGIN_UNICODE && c <= ENGLISH_UPPER_LAST_UNICODE)
                || (c >= ENGLISH_ROWER_BEGIN_UNICODE && c <= ENGLISH_ROWER_LAST_UNICODE)
                || (c >= HANGUL_BEGIN_UNICODE && c <= HANGUL_LAST_UNICODE)
                || (c >= INITIAL_SOUND_BEGIN_UNICODE && c <= INITIAL_SOUND_LAST_UNICODE))
                ) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean matchString(String keyword, String value){
        value = value.replaceAll(" ", "");
        keyword = keyword.replaceAll(" ", "");

        int seof = value.length() - keyword.length();
        int slen = keyword.length();

        if (seof < 0 || slen < 1) {
            return false;
        }

        for (int i = 0; i <= seof; i++) {
            int t = 0;

            while (t < slen) {
                if (!checkUnicode(keyword.charAt(t))) {
                    return false;
                }

                if (isInitialSound(keyword.charAt(t)) && isHangul(value.charAt(i+t))) {
                    if (getInitialSound(value.charAt(i+t)) == keyword.charAt(t)) {
                        t++;
                    } else {
                        break;
                    }
                } else {
                    if (value.charAt(i+t) == keyword.charAt(t)) {
                        t++;
                    } else {
                        break;
                    }
                }
            }

            if (t == slen) {
                return true;
            }
        }

        return false;
    }

    public static ArrayList<String> searchItem(ArrayList<String> list, String keyword) {
        ArrayList<String> items = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (matchString(keyword, list.get(i))) {
                items.add(list.get(i));
            }
        }

        return items;
    }
}
