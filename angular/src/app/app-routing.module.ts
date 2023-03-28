import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CheckoutComponent } from './checkout/checkout.component';
import { MainHomeComponent } from './main-home/main-home.component';
import { ProductPageComponent } from './product-page/product-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/OneStopShop', pathMatch: 'full'},
  {path: 'OneStopShop', component: MainHomeComponent},
  {path: 'product/details/:id', component: ProductPageComponent },
  {path: 'checkout', component: CheckoutComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
