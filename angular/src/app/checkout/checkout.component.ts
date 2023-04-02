import { Component, OnInit } from '@angular/core';
import { FormBuilder , FormGroup, Validators} from '@angular/forms';
import { ShoppingCartService } from '../services/shoppingcart/shopping-cart.service';
import { Product } from '../models/product';

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
    private shoppingCartService: ShoppingCartService
    ){}

  ngOnInit(): void {
   this.loadTotalItemsInCart();
   this.loadTotalCost();
   this.shoppingCart = this.shoppingCartService.loadShoppingCart()||[];
   console.log(this.shoppingCart)
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
