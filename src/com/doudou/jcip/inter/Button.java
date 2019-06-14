package com.doudou.jcip.inter;

import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class Button {

    public Map<String, Date> lastLogin = Collections.synchronizedMap(new HashMap<>());


    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/5/14 15:19a
    */
    public static void main(String[] args){
        List list = new List();
        list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer integer = Integer.valueOf("1");
                integer.toString();
                String s = "11";
                s.hashCode();
                Path sourece = Paths.get("/45");
                Path target = Paths.get("/45");
                //CopyOption copyOption = CopyOption
               /* try (Path path = Files.move(sourece, target, StandardCopyOption.REPLACE_EXISTING)){

                } catch (IOException e1) {
                    e1.printStackTrace();
                }*/

            }
        });

        for (int i = 0; i < 3; i++){
           getButton();
        }
    }

   public void registerListener(EventLisner lisner){

   }



   private static ThreadLocal<Button> buttonHolder = new ThreadLocal<Button>(){
        @Override
        public Button initialValue(){
            System.out.println("1");


            Charset charset = Charset.forName("US-ASCII");
            String s = "";
            Path file = Paths.get("s");
            try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
                writer.write(s, 0, s.length());
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
            return new Button();
        }
   };


    public static Button getButton(){
        return buttonHolder.get();
    }
}
