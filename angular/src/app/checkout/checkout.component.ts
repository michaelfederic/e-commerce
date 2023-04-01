import { Component, OnInit } from '@angular/core';
import { FormBuilder , FormGroup, Validators} from '@angular/forms';
import { ECommerceService } from '../services/e-commerce.service';
import { ShoppingCartService } from '../services/shoppingcart/shopping-cart.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
 
})
export class CheckoutComponent implements OnInit {
  itemsInCart = 0;
  totalCost = 0;
  constructor(
    private fb: FormBuilder,
    private e_commerce: ECommerceService,
    private shoppingCartService: ShoppingCartService
    ){}

  ngOnInit(): void {
   this.loadTotalItemsInCart();
   this.loadTotalCost();
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
