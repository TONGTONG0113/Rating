import { Component } from "@angular/core"

@Component({
  selector: "app-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.scss"],
})
export class NavbarComponent {
  links = [
    {
      name: "Home",
      href: "/",
    },
    {
      name: "Restaurants",
      href: "/restaurants",
    },
    {
      name: "Reviews",
      href: "/reviews",
    },
    {
      name: "Users",
      href: "/users",
    },
  ]
}
