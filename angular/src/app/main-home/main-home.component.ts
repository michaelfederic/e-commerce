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
    
    // Check if shoppingCart exist inside sesssionStorage, 
    this.shoppingCartService.checkIfShoppingCartExists();
  }


  // Retrieve list of items from service layer
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


  // Search list 
  search(key: string){
    // If the key is empty return the entire list
    if(key.trim()==''){
      this.getItems('all');
    }

    // Filter list based of key
    this.currentItems = this.currentItems.filter((item)=> 
    item.title.toLowerCase().includes(key.toLowerCase()) ||
    item.category.toLowerCase().includes(key.toLowerCase()) ||
    item.description.toLowerCase().includes(key.toLowerCase())
    
    )
    
   
  }

  
}
