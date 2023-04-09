import { Product } from "./product";

export interface Order{
    paypalOrderId: string,
    amount: number,
    products: Product[],
    name: string,
    date: string,


}