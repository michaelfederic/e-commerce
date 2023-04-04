import { Product } from "./product";

export interface PayPalOrder{
    productDTOs?: Product[];
    totalCost: number;
}