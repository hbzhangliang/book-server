package cn.com.flaginfo.platform.terminal.test;

public class Thread2 {

    public static void main(String[] args){

        Thread thread=new Thread(){
            @Override
            public void run(){
                System.out.println("thread2 is running");
            }
        };

        thread.start();


    }

}
