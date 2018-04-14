package cn.com.flaginfo.platform.terminal.test;

public class Thread4 {

    public static void main(String[] args){

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("thread4 is running");
            }
        };

        Thread thread=new Thread(runnable);

        thread.start();

    }

}
