import { Injectable } from '@angular/core';
import { CustomerCart } from 'src/app/models/customer-cart';
import { PayPalOrder } from 'src/app/models/paypalorder';
import { Product } from 'src/app/models/product';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor() { }

  checkIfShoppingCartExists(){
    //create a shoppingCart inside session storage
    if(!localStorage.getItem('shoppingCart')){
      var shoppingCart: Product[] = [];
      localStorage.setItem('shoppingCart',JSON.stringify(shoppingCart))
      localStorage.setItem('totalCost', '0');
    }
  }

  addOneToCart(product: Product){
    //get cart from session storage
    const cart = this.loadShoppingCart();
    
    if(cart){

      //if item exists return first index of item else -1
      const itemExist = cart.findIndex((item)=> item.id ===product.id);
      
      
        if(itemExist === -1){
          product.quantity= 1;

          //add item to cart
          cart.push(product);
        }else{
          //add 1 to quantity
          cart[itemExist].quantity++;
        }
    }
    //update shopping cart
    localStorage.setItem('shoppingCart', JSON.stringify(cart));
  }

  loadShoppingCart(){

    //set shopping cart
    const cartString =localStorage.getItem('shoppingCart');
    
    if(cartString){
      
      return JSON.parse(cartString) as Product[];
    }
    
    return null;
  }

  getTotalItemsInShoppingCart(){
    //get the cart from session
    const cart =this.loadShoppingCart();

    //declare totalItems variable
    var totalItems = 0;

    // for each item add the total to the cart
    if(cart){
      cart.forEach((item: { quantity: number; })=>totalItems+=item.quantity)
    }
 
    //return total items
    return totalItems;
  }

  //remove item from cart
  removeProduct(productId: number, qty: number){
    const cart = this.loadShoppingCart();
    const findItem = cart?.findIndex((item)=> item.id===productId);

    if(findItem !== -1){

      const product = cart![findItem!];

      cart?.splice(findItem!, 1);

      //update total cost 
      const totalCost = this.loadTotalCost()- product.price*qty;

      //set new total 
      localStorage.setItem('totalCost', String(totalCost));
      
      //set cart
      localStorage.setItem('shoppingCart', JSON.stringify(cart));
      
    }


  }

  updateShoppingCart(product: Product, qty: number){
      this.modifyCart(product, qty);
      this.loadTotalCost();
  }

  modifyCart(product: Product, qty: number){
    //get the cart
    const cart = this.loadShoppingCart();

    if(cart){

      //if item exists return first index of item else -1
      const itemExist = cart.findIndex((item)=> item.id ===product.id);

      //if cart does not exist
      if(itemExist === -1){
        product.quantity= qty;

        //add item to cart
        cart.push(product);
      }else{

        //set new qty
        cart[itemExist].quantity=qty;
      }
    }
    
    //set updated cart
    localStorage.setItem('shoppingCart', JSON.stringify(cart));

  }

  loadTotalCost(){
    
     //get the cart from session
     const cart =this.loadShoppingCart();

     //declare totalItems variable
     var totalCost = 0;
 
     // for each item add the total to the totalCost
     if(cart){
       cart.forEach((item)=>totalCost += item.price*item.quantity)
     }
     
     //set new total 
     localStorage.setItem('totalCost', String(totalCost));
     
     //return total items
     return totalCost;
  }

  checkIfAnyQtyIsZero(){
    let cart = this.loadShoppingCart();

    if(cart){
      //if any item has zero qty , remove it
      cart = cart.filter(item => item.quantity !==0 );

      //update cart
      localStorage.setItem('shoppingCart', JSON.stringify(cart));
    }
  }

  createOrderToSendToServer(): PayPalOrder{
    let cart = this.loadShoppingCart();

    //modify the items description before adding the item to the cart
    //this is a fix for an paypal error stating the description is too long
    cart?.map(item=> item.description = item.description.slice(0, 115))
    
    // Only include the productsDTO field if the cart is not empty
    const productDTOs = cart!.length > 0 ? cart : [];

    const order: PayPalOrder ={
      "totalCost": Number(this.loadTotalCost()),

      // Spread in the productDTOs field
      ...(productDTOs && { productDTOs }), 
    }

    console.log(productDTOs)
    return order;
  }
}
