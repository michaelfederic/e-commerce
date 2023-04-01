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

      if(!token){
         //return to home page
        this.router.navigate([''])
        return false;

        //in the event user logs out and tries to go back
      }else if((state.url=="/checkout" && !token) || shoppingCartTotal==0){

        //return to home page
        this.router.navigate([''])
        return false;
      }
      return true;
      
  }
  
}
