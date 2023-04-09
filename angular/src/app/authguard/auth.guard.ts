import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { ShoppingCartService } from '../services/shoppingcart/shopping-cart.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private shoppingCartService: ShoppingCartService
    ){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      //check if token exists in session
      const token = sessionStorage.getItem('token');
      const shoppingCartTotal = this.shoppingCartService.getTotalItemsInShoppingCart();
    
      // If the customer is not logged in
      if(!token){

         // Return to home page
        this.router.navigate([''])
        return false;
        
        // If the customer is logged in and wants to access orderhistory
      }else if(state.url=='/orderhistory'){

        // Allow them to access
        return true;

        // In the event customer logs out and tries to go back
      }else if((state.url=="/checkout" && !token) || shoppingCartTotal==0){

        // Return to home page
        this.router.navigate([''])
        return false;
      }
      return true;
      
  }
  
}
