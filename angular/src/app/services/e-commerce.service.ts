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


}
