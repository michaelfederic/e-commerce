import { Component, OnInit } from '@angular/core';
import { Product } from '../models/product';
import { ECommerceService } from '../services/e-commerce.service';
import { ShoppingCartService } from '../services/shoppingcart/shopping-cart.service';

@Component({
  selector: 'app-main-home',
  templateUrl: './main-home.component.html',
  styleUrls: ['./main-home.component.css']
})
export class MainHomeComponent implements OnInit{
  currentItems: Product[] =[]
  
  constructor(
    private e_service: ECommerceService,
    private shoppingCartService: ShoppingCartService){}

  ngOnInit(): void {
    this.getItems('all')
    
    //check if shoppingCart exist inside sesssionStorage, 
    this.shoppingCartService.checkIfShoppingCartExists();
  }
  

  //retrieve list of items from service layer
  getItems(category: string){
    if(category==='all'){
      this.e_service.getAllItems().subscribe((data: Product[])=>{
        this.currentItems= data;
      })
    }else{
      this.e_service.getItems(category).subscribe((data: Product[])=>{
        this.currentItems= data;   
      })
    }
    
  }


  //search list 
  search(key: string){
    console.log(key)
  }

  
}
