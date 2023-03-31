import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, TemplateRef } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgbOffcanvas } from '@ng-bootstrap/ng-bootstrap';
import { Product } from '../models/product';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ECommerceService } from '../services/e-commerce.service';
import { ErrorMessage } from '../models/error';
@Component({
  selector: 'app-menu-navbar',
  templateUrl: './menu-navbar.component.html',
  styleUrls: ['./menu-navbar.component.css']
})
export class MenuNavbarComponent implements OnInit {
  shoppingCart: Product[]=[]
  itemsInCart: number=0;
  totalCost: number = 0;
  error: ErrorMessage | undefined;
  //login form variable to hold form information
 loginForm = this.fb.group({
  username: ['', [Validators.required]],
  password: ['', [Validators.required]]
 })
  constructor(
    private modalService: NgbModal,
    private offcanvasService: NgbOffcanvas,
    private fb: FormBuilder,
    private e_commerce: ECommerceService,
    
  ){}


  ngOnInit(): void {
    //set shopping cart
    const cart =sessionStorage.getItem('shoppingCart');
    this.shoppingCart = JSON.parse(cart!);
    if(this.shoppingCart){
      this.itemsInCart = this.shoppingCart.length;
    }
    

    //set total cost
    const totalCost = Number(sessionStorage.getItem('totalCost'));
    this.totalCost = totalCost;

  }

  openFullscreen(content: any){
    this.modalService.open(content, {fullscreen: true})
  }

  openEnd(content: TemplateRef<any>) {
		this.offcanvasService.open(content, { position: 'end' });
	}

  login(form: any){
    //call login method of service class passing credentials as parameter
    this.e_commerce.login(form).subscribe({
      next: (response: any)=> 
      {
        const token = response['token'];
        sessionStorage.setItem('token', token);
        
      }
      ,
      error: (error) => {
         this.error = error.error;
         console.error(this.error?.errorMessage)
      
      },
      complete: () => {
        console.log('Request complete');
      }

    });
  }


 
}
