/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package demo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import demo.model.User;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.StringTokenizer;

/**
 * Profile Controller.
 */
public class ProfileController extends AnchorPane implements Initializable {

    @FXML
    private TextField fIp;
    @FXML
    private TextField lIP;
    
    @FXML 
    private Button ping;
    @FXML 
    private Label success;
    
    private Main application;
    
    public void setApp(Main application){
        this.application = application;
        User loggedUser = application.getLoggedUser();
        success.setOpacity(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public void processLogout(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            return;
        }
        
        application.userLogout();
    }
    
    public void saveProfile(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            animateMessage();
            return;
        }
        User loggedUser = application.getLoggedUser();
       
    
        animateMessage();
    }
    
    public void resetProfile(ActionEvent event){
        if (application == null){
            return;
        }
        fIp.setText("");
        lIP.setText("");
        success.setOpacity(0.0);
        
    }

    private void animateMessage() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();
    }
    
    @FXML
    private void ping (ActionEvent event) {
        System.out.println("hello piiiiiiiinggggg ! ");
        String nomHote ;
        String adresseIPLocale = null ;
        ping.setDisable(false);
       try{
           InetAddress inetadr = InetAddress.getLocalHost();
           //nom de machine
           nomHote =  inetadr.getHostName();
           System.out.println("Nom de la machine = "+nomHote );
           //adresse ip sur le réseau
           adresseIPLocale =  inetadr.getHostAddress();
           System.out.println("Adresse IP locale = "+adresseIPLocale );
       } catch (IOException e) {
        }
         try{
           System.out.println("les interfaces réseaux:");
           Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	while (interfaces.hasMoreElements()) {
		NetworkInterface ni = interfaces.nextElement();
		if (ni.isUp())
                 System.out.println("nom de l'interface:" + ni.getName() );
                //System.out.println( ni.getDisplayName() );
	}
           StringTokenizer st = new StringTokenizer(adresseIPLocale , ".");   
         String base = "";
         for(int j=0;j<3;j++)
             base = base + st.nextToken() + ".";
         System.out.println("Test de connectivité:");
         System.out.println("Sending Ping Request to " + base+"1");
             InetAddress adr = InetAddress.getByName(base+"1"); 
           //  System.out.println(InetAddress.getLocalHost()+" Host is reachable");
              if  (adr.isReachable(3000)) 
                  System.out.println(" Host is reachable"); 
              else
                  System.out.println("Sorry ! We can't reach to this host"); 
         System.out.println("Sending Ping Request to " + base+"254"); 
             InetAddress addr = InetAddress.getByName(base+"254");
              if  (addr.isReachable(3000)) 
                  System.out.println("Host is reachable"); 
              else
                  System.out.println("Sorry ! We can't reach to this host");     
              int k;
              System.out.println("les machines connéctées au réseau:");
              for(k=2;k<=255;k++){
                 InetAddress nv= InetAddress.getByName(base+k);
                  if (nv.isReachable(1000))
                      System.out.println("nom: " + nv.getHostName());
                   //  System.out.println(base+k);
             }
         } catch (IOException e){
       } 

    }
}
