import { NgModule } from "@angular/core"
import { RouterModule, Routes } from "@angular/router"

import { HomeComponent } from "./home/home.component"
import { UsersComponent } from "./users/users.component"
import { RestaurantComponent } from "./restaurant/restaurant.component"
import { RestaurantDetailComponent } from "./restaurant-detail/restaurant-detail.component"

const routes: Routes = [
  {
    path: "",
    component: HomeComponent,
  },
  {
    path: "users",
    component: UsersComponent,
  },
  {
    path: "restaurants",
    component: RestaurantComponent,
  },
  {
    path: "restaurants/:id",
    component: RestaurantDetailComponent,
  },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}