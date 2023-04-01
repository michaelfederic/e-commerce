import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './authguard/auth.guard';
import { CheckoutComponent } from './checkout/checkout.component';
import { MainHomeComponent } from './main-home/main-home.component';
import { ProductPageComponent } from './product-page/product-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/OneStopShop', pathMatch: 'full'},
  { path: 'OneStopShop', component: MainHomeComponent },
  { path: 'product/details/:id', component: ProductPageComponent },

  //secure checkout path if user is not logged in
  { path: 'checkout', component: CheckoutComponent, canActivate: [AuthGuard]},

  { path: '**', redirectTo: 'OneStopShop' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
