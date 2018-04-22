package com.cat.downpic.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static String getTextFormStream(InputStream is){
        byte[] b = new byte[1024];
        int len = 0;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while ((len = is.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            String text = new String(bos.toByteArray());
            return text;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
