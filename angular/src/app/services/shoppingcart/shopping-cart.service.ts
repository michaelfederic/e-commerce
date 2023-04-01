import { Injectable } from '@angular/core';
import { Product } from 'src/app/models/product';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor() { }

   
  checkIfShoppingCartExists(){
    //create a shoppingCart inside session storage
    if(!sessionStorage.getItem('shoppingCart')){
      var shoppingCart: Product[] = [];
      sessionStorage.setItem('shoppingCart',JSON.stringify(shoppingCart))
      sessionStorage.setItem('totalCost', '0');
    }
  }

  updateShoppingCart(product: Product, isAdding: boolean){
    //get cart from session storage
    const cart = this.loadShoppingCart();
    
    if(cart){

      //if item exists return first index of item else -1
      const itemExist = cart.findIndex((item)=> item.id ===product.id);

      if(isAdding){
        if(itemExist === -1){
          product.quantity= 1;
          //add item to cart
          cart.push(product);
        }else{
          //add 1 to quantity
          cart[itemExist].quantity++;
        }
      }else if(itemExist !== -1){
        //subtract 1 from quantity
        cart[itemExist].quantity--;
      }
      
       //update shopping cart
       sessionStorage.setItem('shoppingCart', JSON.stringify(cart));
    }
    

    //update shopping cart
    sessionStorage.setItem('shoppingCart', JSON.stringify(cart));

    this.updateTotalCost(product,isAdding);
  }

  updateTotalCost(product: Product | null, isAddition: boolean){
    //get the cart from session
    const cart =this.loadShoppingCart();
   
    //convert from string to number total cost
    var totalCost = this.loadTotalCost();

    if(cart){
      //adding to quantity add to total else...
      if(isAddition && product){
        totalCost += product.price;

        //subtract from total
      }else if(!isAddition && product){

        totalCost -= product.price;
      }
      
    }
    sessionStorage.setItem('totalCost', String(totalCost));
}

  loadShoppingCart(){

    //set shopping cart
    const cartString =sessionStorage.getItem('shoppingCart');
    
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

  updateQuantityOfItem(productId: number, quantity: number, isAddition: boolean){
    //get the cart from session
    const cart =this.loadShoppingCart();

    if(cart){

      // find item and update quanity
      var itemExist = cart.findIndex((item)=>item.id===productId);
      cart[itemExist].quantity=quantity;

      this.updateTotalCost(cart[itemExist],isAddition);

      //update shopping cart
      sessionStorage.setItem('shoppingCart', JSON.stringify(cart));
    }

  }

  

  getItemQuantity(productId: number){
    //get the cart from session
    const cart =this.loadShoppingCart();

    if(cart){
      var index = cart.findIndex((item)=> item.id===productId);
      return cart[index].quantity;
    }
    return null;
  }


  loadTotalCost(){
    let totalCost = sessionStorage.getItem('totalCost');
    if(!totalCost){
      totalCost = "0";
    }
      //This fixes a floating-point arithmetic issue where the total cost may not be displayed
      // correctly when it reaches zero. We 
      //  - convert the totalCost to a number, round it to 2 decimal places using toFixed()
      //  - parse it back to a float using parseFloat().
      return parseFloat(Number(totalCost).toFixed(2));
    
  }

  
  //remove item from cart
  removeProduct(productId: number, qty: number){
    const cart = this.loadShoppingCart();
    const findItem = cart?.findIndex((item)=> item.id===productId);

    if(findItem !== -1){

      const product = cart![findItem!]

      cart?.splice(findItem!, 1);

      //update total cost 
      const totalCost = this.loadTotalCost()- product.price*qty;

      //set new total 
      sessionStorage.setItem('totalCost', String(totalCost));
      
      //set cart
      sessionStorage.setItem('shoppingCart', JSON.stringify(cart));
    }


  }
}
