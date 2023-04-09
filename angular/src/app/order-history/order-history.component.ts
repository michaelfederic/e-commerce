import { Component, OnInit } from '@angular/core';
import { ECommerceService } from '../services/e-commerce.service';
import { Order } from '../models/order';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {
constructor(private ecommerce: ECommerceService){}
orders : Order[] = [];

  ngOnInit(): void {
    this.getMyOrders();

  }

  getMyOrders(){
    this.ecommerce.getMyOrders().subscribe({
      next:(orders: any)=>{
        console.log(orders)
        
        orders.forEach((order: { paypalOrderId: string; }) => this.getOrderDetail(order.paypalOrderId));
        
      },
      error:(error)=>{
        console.error(error)
      }
    })
  }

  getOrderDetail(orderId: string){
    this.ecommerce.getOrderDetails(orderId).subscribe({
      next: (orderDetails: any)=>{
       console.log(orderDetails)
       this.createOrderToAddToOrderList(orderDetails);
      },
      error: (error)=>{
        console.error("There a was an issue retrieving the order: ", error);
      }
    })
  }

  // Create the order object, add it to the orders list
  createOrderToAddToOrderList(orderDetails: any){
  
    var order: Order ={
      paypalOrderId: orderDetails.id ,
      amount: Number(orderDetails.purchase_units[0].amount.value),
      products: [],
      name: orderDetails.purchase_units[0].shipping.name.full_name,
      date: this.convertDate(orderDetails.purchase_units[0].payments.captures[0].create_time),
    }

    this.orders.push(order);
  }

  // Convert the date sent from the server to a human readable date ex. "March 05, 2020"
  convertDate(dateString: string){
    const date = new Date(dateString);
    const formattedDate = date.toLocaleDateString('en-US', {year: 'numeric', month:'long', day:'2-digit'});
    return formattedDate;
  }

}
