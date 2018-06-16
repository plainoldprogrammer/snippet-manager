package com.personal;

import java.awt.EventQueue;
import javax.swing.UIManager;

public class Program implements Runnable
{
    public static void main(String args[])
    {
        EventQueue.invokeLater(new Program());
    }

    @Override
    public void run()
    {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}

