import { NgModule } from "@angular/core"
import { BrowserModule } from "@angular/platform-browser"
import { BrowserAnimationsModule } from "@angular/platform-browser/animations"
import { FormsModule } from "@angular/forms"
import { HttpClientModule } from "@angular/common/http"

import { MatListModule } from "@angular/material/list"
import { MatIconModule } from "@angular/material/icon"
import { MatButtonModule } from "@angular/material/button"

import { AppRoutingModule } from "./app-routing.module"
import { AppComponent } from "./app.component"
import { NavbarComponent } from "./navbar/navbar.component"
import { HomeComponent } from "./home/home.component"

import { StudentsComponent } from "students/students.component"
import { StudentDetailsComponent } from "students/student-details/student-details.component"

import { MajorsComponent } from "majors/majors.component"
import { MajorStudentsComponent } from "majors/major-students/major-students.component"

import { RestaurantComponent } from "./restaurant/restaurant.component"
import { RestaurantDetailComponent } from "./restaurant-detail/restaurant-detail.component"

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    StudentsComponent,
    StudentDetailsComponent,
    MajorsComponent,
    MajorStudentsComponent,
    RestaurantComponent,
    RestaurantDetailComponent,
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
    HomeComponent,
  ],

  providers: [],

  bootstrap: [AppComponent],
})
export class AppModule {}