import { Component } from "@angular/core"

interface Restaurant {
  id: number
  name: string
  category: string
  price: string
  rating: number
  reviews: number
  address: string
  description: string
  image: string
}

@Component({
  selector: "app-restaurant",
  standalone: false,
  templateUrl: "./restaurant.component.html",
  styleUrls: ["./restaurant.component.scss"],
})
export class RestaurantComponent {
  searchTerm = ""
  selectedCategory = "Tous"
  selectedPrice = "Tous"
  selectedRating = 0

  categories = ["Tous", "Français", "Italien", "Japonais", "Chinois", "Indien", "Fast Food"]

  prices = ["Tous", "€", "€€", "€€€"]

  restaurants: Restaurant[] = [
    {
      id: 1,
      name: "Le Petit Paris",
      category: "Français",
      price: "€€",
      rating: 4.8,
      reviews: 126,
      address: "Paris 5e",
      description: "Cuisine française traditionnelle et produits frais.",
      image: "https://images.unsplash.com/photo-1515003197210-e0cd71810b5f?auto=format&fit=crop&w=800&q=80",
    },

    {
      id: 2,
      name: "Tokyo House",
      category: "Japonais",
      price: "€€",
      rating: 4.6,
      reviews: 98,
      address: "Paris 8e",
      description: "Cuisine japonaise authentique et raffinée.",
      image: "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?auto=format&fit=crop&w=800&q=80",
    },

    {
      id: 3,
      name: "Bella Italia",
      category: "Italien",
      price: "€€",
      rating: 4.7,
      reviews: 154,
      address: "Paris 11e",
      description: "Pizzas et spécialités italiennes faites maison.",
      image: "https://images.unsplash.com/photo-1579684947550-22e945225d9a?auto=format&fit=crop&w=800&q=80",
    },

    {
      id: 4,
      name: "Dragon d'Or",
      category: "Chinois",
      price: "€€",
      rating: 4.5,
      reviews: 87,
      address: "Paris 13e",
      description: "Une cuisine chinoise traditionnelle et généreuse.",
      image: "https://images.unsplash.com/photo-1563245372-f21724e3856d?auto=format&fit=crop&w=800&q=80",
    },

    {
      id: 5,
      name: "Bombay Palace",
      category: "Indien",
      price: "€€€",
      rating: 4.4,
      reviews: 76,
      address: "Paris 10e",
      description: "Saveurs indiennes authentiques et épices traditionnelles.",
      image: "https://images.unsplash.com/photo-1585937421612-70a008356fbe?auto=format&fit=crop&w=800&q=80",
    },

    {
      id: 6,
      name: "Burger Factory",
      category: "Fast Food",
      price: "€",
      rating: 4.2,
      reviews: 203,
      address: "Paris 1er",
      description: "Burgers gourmands préparés avec des produits frais.",
      image: "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=800&q=80",
    },
  ]

  get filteredRestaurants(): Restaurant[] {
    return this.restaurants.filter((restaurant) => {
      const search = this.searchTerm.toLowerCase().trim()

      const matchesSearch =
        restaurant.name.toLowerCase().includes(search) ||
        restaurant.category.toLowerCase().includes(search) ||
        restaurant.address.toLowerCase().includes(search)

      const matchesCategory = this.selectedCategory === "Tous" || restaurant.category === this.selectedCategory

      const matchesPrice = this.selectedPrice === "Tous" || restaurant.price === this.selectedPrice

      const matchesRating = restaurant.rating >= this.selectedRating

      return matchesSearch && matchesCategory && matchesPrice && matchesRating
    })
  }

  resetFilters(): void {
    this.searchTerm = ""

    this.selectedCategory = "Tous"

    this.selectedPrice = "Tous"

    this.selectedRating = 0
  }
}
