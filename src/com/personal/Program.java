package com.personal;

import java.awt.EventQueue;

public class Program implements Runnable
{
    public static void main(String args[])
    {
        EventQueue.invokeLater(new Program());
    }

    @Override
    public void run()
    {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}
