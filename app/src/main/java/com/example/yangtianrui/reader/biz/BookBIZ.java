package com.example.yangtianrui.reader.biz;

import com.example.yangtianrui.reader.config.Constants;

/**
 * Created by yangtianrui on 16-5-31.
 */
public class BookBIZ {


    public String switchKeyWorld() {
        int index = (int) (Math.random() * 8);
        return Constants.MATE_KEY_WORD[index];
    }

}
