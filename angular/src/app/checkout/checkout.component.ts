import { Component, OnInit } from '@angular/core';
import { FormBuilder , FormGroup, Validators} from '@angular/forms';
import { ShoppingCartService } from '../services/shoppingcart/shopping-cart.service';
import { Product } from '../models/product';
import { PaypalService } from '../services/paypal/paypal.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
 
})
export class CheckoutComponent implements OnInit {
  itemsInCart = 0;
  totalCost = 0;
  shoppingCart: Product[]= [];
  
  constructor(
    private fb: FormBuilder,
    private shoppingCartService: ShoppingCartService,
    private paypalService: PaypalService
    ){}

  ngOnInit(): void {
   this.loadTotalItemsInCart();
   this.loadTotalCost();
   this.shoppingCart = this.shoppingCartService.loadShoppingCart()||[];
   
   // Get the order object which contains total cost and products
   const order = this.shoppingCartService.createOrderToSendToServer();

   // Render paypyal button, pass in the order and id of paypal button container
   this.paypalService.loadPaypal(order, 'paypal-button-container');
  }

  shippingForm= this.fb.group({
    firstName : ['', [Validators.required]],
    lastName : ['', [Validators.required]],
	 
    addressLine1:['', [Validators.required]],
    addressLine2:[''],
    city: ['', [Validators.required]],
    state: ['', [Validators.required]],
    postalCode:['', [Validators.required]],
    country: ['', [Validators.required]],
    email: ['']
  })

  paymentForm= this.fb.group({
    nameOnCard: ['', [Validators.required]],
    creditCardNumber: ['',[Validators.required]],
    expirationDate: ['',[Validators.required]],
    cvv: ['', [Validators.required]],
    paymentType: ['', [Validators.required]]
  })

 showForms(shippingFormDetails: any, paymentFormDetails: any ){
  console.log(shippingFormDetails);
  console.log(paymentFormDetails)
 }

 loadTotalItemsInCart(){
  this.itemsInCart= this.shoppingCartService.getTotalItemsInShoppingCart();
 }

 loadTotalCost(){
  this.totalCost = this.shoppingCartService.loadTotalCost()||0;
 }



}
