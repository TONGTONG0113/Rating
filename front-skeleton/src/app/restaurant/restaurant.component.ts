import { Component, OnInit } from "@angular/core"
import { HttpClient } from "@angular/common/http"

interface Restaurant {
  id: number
  title: string
  address: string
  openingHours: string
  imageUrl: string | null
  cuisine: string
  averageRating: number
  reviewCount: number
}

@Component({
  selector: "app-restaurant",
  templateUrl: "./restaurant.component.html",
  styleUrls: ["./restaurant.component.scss"],
})
export class RestaurantComponent implements OnInit {
  restaurants: Restaurant[] = []

  searchTerm = ""
  selectedCuisine = "All"

  loading = false
  errorMessage = ""

  failedImages = new Set<number>()

  private readonly apiUrl = "http://localhost:8080/api/restaurants"

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadRestaurants()
  }

  /**
   * Generate cuisine filters dynamically from backend data.
   * "All" is always displayed first.
   */
  get cuisines(): string[] {
    const values = this.restaurants
      .map((restaurant) => restaurant.cuisine)
      .filter((cuisine): cuisine is string => !!cuisine && cuisine.trim().length > 0)

    return ["All", ...Array.from(new Set(values)).sort((a, b) => a.localeCompare(b))]
  }

  /**
   * Number of restaurants available for each cuisine.
   */
  getCuisineCount(cuisine: string): number {
    if (cuisine === "All") {
      return this.restaurants.length
    }

    return this.restaurants.filter((restaurant) => restaurant.cuisine === cuisine).length
  }

  /**
   * Search and cuisine filters can work together.
   */
  get filteredRestaurants(): Restaurant[] {
    const term = this.searchTerm.trim().toLowerCase()

    return this.restaurants.filter((restaurant) => {
      const matchesSearch =
        !term || restaurant.title.toLowerCase().includes(term) || restaurant.address.toLowerCase().includes(term)

      const matchesCuisine = this.selectedCuisine === "All" || restaurant.cuisine === this.selectedCuisine

      return matchesSearch && matchesCuisine
    })
  }

  selectCuisine(cuisine: string): void {
    this.selectedCuisine = cuisine
  }

  clearFilters(): void {
    this.searchTerm = ""
    this.selectedCuisine = "All"
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
