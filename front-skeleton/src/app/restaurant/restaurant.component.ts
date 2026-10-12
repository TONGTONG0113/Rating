import { Component, OnInit } from "@angular/core"
import { HttpClient } from "@angular/common/http"

interface Restaurant {
  id: number
  title: string
  address: string
  openingHours: string
  imageUrl: string | null
}

@Component({
  selector: "app-restaurant",
  templateUrl: "./restaurant.component.html",
  styleUrls: ["./restaurant.component.scss"],
})
export class RestaurantComponent implements OnInit {
  restaurants: Restaurant[] = []
  searchTerm = ""
  loading = false
  errorMessage = ""

  failedImages = new Set<number>()

  private readonly apiUrl = "http://localhost:8080/api/restaurants"

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadRestaurants()
  }

  get filteredRestaurants(): Restaurant[] {
    const term = this.searchTerm.trim().toLowerCase()

    if (!term) {
      return this.restaurants
    }

    return this.restaurants.filter(
      (restaurant) => restaurant.title.toLowerCase().includes(term) || restaurant.address.toLowerCase().includes(term),
    )
  }

  loadRestaurants(): void {
    this.loading = true
    this.errorMessage = ""

    this.http.get<Restaurant[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.restaurants = data
        this.failedImages.clear()
        this.loading = false
      },
      error: (error) => {
        console.error("Failed to load restaurants:", error)
        this.errorMessage = "Unable to load restaurants. Please check that the backend is running."
        this.loading = false
      },
    })
  }

  onImageError(restaurantId: number): void {
    this.failedImages.add(restaurantId)
  }
}
