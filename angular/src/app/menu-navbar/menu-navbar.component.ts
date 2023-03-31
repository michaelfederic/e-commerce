import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Product } from '../models/product';

@Component({
  selector: 'app-menu-navbar',
  templateUrl: './menu-navbar.component.html',
  styleUrls: ['./menu-navbar.component.css']
})
export class MenuNavbarComponent implements OnInit {
  shoppingCart: Product[]=[]
  itemsInCart: number=0;
  totalCost: number = 0;
  constructor(private modalService: NgbModal){}


  ngOnInit(): void {
    //set shopping cart
    const cart =sessionStorage.getItem('shoppingCart');
    this.shoppingCart = JSON.parse(cart!);
    this.itemsInCart = this.shoppingCart.length;

    //set total cost
    const totalCost = Number(sessionStorage.getItem('totalCost'));
    this.totalCost = totalCost;

  }

  openFullscreen(content: any){
    this.modalService.open(content, {fullscreen: true})
  }

 
}
