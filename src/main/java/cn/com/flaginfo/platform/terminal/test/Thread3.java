package cn.com.flaginfo.platform.terminal.test;

public class Thread3 implements Runnable {

    @Override
    public void run() {
        System.out.println("thread3 is running");
    }


    public static void main(String[] args){
        Thread thread=new Thread(new Thread3());
        thread.start();
    }


}
