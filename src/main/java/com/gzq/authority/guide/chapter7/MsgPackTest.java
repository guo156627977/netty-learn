package com.gzq.authority.guide.chapter7;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-08-13 16:26.
 */
public class MsgPackTest {
    public static void main(String[] args) throws IOException {
        ArrayList<String> src = new ArrayList<>();
        src.add("msgPack");
        src.add("guozhiqiang");
        src.add("guozhiqiang2");
        MessagePack messagePack = new MessagePack();
        byte[] raw = messagePack.write(src);
        List<String> read = messagePack.read(raw, Templates.tList(Templates.TString));
        for (String s : read) {
            System.out.println("s = " + s);
        }
    }
}
