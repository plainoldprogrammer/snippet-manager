package com.personal;

import java.awt.EventQueue;
import javax.swing.UIManager;
import com.personal.gui.main.MainWindow;

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

            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

