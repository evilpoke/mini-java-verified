

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.Socket;

public class TestItUI extends Frame implements WindowListener {
    Socket socket = new Socket("127.0.0.1", 8000);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

    public TestItUI() throws IOException {
        setSize(500,500);
        addWindowListener(this);
        Panel otherstuff = new Panel();

        Panel queryform = new Panel();
        queryform.setLayout(new GridLayout(0, 2));
        TextField querycommand = new TextField();
        querycommand.setVisible(true);
        queryform.add(querycommand);
        Button query = new Button("Query");
        queryform.add(query);
        query.setVisible(true);
        querycommand.setVisible(true);
        otherstuff.add(queryform);
        //add(queryform, "North");


        Panel countingform = new Panel();
        countingform.setLayout(new GridLayout(0,2));
        Button counting = new Button("Count");
        counting.setVisible(true);
        TextField countingcommand = new TextField();
        countingcommand.setVisible(true);
        countingform.add(countingcommand);
        countingform.add(counting);
        otherstuff.add(countingform);




        /*TextArea textArea = new TextArea();
        add(textArea, "Center");*/

        otherstuff.setLayout(new GridLayout(5, 1));
        Button exit = new Button("exit");
        otherstuff.add(exit);
        Button adding = new Button("Add");
        otherstuff.add(adding);
        Button listing = new Button("List");
        otherstuff.add(listing);
        Button pagerank = new Button("PageRank");
        otherstuff.add(pagerank);
        pagerank.setVisible(true);


        add(otherstuff, "North");

        //setLayout(new BorderLayout());

        //pack();
        setVisible(true);

        exit.addActionListener(actionevent ->{
            dispose();
        });


        counting.addActionListener(actionEvent ->{
            if(!countingcommand.getText().equals("")) {
                java.awt.EventQueue.invokeLater(() -> {
                    javax.swing.JFrame newWindow = new javax.swing.JFrame("New Window");
                    newWindow.setVisible(true);
                    newWindow.setSize(500, 500);
                    JPanel panel = new JPanel(new GridLayout(1, 0));

                    JTextArea title = new JTextArea();
                    title.setVisible(true);

                    String lists = "";

                    out.println("count " + countingcommand.getText());
                    out.flush();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        String firstline = in.readLine();
                        if (!firstline.equals("LabelContainer")) {
                            throw new IOException();
                        }
                        while (in.ready()) {
                            lists += "\n" + in.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    do {
                        lists = lists.replaceAll("LabelContainer", "");
                    } while (lists.contains("LabelContainer"));


                    title.setText(lists);

                    panel.add(title);
                    panel.setVisible(true);
                    newWindow.add(panel);

                });
            }

        });

        pagerank.addActionListener(actionEvent ->{

            java.awt.EventQueue.invokeLater(() -> {
                javax.swing.JFrame newWindow = new javax.swing.JFrame("New Window");
                newWindow.setVisible(true);
                newWindow.setSize(500, 500);
                JPanel panel = new JPanel(new GridLayout(1, 0));

                JTextArea title = new JTextArea();
                title.setVisible(true);

                String lists = "";

                out.println("pageRank");
                out.flush();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    String firstline = in.readLine();
                    if(!firstline.equals("LabelContainer")){
                        throw new IOException();
                    }
                    while(in.ready()) {
                        lists += "\n"+in.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                do{
                    lists =  lists.replaceAll("LabelContainer", "");
                }while(lists.contains("LabelContainer"));


                title.setText(lists);

                panel.add(title);
                panel.setVisible(true);
                newWindow.add(panel);

            });


        });

        query.addActionListener(actionEvent ->{
            if(!querycommand.getText().equals("")) {
                java.awt.EventQueue.invokeLater(() -> {
                    javax.swing.JFrame newWindow = new javax.swing.JFrame("New Window");
                    newWindow.setVisible(true);
                    newWindow.setSize(500, 500);
                    JPanel panel = new JPanel(new GridLayout(1, 0));

                    JTextArea title = new JTextArea();
                    title.setVisible(true);

                    String lists = "";

                    out.println("query " + querycommand.getText());
                    out.flush();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        String firstline = in.readLine();
                        if (!firstline.equals("LabelContainer")) {
                            throw new IOException();
                        }
                        while (in.ready()) {
                            lists += "\n" + in.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    do {
                        lists = lists.replaceAll("LabelContainer", "");
                    } while (lists.contains("LabelContainer"));


                    title.setText(lists);

                    panel.add(title);
                    panel.setVisible(true);
                    newWindow.add(panel);

                });
            }

        });


        listing.addActionListener(actionEvent -> {
            java.awt.EventQueue.invokeLater(() -> {
                javax.swing.JFrame newWindow = new javax.swing.JFrame("New Window");
                newWindow.setVisible(true);
                newWindow.setSize(500, 500);
                JPanel panel = new JPanel(new GridLayout(1, 0));

                JTextArea title = new JTextArea();
                title.setVisible(true);

                String lists = "";

                out.println("list");
                out.flush();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    String firstline = in.readLine();
                    if(!firstline.equals("LabelContainer")){
                        throw new IOException();
                    }
                    while(in.ready()) {
                        lists += "\n"+in.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                do{
                   lists =  lists.replaceAll("LabelContainer", "");
                }while(lists.contains("LabelContainer"));


                title.setText(lists);

                panel.add(title);
                panel.setVisible(true);
                newWindow.add(panel);

            });
        });


        adding.addActionListener(actionEvent -> {
            java.awt.EventQueue.invokeLater(() -> {
                javax.swing.JFrame newWindow = new javax.swing.JFrame("New Window");
                newWindow.setVisible(true);
                newWindow.setSize(500, 500);
                JPanel panel = new JPanel(new GridLayout(4, 0));

                JTextField title = new JTextField(4);
                title.setVisible(true);
                JLabel titlelabel = new JLabel("Title");
                titlelabel.setLabelFor(title);
                titlelabel.setVisible(true);
                JTextField field = new JTextField(5);
                JLabel label1 = new JLabel("Document content");
                label1.setLabelFor(field);

                JButton button = new JButton("Add document");

                title.setVisible(true);
                titlelabel.setVisible(true);
                field.setVisible(true);
                label1.setVisible(true);
                button.setVisible(true);


                panel.add(title);
                panel.add(titlelabel);
                panel.add(field);
                panel.add(label1);
                panel.add(button);
                panel.setVisible(true);
                newWindow.add(panel);

                button.addActionListener(s -> {

                    String stringtitle = title.getText();
                    String stringcontent = field.getText();
                    String send = "add " + stringtitle + ":" + stringcontent;
                    if((!stringcontent.equals("")) && (!stringtitle.equals(""))) {
                        out.println(send);
                        out.flush();
                    }
                    title.setText("");
                    field.setText("");

                });
            });

        });


    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e){
        dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }



    public static void main(String[] args) {

        try {
            TestItUI testItUI = new TestItUI();
        } catch (IOException e) {
            System.err.println("Server not available: " + e.getMessage());
            System.exit(-1);
        }

    }


}
