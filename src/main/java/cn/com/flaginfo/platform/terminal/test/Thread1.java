package cn.com.flaginfo.platform.terminal.test;

public class Thread1 extends  Thread{


    @Override
    public void run(){
        System.out.println("this is thread");
    }


    public static void main(String[] args){
        Thread1 thread1=new Thread1();

        thread1.start();
    }

}
