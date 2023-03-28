import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../models/product';
import { ECommerceService } from '../services/e-commerce.service';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit  {
  product: Product | undefined;
  products: Product[] =[];
   constructor(
    private route: ActivatedRoute,
    private e_service: ECommerceService,
    
    ){
  

   }

  ngOnInit(): void {
   //get the specified product when the component renders
   this.getProductDetails();
   
   //call the method three times to generate three different items
   for (let i = 0; i < 3; i++) {
    this.generateRandomProduct();
    }
    
  }

   getProductDetails(){

    //Extract the products id from the url path
    this.route.paramMap.subscribe((params)=>{
      const id = Number(params.get('id'));

       //retrieve the details of the product
      this.e_service.getProductDetails(id).subscribe({
        next: (data: Product)=>{
          this.product = data;
          console.log(this.product)
          document.body.scrollTop = document.documentElement.scrollTop = 0;
        },
        error: (error: Error) => {console.error(error)},
        complete: ()=>{console.log('product retrieved')}

      })
    })

   
   }

   generateRandomProduct(){
    // Returns a random integer from 1 to 20(number of available products)
    const num = Math.floor(Math.random() * 20) + 1;
    console.log(num)
    
    this.e_service.getProductDetails(num).subscribe({
      next: (data: Product)=>{
        this.products.push(data)
      },
      error: (error: Error) => {console.error(error)},
      complete: ()=>{console.log('product retrieved')}

    })
   }
}
