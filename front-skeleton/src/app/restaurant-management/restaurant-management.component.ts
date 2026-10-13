import { CommonModule } from "@angular/common"
import { Component, OnInit } from "@angular/core"
import { FormsModule } from "@angular/forms"

import { Restaurant } from "../models/restaurant.model"
import { RestaurantService } from "../services/restaurant.service"

@Component({
  selector: "app-restaurant-management",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./restaurant-management.component.html",
  styleUrls: ["./restaurant-management.component.scss"],
})
export class RestaurantManagementComponent implements OnInit {
  restaurants: Restaurant[] = []

  formRestaurant: Restaurant = this.emptyRestaurant()

  editingRestaurantId?: number

  searchTerm = ""
  loading = false
  saving = false

  errorMessage = ""
  successMessage = ""

  constructor(private restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.loadRestaurants()
  }

  get filteredRestaurants(): Restaurant[] {
    const search = this.searchTerm.trim().toLowerCase()

    if (!search) {
      return this.restaurants
    }

    return this.restaurants.filter((restaurant) =>
      [
        restaurant.id?.toString() ?? "",
        restaurant.title ?? "",
        restaurant.address ?? "",
        restaurant.openingHours ?? "",
        restaurant.cuisine ?? "",
      ].some((value) => value.toLowerCase().includes(search)),
    )
  }

  loadRestaurants(): void {
    this.loading = true
    this.errorMessage = ""

    this.restaurantService.getAll().subscribe({
      next: (restaurants) => {
        this.restaurants = restaurants ?? []
        this.loading = false
      },
      error: (error) => {
        console.error("Unable to load restaurants", error)

        this.errorMessage = "Unable to load restaurants. Check that the backend is running."

        this.loading = false
      },
    })
  }

  saveRestaurant(): void {
    this.clearMessages()

    if (!this.formRestaurant.title.trim()) {
      this.errorMessage = "Restaurant title is required."
      return
    }

    if (!this.formRestaurant.cuisine.trim()) {
      this.errorMessage = "Cuisine is required."
      return
    }

    if (this.saving) {
      return
    }

    const restaurant: Restaurant = {
      title: this.formRestaurant.title.trim(),
      address: this.formRestaurant.address.trim(),
      openingHours: this.formRestaurant.openingHours.trim(),
      imageUrl: this.formRestaurant.imageUrl.trim(),
      cuisine: this.formRestaurant.cuisine.trim(),
    }

    this.saving = true

    if (this.editingRestaurantId !== undefined) {
      this.restaurantService.update(this.editingRestaurantId, restaurant).subscribe({
        next: () => {
          this.successMessage = "Restaurant updated successfully."
          this.saving = false
          this.resetForm(false)
          this.loadRestaurants()
        },
        error: (error) => {
          console.error("Unable to update restaurant", error)

          this.errorMessage = error.status === 404 ? "Restaurant not found." : "Unable to update restaurant."

          this.saving = false
        },
      })
    } else {
      this.restaurantService.create(restaurant).subscribe({
        next: () => {
          this.successMessage = "Restaurant created successfully."
          this.saving = false
          this.resetForm(false)
          this.loadRestaurants()
        },
        error: (error) => {
          console.error("Unable to create restaurant", error)

          this.errorMessage = error.status === 400 ? "Invalid restaurant information." : "Unable to create restaurant."

          this.saving = false
        },
      })
    }
  }

  startEdit(restaurant: Restaurant): void {
    if (restaurant.id === undefined) {
      return
    }

    this.clearMessages()
    this.editingRestaurantId = restaurant.id

    this.formRestaurant = {
      id: restaurant.id,
      title: restaurant.title ?? "",
      address: restaurant.address ?? "",
      openingHours: restaurant.openingHours ?? "",
      imageUrl: restaurant.imageUrl ?? "",
      cuisine: restaurant.cuisine ?? "",
    }

    document.getElementById("restaurant-management-form")?.scrollIntoView({ behavior: "smooth", block: "start" })
  }

  cancelEdit(): void {
    this.resetForm()
  }

  deleteRestaurant(restaurant: Restaurant): void {
    if (restaurant.id === undefined) {
      return
    }

    const confirmed = confirm(`Are you sure you want to delete "${restaurant.title}"?`)

    if (!confirmed) {
      return
    }

    this.clearMessages()

    this.restaurantService.delete(restaurant.id).subscribe({
      next: () => {
        this.successMessage = "Restaurant deleted successfully."

        if (this.editingRestaurantId === restaurant.id) {
          this.resetForm(false)
        }

        this.loadRestaurants()
      },
      error: (error) => {
        console.error("Unable to delete restaurant", error)

        this.errorMessage = error.status === 404 ? "Restaurant not found." : "Unable to delete restaurant."
      },
    })
  }

  refreshRestaurants(): void {
    this.clearMessages()
    this.loadRestaurants()
  }

  resetForm(clearMessages = true): void {
    this.formRestaurant = this.emptyRestaurant()
    this.editingRestaurantId = undefined

    if (clearMessages) {
      this.clearMessages()
    }
  }

  clearMessages(): void {
    this.errorMessage = ""
    this.successMessage = ""
  }

  private emptyRestaurant(): Restaurant {
    return {
      title: "",
      address: "",
      openingHours: "",
      imageUrl: "",
      cuisine: "",
    }
  }
}
