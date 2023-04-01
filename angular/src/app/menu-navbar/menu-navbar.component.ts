import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, TemplateRef } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgbOffcanvas } from '@ng-bootstrap/ng-bootstrap';
import { Product } from '../models/product';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ECommerceService } from '../services/e-commerce.service';
import { ErrorMessage } from '../models/error';
import { Customer } from '../models/customer';
import { Router } from '@angular/router';
@Component({
  selector: 'app-menu-navbar',
  templateUrl: './menu-navbar.component.html',
  styleUrls: ['./menu-navbar.component.css']
})
export class MenuNavbarComponent implements OnInit {
  shoppingCart: Product[]=[];
  itemsInCart: number=0;
  totalCost: number = 0;
  customerLoginErrorMessage: string | undefined;
  customerSignupErrorMessage: string | undefined;
  username: string = sessionStorage.getItem('username')||'';
  customer: Customer | undefined;
  isCheckingOut= false;

  constructor(
    private modalService: NgbModal,
    private offcanvasService: NgbOffcanvas,
    private fb: FormBuilder,
    private e_commerce: ECommerceService,
    private router: Router
    
  ){}

  //login form variable to hold form information
 loginForm = this.fb.group({
  username: ['', [Validators.required]],
  password: ['', [Validators.required]]
 })

 signUpForm = this.fb.group({
  username: ['', [Validators.required]],
  email: [''],
  password: ['', [Validators.required]],
  confirmPassword: ['', [Validators.required]]
 })

  ngOnInit(): void {
    this.loadShoppingCart();
    this.loadTotalCost();
    
  }

  loadShoppingCart(){
    //set shopping cart
    const cart =sessionStorage.getItem('shoppingCart');
    if(cart){
      this.shoppingCart = JSON.parse(cart!);
      this.itemsInCart = this.shoppingCart.length;
    }
  }

  loadTotalCost(){
    //set total cost
    const totalCost = Number(sessionStorage.getItem('totalCost'));
    if(totalCost){
      this.totalCost =Number(totalCost);
    } 
  }

  openFullscreen(content: any){
    this.modalService.open(content, {fullscreen: true})
  }

  openEnd(content: any) {
		this.offcanvasService.open(content, { position: 'end' });
	}

   //close modal and reset fields
  closeSignupOffcanvas(){
    this.offcanvasService.dismiss('signupoffcanvas');
  }

  //close modal and reset fields
  closeLoginOffCanvas(){
    this.offcanvasService.dismiss('loginoffcanvas');
  }

  login(loginFormDetails: any){
    //call login method of service class passing credentials as parameter
    this.e_commerce.login(loginFormDetails).subscribe({
      next: (response: any)=> {
        const token = response['token'];
        sessionStorage.setItem('token', token);

        //close the login modal and clear the form
        this.closeLoginOffCanvas();

        //reset form(s)
        this.resetForms();

        //get the customer details
        this.getCustomerDetails();
        if(this.isCheckingOut){
          this.isCheckingOut=false;
          this.router.navigate(['/checkout'])
        }
      }
      ,
      error: (error) => {
        const errorMessage = error.error.errorMessage;
        // console.error(errorMessage);
        //check error message for specific messages to send to client
        if(errorMessage){
          if(errorMessage.includes('does not exist')||errorMessage.includes('Bad credentials')){
            this.customerLoginErrorMessage=errorMessage;
          }
        }
      
      },
      complete: () => {
        console.log('Request complete');
      }

    });
  }

  signUp(signUpFormDetails: any){
    this.e_commerce.signup(signUpFormDetails).subscribe({
      next: (response)=> {
        console.log(response)
        //on successfull sign up close sign up offcanvas
        this.closeSignupOffcanvas();
        
        //reset form(s)
        this.resetForms();

        //login user after successful signup
        this.login(signUpFormDetails);
      },
      error: (error)=>{
        const errorMessage = error.error.errorMessage;
        
        //check error message for specific messages to send to client
        if(errorMessage){
          if(errorMessage.includes('passwords match')||errorMessage.includes('already exists')){
            this.customerSignupErrorMessage=errorMessage;
          }
        }
        console.error(error)
      },
      complete: () =>{
        console.log('Request complete');
      }

      
    })
  }

  getCustomerDetails(){
    this.e_commerce.getDetails().subscribe({
      next: (data: any)=>{
        console.log(data)
        this.customer = data;
        sessionStorage.setItem('username',this.customer?.username||'')
        this.username = sessionStorage.getItem('username')||'';
        console.log(this.username)
      }
    })
  }

//reset forms or messages
resetForms(){
  this.loginForm.reset();
  this.signUpForm.reset();
  this. customerSignupErrorMessage= undefined;
  this.customerLoginErrorMessage = undefined;
}

//check if user is signed in
checkout(content: any){
  this.isCheckingOut=true;
  if(!sessionStorage.getItem('username')){

    //open the sign in offcanvas
    this.openEnd(content); 
  }else{

    //redirect to checkout page
    this.router.navigate(['/checkout'])
  }

  //close the current modal
  this.modalService.dismissAll();
}
 
}
