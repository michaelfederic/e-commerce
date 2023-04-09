import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder , FormGroup, Validators} from '@angular/forms';
import { ShoppingCartService } from '../services/shoppingcart/shopping-cart.service';
import { Product } from '../models/product';
import { PaypalService } from '../services/paypal/paypal.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { CustomerCart } from '../models/customer-cart';
import { ECommerceService } from '../services/e-commerce.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
 
})
export class CheckoutComponent implements OnInit {
  itemsInCart = 0;
  totalCost = 0;
  shoppingCart: Product[]= [];
  username = sessionStorage.getItem("username");
  // Use ViewChild to get a reference to the successorder template
  @ViewChild('successorder', { static: true }) successorderTemplate: TemplateRef<any> | undefined;
  modalRef: any;
  constructor(
    private fb: FormBuilder,
    private shoppingCartService: ShoppingCartService,
    private paypalService: PaypalService,
    private modalService: NgbModal,
    private router: Router,
    private e_commerce: ECommerceService
    ){}

  ngOnInit(): void {
   this.loadTotalItemsInCart();
   this.loadTotalCost();
   this.shoppingCart = this.shoppingCartService.loadShoppingCart()||[];
   this.loadPaypalSDK();
   this.displaySuccessMessageModal();
  
   // Add the shopping cart only if the customer id has populated
   if(sessionStorage.getItem('id')){
    // this.addShoppingCart();
   }
   
  
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

 
 openVerticallyCentered(content: any){
  this.modalService.open(content, { size: 'xl' ,centered: true })
}

addShoppingCart(){
  let customerCart : CustomerCart = {
    customerId: Number(sessionStorage.getItem("id")),
    productDTOs: this.shoppingCartService.loadShoppingCart()||[],
    totalCost: this.shoppingCartService.loadTotalCost()
  }
  console.log(customerCart)
  // Set the shopping cart
  this.e_commerce.addShoppingCart(customerCart).subscribe({
    next: (customerCart: any) =>{

      this.shoppingCart= customerCart.productDTOs;
      this.totalCost = customerCart.totalCost;
      this.itemsInCart = this.shoppingCartService.getTotalItemsInShoppingCart();
    }, 
    error: (error) =>{
      console.log("There was an issue adding the shopping cart: ", error)
    }
    
  })
}

loadPaypalSDK(){
   // Get the order object which contains total cost and products
   const order = this.shoppingCartService.createOrderToSendToServer();

   // Render paypyal button, pass in the order and id of paypal button container
   this.paypalService.loadPaypal(order, 'paypal-button-container');
}

displaySuccessMessageModal(){
   // Display the successful modal message
   if(this.paypalService.successfulOrder){
    this.modalRef = this.modalService.open(this.successorderTemplate, { size: 'xl' ,centered: true })
   this.paypalService.successfulOrder=false;

   // On modal dismiss, navigate to the home page
   this.modalRef.dismissed.subscribe(()=>{
     this.router.navigate(['/']);
     
     // Empty out shopping cart on the front end
     localStorage.setItem('shoppingCart', JSON.stringify([]));
     localStorage.setItem('totalCost', String(0));
   })
  }
}

}
