import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../models/product';
import { Customer } from '../models/customer';
import { CustomerCart } from '../models/customer-cart';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ECommerceService {
  userBaseUrl = environment.userBaseUrl;
  authBaseUrl = environment.authBaseUrl;
  fakeApiUrl = environment.fakeApiUrl;
  constructor(private http: HttpClient) { }

  // return list of items depending on category
  getItems(category: string){
    return this.http.get<Product[]>(`${this.fakeApiUrl}/category/${category}`, {responseType:'json'})
  }

  //return list of all the items
  getAllItems(){
    return this.http.get<Product[]>(`${this.fakeApiUrl}/products`, {responseType:'json'})
  }

  //return product details
  getProductDetails(productId: number){
    return this.http.get<Product>(`${this.fakeApiUrl}/products/${productId}`)
  }


  login(loginFormDetails: any){
    return this.http.post(`${this.authBaseUrl}/login`, loginFormDetails, {responseType: 'json'})
  
  }

  signup(signUpFormDetails: any){
    return this.http.post(`${this.authBaseUrl}/register`, signUpFormDetails, {responseType:'json'})
  }

  getDetails(){
    return this.http.get(`${this.userBaseUrl}/details`, {responseType:'json'})
  }

  addShoppingCart(customerCart: CustomerCart){
    return this.http.put(`${this.userBaseUrl}/shoppingcart`, customerCart, {responseType: 'json'})
  }

  getMyOrders(){
    return this.http.get(`${this.userBaseUrl}/getmyorders`,{responseType:'json'})
  }

  getOrderDetails(orderId: string){
    return this.http.get(`${this.userBaseUrl}/orderdetails/${orderId}`, {responseType: 'json'})
  }

}
