import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { Router } from "@angular/router"

@Component({
  selector: "app-home",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./home.component.html",
  styleUrl: "./home.component.scss",
})
export class HomeComponent {
  searchTerm = ""
  location = "Paris"

  cuisines = ["Français", "Italien", "Japonais", "Chinois", "Indien", "Fast Food"]

  constructor(private router: Router) {}

  searchRestaurants(): void {
    this.router.navigate(["/restaurants"], {
      queryParams: {
        search: this.searchTerm,
        location: this.location,
      },
    })
  }

  searchCuisine(cuisine: string): void {
    this.router.navigate(["/restaurants"], {
      queryParams: {
        category: cuisine,
        location: this.location,
      },
    })
  }
}
