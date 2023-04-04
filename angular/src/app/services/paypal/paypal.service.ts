import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { loadScript } from '@paypal/paypal-js';
import { PayPalOrder } from 'src/app/models/paypalorder';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class PaypalService {
  private paypal: any;

  constructor(private http: HttpClient) {
    
   }
//load the paypal scripts
   loadPaypal(paypalOrder: PayPalOrder, containerId: string) {
    loadScript({
      //set the client id 
      'client-id': `${environment.clientId}`,
      currency: "USD",
      'disable-funding': 'credit'

      //when ready....
    }).then((paypal) => {
    
      //...set the paypal object
      this.paypal = paypal;

      //render the button(s) onto html template
      this.renderButton(paypalOrder ,containerId);

    }).catch((err) => {
      console.error('failed to load the PayPal JS SDK script', err);
    });
  }
  
  renderButton(paypalOrder: PayPalOrder,containerId: string) {
    if (this.paypal) {
      //on button click
      this.paypal.Buttons({
        
        createOrder: (data:any, actions:any) => {
          // Send the oprder to my server to recieve an order id
          return fetch("http://localhost:8080/api/user/checkout",{
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              // Authenticates the request (must include)
              "Authorization" : "Bearer "+ sessionStorage.getItem("token")
            },
            body: JSON.stringify(paypalOrder)
          })
          .then((response) => response.json())
          
          // Paypal needs this order id(apparently must be in this exact format)
          .then((order) => order.id);
        },
        onApprove: (data:any, actions:any) => {
          // TO-DO- apply capture config
          console.log(data.orderID)
          return fetch(`http://localhost:8080/api/user/capture`,{
            method: "POST",
            headers:{
              "Content-Type": "application/json",
              "Authorization" : "Bearer "+ sessionStorage.getItem("token")
            }, 
            body: JSON.stringify({
              orderID: data.orderID})
          })
          .then((response)=> response.json())
          .then((orderData)=>{
            console.log("Capture result", orderData)
          })
        },
        onError: (err: Error) => {
          console.error('An error occurred while rendering the PayPal button', err);
        },

        style:{
          layout: 'vertical',
          color: 'gold',
          shape: 'pill',
          label: 'paypal'
        },
        advanced:{
          commit:false
        }
        
        //render the button
      }).render('#' + containerId);
    }
  }

}
