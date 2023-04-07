import { Product } from './product';
export interface CustomerCart{
    customerId: number,
    productDTOs: Product[],
    totalCost: number
}