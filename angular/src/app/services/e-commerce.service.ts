import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ECommerceService {

  constructor(private http: HttpClient) { }

  // return list of items depending on category
  getItems(category: string){
    return this.http.get<Product[]>(`https://fakestoreapi.com/products/category/${category}`, {responseType:'json'})
  }

  //return list of all the items
  getAllItems(){
    return this.http.get<Product[]>(`https://fakestoreapi.com/products`, {responseType:'json'})
  }

  //return product details
  getProductDetails(productId: number){
    return this.http.get<Product>(`https://fakestoreapi.com/products/${productId}`)
  }

  initializeShoppingCart(){
    //create a shoppingCart inside session storage
    if(!sessionStorage.getItem('shoppingCart')){
      var shoppingCart: Product[] = [];
      sessionStorage.setItem('shoppingCart',JSON.stringify(shoppingCart))
      sessionStorage.setItem('totalCost', '0');
    }
  }

  addToShoppingCart(product: Product){
    //get cart from session storage
    const cart = JSON.parse(sessionStorage.getItem('shoppingCart') || '[]');

    //add item to cart
    cart.push(product);
    sessionStorage.setItem('shoppingCart', JSON.stringify(cart));

    //convert from string to number total cost
    var totalCost = Number( sessionStorage.getItem('totalCost'));

    //add product price to total cost
    totalCost+=product.price;


    sessionStorage.setItem('totalCost', String(totalCost));
  }

  deleteFromShoppingCart(product: Product){
    const cart = JSON.parse(sessionStorage.getItem('shoppingCart') || '[]');

  }
}
