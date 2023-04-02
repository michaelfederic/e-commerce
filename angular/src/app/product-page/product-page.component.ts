import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../models/product';
import { ECommerceService } from '../services/e-commerce.service';
import { MenuNavbarComponent } from '../menu-navbar/menu-navbar.component';
import { ShoppingCartService } from '../services/shoppingcart/shopping-cart.service';
@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit  {
  product: Product | undefined;
  products: Product[] =[];
  @ViewChild(MenuNavbarComponent) child: MenuNavbarComponent | undefined;

   constructor(
    private route: ActivatedRoute,
    private e_service: ECommerceService,
    private shoppingCartService: ShoppingCartService
    
    ){
  

   }

  ngOnInit(): void {
   //get the specified product when the component renders
   this.getProductDetails();
   
   //call the method three times to generate three different items
   for (let i = 0; i < 3; i++) {
    this.generateRandomProduct();
    }
    //check if shopping cart exists
    this.shoppingCartService.checkIfShoppingCartExists();
  }

   getProductDetails(){

    //Extract the products id from the url path
    this.route.paramMap.subscribe((params)=>{
      const id = Number(params.get('id'));

       //retrieve the details of the product
       this.e_service.getProductDetails(id).subscribe({
        next: (product: Product) => {
          this.product = product;
          
          //scroll to the top
          window.scrollTo({top: 0, behavior: 'smooth'});
        },
        error: (error: Error) => {
          console.error(error);
        },
        complete: () => {
          console.log('Product retrieval complete');
        }
      });
    })

   
   }

   generateRandomProduct(){
    // Returns a random integer from 1 to 20(number of available products)
    const num = Math.floor(Math.random() * 20) + 1;
    
    this.e_service.getProductDetails(num).subscribe({
      next: (data: Product)=>{
        this.products.push(data)
      },
      error: (error: Error) => {console.error(error)},
      complete: ()=>{console.log('product retrieved')}

    })
   }

   addToCart(product: Product){
  
   this.shoppingCartService.addOneToCart(product);
    
    //re render the menu child to show the updated cart
    this.child?.ngOnInit();
   }

}
