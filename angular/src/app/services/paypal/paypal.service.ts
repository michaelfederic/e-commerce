import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { loadScript } from '@paypal/paypal-js';
import { PayPalOrder } from 'src/app/models/paypalorder';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class PaypalService {
  private paypal: any;
  successfulOrder =false;
  constructor(private router: Router) {
    
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
              /*Authenticates the request (must include) 
              * Now because this fetch 
              * is not using the httpclient(configured all requests using http 
              * client to use authorization http interceptor) i needed to include
              * authorization bearer
              */
              "Authorization" : "Bearer "+ sessionStorage.getItem("token")
            },
            body: JSON.stringify(paypalOrder)
          })
          .then((response) => response.json())
          
          // Paypal needs this order id(apparently must be in this exact format)
          .then((order) => order.id);
        },
        onApprove: (data:any, actions:any) => {
          // Call being made to my server api, then my server calls PayPal's apis
          return fetch(`http://localhost:8080/api/user/capture`,{
            method: "POST",
            headers:{
              "Content-Type": "application/json",
              "Authorization" : "Bearer "+ sessionStorage.getItem("token")
            }, 
            // send the order id as a object with orderID being the name
            body: JSON.stringify({
              orderID: data.orderID})
          })
          .then((response)=> response.json())
          // On successful capture return transaction details
          .then((orderData)=>{
            console.log("Capture result", orderData)

            // Variable to track if the order was a success, this variable triggers a modal inside checkout component
            this.successfulOrder=true;
            this.refresh();
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

  // Refresh the checkout component, solution for same url 
  refresh(){
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>
    this.router.navigate(["/checkout"]));
  }
  
}
