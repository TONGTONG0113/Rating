import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";

import { MatListModule } from "@angular/material/list";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { HomeComponent } from "./home/home.component";
import { UsersComponent } from "./users/users.component";
import { RestaurantComponent } from "./restaurant/restaurant.component";
import { RestaurantDetailComponent } from "./restaurant-detail/restaurant-detail.component";
import { RestaurantRankingComponent } from "./restaurant-ranking/restaurant-ranking.component";

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    RestaurantComponent,
    RestaurantDetailComponent,
    RestaurantRankingComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    UsersComponent,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
