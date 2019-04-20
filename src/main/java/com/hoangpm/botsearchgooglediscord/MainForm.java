/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hoangpm.botsearchgooglediscord;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * @author ZeroX
 */
public class MainForm extends javax.swing.JFrame {

    public static final String QUEUE_NAME = "CONFETTI";

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
    }

    public static StringBuilder handleStringArray(String[] arr) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (String s : arr) {
            stringBuilder.append(s);
            if (i < arr.length) {
                stringBuilder.append("%20");
                i++;
            }
        }
        return stringBuilder;
    }

    public static void sendMessage(TextChannel ch, Question question) throws IOException {
        String[] quesArr = (question.getQuestionText() + " "
                + question.getOptionA() + " "
                + question.getOptionB() + " "
                + question.getOptionC())
                .split("[,!-.;/\\?@ #\"()]");

        String[] ansA = (question.getOptionA() + " " + question.getQuestionText()).split("[,!-.;/\\?@ #\"()]");
        String[] ansB = (question.getOptionB() + " " + question.getQuestionText()).split("[,!-.;/\\?@ #\"()]");
        String[] ansC = (question.getOptionC() + " " + question.getQuestionText()).split("[,!-.;/\\?@ #\"()]");
//        String[] quesArrEng = GoogleTranslate.translate("en", question.getQuestionText()).split("[,!-.;/\\?@ #\"()]");
        StringBuilder vieBuilder = handleStringArray(quesArr);
//        StringBuilder engBuilder = handleStringArray(quesArrEng);
        StringBuilder ansABuilder = handleStringArray(ansA);
        StringBuilder ansBBuilder = handleStringArray(ansB);
        StringBuilder ansCBuilder = handleStringArray(ansC);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.red);
        builder.setTitle("Question: " + question.getQuestionText());
        builder.setDescription(
                "A. " + question.getOptionA()
                + "\nB. " + question.getOptionB()
                + "\nC. " + question.getOptionC()
                + "\n[Question-AnsA-AnsB-AnsC-Google](https://www.google.com/search?q=" + vieBuilder.toString() + ")"
                + "\n[Question-AnsA](https://www.google.com/search?q=" + ansABuilder.toString() + ")"
                + "\n[Question-AnsB](https://www.google.com/search?q=" + ansBBuilder.toString() + ")"
                + "\n[Question-AnsC](https://www.google.com/search?q=" + ansCBuilder.toString() + ")"
        //                        + "\n[Google Search - English](https://www.google.com/search?q=" + engBuilder.toString() + ")"
        );
        ch.sendMessage(builder.build()).queue();
    }

    public static String message = "";

    public static void rabbitmq(JDA jda, String room) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setRequestedHeartbeat(30);
        factory.setConnectionTimeout(30000);
        try {
            factory.setUri("amqp://qjoahoff:Qxm4reMreDbVPRyDlie5PfuQEOe_SWR2@dinosaur.rmq.cloudamqp.com/qjoahoff");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, true, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                if (!message.equals(new String(delivery.getBody(), "UTF-8"))) {
                    message = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                    List<TextChannel> channels = jda.getTextChannelsByName(room, true);
                    Gson gson = new Gson();
                    Question question = gson.fromJson(message, Question.class);
                    for (TextChannel ch : channels) {
                        sendMessage(ch, question);
                    }
                }
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtToken = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        txtRoomName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtToken.setText("NTY3OTA1NzkwMTMzMDEwNDMy.XLaXNw.1DUGbZ0yLiD0aItecPbMTzzYsBE");

        jLabel1.setText("Bot Token");

        btnStart.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        txtRoomName.setText("penguinbot");

        jLabel2.setText("Room Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtToken, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtRoomName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtToken, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRoomName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStart)
                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        // TODO add your handling code here:
        JDABuilder builder = new JDABuilder(AccountType.BOT.BOT);
//        String token = "NTY3MTczODA3NDMzODQyNjk4.XLPyFQ.2K0aEUgLRURN9wTEN_r-5pcs80M";
        String token = txtToken.getText().toString().trim();
        String room = txtRoomName.getText().toString().trim();
        builder.setToken(token);
        try {
            JDA jda = builder.build();
            jda.awaitReady();
            rabbitmq(jda, room);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnStartActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtRoomName;
    private javax.swing.JTextField txtToken;
    // End of variables declaration//GEN-END:variables
}
